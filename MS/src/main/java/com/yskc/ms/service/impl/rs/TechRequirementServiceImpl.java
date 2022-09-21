package com.yskc.ms.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.ProjectDao;
import com.yskc.ms.dao.ms.UserDao;
import com.yskc.ms.dao.ms.UserDeptDao;
import com.yskc.ms.dao.rs.TechRequirementDao;
import com.yskc.ms.entity.rs.TechRequirement;
import com.yskc.ms.enums.TechRequirementEnum;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.techrequirement.QueryTechRequirement;
import com.yskc.ms.models.techrequirement.TechRequirementModel;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.service.rs.TechRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-21 09:22
 * @Description: 技术需求业务实现层
 */
@Service
public class TechRequirementServiceImpl implements TechRequirementService {

    @Autowired
    private TechRequirementDao techRequirementDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDeptDao userDeptDao;

    @Override
    public PageModel<List<TechRequirementModel>> getList(QueryTechRequirement query, DataPermModel perm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(CollUtil.newArrayList("lastUpdateTime"));
        }
//        List<Integer> companyIds = null;
//        if (perm.getPermType() < 2) {
//            companyIds = projectDao.getOwnCompanyId(perm);
//        }
        List<Integer> uIds=null;
        if(perm.getPermType() == 0){
            uIds = CollUtil.newArrayList(perm.getUserId());
        }else if(perm.getPermType() == 1){
            uIds = userDeptDao.getUserIdByFullPath(perm.getDeptPaths());
        }
        List<TechRequirementModel> data = techRequirementDao.getList(page, query,uIds);
        if (!CollectionUtils.isEmpty(data)) {
            Set<Integer> userIds = new HashSet<>();
            data.forEach(item -> {
                userIds.add(item.getMsCreatorId());
                userIds.add(item.getMsLastUpdatorId());
            });
            List<MiniUserModel> userList = userDao.getUsers(userIds);
            if (!CollectionUtils.isEmpty(userList)) {
                Map<Integer, String> realNameMap = new HashMap<>();
                userList.forEach(item -> realNameMap.put(item.getId(), item.getRealName()));
                data.forEach(item -> {
                    item.setCreateName(realNameMap.get(item.getMsCreatorId()));
                    item.setUpdateName(realNameMap.get(item.getMsLastUpdatorId()));
                });
            }
        }
        return PageUtils.build(page, data);
    }

    @Override
    public Boolean add(TechRequirement techRequirement, UserInfo userInfo) {
        Date now = new Date();
        if (techRequirement.getDescription() == null) {
            techRequirement.setDescription("");
        }
        techRequirement.setStatus(TechRequirementEnum.NORMAL.getStatus());
        techRequirement.setCreateTime(now);
        techRequirement.setCreatorId(-1);
        techRequirement.setLastUpdatorId(-1);
        techRequirement.setLastUpdateTime(now);
        techRequirement.setMsCreatorId(userInfo.getId());
        techRequirement.setMsLastUpdatorId(userInfo.getId());
        return techRequirementDao.insert(techRequirement) > 0;
    }

    @Override
    public Boolean update(TechRequirement requirement, UserInfo userInfo) throws OwnerException {
        Integer id = techRequirementDao.checkExistId(requirement.getId());
        if (id == null) {
            throw new OwnerException(MessageFormat.format("技术需求名称：[{0}]不存在，请稍后再试。", requirement.getTechName()));
        }
        if (requirement.getDescription() == null) {
            requirement.setDescription("");
        }
        requirement.setStatus(TechRequirementEnum.NORMAL.getStatus());
        requirement.setLastUpdatorId(-1);
        requirement.setMsLastUpdatorId(userInfo.getId());
        requirement.setLastUpdateTime(new Date());
        return techRequirementDao.updateById(requirement) > 0;
    }

    @Override
    public Boolean delete(BatchDeleteModel deleteModel) {
        return techRequirementDao.deleteBatchIds(deleteModel.getIds()) > 0;
    }

    @Override
    public String getFilePath(Integer id) {
        return techRequirementDao.getFilePath(id);
    }

    @Override
    public boolean invalid(UserInfo userInfo, BatchDeleteModel batchModel) {
        return techRequirementDao.updateStatus(
                batchModel.getIds(), -1, userInfo.getId(),
                new Date(), TechRequirementEnum.INVALID.getStatus()) > 0;
    }

    @Override
    public Map<String, Object> exportWord(Integer id, UserInfo userInfo) throws OwnerException {
        TechRequirementModel model = techRequirementDao.getById(id);
        if (model != null) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("companyName", model.getCompanyName());
            dataMap.put("address", model.getCompanyAddress());
            dataMap.put("linkman", model.getLinkName());
            dataMap.put("email", model.getLinkEmail());
            dataMap.put("phone", model.getLinkTel());
            dataMap.put("position", model.getPosition());
            dataMap.put("description", model.getDescription());
            dataMap.put("requirement", model.getRequirement());
            dataMap.put("techName", model.getTechName());
            List<String> domainList = Arrays.asList(model.getDomain().split("，"));
            Map<String,Boolean> domainMap=getMap(domainList,1);
            List<String> typeList=Arrays.asList(model.getCooperateType().split("，"));
            Map<String,Boolean> cooperateMap=getMap(typeList,2);
            dataMap.putAll(domainMap);
            dataMap.putAll(cooperateMap);
            dataMap.put("otherDomain",model.getOtherDomain());
            dataMap.put("otherType",model.getOtherCooperateType());
            dataMap.put("investment",model.getInvestment());
            return dataMap;
        }
        throw new OwnerException("技术需求不存在或已删除，导出失败！");
    }

    private Map<String, Boolean> getMap(List<String> strList,Integer sign) {
        Map<String,Boolean> resultMap=new HashMap<>();
        Map<String, Integer> map = new HashMap<>();
        Map<String,String> paramMap=new HashMap<>();
        for (String str:strList) {
            paramMap.put(str,str);
        }
        //电子信息  生物医药  新材料  装备制造  能源环保  现代农业  医疗器械  其他
        if(sign==1) {
            map.put("电子信息", 0);
            map.put("生物医药", 1);
            map.put("新材料", 2);
            map.put("装备制造", 3);
            map.put("能源环保", 4);
            map.put("现代农业", 5);
            map.put("医疗器械", 6);
            map.put("其他", 7);
        }else {
            //技术开发  技术转让  技术服务  技术入股  共同载体  其他
            map.put("技术开发", 0);
            map.put("技术转让", 1);
            map.put("技术服务", 2);
            map.put("技术入股", 3);
            map.put("共同载体", 4);
            map.put("其他", 5);
        }
        Set<String> set=map.keySet();
        for (String str:set) {
            String message;
            if(sign==1) {
                message = MessageFormat.format("type{0}_chk", map.get(str));
            }else {
                message=MessageFormat.format("techType{0}_chk", map.get(str));
            }
           if(paramMap.containsKey(str)){
               resultMap.put(message,true);
           }else {
               resultMap.put(message,false);
           }
        }
        return resultMap;
    }
}
