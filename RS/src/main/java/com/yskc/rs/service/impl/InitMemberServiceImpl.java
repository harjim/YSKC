package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.RdEmployeeDao;
import com.yskc.rs.dao.StageDao;
import com.yskc.rs.dao.init.InitMemberDao;
import com.yskc.rs.dao.project.ProjectRdEmployeeDao;
import com.yskc.rs.dao.project.ProjectYearInfoDao;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.init.InitMemberEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.entity.project.ProjectYearInfoEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.InitMemberExcel;
import com.yskc.rs.models.init.ImportProjectInfoModel;
import com.yskc.rs.models.init.InitRdUsedModel;
import com.yskc.rs.models.init.InitUsedRoleModel;
import com.yskc.rs.models.init.member.*;
import com.yskc.rs.service.InitMemberService;
import com.yskc.rs.service.ProjectRdEmployeeService;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.MessageFormat;
import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-23 17:18
 * @Description: 初始化项目人员列表业务实现层
 */
@Service
public class InitMemberServiceImpl implements InitMemberService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InitMemberDao initMemberDao;

    @Autowired
    private RdEmployeeDao rdEmployeeDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;

    @Autowired
    private ProjectRdEmployeeService projectRdEmployeeService;
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private StageDao stageDao;
    @Autowired
    private ProjectYearInfoDao projectYearInfoDao;

    @Override
    public PageModel<List<InitMemberModel>> getList(Integer companyId, QueryProjectInitMemberModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("enumber"));
            page.setAscs(CollUtil.newArrayList("!isMaster", "etype"));
        }
        String fileName = MessageFormat.format("org_{0,number,#}_{1,number,#}.png", companyId, query.getYear());
        String tempPath = MessageFormat.format("{0}/static/images/{1,number,#}/org", rsConfig.getUploadConfig().getResourcePath(), companyId);
        String orgUrl = null;
        File file = new File(tempPath + "/" + fileName);
        if (file.exists()) {
            orgUrl = MessageFormat.format("/static/images/{0,number,#}/org/{1}", companyId, fileName);
        }
        return PageUtils.buildPageResult(page, initMemberDao.getList(page, companyId, query), orgUrl);
    }

    @Override
    public PageModel<List<InitMemberModel>> getEmployeeList(Integer companyId, QueryInitMemberModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("e.enumber");
            ascs.add("e.ename");
            page.setAscs(ascs);
        }
        return PageUtils.build(page, initMemberDao.getEmployeeList(page, companyId, query));
    }

    @Override
    public boolean addList(UserInfo userInfo, BatchInitMemberModel model) throws OwnerException {
        Integer companyId = userInfo.getCompanyId();
        Integer projectId = model.getProjectId();
        Integer year = model.getYear();
        List<InitMemberEntity> existList = initMemberDao.getByEnumbers(companyId, projectId, model.getEnumbers(), year);
        Set<String> existEnumbers = new HashSet<>();
        if (!CollectionUtils.isEmpty(existList)) {
            existList.forEach(item -> existEnumbers.add(item.getEnumber()));
        }
        List<String> enumbers = (ArrayList<String>) CollUtil.disjunction(model.getEnumbers(), existEnumbers);
        List<InitMemberEntity> initMemberEntities = new ArrayList<>();
        Date now = new Date();
        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();
        enumbers.forEach((eumber) -> {
            initMemberEntities.add(new InitMemberEntity(userId, msUserId, now, eumber, false, companyId, projectId, year,null));
        });
        if (CollectionUtils.isEmpty(initMemberEntities)) {
            return true;
        }
        return initMemberDao.addbatch(initMemberEntities) > 0;
    }

    @Override
    public boolean del(InitMemberModel model, UserInfo info) throws OwnerException {
        List<InitRdUsedModel> rdUsed = getRdUsed(CollUtil.newArrayList(model.getId()), model.getProjectId(), null, model.getYear());
        if (!CollectionUtils.isEmpty(rdUsed)) {
            throw new OwnerException("删除失败，当前人员已存在归集数据。");
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            initMemberDao.deleteById(model.getId());

            if (model.getMaster()) {
                ProjectYearInfoEntity entity = projectYearInfoDao.getByYear(model.getProjectId(),model.getYear());
                entity.setMasterENumber(null);
                entity.setMsLastUpdatorId(info.getMsUserId());
                entity.setLastUpdatorId(info.getUserId());
                entity.setLastUpdateTime(new Date());
                List<ProjectYearInfoEntity> projectYearInfoEntities = Arrays.asList(entity);
                projectYearInfoDao.addBatchMaster(projectYearInfoEntities);
                projectDao.updateMaster(model.getProjectId(), "");
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("删除项目成员失败。");
        }
    }

    @Override
    public boolean updateInitMemberRole(InitUsedRoleModel model) {
        return initMemberDao.updateInitMemberRole(model.getRole(), model.getIds()) > 0;
    }

    @Override
    public Boolean importMember(UserInfo userInfo, List<InitMemberExcel> data, Integer year, Integer projectId) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            throw new OwnerException("未获取到任何项目成员，请检查是否录入了数据。");
        }
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        if (null == projectEntity) {
            throw new OwnerException("当前所选项目不存在。请稍后重试！");
        }
        Map<String, InitMemberExcel> dataMap = new LinkedHashMap<>();
        data.forEach(item -> {
            if (dataMap.containsKey(item.getEnumber())) {
                return;
            }
            dataMap.put(item.getEnumber(), item);
        });
        data = new ArrayList<>(data.size());
        data.addAll(dataMap.values());
        List<String> numberList = new ArrayList<>(dataMap.keySet());
        List<InitMemberEntity> initExistNumber = initMemberDao.getByEnumbers(userInfo.getCompanyId(), projectId, numberList, year);
        List<String> rdExistNumber = rdEmployeeDao.getByEnumbers(userInfo.getCompanyId(), year, numberList);
        Map<String, Integer> initExistMap = new HashMap<>();
        Map<String, Boolean> rdExistMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(initExistNumber)) {
            initExistNumber.forEach(item -> {
                initExistMap.put(item.getEnumber(), item.getId());
            });
        }
        if (!CollectionUtils.isEmpty(rdExistNumber)) {
            rdExistNumber.forEach(n -> {
                rdExistMap.put(n, true);
            });
        }
        List<InitMemberEntity> initList = new ArrayList<>();
        Date now = new Date();
        long projectBegin = projectEntity.getBeginDate().getTime();
        long projectEnd = projectEntity.getEndDate().getTime();
        List<InitMemberEntity> updateList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            InitMemberExcel excel = data.get(i);
            if (!rdExistMap.containsKey(excel.getEnumber())) {
                throw new OwnerException(MessageFormat.format("第{3,number,#}行，工号【{0}】，姓名【{1}】不存在【{2,number,#}】年研发人员列表中，请先在研发人员中添加。"
                        , excel.getEnumber(), excel.getEname(), year, i + 2
                ));
            }
            if (null != excel.getEntryDate()) {
                if (excel.getEntryDate().getTime() < projectBegin || excel.getEntryDate().getTime() > projectEnd) {
                    throw new OwnerException(MessageFormat.format("第{0,number,#}行，进入时间【{1}】，必须在项目起止日期【{2}至{3}】以内。",
                            i + 2,
                            DateUtil.format(excel.getEntryDate(), DateUtil.DEFAULT_DATE_FORMAT),
                            DateUtil.format(projectEntity.getBeginDate(), DateUtil.DEFAULT_DATE_FORMAT),
                            DateUtil.format(projectEntity.getEndDate(), DateUtil.DEFAULT_DATE_FORMAT)
                    ));
                }
            }
            String enumber = excel.getEnumber();
            if (!initExistMap.containsKey(enumber)) {
                initList.add(setInitMember(userInfo, excel, projectId, now, year));
            } else {
                InitMemberEntity memberEntity = setUpdateMember(userInfo, excel, now);
                memberEntity.setId(initExistMap.get(enumber));
                updateList.add(memberEntity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (!CollectionUtils.isEmpty(initList)) {
                List<List<InitMemberEntity>> insertInitList = ListUtils.subList(initList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<InitMemberEntity> items : insertInitList) {
                    initMemberDao.addbatch(items);
                }
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                for (List<InitMemberEntity> updates : ListUtils.subList(updateList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    initMemberDao.updateBatch(updates);
                }
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("导入项目成员失败", e);
            throw new OwnerException("保存数据失败");
        }
    }

    @Override
    public boolean delInitMembers(InitUsedRoleModel model, UserInfo info) throws OwnerException {
        TransactionStatus transactionStatus = null;
        List<Integer> ids = getModifyIds(model.getIds(), model.getProjectId(), null, model.getYear());
        if (CollectionUtils.isEmpty(ids)) {
            throw new OwnerException("所选人员已全部存在归集数据，不能删除!");
        }
        try {
            transactionStatus = TransactionUtils.newTransaction();
            initMemberDao.deleteBatchIds(ids);
            if (model.isHasMaster()) {
                projectDao.updateMaster(model.getProjectId(), "");
                ProjectYearInfoEntity entity = projectYearInfoDao.getByYear(model.getProjectId(), model.getYear());
                entity.setMasterENumber(null);
                entity.setMsLastUpdatorId(info.getMsUserId());
                entity.setLastUpdatorId(info.getUserId());
                entity.setLastUpdateTime(new Date());
                projectYearInfoDao.addBatchMaster(Arrays.asList(entity));
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("删除项目成员失败。");
        }
    }

    @Override
    public boolean setMaster(UserInfo userInfo, InitMemberModel memberModel) throws OwnerException {
        InitMemberEntity member = initMemberDao.selectById(memberModel.getId());
        if (member == null) {
            throw new OwnerException("该项目成员不存在或已删除，操作失败！");
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            Date now = new Date();
            Integer userId = userInfo.getUserId();
            Integer msUserId = userInfo.getMsUserId();
            initMemberDao.cleanProjectMaster(memberModel.getProjectId(), now, msUserId, userId, member.getYear());
            initMemberDao.updateProjectMaster(now, msUserId, userId, memberModel.getId(), member.getYear());
            projectDao.updateMaster(memberModel.getProjectId(), memberModel.getEnumber());
            ProjectYearInfoEntity entity=new ProjectYearInfoEntity(member.getYear(),member.getEnumber(),null,memberModel.getProjectId(),member.getCompanyId());
            entity.create(userId,msUserId,now);
            projectYearInfoDao.addBatchMaster(Arrays.asList(entity));
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("设置负责人失败！");
        }
    }

    @Override
    public boolean setEntryDate(UserInfo userInfo, InitUsedRoleModel model) throws OwnerException {
        Date now = new Date();
        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();
        Date entryDate = cn.hutool.core.date.DateUtil.beginOfDay(model.getEntryDate());
        List<Integer> ids = getModifyIds(model.getIds(), model.getProjectId(), entryDate, model.getYear());
        if (CollectionUtils.isEmpty(ids)) {
            throw new OwnerException("设置加入时间失败，所选人员已全部存在归集数据!");
        }
        try {
            initMemberDao.updateEntryDate(now, userId, msUserId, ids, entryDate);
        } catch (Exception e) {
            throw new OwnerException("设置加入时间失败。");
        }
        return true;
    }

    @Override
    public String checkRdUsed(InitUsedRoleModel model) {
        List<InitRdUsedModel> models = getRdUsed(model.getIds(), model.getProjectId(), model.getEntryDate(), model.getYear());
        StringBuilder builder = new StringBuilder();
        if (!CollectionUtils.isEmpty(models)) {
            Map<String, Boolean> keyMap = new HashMap<>();
            for (InitRdUsedModel m : models) {
                if (!keyMap.containsKey(m.getKey())) {
                    builder.append(m.getName()).append("（").append(m.getKey()).append("）,");
                    keyMap.put(m.getKey(), true);
                }

            }
            builder.append("已被归集费用。继续操作将跳过这些数据。");
        }
        return builder.toString();
    }

    private List<InitRdUsedModel> getRdUsed(List<Integer> ids, Integer projectId, Date entryDate, Integer year) {
        Date begin = DateUtil.getYearFirstDay(year);
        return projectRdEmployeeDao.getRdUsed(ids, projectId, entryDate != null ? DateUtil.getMonthFirstDay(entryDate) : DateUtil.getYearLastDay(year), year, begin);
    }

    /**
     * 设置项目成员
     *
     * @param info
     * @param excel
     * @param projectId
     * @param now
     * @return
     */
    private InitMemberEntity setInitMember(UserInfo info, InitMemberExcel excel, Integer projectId, Date now, Integer year) {
        InitMemberEntity initMemberEntity = setUpdateMember(info, excel, now);
        initMemberEntity.setMsCreatorId(info.getMsUserId());
        initMemberEntity.setCreatorId(info.getUserId());
        initMemberEntity.setCompanyId(info.getCompanyId());
        initMemberEntity.setCreateTime(now);
        initMemberEntity.setProjectId(projectId);
        initMemberEntity.setMaster(false);
        initMemberEntity.setEnumber(excel.getEnumber());
        initMemberEntity.setYear(year);
        return initMemberEntity;
    }

    /**
     * 设置项目成员
     *
     * @param info
     * @param excel
     * @param now
     * @return
     */
    private InitMemberEntity setUpdateMember(UserInfo info, InitMemberExcel excel, Date now) {
        InitMemberEntity initMemberEntity = new InitMemberEntity();
        initMemberEntity.setMsLastUpdatorId(info.getMsUserId());
        initMemberEntity.setLastUpdatorId(info.getUserId());
        initMemberEntity.setCompanyId(info.getCompanyId());
        initMemberEntity.setLastUpdateTime(now);
        initMemberEntity.setRole(excel.getRole());
        initMemberEntity.setEntryDate(cn.hutool.core.date.DateUtil.beginOfDay(excel.getEntryDate()));
        return initMemberEntity;
    }

    @Override
    public List<InitMemberListModel> getStaffInfoList(Integer projectId, Integer companyId, Integer[] memberIds,
                                                      Boolean addData, Integer pDocFileId) {
        ProjectEntity projectEntity = projectDao.selectById(projectId);
//        List<Integer> projectIds = new ArrayList<>();
        //大项目获取所有子项目成员列表
//        if (projectEntity.getHasChild() && projectEntity.getParentId() == 0) {
//            List<ProjectEntity> projectEntities = projectDao.getChildsById(projectId);
//            if (!CollectionUtils.isEmpty(projectEntities)) {
//                List<Integer> projects = projectEntities.stream().map(ProjectEntity::getId).collect(Collectors.toList());
//                projectIds.addAll(projects);
//            }
//        } else {
//            projectIds.add(projectId);
//        }
        List<InitMemberListModel> models;
        if (addData) {
            models = initMemberDao.getStaffsInfo(projectId, companyId, null, memberIds);
        } else {
            //查询项目立项报告阶段日期
            StageEntity stage = stageDao.getStageInfo(projectId, pDocFileId);
            Integer year = cn.hutool.core.date.DateUtil.year(stage != null && stage.getBeginDate() != null ? stage.getBeginDate() : projectEntity.getBeginDate());
            models = initMemberDao.getStaffInfoList(projectId, companyId, year, memberIds != null && memberIds.length > 0 ? Arrays.asList(memberIds) : null);
            models.forEach(item->{
                String gender = item.getGender();
                if (StringUtils.hasLength(gender)) {
                    item.setGender(gender.equals("1")?"女":"男");
                }
                String etype = item.getEtype();
                if (StringUtils.hasLength(etype)) {
                    switch (etype) {
                        case "1":
                            item.setEtype("研究人员");
                            break;
                        case "2":
                            item.setEtype("技术人员");
                            break;
                        case "3":
                            item.setEtype("辅助人员");
                        default:
                            break;
                    }
                }
            });
        }
        return models;
    }


    @Override
    public PageModel<List<InitMemberModel>> getAllStaff(Integer companyId, QueryInitMemberModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("e.enumber");
            ascs.add("e.ename");
            page.setAscs(ascs);
        }
        StageEntity stage = stageDao.getStageInfo(query.getProjectId(), query.getpDocFileId());
        Integer year;
        if (stage != null && stage.getBeginDate() != null) {
            year = cn.hutool.core.date.DateUtil.year(stage.getBeginDate());
        } else {
            ProjectEntity project = projectDao.selectById(query.getProjectId());
            year = project.getBeginYear();
        }
        return PageUtils.build(page, initMemberDao.getAllStaff(page, companyId, query, query.getExistIds(), year));
    }

    @Override
    public boolean importMembers(UserInfo userInfo, ImportProjectInfoModel model) throws OwnerException {
        if (model == null) {
            return true;
        }
        if (model.getSourceYear().equals(model.getTargetYear())) {
            throw new OwnerException("不能引入相同年份的数据！");
        }
        ProjectEntity project = projectDao.selectById(model.getProjectId());
        if (model.getSourceYear() < project.getBeginYear() || model.getSourceYear() > project.getEndYear()) {
            throw new OwnerException("选择引入数据的年份应处于项目周期内！");
        }
        List<InitMemberEntity> initMembers = initMemberDao.getAddMembers(model.getSourceYear(), model.getProjectId(), userInfo.getCompanyId(), model.getTargetYear());
        if (CollectionUtils.isEmpty(initMembers)) {
            throw new OwnerException(MessageFormat.format("引入失败，{0}年研发人员名单不包含{1}年的项目成员！",model.getTargetYear(),model.getSourceYear()));
        }
        Date date = new Date();
        Map<String, InitMemberEntity> memberMap = new HashMap<>();
        List<InitMemberEntity> existMembers = initMemberDao.getMembers(model.getTargetYear(), model.getProjectId());
        Boolean hasMaster = false;
        if (!CollectionUtils.isEmpty(existMembers)) {
            for (InitMemberEntity existMember : existMembers) {
                if (!memberMap.containsKey(existMember.getEnumber())) {
                    memberMap.put(existMember.getEnumber(), existMember);
                }
                if (existMember.getMaster()) {
                    hasMaster = true;
                }
            }
        }
        List<InitMemberEntity> insertList = new ArrayList<>();
        List<ProjectYearInfoEntity> pYearInfoList = new ArrayList<>();
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            for (InitMemberEntity initMember : initMembers) {
                if (memberMap.containsKey(initMember.getEnumber())) {
                    continue;
                }
                if(initMember.getMaster()==true){
                    ProjectYearInfoEntity projectYearInfoEntity = new ProjectYearInfoEntity(model.getTargetYear(), initMember.getEnumber(),null,initMember.getProjectId(),userInfo.getCompanyId());
                    projectYearInfoEntity.setLastUpdateTime(date);
                    projectYearInfoEntity.setLastUpdatorId(userInfo.getUserId());
                    projectYearInfoEntity.setMsLastUpdatorId(userInfo.getMsUserId());
                    projectYearInfoEntity.setCreateTime(date);
                    projectYearInfoEntity.setCreatorId(userInfo.getUserId());
                    projectYearInfoEntity.setMsCreatorId(userInfo.getMsUserId());
                    pYearInfoList.add(projectYearInfoEntity);

                }
                InitMemberEntity entity = new InitMemberEntity(userInfo.getUserId(), userInfo.getMsUserId(), date, initMember.getEnumber(),
                        hasMaster ? false : initMember.getMaster(), userInfo.getCompanyId(), model.getProjectId(), model.getTargetYear(),initMember.getRole());
                insertList.add(entity);
                if (!hasMaster && initMember.getMaster()) {
                    if (!project.getMasterENumber().equals(initMember.getEnumber())) {
                        project.setMasterENumber(initMember.getEnumber());
                        project.setLastUpdateTime(date);
                        project.setLastUpdatorId(userInfo.getUserId());
                        project.setMsLastUpdatorId(userInfo.getMsUserId());
                        projectDao.updateById(project);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                initMemberDao.addbatch(insertList);
            }
            if (!CollectionUtils.isEmpty(pYearInfoList)){
                projectYearInfoDao.addBatchMaster(pYearInfoList);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("设置负责人失败，请稍后重试。");
        }
    }

    @Override
    public List<Integer> getMembersYear(Integer year, Integer projectId) {
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        if (projectEntity.getBeginYear().equals(projectEntity.getEndYear())) {
            return new ArrayList<>();
        }
        return initMemberDao.getYears(year, projectId);
    }

    @Override
    public Map<Integer, Map<String,Long>> getMemberByProAndYear(List<Integer> projectIds, Integer year) {
        return initMemberDao.getMemberByProAndYear(projectIds, year);
    }

    private List<Integer> getModifyIds(List<Integer> dataIds, Integer projectId, Date entryDate, Integer year) {
        List<InitRdUsedModel> rdUsed = getRdUsed(dataIds, projectId, entryDate, year);
        List<Integer> ids;
        if (!CollectionUtils.isEmpty(rdUsed)) {
            Map<String, Boolean> usedMap = new HashMap<>();
            rdUsed.forEach(item -> usedMap.put(item.getKey(), Boolean.TRUE));
            List<InitMemberEntity> initMembers = initMemberDao.getEnumbersByIds(dataIds);
            List<Integer> finalIds = new ArrayList<>();
            initMembers.forEach(item -> {
                if (!usedMap.containsKey(item.getEnumber())) {
                    finalIds.add(item.getId());
                }
            });
            ids = finalIds;
        } else {
            ids = dataIds;
        }
        return ids;
    }
}
