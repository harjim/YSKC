package com.yskc.rs.service.impl;

import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.RdDeptDao;
import com.yskc.rs.entity.RdDeptEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.rddept.*;
import com.yskc.rs.service.RdDeptService;
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
 * @CreateTime: 2019-08-08 15:32
 * @Description: 研发部门实现类
 */
@Service
public class RdDeptServiceImpl implements RdDeptService {
    @Autowired
    private RdDeptDao rdDeptDao;

    @Autowired
    private CommonService commonService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<RdDeptTree> queryRdDeptTree(Integer companyId, Integer year) {
        return rdDeptDao.queryRdDeptTree(companyId, year);
    }

    @Override
    public Boolean modifyRdDept(RdDeptModel rdDeptModel, UserInfo userInfo) throws OwnerException {
        commonService.checkAuditModify(userInfo.getCompanyId(), rdDeptModel.getYear(), AuditModeEnum.RD_ORG, userInfo.getUserSource());
        RdDeptEntity rdDeptEntity;
        Date now = new Date();
        Integer level = rdDeptModel.getLevel();
        if (rdDeptModel.getId() > 0) {
            rdDeptEntity = rdDeptDao.selectById(rdDeptModel.getId());
            if (!rdDeptEntity.getDeptName().equals(rdDeptModel.getDeptName())) {
                if (rdDeptDao.checkedByDeptNameAndLevelAndPid(rdDeptModel.getDeptName(), userInfo.getCompanyId(), rdDeptModel.getLevel(), rdDeptModel.getParentId(), rdDeptModel.getYear()) > 0) {
                    throw new OwnerException("同级已存在相同部门");
                }
            }
        } else {
            if (rdDeptDao.checkedByDeptNameAndLevelAndPid(rdDeptModel.getDeptName(), userInfo.getCompanyId(), rdDeptModel.getLevel(), rdDeptModel.getParentId(), rdDeptModel.getYear()) > 0) {
                throw new OwnerException("同级已存在相同部门");
            }
            rdDeptEntity = new RdDeptEntity();
            rdDeptEntity.setCompanyId(userInfo.getCompanyId());
            rdDeptEntity.setParentId(rdDeptModel.getParentId() == null ? -1 : rdDeptModel.getParentId());
            rdDeptEntity.setCreateTime(now);
            rdDeptEntity.setLevel(level);
            rdDeptEntity.setIdentity(rdDeptModel.getThisIdentity());
            rdDeptEntity.setYear(rdDeptModel.getYear());
            rdDeptEntity.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
            rdDeptEntity.setMsCreatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
        }
        rdDeptEntity.setNodeType(rdDeptModel.getNodeType());
        rdDeptEntity.setTextDirection(rdDeptModel.getTextDirection());
        rdDeptEntity.setAlign(rdDeptModel.getAlign());
        rdDeptEntity.setDeptName(rdDeptModel.getDeptName());
        rdDeptEntity.setLastUpdateTime(now);
        rdDeptEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        rdDeptEntity.setMsLastUpdatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
        String fullName = "";
        if (rdDeptEntity.getId() != null) {
            fullName = getFullName(rdDeptEntity, rdDeptModel.getDeptName(), rdDeptEntity.getId());
            rdDeptEntity.setFullName(fullName);
            dealChildDept(Arrays.asList(rdDeptEntity), rdDeptEntity.getId(), rdDeptModel.getDeptName(), userInfo);
            return rdDeptDao.updateById(rdDeptEntity) > 0;
        }
        if (level > 0) {
            RdDeptEntity parentDept = rdDeptDao.selectById(rdDeptModel.getParentId());
            fullName = parentDept.getFullName() + "/" + rdDeptModel.getDeptName();
            Integer maxSeq = rdDeptDao.getMaxSeq(userInfo.getCompanyId(), rdDeptModel.getYear(), level);
            if (null == maxSeq) {
                maxSeq = 0;
            }
            maxSeq += 1;
            rdDeptEntity.setSeq(maxSeq);
        } else {
            fullName = rdDeptModel.getDeptName();
            rdDeptEntity.setSeq(1);
        }
        rdDeptEntity.setFullName(fullName);
        rdDeptDao.insertAllColumn(rdDeptEntity);
        String identity = rdDeptEntity.getIdentity();
        rdDeptEntity.setIdentity(StringUtils.isEmpty(identity) ? rdDeptEntity.getId() + Constant.PATH_SEPARATOR :
                identity + rdDeptEntity.getId() + Constant.PATH_SEPARATOR);
        return rdDeptDao.updateById(rdDeptEntity) > 0;
    }

