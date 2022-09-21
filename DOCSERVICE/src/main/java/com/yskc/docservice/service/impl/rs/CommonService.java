package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.RedisUtils;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.DeptDao;
import com.yskc.docservice.dao.rs.RdDeptDao;
import com.yskc.docservice.dao.rs.WorkshopDao;
import com.yskc.docservice.dao.rs.project.AuditRdFeeDao;
import com.yskc.docservice.dao.rs.project.ProjectAuditDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.entity.rs.project.ProjectAuditEntity;
import com.yskc.docservice.enums.AuditRdTypeEnum;
import com.yskc.docservice.enums.OrgEnum;
import com.yskc.docservice.enums.RsProjectStatusEnum;
import com.yskc.docservice.models.rs.CheckStatusModel;
import com.yskc.docservice.models.rs.CommonOrgModel;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.project.AuditRdFeeModel;
import com.yskc.docservice.models.rs.project.RdStatusModel;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;

/**
 * 公共服务
 *
 * @author huronghua
 */
@Service
public class CommonService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private RdDeptDao rdDeptDao;
    @Autowired
    private WorkshopDao workshopDao;
    @Autowired
    private ProjectAuditDao projectAuditDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private AuditRdFeeDao auditRdFeeDao;


    /**
     * 初始组织全路径map
     *
     * @param companyId
     * @param org
     * @param year
     * @return
     */
    public Map<String, Integer> initOrgFullPathMap(Integer companyId, OrgEnum org, Integer year) {
        List<CommonOrgModel> orgList = null;
        Boolean isDept = null;
        switch (org) {
            case DEPT:
                orgList = deptDao.getCompanyOrg(companyId);
                isDept = true;
                break;
            case RD_DEPT:
                orgList = rdDeptDao.getCompanyOrg(companyId, year);
                isDept = false;
                break;
            case WORKSHOP:
                orgList = workshopDao.getCompanyOrg(companyId);
                isDept = false;
                break;
            default:
                isDept = false;
                break;
        }
        Map<String, Integer> result = new HashMap<>();
        if (CollectionUtils.isEmpty(orgList)) {
            return result;
        }
        Map<Integer, String> nameMap = new HashMap<>();
        List<String> fullPathList = new ArrayList<>();
        orgList.forEach(item -> {
            if (null == item.getFullPath()) {
                return;
            }
            nameMap.put(item.getId(), item.getName().trim());
            fullPathList.add(item.getFullPath());
        });
        int begin = isDept ? 1 : 0;
        fullPathList.forEach(item -> {
            String[] idStrArr = item.split(Constant.PATH_SEPARATOR);
            Integer currentId = null;
            StringBuffer buffer = new StringBuffer("");
            for (int i = begin; i < idStrArr.length; i++) {
                if (!StringUtils.isEmpty(idStrArr[i])) {
                    currentId = Integer.valueOf(idStrArr[i]);
                    if (nameMap.containsKey(currentId)) {
                        buffer.append(Constant.PATH_SEPARATOR + nameMap.get(currentId));
                    }
                }
            }
            if (buffer.length() > 1) {
                result.put(buffer.toString(), currentId);
            }
        });
        return result;
    }

    /**
     * 是否可保存
     *
     * @param companyId
     * @param year
     * @param mode
     * @return
     * @throws OwnerException
     */
    public void checkAuditModify(Integer companyId, Integer year, AuditModeEnum mode, int userSource) throws OwnerException {
        if (userSource == 0) {
            return;
        }
        ProjectAuditEntity entity = getAudit(companyId, year, mode);
        if (entity != null && !FlowInstanceStatusEnum.canModify(entity.getStatus())) {
            throw new OwnerException("操作失败，已提交审核。");
        }
    }


    public ProjectAuditEntity getAudit(Integer companyId, Integer year, AuditModeEnum mode) throws OwnerException {
        if (null == year) {
            throw new OwnerException("操作失败，获取年份失败，请联系管理员。");
        }
        return projectAuditDao.getAudit(companyId, year, mode.getModuleId());
    }

