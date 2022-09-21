package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.google.common.base.Joiner;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.WorkshopDao;
import com.yskc.rs.entity.WorkshopEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.DeptExcel;
import com.yskc.rs.models.excel.WorkshopExcel;
import com.yskc.rs.models.workshop.WorkshopModel;
import com.yskc.rs.models.workshop.WorkshopTreeModel;
import com.yskc.rs.service.WorkshopService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: wangxing
 * @CreateTime: 2019-11-21 09:18
 * @Description: 工艺线/车间业务层
 */
@Service
public class WorkshopServiceImpl implements WorkshopService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WorkshopDao workshopDao;

    @Override
    public List<WorkshopModel> queryWorkshop(Integer companyId, String workshopName) {
        List<WorkshopModel> workshopModels = workshopDao.queryWorkshop(companyId, workshopName);
        if (CollectionUtils.isEmpty(workshopModels)) {
            return workshopModels;
        }
        return recurrenceData(workshopModels);
    }


    public List<WorkshopModel> recurrenceData(List<WorkshopModel> modelList) {
        Map<Integer, List<WorkshopModel>> listMap = new HashMap<>();
        modelList.forEach(e -> {
            int parentId = e.getParentId();
            if (!listMap.containsKey(parentId)) {
                listMap.put(parentId, new ArrayList<>());
            }
            listMap.get(parentId).add(e);
        });
        List<WorkshopModel> result = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();
        for (Integer key : listMap.keySet()) {
            if (ids.contains(key)) {
                continue;
            }
            ids.add(key);
            List<WorkshopModel> temp = recurrenceData(listMap, key, ids);
            if (!CollectionUtils.isEmpty(temp)) {
                result.addAll(temp);
            } else {
                result.addAll(listMap.get(key));
            }
        }
        return result;
    }

    private static List<WorkshopModel> recurrenceData(Map<Integer, List<WorkshopModel>> parentGroup, Integer parentId, Set<Integer> ids) {
        List<WorkshopModel> group = parentGroup.get(parentId);
        if (CollectionUtils.isEmpty(group)) {
            return null;
        }
        List<WorkshopModel> result = new ArrayList<>();
        for (WorkshopModel model : group) {
            if (ids.contains(model.getId())) {
                continue;
            }
            ids.add(model.getId());
            List<WorkshopModel> temp = recurrenceData(parentGroup, model.getId(), ids);
            if (!CollectionUtils.isEmpty(temp)) {
                model.setChildren(temp);
            }
            result.add(model);
        }
        return result;
    }

    @Override
    public List<WorkshopTreeModel> getWorkshopTree(Integer companyId) {
        List<WorkshopTreeModel> pulldownData = workshopDao.getWorkshopTree(companyId);
        WorkshopTreeModel model = new WorkshopTreeModel();
        model.setKey("");
        model.setTitle("根节点");
        model.setParentId(-2);
        model.setValue("-1");
        pulldownData.add(model);
        return pulldownData;
    }

    @Override
    public boolean addWorkshop(UserInfo info, WorkshopModel model) throws OwnerException {
        WorkshopEntity entity = workshopDao.getSameLevelShop(info.getCompanyId(), model.getParentId(), model.getWorkshopName());
        if (null != entity) {
            throw new OwnerException("同级存在相同的工艺线/车间，请输入其他工艺线/车间");
        }
        entity = new WorkshopEntity();
        BeanUtil.copyProperties(model, entity);
        entity.setCompanyId(info.getCompanyId());
        entity.setCreateTime(new Date());
        entity.setCreatorId(info.getUserId());
        entity.setMsCreatorId(info.getMsUserId());
        entity.setMsLastUpdatorId(info.getMsUserId());
        entity.setLastUpdateTime(new Date());
        entity.setLastUpdatorId(info.getUserId());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            workshopDao.insert(entity);
            String fullPath = "";
            if (model.getParentId() > 0) {
                WorkshopEntity parentEntity = workshopDao.selectById(model.getParentId());
                if (parentEntity != null) {
                    fullPath = StringUtils.isEmpty(parentEntity.getFullPath()) ? parentEntity.getId() + Constant.PATH_SEPARATOR : parentEntity.getFullPath();
                }
            }
            WorkshopEntity workshopEntity = new WorkshopEntity();
            workshopEntity.setId(entity.getId());
            workshopEntity.setFullPath(StringUtils.isEmpty(fullPath) ? entity.getId() + Constant.PATH_SEPARATOR : fullPath + entity.getId() + Constant.PATH_SEPARATOR);
            workshopDao.updateById(workshopEntity);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("addWorkshop", e);
        }
        return true;
    }

    @Override
    public boolean updateWorkshop(UserInfo info, WorkshopModel model) throws OwnerException {
        WorkshopEntity exist = workshopDao.getSameLevelShop(info.getCompanyId(), model.getParentId(), model.getWorkshopName());
        if (null != exist && !exist.getId().equals(model.getId())) {
            throw new OwnerException("同级存在相同的工艺线/车间，请输入其他工艺线/车间");
        }

        WorkshopEntity oldEntity = workshopDao.selectById(model.getId());
        String fullPath = "";
        if (model.getParentId() > 0) {
            if (model.getParentId() > 0) {
                WorkshopEntity parentEntity = workshopDao.selectById(model.getParentId());
                if (parentEntity != null) {
                    fullPath = StringUtils.isEmpty(parentEntity.getFullPath()) ? parentEntity.getId() + Constant.PATH_SEPARATOR : parentEntity.getFullPath();
                }
            }
        }
        List<WorkshopEntity> updateList = new ArrayList<>();
        WorkshopEntity entity = new WorkshopEntity();
        BeanUtil.copyProperties(model, entity);
        entity.setFullPath(StringUtils.isEmpty(fullPath) ? entity.getId() + Constant.PATH_SEPARATOR : fullPath + entity.getId() + Constant.PATH_SEPARATOR);
        entity.setLastUpdateTime(new Date());
        entity.setLastUpdatorId(info.getUserId());
        entity.setMsLastUpdatorId(info.getMsUserId());
        updateList.add(entity);
        List<WorkshopEntity> oldChildren = workshopDao.queryChildrenByTerm(info.getCompanyId(), oldEntity.getFullPath(), oldEntity.getId());
        if (!CollectionUtils.isEmpty(oldChildren)) {
            String oldFullPath = oldEntity.getFullPath();
            Date now = new Date();
            oldChildren.forEach(old -> {
                String newFullPath = old.getFullPath();
                old.setFullPath(newFullPath.replace(oldFullPath, entity.getFullPath()));
                old.setLastUpdateTime(now);
                old.setLastUpdatorId(info.getUserId());
                old.setMsLastUpdatorId(info.getMsUserId());
                old.setCompanyId(info.getCompanyId());
                updateList.add(old);
            });
        }
        return workshopDao.updateList(updateList) > 0;
    }

    @Override
    public boolean delWorkshop(Integer companyId, Integer id) throws OwnerException {
        if (workshopDao.getDataByparentId(companyId, id) > 0) {
            throw new OwnerException("该工艺线/车间下有子节点不能进行删除操作!");
        }
        return workshopDao.deleteById(id) > 0;
    }

    @Override
    public boolean checkSeq(Integer companyId, Integer parentId, Integer seq) {
        List<WorkshopEntity> entityList = workshopDao.selectSeq(companyId, parentId, seq);
        if (entityList != null && entityList.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public Integer selectMaxSeq(Integer companyId, Integer parentId) {
        Integer maxSeq = workshopDao.selectMaxSeq(companyId, parentId);
        if (maxSeq == null) {
            maxSeq = 1;
        } else {
            maxSeq += 1;
        }
        return maxSeq;
    }

    @Override
    public String importWorkshop(UserInfo info, List<WorkshopExcel> workshopExcels) throws OwnerException {
        if(workshopExcels.size()>0) {
            List<String> deptNameList = workshopExcels.stream().filter(a -> !StringUtils.isEmpty(a.getWorkshopName())).map(WorkshopExcel::getWorkshopName).collect(Collectors.toList());
            List<String> checkDeptName = new ArrayList<>();
            List<String> repeatDeptName = new ArrayList<>();
            for (String deptName : deptNameList) {
                if (!checkDeptName.contains(deptName)) {
                    checkDeptName.add(deptName);
                } else {
                    repeatDeptName.add(deptName);
                }
            }
            if (repeatDeptName.size() > 0) {
                String patentNoStr = Joiner.on(",").join(repeatDeptName);
                return "有重复的工艺线/车间:" + patentNoStr + ",请修改后重新导入!";
            }

            List<WorkshopEntity> companyDept = workshopDao.queryByCompanyId(info.getCompanyId());
            Map<Integer, String> companyDeptMap = companyDept.stream().collect(Collectors.toMap(WorkshopEntity::getId, WorkshopEntity::getWorkshopName));
            Map<String, WorkshopEntity> fullNameMap = new HashMap<>();
            for (WorkshopEntity dept : companyDept) {
                if (dept != null && dept.getFullPath() != null) {
                    String[] deptIdArr = dept.getFullPath().split("/");
                    List<String> fullList = new ArrayList<>();
                    for (String deptId : deptIdArr) {
                        if (!StringUtils.isEmpty(deptId)) {
                            String s = companyDeptMap.get(Integer.parseInt(deptId));
                            fullList.add(s);
                        }
                    }
                    String fullNameStr = String.join("/", fullList);
                    fullNameMap.put(fullNameStr, dept);
                }
            }


//            List<String> deptNameList = workshopExcels.stream().filter(a -> !StringUtils.isEmpty(a.getWorkshopName())).map(WorkshopExcel::getWorkshopName).collect(Collectors.toList());
            Map<String, String> remarkMap = workshopExcels.stream().collect(Collectors.toMap(WorkshopExcel::getWorkshopName, WorkshopExcel::getRemark));
            Map<String, Integer> seqMap = workshopExcels.stream().collect(Collectors.toMap(WorkshopExcel::getWorkshopName, WorkshopExcel::getSeq));
            Map<Integer, List<WorkshopModel>> deptNameMap = new HashMap<>();
            Map<String, WorkshopEntity> deptMap = new HashMap<>();
            for (String deptName : deptNameList) {
                String[] deptNameArr = deptName.split("/");
                for (int i = 0; i < deptNameArr.length; i++) {
                    List<WorkshopModel> deptList = deptNameMap.get(i);

                    String fullDeptName = "";
                    if (i != 0) {
                        for (int j = 0; j < i; j++) {
                            fullDeptName += deptNameArr[j] + "/";
                        }
                    }
                    int seq = seqMap.get(fullDeptName + deptNameArr[i]) != null ? seqMap.get(fullDeptName + deptNameArr[i]) : 1;
                    String remark = remarkMap.get(fullDeptName + deptNameArr[i]) != null ? remarkMap.get(fullDeptName + deptNameArr[i]) : "";
                    WorkshopEntity dept = setWorkshop(info, deptNameArr[i], seq, remark);
                    if (fullNameMap.get(fullDeptName + deptNameArr[i]) == null) {
                        deptMap.put(fullDeptName + deptNameArr[i], dept);
                    }

                    if (deptList != null) {
                        List<String> dList = deptList.stream().filter(a -> !StringUtils.isEmpty(a.getFullWorkshopName())).map(WorkshopModel::getFullWorkshopName).collect(Collectors.toList());
                        if (!dList.contains(fullDeptName + deptNameArr[i])) {
                            WorkshopModel model = new WorkshopModel();
                            model.setWorkshopName(deptNameArr[i]);
                            model.setFullWorkshopName(fullDeptName + deptNameArr[i]);
                            deptList.add(model);
                            deptNameMap.put(i, deptList);
                        }
                    } else {
                        deptList = new ArrayList<>();
                        WorkshopModel model = new WorkshopModel();
                        model.setWorkshopName(deptNameArr[i]);
                        model.setFullWorkshopName(fullDeptName + deptNameArr[i]);
                        deptList.add(model);
                        deptNameMap.put(i, deptList);
                    }
                }
            }

            Map<String, WorkshopEntity> insertMap = new HashMap<>();
//        WorkshopEntity parentDept = workshopDao.selectParentDept(info.getCompanyId());

            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {
                for (Integer level : deptNameMap.keySet()) {
                    List<WorkshopEntity> insertList = new ArrayList<>();
                    List<WorkshopModel> deptModels = deptNameMap.get(level);
                    for (WorkshopModel model : deptModels) {
                        WorkshopEntity dept = deptMap.get(model.getFullWorkshopName());
                        if (dept != null) {
                            String pFullPath = model.getFullWorkshopName().replace("/" + model.getWorkshopName(), "");
                            WorkshopEntity pDept = insertMap.get(pFullPath);
                            if (pDept != null) {
                                dept.setParentId(pDept.getId());
                                dept.setFullPath(pDept.getFullPath());
                            } else {
                                WorkshopEntity oldDept = fullNameMap.get(model.getFullWorkshopName().replace("/" + model.getWorkshopName(), ""));
                                if (oldDept != null) {
                                    dept.setParentId(oldDept.getId());
                                    dept.setFullPath(oldDept.getFullPath());
                                } else {
                                    dept.setParentId(-1);
                                    dept.setFullPath("");
                                }
                            }
                            insertList.add(dept);
                            insertMap.put(model.getFullWorkshopName(), dept);
                        }
                    }
                    if (insertList.size() > 0) {
                        workshopDao.addBatch(insertList);
                        for (WorkshopEntity dept : insertList) {
                            dept.setFullPath(dept.getFullPath() + dept.getId() + "/");
                        }
                        workshopDao.updateList(insertList);
                    }

                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                TransactionUtils.rollback(transactionStatus);
                throw new OwnerException(ErrorEnum.FAIL);
            }
        }else{
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        return "";
    }

    private WorkshopEntity setWorkshop(UserInfo info, String workshopName, int seq, String remark) {
        WorkshopEntity workshop = new WorkshopEntity();
        workshop.setCompanyId(info.getCompanyId());
        workshop.setWorkshopName(workshopName);
        workshop.setParentId(0);
        workshop.setRemark(remark);
        workshop.setSeq(seq);
        workshop.setCreateTime(new Date());
        workshop.setLastUpdateTime(new Date());
        workshop.setCreatorId(info.getUserId());
        workshop.setMsCreatorId(info.getMsUserId());
        workshop.setLastUpdatorId(info.getUserId());
        workshop.setMsLastUpdatorId(info.getMsUserId());
        return workshop;
    }
}
