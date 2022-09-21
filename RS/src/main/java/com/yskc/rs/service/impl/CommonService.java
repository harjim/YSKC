package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.RedisUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.DeptDao;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.RdDeptDao;
import com.yskc.rs.dao.WorkshopDao;
import com.yskc.rs.dao.project.AuditDocFileDao;
import com.yskc.rs.dao.project.AuditRdFeeDao;
import com.yskc.rs.dao.project.ProjectAuditDao;
import com.yskc.rs.dao.project.ProjectStateDao;
import com.yskc.rs.entity.Dept;
import com.yskc.rs.entity.project.AuditDocFileEntity;
import com.yskc.rs.entity.project.AuditRdFeeEntity;
import com.yskc.rs.entity.project.ProjectAuditEntity;
import com.yskc.rs.entity.project.ProjectStateEntity;
import com.yskc.rs.enums.AuditRdTypeEnum;
import com.yskc.rs.enums.OrgEnum;
import com.yskc.rs.enums.RsProjectStatusEnum;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.CommonOrgModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.project.AuditRdFeeModel;
import com.yskc.rs.models.project.RdStatusModel;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.TransactionUtils;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    private ProjectDao projectDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ProjectAuditDao projectAuditDao;
    @Autowired
    private ProjectStateDao projectStateDao;
    @Autowired
    private AuditDocFileDao auditDocFileDao;
    @Autowired
    private AuditRdFeeDao auditRdFeeDao;


    /**
     * 初始化部门，没有就创建，并返回部门信息
     * 返回的部门信息包含id
     *
     * @param info
     * @param deptNameList
     * @param remark
     * @return 部门信息
     * @throws OwnerException
     */
    public List<Dept> initializeDept(UserInfo info, List<String> deptNameList, String remark) throws OwnerException {
        try {
            List<Dept> insertDeptList = new ArrayList<>();
            Dept parentDept = deptDao.selectParentDept(info.getCompanyId());
            List<Dept> deptList = deptNameList.size() == 0 ? new ArrayList<>() : deptDao.selectByNames(info.getCompanyId(), deptNameList);
            List<String> hasDeptNameList = deptList.stream().map(Dept::getDeptName).collect(Collectors.toList());
            deptNameList.removeAll(hasDeptNameList);
            HashSet hashSet = new HashSet(deptNameList);
            deptNameList.clear();
            deptNameList.addAll(hashSet);
            if (deptNameList.size() > 0) {
                deptNameList.forEach(deptName -> {
                    insertDeptList.add(setDept(info, deptName, parentDept == null ? -1 : parentDept.getId(), remark));
                });
            }
            if (insertDeptList.size() > 0) {
                if (insertDeptList.size() > 0) {
                    TransactionStatus transactionStatus = TransactionUtils.newTransaction();
                    try {
                        List<List<Dept>> insertList = ListUtils.subList(insertDeptList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                        for (List<Dept> items : insertList) {
                            deptDao.addBatch(items);
                            for (Dept d : items) {
                                d.setFullPath(null == parentDept ? d.getId() + Constant.PATH_SEPARATOR : parentDept.getFullPath() + d.getId() + Constant.PATH_SEPARATOR);
                            }
                            deptDao.updateList(items);
                        }
                        TransactionUtils.commit(transactionStatus);
                    } catch (Exception ex) {
                        TransactionUtils.rollback(transactionStatus);
                        logger.error("initializeDept", ex);
                        throw new OwnerException(ErrorEnum.FAIL);
                    }
                }
            }
            deptList.addAll(insertDeptList);
            return deptList;
        } catch (Exception ex) {
            logger.error("", ex);
            throw new OwnerException("添加部门出错啦");
        }
    }

    /**
     * 初始化部门，并返回map
     *
     * @param info
     * @param deptNameList
     * @param remark
     * @return
     * @throws OwnerException
     */
    public Map<String, Dept> initializeDeptMap(UserInfo info, List<String> deptNameList, String remark) throws OwnerException {
        List<Dept> insertDeptList = initializeDept(info, deptNameList, remark);
        Map<String, List<Dept>> deptListMap = insertDeptList.stream().collect(Collectors.groupingBy(Dept::getDeptName));
        Map<String, Dept> deptMap = new HashMap<>();
        for (String key : deptListMap.keySet()) {
            List<Dept> deptList = deptListMap.get(key);
            if (!deptList.isEmpty()) {
                deptMap.put(key, deptList.get(0));
            }
        }
        return deptMap;
    }

    /**
     * @param userInfo
     * @param deptName
     * @param parentId
     * @param remark
     * @return
     */
    private Dept setDept(UserInfo userInfo, String deptName, Integer parentId, String remark) {
        Dept deptModel = new Dept();
        deptModel.setIdentity("");
        deptModel.setCompanyId(userInfo.getCompanyId());
        deptModel.setDeptName(deptName);
        deptModel.setParentId(parentId);
        deptModel.setLevel(1);
        deptModel.setRemark(remark);
        deptModel.setCreateTime(new Date());
        deptModel.setLastUpdateTime(new Date());
        deptModel.setCreatorId(userInfo.getUserId());
        deptModel.setLastUpdatorId(userInfo.getUserId());
        deptModel.setMsCreatorId(userInfo.getMsUserId());
        deptModel.setMsLastUpdatorId(userInfo.getMsUserId());
        return deptModel;
    }

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
    public Boolean checkStatus(Integer projectId, Date month) throws OwnerException {
        String monthStr = DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT);
        Map<String, Integer> map = getStatusMap(projectId);
        if (map.getOrDefault(monthStr, 0).equals(RsProjectStatusEnum.FINALIZATION.getStatus())) {
            throw new OwnerException("操作失败，" + monthStr + "已审核。");
        }
        return true;
    }

    public Boolean checkStatus(List<CheckStatusModel> models,List<Integer> rdTypes,UserInfo info) throws OwnerException {
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

    public Boolean checkStatus(Set<Integer> projectIds, Date month, List<Integer> rdTypes,UserInfo info) throws OwnerException {
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

    public Boolean checkStatus(Integer projectId, Set<Date> months, List<Integer> rdTypes,UserInfo info) throws OwnerException {
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

    /**
     * 判断项目是否结项
     *
     * @param projectId
     */
    public void checkProjectState(Integer projectId) throws OwnerException {
        ProjectStateEntity projectState = projectStateDao.getByProject(projectId);
        if (projectState != null && projectState.getStatus() == 1) {
            throw new OwnerException("项目已结项，操作失败");
        }
    }

    /**
     * 判断过程文件审核状态
     *
     * @param projectId
     * @param docFileId
     * @return
     */
    public void checkDocModify(Integer userSource, Integer projectId, Integer docFileId) throws OwnerException {
        if (userSource == 0) {
            return;
        }
        List<AuditDocFileEntity> auditDocFile = auditDocFileDao.getDocAuditStatus(projectId, Arrays.asList(docFileId));
        if (CollectionUtils.isEmpty(auditDocFile)) {
            return;
        }
        for (AuditDocFileEntity audit : auditDocFile) {
            if (!FlowInstanceStatusEnum.canModify(audit.getStatus())) {
                throw new OwnerException("操作失败，当前文档已提交审核！");
            }
        }
    }
}