//    public Boolean checkStatus(Integer projectId, Date month) throws OwnerException {
//        String monthStr = DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT);
//        Map<String, Integer> map = getStatusMap(projectId);
//        if (map.getOrDefault(monthStr, 0).equals(RsProjectStatusEnum.FINALIZATION.getStatus())) {
//            throw new OwnerException("操作失败，" + monthStr + "已审核。");
//        }
//        return true;
//    }

    public Boolean checkStatus(Set<Integer> projectIds, Date month, List<Integer> rdTypes,RsUserInfo info) throws OwnerException {
        if (info.getUserSource() < 1) {
            return true;
        }
        List<CheckStatusModel> list = new ArrayList<>();
        projectIds.forEach(item->{
            CheckStatusModel model = new CheckStatusModel(item,month);
            list.add(model);
        });
        List<Integer> parents = AuditRdTypeEnum.getParents(rdTypes);
        if (!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(parents)){
            List<AuditRdFeeModel> modelList = auditRdFeeDao.getStatusList(list, AuditRdTypeEnum.getParents(rdTypes));
            if (!CollectionUtils.isEmpty(modelList)) {
                for (AuditRdFeeModel model : modelList) {
                    Boolean aBoolean = FlowInstanceStatusEnum.canModify(model.getStatus());
                    if (!aBoolean) {
                        throw new OwnerException("操作失败，" + model.getRdTitle() + "已审核。");
                    }
                }
            }
        }
        return true;
    }

    public Boolean checkStatus(List<CheckStatusModel> models, List<Integer> rdTypes, RsUserInfo info) throws OwnerException {
        if (info.getUserSource() < 1) {
            return true;
        }
        List<Integer> parents = AuditRdTypeEnum.getParents(rdTypes);
        if (!CollectionUtils.isEmpty(models) && !CollectionUtils.isEmpty(parents)){
            List<AuditRdFeeModel> modelList = auditRdFeeDao.getStatusList(models, AuditRdTypeEnum.getParents(rdTypes));
            if (!CollectionUtils.isEmpty(modelList)) {
                for (AuditRdFeeModel model : modelList) {
                    Boolean aBoolean = FlowInstanceStatusEnum.canModify(model.getStatus());
                    if (!aBoolean) {
                        String monthStr = DateUtil.format(model.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT);
                        throw new OwnerException("操作失败，" + model.getRdTitle() + "月份"+ monthStr + "已审核。");
                    }
                }
            }
        }
        return true;
    }
    public Boolean checkStatus(Integer projectId, Set<Date> months, List<Integer> rdTypes,RsUserInfo info) throws OwnerException {
        if (info.getUserSource() < 1) {
            return true;
        }
        List<CheckStatusModel> list = new ArrayList<>();
        months.forEach(item->{
            CheckStatusModel model = new CheckStatusModel(projectId,item);
            list.add(model);
        });
        List<Integer> parents = AuditRdTypeEnum.getParents(rdTypes);
        if (!CollectionUtils.isEmpty(list) && !CollectionUtils.isEmpty(parents)) {
            List<AuditRdFeeModel> modelList = auditRdFeeDao.getStatusList(list, AuditRdTypeEnum.getParents(rdTypes));
            if (!CollectionUtils.isEmpty(modelList)) {
                for (AuditRdFeeModel model : modelList) {
                    Boolean aBoolean = FlowInstanceStatusEnum.canModify(model.getStatus());
                    if (!aBoolean) {
                        String monthStr = DateUtil.format(model.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT);
                        throw new OwnerException("操作失败，" + monthStr + "已审核。");
                    }
                }
            }
        }
        return true;
    }
    public Map<String, Integer> getStatusMap(Integer projectId) {
        Map<String, Integer> result;
        String key = MessageFormat.format(Constant.REDIS_KEY_PROJECT_STATUS, projectId);
        if (redisUtils.hasKey(key)) {
            result = redisUtils.get(key, HashMap.class);
            redisUtils.expire(key, Constant.RS_PROJECT_STATUS_TIMEOUT);
        } else {
            result = new HashMap<>();
            List<RdStatusModel> statusList = projectDao.getRdsStatus(CollUtil.newArrayList(projectId));
            if (!CollectionUtils.isEmpty(statusList)) {
                statusList.forEach(item -> {
                    result.put(DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT), item.getStatus());
                });
            }
            redisUtils.set(key, result, Constant.RS_PROJECT_STATUS_TIMEOUT);
        }
        return result;
    }

}
