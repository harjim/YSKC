package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.entity.ms.*;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.params.ProjectParams;
import com.yskc.ms.models.project.BatchStaffModel;
import com.yskc.ms.models.project.ProjectInfoModel;
import com.yskc.ms.models.project.ProjectMemberModel;
import com.yskc.ms.models.project.SetOwerModel;
import com.yskc.ms.service.ms.ProjectService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectMemberDao projectMemberDao;
    @Autowired
    private ProjectMemberLogDao projectMemberLogDao;
    @Autowired
    private InnovationProjectDao innovationProjectDao;
    @Autowired
    private InnovationMemberDao innovationMemberDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public PageModel<List<ProjectInfoModel>> getProjectList(ProjectParams query, UserInfo userInfo, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("year");
            descs.add("createTime");
            page.setDescs(descs);
        }
        List<ProjectInfoModel> projectModels = projectDao.getList(page, query, dataPerm);
        if (projectModels.size() == 0) {
            return PageUtils.build(page, new ArrayList<>());
        } else {
            List<Integer> projectIds = new ArrayList<>();
            for (ProjectInfoModel pModel :
                    projectModels) {
                projectIds.add(pModel.getId()); // 项目id
            }
            Map<String, List<String>> mapMember = new HashMap<>();
            List<ProjectMemberModel> projectMemberList = projectMemberDao.queryMemberByIds(projectIds);
            for (ProjectMemberModel pmm :
                    projectMemberList) {
                if (pmm.getRealName() == null) continue;
                String k = MessageFormat.format("{0}_{1}", pmm.getProjectId(), pmm.getmType());
                if (mapMember.get(k) == null) {
                    mapMember.put(k, new ArrayList<>());
                }
                mapMember.get(k).add(pmm.getRealName());
            }

            for (ProjectInfoModel pMode :
                    projectModels) {
                String tk = MessageFormat.format("{0}_{1}", pMode.getId(), MemberTypeEnum.Tech.getId()); // 技术人员
                String fk = MessageFormat.format("{0}_{1}", pMode.getId(), MemberTypeEnum.FINANCE.getId()); // 财务人员
                String sk = MessageFormat.format("{0}_{1}", pMode.getId(), MemberTypeEnum.Saler.getId()); // 业务员
                String bk = MessageFormat.format("{0}_{1}", pMode.getId(), MemberTypeEnum.Businesser.getId()); // 谈单经理
                if (mapMember.get(tk) != null) {
                    pMode.setTechRealName(String.join(",", mapMember.get(tk)));
                }
                if (mapMember.get(fk) != null) {
                    pMode.setFinanceRealName(String.join(",", mapMember.get(fk)));
                }
                if (mapMember.get(sk) != null) {
                    pMode.setOwerName(String.join(",", mapMember.get(sk)));
                }
                if (mapMember.get(bk) != null) {
                    pMode.setBusinessName(String.join(",", mapMember.get(bk)));
                }
            }

            return PageUtils.build(page, projectModels);
        }
    }


    @Override
    public ProjectInfoModel getProjectDetail(UserInfo userInfo, Integer projectId) {
        ProjectInfoModel projectModel = projectDao.getProject(projectId);
        if (projectModel != null) {
            List<Integer> projectIds = new ArrayList<>();
            projectIds.add(projectId);
            List<ProjectMemberModel> members = projectMemberDao.getMemberList(null, projectIds, null);
            projectModel.setMembers(members);
        }
        return projectModel;
    }

    @Override
    public boolean addProject(UserInfo userInfo, ProjectInfoModel projectInfo) throws OwnerException {
        Map<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("productId", projectInfo.getProductId());
        pMap.put("year", projectInfo.getYear());
        pMap.put("customerId", projectInfo.getCustomerId());
        int cnt = projectDao.selectCount(new EntityWrapper<Project>().allEq(pMap));
        if (cnt > 0) {
            throw new OwnerException("请勿重复添加项目!");
        }
        Date now = new Date();
        Project p = new Project();
        p.setYear(projectInfo.getYear());
        p.setProductId(projectInfo.getProductId());
        p.setCustomerId(projectInfo.getCustomerId());
        p.setDeptId(projectInfo.getDeptId());
        p.setCreatorId(userInfo.getId());
        p.setCreateTime(now);
        p.setLastUpdatorId(userInfo.getId());
        p.setLastUpdateTime(now);
        List<ProjectMemberModel> members = projectInfo.getMembers();
        List<ProjectMemberLogEntity> logEntities = new ArrayList<>();
        if (members.size() > 0) {
            TransactionStatus transactionStatus = null;
            try {
                transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);

                List<ProjectMember> memberEntities = new ArrayList<>();
                Set<Integer> mTypeSet = new HashSet<>();
                for (ProjectMemberModel m :
                        members) {
                    if (m.getMemberId() == null) {
                        continue;
                    }
                    mTypeSet.add(m.getmType());
                    logEntities.add(ProjectMemberLogEntity.build(null, projectInfo.getCustomerId(), m.getmType(), m.getMemberId(), userInfo.getId(), now));
                    memberEntities.add(ProjectMember.build(userInfo, now, m.getmType(), m.getMemberId(), null, projectInfo.getCustomerId()));
                    if (MemberTypeEnum.Saler.getId().equals(m.getmType())) {
                        p.setOwerId(m.getMemberId());
                    } else if (MemberTypeEnum.Businesser.getId().equals(m.getmType())) {
                        p.setBusinessId(m.getMemberId());
                    }
                }
                projectDao.insert(p);
                Integer pId = p.getId();
                logEntities.forEach(item -> item.setProjectId(pId));
                memberEntities.forEach(item->item.setProjectId(pId));
                ProjectInfoModel project = new ProjectInfoModel();
                BeanUtils.copyProperties(p, project);
                project.setProductType(productDao.getProductType(project.getProductId()));
                insertMembers(project, memberEntities, new ArrayList<>(mTypeSet), userInfo, now, logEntities);
                TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
                return true;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
                throw new OwnerException("添加失败，请联系管理员。");
            }
        }
        return false;
    }


    @Override
    public boolean updateProject(UserInfo userInfo, ProjectInfoModel projectInfo) throws OwnerException {
        Date date = new Date();
        List<ProjectMemberModel> members = projectInfo.getMembers();
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            List<ProjectMemberLogEntity> entities = new ArrayList<>();
            if (members.size() > 0) {
                Set<Integer> mTypeSet = new HashSet<>();
                List<ProjectMember> updateMembers = new ArrayList<>();
                for (ProjectMemberModel pm :
                        members) {
                    mTypeSet.add(pm.getmType());
                    if (pm.getMemberId() != null) {
                        updateMembers.add(ProjectMember.build(userInfo, date, pm.getmType(), pm.getMemberId(), pm.getProjectId(), projectInfo.getCustomerId()));
                        ProjectMemberLogEntity logEntity = new ProjectMemberLogEntity();
                        logEntity.setmType(pm.getmType());
                        logEntity.setOperationTime(date);
                        logEntity.setOperationId(userInfo.getId());
                        logEntity.setMemberId(pm.getMemberId());
                        logEntity.setCustomerId(projectInfo.getCustomerId());
                        logEntity.setProjectId(pm.getProjectId());
                        entities.add(logEntity);
                    }
                }
                ProjectInfoModel project = projectDao.getProject(projectInfo.getId());
                insertMembers(project, updateMembers, new ArrayList<>(mTypeSet), userInfo, date, entities);
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("编辑失败，请联系管理员。");
        }
    }

    private void insertMembers(ProjectInfoModel project, List<ProjectMember> members, List<Integer> mTypes, UserInfo info,
                               Date now, List<ProjectMemberLogEntity> logs) {
        projectMemberLogDao.addLogs(logs);
        projectMemberDao.deleteMember(mTypes, CollUtil.newArrayList(project.getId()));
        projectMemberDao.addMemberList(members);
        Integer productType = project.getProductType();
        Boolean isInnovation = productType == 1 || productType == 2;
        if (isInnovation) {
            InnovationProject innovationProject = innovationProjectDao.getInnovationProject(project.getCustomerId(), project.getYear());
            boolean newInnovation = null == innovationProject;
            if (newInnovation) {
                innovationProject = InnovationProject.build(project.getCustomerId(), project.getYear(), project.getProductId(), info.getId(), now, project.getDeptId());
                innovationProjectDao.insert(innovationProject);
            } else {
                // 如果类型不存在创新项目中，则添加该类型
                if (!innovationProject.getTypes().contains(project.getProductId().toString())) {
                    innovationProject.setTypes(innovationProject.getTypes() + "," + project.getProductId());
                }
                innovationProject.setDeptId(project.getDeptId());
                innovationProjectDao.updateById(innovationProject);
            }
            String format = "{0}_{1}";
            //查询刚插入的人员及当年其他项目类型1,2的项目成员
            List<ProjectMember> projectMembers = projectMemberDao.getMembers(project.getCustomerId(), project.getYear());
            Map<String, Boolean> memberMap = projectMembers.stream().collect(Collectors.toMap(
                    a -> MessageFormat.format(format, a.getMemberId(), a.getmType()), b -> Boolean.TRUE, (o, n) -> n
            ));
            Integer iId = innovationProject.getId();
            Map<Integer, Boolean> mTypeMap = new HashMap<>();
            Map<String, Boolean> existInnovationMemberMap = new HashMap<>();
            if (!newInnovation) {
                List<Integer> deleteInnovationIds = new ArrayList<>();
                List<InnovationMember> innovationMembers = innovationMemberDao.getMembers(iId);
                innovationMembers.forEach(item -> {
                    String key = MessageFormat.format(format, item.getMemberId(), item.getmType());
                    if (!memberMap.containsKey(key)) {
                        deleteInnovationIds.add(item.getId());
                        return;
                    }
                    existInnovationMemberMap.put(key, Boolean.TRUE);
                    if (item.getMaster()) {
                        mTypeMap.put(item.getmType(), false);
                    }
                });
                if (!CollectionUtils.isEmpty(deleteInnovationIds)) {
                    innovationMemberDao.deleteBatchIds(deleteInnovationIds);
                }
            }
            List<InnovationMember> insertInnovationMembers = new ArrayList<>();
            projectMembers.forEach(item -> {
                Integer curMtype = item.getmType();
                String key = MessageFormat.format(format, item.getMemberId(), curMtype);
                if (existInnovationMemberMap.containsKey(key)) {
                    return;
                }
                // 不是新数据且不是技术或财务人员，跳过
                if (!newInnovation && !MemberTypeEnum.isTechOrFina(curMtype)) {
                    return;
                }
                existInnovationMemberMap.put(key, true);
                if (!MemberTypeEnum.isTechOrFina(curMtype)) {
                    mTypeMap.put(curMtype, false);
                }
                insertInnovationMembers.add(InnovationMember.build(iId, now, item.getMemberId(), curMtype,
                        info.getId(), mTypeMap.getOrDefault(curMtype, true)));
                mTypeMap.put(curMtype, false);
            });
            if (!CollectionUtils.isEmpty(insertInnovationMembers)) {
                innovationMemberDao.addBatch(insertInnovationMembers);
            }
        }
    }

    @Override
    public boolean setProjectTechIds(BatchStaffModel batch, UserInfo userInfo, Boolean isAdd) throws OwnerException {
        return setProjectStaff(MemberTypeEnum.Tech, batch, userInfo, isAdd);
    }


    @Override
    public boolean setProjectFinanceIds(BatchStaffModel batch, UserInfo userInfo, Boolean isAdd) throws OwnerException {
        return setProjectStaff(MemberTypeEnum.FINANCE, batch, userInfo, isAdd);
    }

    @Override
    public boolean setProjectOwerIds(SetOwerModel setOwer, UserInfo userInfo) throws OwnerException {
        List<ProjectMember> data = new ArrayList<>();
        Date now = new Date();
        List<Integer> projectIds = setOwer.getProjectIds();
        List<ProjectMember> projectCustomerList = projectDao.getCustomerIdsByIds(projectIds);
        List<ProjectMemberLogEntity> logEntities = new ArrayList<>();
        Project project = new Project();
        project.setLastUpdateTime(now);
        project.setLastUpdatorId(userInfo.getId());
        project.setDeptId(setOwer.getDeptId());
        project.setOwerId(setOwer.getOwerId());
        List<Integer> mTypes = new ArrayList<>();
        List<Integer> innovationIds = innovationProjectDao.getInnovationIds(projectIds);
        List<InnovationMember> innovationMembers = new ArrayList<>();
        if (!CollectionUtils.isEmpty(innovationIds)) {
            innovationIds.forEach(iId ->
                    innovationMembers.add(InnovationMember.build(iId, now, setOwer.getOwerId(), MemberTypeEnum.Saler.getId(), userInfo.getId(), false)));
        }
        mTypes.add(MemberTypeEnum.Saler.getId());
        data.addAll(getMemberModel(projectCustomerList, now, MemberTypeEnum.Saler.getId(),
                CollUtil.newArrayList(setOwer.getOwerId()), userInfo, logEntities));
        Integer businessId = setOwer.getBusinessId();
        if (null != businessId && businessId > 0) {
            if (!CollectionUtils.isEmpty(innovationIds)) {
                innovationIds.forEach(iId ->
                        innovationMembers.add(InnovationMember.build(iId, now, businessId, MemberTypeEnum.Businesser.getId(), userInfo.getId(), false)));
            }
            project.setBusinessId(businessId);
            mTypes.add(MemberTypeEnum.Businesser.getId());
            data.addAll(getMemberModel(projectCustomerList, now, MemberTypeEnum.Businesser.getId(),
                    CollUtil.newArrayList(businessId), userInfo, logEntities));
        }
        return saveMember(CollUtil.newArrayList(mTypes), projectIds, data, logEntities, project, innovationIds, innovationMembers, false);
    }

    /**
     * 设置项目人员
     *
     * @param memberEnum
     * @param batch
     * @param userInfo
     * @return
     */
    private boolean setProjectStaff(MemberTypeEnum memberEnum, BatchStaffModel batch, UserInfo userInfo, Boolean isAdd) throws OwnerException {
        Integer mType = memberEnum.getId();
        Date now = new Date();
        List<Integer> projectIds = batch.getProjectIds();
        List<ProjectMember> projectCustomerList = projectDao.getCustomerIdsByIds(projectIds);
        List<ProjectMemberLogEntity> logEntities = new ArrayList<>();
        List<Integer> staffIds = batch.getStaffIds();
        List<ProjectMember> data = getMemberModel(projectCustomerList, now, mType, staffIds, userInfo, logEntities);
        List<Integer> innovationIds = innovationProjectDao.getInnovationIds(projectIds);
        List<InnovationMember> innovationMembers = new ArrayList<>();
        if (!CollectionUtils.isEmpty(innovationIds)) {
            for (Integer iId : innovationIds) {
                for (int i = 0; i < staffIds.size(); i++) {
                    innovationMembers.add(InnovationMember.build(iId, now, staffIds.get(i), mType, userInfo.getId(), i == 0));
                }
            }
        }
        return saveMember(CollUtil.newArrayList(mType), projectIds, data, logEntities, null, innovationIds, innovationMembers, isAdd);
    }

    private List<ProjectMember> getMemberModel(List<ProjectMember> projectCustomerList, Date now, Integer mType,
                                               List<Integer> staffIds, UserInfo userInfo, List<ProjectMemberLogEntity> logEntities) {
        List<ProjectMember> data = new ArrayList<>();
        for (ProjectMember pm : projectCustomerList) {
            for (Integer staffId : staffIds) {
                data.add(ProjectMember.build(userInfo, now, mType, staffId, pm.getId(), pm.getCustomerId()));
                logEntities.add(ProjectMemberLogEntity.build(pm.getId(), pm.getCustomerId(), mType, staffId, userInfo.getId(), now));
            }
        }
        return data;
    }

    private boolean saveMember(List<Integer> mTypes, List<Integer> projectIds, List<ProjectMember> data,
                               List<ProjectMemberLogEntity> logEntities, Project project, List<Integer> innovationIds,
                               List<InnovationMember> innovationMembers, Boolean isAdd) throws OwnerException {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            if (!isAdd) {
                projectMemberDao.deleteMember(mTypes, projectIds);
            }
            projectMemberDao.addMemberList(data);
            if (!CollectionUtils.isEmpty(logEntities)) {
                projectMemberLogDao.addLogs(logEntities);
            }
            if (null != project) {
                projectDao.updateProjectOwerId(project, projectIds);
            }
            // 同步创新成员
            if (!CollectionUtils.isEmpty(innovationMembers)) {
                if (!isAdd) {
                    innovationMemberDao.deleteMembers(innovationIds, mTypes);
                }
                innovationMemberDao.addBatch(innovationMembers);
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("设置失败，请联系管理员。");
        }
    }

}