    @Override
    public Boolean delete(RdDeptModel rdDeptModel, UserInfo info) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), rdDeptModel.getYear(), AuditModeEnum.RD_ORG, info.getUserSource());
        Integer id = rdDeptModel.getId();
        if (rdDeptDao.countNode(id) > 0) {
            throw new OwnerException("当前部门存在子部门，无法删除。");
        }
        if (rdDeptDao.checkedUsed(id) > 0) {
            throw new OwnerException("当前部门已被使用，无法删除。");
        }
        return rdDeptDao.deleteById(id) > 0;
    }


    @Override
    public ResultRdDeptModel getList(Integer companyId, Integer year) {
        ResultRdDeptModel result = new ResultRdDeptModel();
        //评审委员会map
        Map<Integer, List<RdDeptTree>> treeMap = new HashMap<>();
        List<RdDeptTree> rdDeptTrees = queryRdDeptTree(companyId, year);
        if (!CollectionUtils.isEmpty(rdDeptTrees)) {
            for (RdDeptTree tree : rdDeptTrees) {
                if (tree.getNodeType() == 1) {
                    if (!treeMap.containsKey(tree.getParentId())) {
                        List<RdDeptTree> list = new ArrayList<>();
                        treeMap.put(tree.getParentId(), list);
                    }
                    treeMap.get(tree.getParentId()).add(tree);
                }
            }
        }
        result.setData(RdDeptTree.getTree(rdDeptTrees, treeMap));
        if (CollectionUtils.isEmpty(result.getData())) {
            result.setPrevYears(rdDeptDao.getPrevYear(companyId, year));
        }
        return result;
    }

    @Override
    public Boolean importYear(UserInfo userInfo, RdYearModel rdYearModel) throws OwnerException {
        commonService.checkAuditModify(userInfo.getCompanyId(), rdYearModel.getCurrentYear(), AuditModeEnum.RD_ORG, userInfo.getUserSource());
        List<RdDeptEntity> list = rdDeptDao.getRdDeptByYear(userInfo.getCompanyId(), rdYearModel.getChYear());
        if (CollectionUtils.isEmpty(list)) {
            throw new OwnerException("获取【" + rdYearModel.getChYear() + "】数据失败");
        }

        Map<Integer, List<RdDeptEntity>> levelMap = new HashMap<>();
        Date now = new Date();
        int maxLevel = 0;
        String remark = "导入于" + rdYearModel.getChYear() + "数据";

        for (RdDeptEntity item : list) {
            maxLevel = maxLevel > item.getLevel() ? maxLevel : item.getLevel();
            item.setLastUpdateTime(now);
            item.setCreateTime(now);
            item.setCreatorId(userInfo.getUserId());
            item.setMsCreatorId(userInfo.getMsUserId());
            item.setLastUpdatorId(userInfo.getUserId());
            item.setMsLastUpdatorId(userInfo.getMsUserId());
            item.setRemark(remark);
            item.setYear(rdYearModel.getCurrentYear());
            if (!levelMap.containsKey(item.getLevel())) {
                levelMap.put(item.getLevel(), new ArrayList<>());
            }
            levelMap.get(item.getLevel()).add(item);
        }
        Map<Integer, String> identifyMap = new HashMap<>();
        Map<Integer, Integer> allIdMap = new HashMap<>();
        List<RdDeptEntity> insertList;
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            for (int i = 0; i <= maxLevel; i++) {
                Map<String, Integer> idMap = new HashMap<>();
                if (!CollectionUtils.isEmpty(levelMap.get(i))) {
                    insertList = new ArrayList<>(levelMap.size());
                    for (RdDeptEntity rdDeptEntity : levelMap.get(i)) {
                        rdDeptEntity.setParentId(allIdMap.getOrDefault(rdDeptEntity.getParentId(), -1));
                        idMap.put(rdDeptEntity.getIdentity(), rdDeptEntity.getId());
                        rdDeptEntity.setId(null);
                        insertList.add(rdDeptEntity);

                    }
                    rdDeptDao.addBatch(insertList);
                    for (RdDeptEntity rdDeptEntity : insertList) {
                        allIdMap.put(idMap.get(rdDeptEntity.getIdentity()), rdDeptEntity.getId());
                        if (rdDeptEntity.getParentId().equals(-1)) {
                            rdDeptEntity.setIdentity(rdDeptEntity.getId() + Constant.PATH_SEPARATOR);
                            identifyMap.put(rdDeptEntity.getId(), rdDeptEntity.getIdentity());
                        } else {
                            rdDeptEntity.setIdentity(identifyMap.get(rdDeptEntity.getParentId()) + rdDeptEntity.getId() + Constant.PATH_SEPARATOR);
                            identifyMap.put(rdDeptEntity.getId(), rdDeptEntity.getIdentity());
                        }
                    }
                    rdDeptDao.updateIdentity(insertList);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("导入研发部门失败", e);
            throw new OwnerException("导入研发部门失败");
        }
        return true;
    }

    @Override
    public Boolean cloneNode(CloneRdDeptModal cloneRdDept, UserInfo userInfo) throws OwnerException {
        commonService.checkAuditModify(userInfo.getCompanyId(), cloneRdDept.getCurrentYear(), AuditModeEnum.RD_ORG, userInfo.getUserSource());
        List<RdDeptEntity> entityList = rdDeptDao.queryChildrenByTerm(cloneRdDept.getCloneIdentity(), userInfo.getCompanyId());
        if (CollectionUtils.isEmpty(entityList)) {
            throw new OwnerException("未查询到被克隆节点，请稍后重试。");
        }
        String fullName = rdDeptDao.getFullName(cloneRdDept.getParentId());
        if (StringUtils.isEmpty(fullName)) {
            throw new OwnerException("未查询到当前节点，请稍后重试。");
        }
        Date now = new Date();
        Map<Integer, List<RdDeptEntity>> levelMap = new HashMap<>();
        int levelDiff = cloneRdDept.getParentLevel() - cloneRdDept.getCloneLevel() + 1;
        int maxLevel = 0;
        RdDeptEntity first = entityList.get(0);
        Integer maxSeq = rdDeptDao.getMaxSeq(userInfo.getCompanyId(), first.getYear(), cloneRdDept.getParentLevel() + 1);
        if (null == maxSeq) {
            maxSeq = 0;
        }
        maxSeq += 1;
        first.setSeq(maxSeq);
        for (RdDeptEntity item : entityList) {
            item.setFullName(null);
            item.setLastUpdateTime(now);
            item.setCreateTime(now);
            item.setLevel(item.getLevel() + levelDiff);
            item.setCreatorId(userInfo.getUserId());
            item.setMsCreatorId(userInfo.getMsUserId());
            item.setLastUpdatorId(userInfo.getUserId());
            item.setMsLastUpdatorId(userInfo.getMsUserId());
            if (!levelMap.containsKey(item.getLevel())) {
                levelMap.put(item.getLevel(), new ArrayList<>());
            }
            levelMap.get(item.getLevel()).add(new RdDeptEntity(item.getId(), item.getIdentity(), item.getParentId()));
            maxLevel = maxLevel > item.getLevel() ? maxLevel : item.getLevel();
        }

        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            rdDeptDao.addBatch(entityList);
            Map<String, RdDeptEntity> identityMap = new HashMap<>(entityList.size());
            entityList.forEach(item -> {
                identityMap.put(item.getIdentity(), item);
            });

            int minLevel = cloneRdDept.getParentLevel() + 1;
            Map<Integer, Integer> idMap = new HashMap<>();
            Map<Integer, RdDeptEntity> parentIdIdentityMap = new HashMap<>();
            List<RdDeptEntity> updateList = new ArrayList<>();
            String separator = Constant.PATH_SEPARATOR;
            for (int i = minLevel; i <= maxLevel; i++) {
                if (levelMap.containsKey(i)) {
                    for (RdDeptEntity tempEntity : levelMap.get(i)) {
                        RdDeptEntity entity = identityMap.get(tempEntity.getIdentity());
                        if (minLevel == i) {
                            entity.setIdentity(cloneRdDept.getParentIdentity() + entity.getId() + separator);
                            entity.setParentId(cloneRdDept.getParentId());
                            entity.setFullName(fullName + separator + entity.getDeptName());
                        } else {
                            RdDeptEntity parent = parentIdIdentityMap.get(idMap.get(entity.getParentId()));
                            entity.setIdentity(parent.getIdentity() + entity.getId() + separator);
                            entity.setParentId(idMap.get(tempEntity.getParentId()));
                            entity.setFullName(parent.getFullName() + separator + entity.getDeptName());
                        }
                        idMap.put(tempEntity.getId(), entity.getId());
                        parentIdIdentityMap.put(entity.getId(), entity);
                        updateList.add(entity);
                    }
                }
            }
            rdDeptDao.updateIdentities(updateList);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("克隆研发部门失败", e);
            throw new OwnerException("克隆研发部门失败");
        }

        return true;
    }

    @Override
    public Boolean sortNode(RdDeptSortModel rdDeptSort, UserInfo userInfo) throws OwnerException {
        RdDeptEntity entity = rdDeptDao.selectById(rdDeptSort.getId());
        if (null == entity) {
            throw new OwnerException("排序失败，无法获取当前排序部门");
        }
        int year = entity.getYear();
        int currentSort = entity.getSeq();
        RdDeptEntity nextSort = rdDeptDao.getNextSort(userInfo.getCompanyId(), year, entity.getLevel(),currentSort,rdDeptSort.getLeft());
        if(nextSort == null){
            throw new OwnerException("排序失败，无法获取当前排序部门");
        }
        return rdDeptDao.updateSeq(entity.getId(),nextSort.getSeq(),nextSort.getId(),currentSort,userInfo.getUserId(),
                userInfo.getMsUserId(),new Date())>0;

    }

    private String getFullName(RdDeptEntity rdDeptEntity, String newName, Integer id) {
        List<String> deptIdStr = Arrays.asList(rdDeptEntity.getIdentity().split("/"));
        List<Integer> deptIds = deptIdStr.stream().map(e -> Integer.valueOf(e)).collect(Collectors.toList());
        List<RdDeptEntity> rdDepts = rdDeptDao.selectBatchIds(deptIds);
        Map<Integer, String> deptNameMap = rdDepts.stream().collect(Collectors.toMap(e -> e.getId(), e -> e.getDeptName()));
        deptNameMap.put(id, newName);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < deptIds.size(); i++) {
            Integer rdDeptId = deptIds.get(i);
            sb.append(deptNameMap.get(rdDeptId));
            if (i < deptIds.size() - 1) {
                sb.append("/");
            }
        }
        return sb.toString();
    }

    /**
     * 修改部门节点同步子节点
     *
     * @param rdDept
     */
    private Boolean dealChildDept(List<RdDeptEntity> rdDept, Integer targetId, String newName, UserInfo userInfo) {
        List<Integer> deptIds = rdDept.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<RdDeptEntity> childs = rdDeptDao.getChildNode(deptIds);
        if (CollectionUtils.isEmpty(childs)) {
            return true;
        }
        List<RdDeptEntity> updateList = new ArrayList<>();
        for (RdDeptEntity rdDeptEntity : childs) {
            String fullName = getFullName(rdDeptEntity, newName, targetId);
            rdDeptEntity.setFullName(fullName);
            rdDeptEntity.setLastUpdateTime(new Date());
            rdDeptEntity.setLastUpdatorId(userInfo.getUserId());
            rdDeptEntity.setMsLastUpdatorId(userInfo.getMsUserId());
            updateList.add(rdDeptEntity);
        }
        dealChildDept(updateList, targetId, newName, userInfo);
        return rdDeptDao.updateList(updateList) > 0;
    }

}
