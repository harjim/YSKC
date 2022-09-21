package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.init.InitMemberDao;
import com.yskc.rs.entity.EmployeeEntity;
import com.yskc.rs.entity.RdEmployeeEntity;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.init.InitMemberEntity;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.enums.EduLevelEnum;
import com.yskc.rs.enums.EmployeeTypeEnum;
import com.yskc.rs.enums.OrgEnum;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.KeysAndIdsModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employee.EmployeeTermModel;
import com.yskc.rs.models.employee.RdEmployeeExportModel;
import com.yskc.rs.models.employee.RdEmployeeModel;
import com.yskc.rs.models.excel.EmployeeExcel;
import com.yskc.rs.models.excel.ImportEmployeeExcel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import com.yskc.rs.models.projectattendance.DataEmployeeQuery;
import com.yskc.rs.models.rdemployeehour.QueryRdEmployeeHourModel;
import com.yskc.rs.models.rdemployeehour.RdEmployeeHourModel;
import com.yskc.rs.models.rdemployeehour.RdEmployeeHourProjectModel;
import com.yskc.rs.models.rdequipment.FullYearProjectModel;
import com.yskc.rs.models.reviewCommittee.QueryReviewModel;
import com.yskc.rs.service.RdEmployeeService;
import com.yskc.rs.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: wangxing
 * @CreateTime: 2019-11-18 14:38
 * @Description: RdEmployeeServiceImpl
 */
@Service
public class RdEmployeeServiceImpl extends ServiceImpl<RdEmployeeDao, RdEmployeeEntity> implements RdEmployeeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private StageDao stageDao;
    @Autowired
    private ProjectDao projectDao;


    @Override
    public PageResult getRdEmployeeData(Integer companyId, EmployeeTermModel termModel) {
        Pagination page = termModel.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("re.enumber");
            page.setAscs(ascs);
        }
        List<RdEmployeeModel> result = rdEmployeeDao.getRdEmployeeData(companyId, page, termModel);
        loadRdsAndLackMonth(result, termModel.getYear(), companyId);
        return PageUtils.buildPageResult(page, result, reportDao.getEmployeeAmount(termModel.getYear(), companyId));
    }

    @Override
    public boolean addRdEmployeeData(UserInfo info, RdEmployeeModel rdEmployeeModel) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), rdEmployeeModel.getYear(), AuditModeEnum.RD_EMPLOYEE, info.getUserSource());
        List<EmployeeEntity> entityList = rdEmployeeModel.getEntityList();
        List<RdEmployeeEntity> rdEmployeeEntities = new ArrayList<>();
        List<String> enumbers = new ArrayList<>();
        entityList.forEach(item -> enumbers.add(item.getEnumber()));
        List<String> existNumber = rdEmployeeDao.getByEnumbers(info.getCompanyId(), rdEmployeeModel.getYear(), enumbers);
        Map<String, Boolean> existMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(existNumber)) {
            existNumber.forEach(enumber -> existMap.put(enumber, true));
        }
        for (int i = 0; i < entityList.size(); i++) {
            if (existMap.containsKey(entityList.get(i).getEnumber())) {
                continue;
            }
            RdEmployeeEntity entity = new RdEmployeeEntity();
            entity.setCompanyId(info.getCompanyId());
            entity.setYear(rdEmployeeModel.getYear());
            entity.setEnumber(entityList.get(i).getEnumber());
            entity.setEtype(-1);
            entity.setRdDeptId(-1);
            entity.setCreateTime(new Date());
            entity.setCreatorId(info.getUserSource() == 0 ? info.getId() : -1);
            entity.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
            entity.setMsCreatorId(info.getUserSource() == 1 ? info.getId() : -1);
            entity.setMsLastUpdatorId(info.getUserSource() == 1 ? info.getId() : -1);
            entity.setLastUpdateTime(new Date());
            rdEmployeeEntities.add(entity);
        }
        if (!CollectionUtils.isEmpty(rdEmployeeEntities)) {
            rdEmployeeDao.addRdEmployeeBatch(rdEmployeeEntities);
        }
        return true;
    }

    @Override
    public boolean delRdEmployee(UserInfo info, RdEmployeeModel model) throws OwnerException {

        commonService.checkAuditModify(info.getCompanyId(), model.getYear(), AuditModeEnum.RD_EMPLOYEE, info.getUserSource());
        Integer id = model.getId();
        List<Integer> ids = CollUtil.newArrayList(id);
        List<Integer> usedIds = rdEmployeeDao.getData(info.getCompanyId(), ids, model.getYear());
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在[加计扣除-项目管理-项目成员]中，不能删除!");
        }

        return rdEmployeeDao.deleteById(id) > 0;
    }

    @Override
    public boolean updateRdEmployeeRdDept(UserInfo info, RdEmployeeModel model) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), model.getYear(), AuditModeEnum.RD_EMPLOYEE, info.getUserSource());
        return rdEmployeeDao.setRdEmployeeDeptId(model.getRdDeptId(), model.getIds(), info.getUserId(), info.getMsUserId(), new Date()) > 0;
    }

    @Override
    public boolean updateRdEmployeeEtype(UserInfo info, RdEmployeeModel model) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), model.getYear(), AuditModeEnum.RD_EMPLOYEE, info.getUserSource());
        return rdEmployeeDao.setRdEmployeeEtype(model.getEtype(), model.getIds(), info.getUserId(), info.getMsUserId(), new Date()) > 0;
    }

    @Override
    public boolean setPosition(UserInfo info, RdEmployeeModel model) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), model.getYear(), AuditModeEnum.RD_EMPLOYEE, info.getUserSource());
        return rdEmployeeDao.setRdEmployeePosition(model.getPosition(), model.getIds(), info.getUserId(), info.getMsUserId(), new Date()) > 0;
    }

    @Override
    public boolean delRdEmployeeBatch(UserInfo info, BatchDeleteModel deleteModel) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), deleteModel.getYear(), AuditModeEnum.RD_EMPLOYEE, info.getUserSource());
        List<Integer> ids = deleteModel.getIds();
        List<Integer> usedIds = rdEmployeeDao.getData(info.getCompanyId(), ids, deleteModel.getYear());
        Collection<Integer> result = CollUtil.disjunction(usedIds, ids);
        if (CollectionUtils.isEmpty(result)) {
            throw new OwnerException("所选研发人员已全部在项目成员中，不能删除!");
        }
//        Set<Integer> usedIdSet = new HashSet<>();
//        usedIdSet.addAll(rdEmployeeDao.getData(TableEnum.D_SALARY.getName(), companyId, ids));
//        usedIdSet.addAll(rdEmployeeDao.getData(TableEnum.D_BONUS.getName(), companyId, ids));
//        usedIdSet.addAll(rdEmployeeDao.getData(TableEnum.I_MEMBER.getName(), companyId, ids));
//        usedIdSet.addAll(rdEmployeeDao.getData(TableEnum.P_PROJECT.getName(), companyId, ids));
//        Collection<Integer> result = CollUtil.disjunction(usedIdSet, ids);
//        if (CollectionUtils.isEmpty(result)) {
//            throw new OwnerException("所选人员已全部在加计扣除中使用，不能删除!");
//        }
        return rdEmployeeDao.deleteBatchIds(result) > 0;
    }

    @Override
    public List<EmployeeExcel> deriveRdEmployee(Integer companyId, EmployeeTermModel termModel) {
        List<EmployeeExcel> result = rdEmployeeDao.deriveRdEmployee(companyId, termModel);
        loadRdsAndLackMonth(termModel.getYear(), companyId, result);
        return result;
    }

    @Override
    public void getRdEmployeeExport(Integer companyId, EmployeeTermModel termModel, OutputStream out, String path) throws OwnerException {
        List<RdEmployeeExportModel> result = rdEmployeeDao.getRdEmployeeData(companyId, termModel);
        loadRdsAndLackMonth(result, termModel.getYear(), companyId);
        for (RdEmployeeExportModel rdm : result) {
            if (rdm.getEtype() != null) {
                rdm.setEtypeStr(EmployeeTypeEnum.getEmployeeTypeEnum(rdm.getEtype()).getType());
            }
            if (rdm.getEduLevel() != null) {
                rdm.setEdLevelStr(EduLevelEnum.getEduLevelEnum(rdm.getEduLevel()).getEdu());
            }
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("result", result);
        YsExcelUtils.generalReport(dataMap, path, (workbook) -> {
            if (workbook != null) {
                try {
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    @Override
    public PageModel<List<ProjectEmployeeInfo>> getProjectEmployeeList(Integer companyId, DataEmployeeQuery query) {
        if (query.getMonth() != null) {
            query.setMonth(DateUtil.getMonthFirstDay(query.getMonth()));
        }
        query.setCompanyId(companyId);
        Pagination page = new Pagination(query.getPageNo(), query.getPageSize());
        return PageUtils.build(page, rdEmployeeDao.getProjectEmployeeInfos(page, query));
    }

    @Override
    public PageModel<List<ProjectEmployeeInfo>> getEmployeeList(Integer companyId, QueryReviewModel query) {
        Pagination page = query.getPagination();
        Integer year;
        if (query.getDocDate() != null) {
            year = cn.hutool.core.date.DateUtil.year(query.getDocDate());
        } else {
            ProjectDocFileEntity docFile = projectDocFileDao.selectById(query.getpDocFileId());
            StageEntity stage = stageDao.getStageInfo(docFile.getProjectId(), query.getpDocFileId());
            if (stage != null && stage.getBeginDate() != null) {
                year = cn.hutool.core.date.DateUtil.year(stage.getBeginDate());
            } else {
                ProjectEntity project = projectDao.selectById(docFile.getProjectId());
                year = project.getBeginYear();
            }
        }

        return PageUtils.build(page, rdEmployeeDao.getEmployeeInfos(page, companyId,query,year));
    }

    @Override
    public String importEmployee(UserInfo info, List<ImportEmployeeExcel> employeeExcels, Integer year) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), year, AuditModeEnum.RD_EMPLOYEE, info.getUserSource());
        if (CollectionUtils.isEmpty(employeeExcels)) {
            throw new OwnerException("文件为空,请添加数据后再导入");
        }
        Map<String, Integer> rdDeptMap = commonService.initOrgFullPathMap(info.getCompanyId(), OrgEnum.RD_DEPT, year);
        //查询存在的研发人员列表
        List<String> enumberList = employeeExcels.stream().filter(a -> !StringUtils.isEmpty(a.getEnumber())).map(ImportEmployeeExcel::getEnumber).collect(Collectors.toList());
        List<RdEmployeeEntity> existRdEnumers = rdEmployeeDao.getListByEnumbers(info.getCompanyId(), year, enumberList);
        Map<String, RdEmployeeEntity> existOrRepeatMap = new HashMap<>();
        existRdEnumers.forEach(item -> {
            existOrRepeatMap.put(item.getEnumber(), item);
        });
        List<EmployeeEntity> employeeModelList = employeeDao.getByNumbers(info.getCompanyId(), enumberList);
        Map<String, Integer> existEmployeeMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(employeeModelList)) {
            employeeModelList.forEach(item -> existEmployeeMap.put(item.getEnumber(), item.getId()));
        }
        //插入数据
        List<RdEmployeeEntity> addRdEmployeeEntitys = new ArrayList<>();
        List<RdEmployeeEntity> updateRdEmployees = new ArrayList<>();
        List<EmployeeEntity> insertEmployeeList = new ArrayList<>();
        Date date = new Date();
        int max = employeeExcels.size();
        Map<String, Object> repeatMap = new HashMap<>();
        for (int i = 0; i < max; i++) {
            ImportEmployeeExcel excels = employeeExcels.get(i);
            String enumber = excels.getEnumber();
            if (repeatMap.containsKey(enumber)) {
                continue;
            }
            repeatMap.put(enumber, null);
            Integer rdDeptId = ToolUtils.getOrgId(rdDeptMap, excels.getRdDeptName().trim());
            if (rdDeptId == null) {
                throw new OwnerException(MessageFormat.format(Constant.IMPORT_DEPT_ERROR,
                        i + 2, excels.getRdDeptName()));
            }
            if (!existEmployeeMap.containsKey(enumber)) {
                insertEmployeeList.add(EmployeeEntity.build(info, date, 0, excels.getEname(), enumber, excels.getRdDeptName()));
            }
            if (StringUtils.isEmpty(excels.getTypeValue())) {
                throw new OwnerException("工号：" + enumber + "，姓名：" + excels.getEname() + "的人员类型不能为空！");
            }
            EmployeeTypeEnum employeeTypeEnum = EmployeeTypeEnum.getEmployeeTypeEnum(excels.getTypeValue());
            if (employeeTypeEnum == EmployeeTypeEnum.ORDINARY_EMPLOYEES) {
                throw new OwnerException("工号：" + enumber + "，姓名：" + excels.getEname() + "的人员类型应为研究人员，技术人员或辅助人员！");
            }
            if (existOrRepeatMap.containsKey(enumber)) {
                RdEmployeeEntity rdEmployeeEntity = existOrRepeatMap.get(enumber);
                rdEmployeeEntity.setEtype(employeeTypeEnum.getValue());
                rdEmployeeEntity.setRdDeptId(rdDeptId);
                rdEmployeeEntity.setPosition(excels.getPosition());
                rdEmployeeEntity.setLastUpdateTime(date);
                rdEmployeeEntity.setMsLastUpdatorId(info.getMsUserId());
                rdEmployeeEntity.setLastUpdatorId(info.getUserId());
                updateRdEmployees.add(rdEmployeeEntity);
            } else {
                RdEmployeeEntity rdEmployeeEntity = new RdEmployeeEntity();
                rdEmployeeEntity.setCreateTime(date);
                rdEmployeeEntity.setCreatorId(info.getUserId());
                rdEmployeeEntity.setEnumber(enumber);
                rdEmployeeEntity.setPosition(excels.getPosition());
                rdEmployeeEntity.setLastUpdateTime(date);
                rdEmployeeEntity.setLastUpdatorId(info.getUserId());
                rdEmployeeEntity.setYear(year);
                rdEmployeeEntity.setCompanyId(info.getCompanyId());
                rdEmployeeEntity.setEtype(employeeTypeEnum.getValue());
                rdEmployeeEntity.setMsCreatorId(info.getMsUserId());
                rdEmployeeEntity.setMsLastUpdatorId(info.getMsUserId());
                rdEmployeeEntity.setRdDeptId(rdDeptId);
                addRdEmployeeEntitys.add(rdEmployeeEntity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (addRdEmployeeEntitys.size() > 0) {
                for (List<RdEmployeeEntity> inserts : ListUtils.subList(addRdEmployeeEntitys, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    rdEmployeeDao.saveRdEmployeeLists(inserts);
                }
            }
            if (!CollectionUtils.isEmpty(updateRdEmployees)) {
                for (List<RdEmployeeEntity> updates : ListUtils.subList(updateRdEmployees, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    rdEmployeeDao.updateList(updates);
                }
            }
            if (!CollectionUtils.isEmpty(insertEmployeeList)) {
                for (List<EmployeeEntity> inserts : ListUtils.subList(insertEmployeeList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    employeeDao.addBatch(inserts);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("导入研发花名册失败");
        }
        return "";
    }

    @Override
    public Boolean setProjectMember(UserInfo userInfo, KeysAndIdsModel keysAndIds) throws OwnerException {
        Integer companyId = userInfo.getCompanyId();
        int year = keysAndIds.getYear();
        commonService.checkAuditModify(companyId, year, AuditModeEnum.RD_EMPLOYEE, userInfo.getUserSource());
        Date now = new Date();
        List<InitMemberEntity> exist = initMemberDao.getExist(companyId, keysAndIds, year);
        String keyFormat = "{0}_{1}";
        Map<String, Boolean> existMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(exist)) {
            exist.forEach(item -> existMap.put(MessageFormat.format(keyFormat, item.getProjectId(), item.getEnumber()), true));
        }
        List<InitMemberEntity> insertList = new ArrayList<>();
        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();
        keysAndIds.getIds().forEach(projectId -> {
            keysAndIds.getKeys().forEach(enumber -> {
                if (existMap.containsKey(MessageFormat.format(keyFormat, projectId, enumber))) {
                    return;
                }
                insertList.add(new InitMemberEntity(userId, msUserId, now, enumber, false, companyId, projectId, year, null));
            });
        });
        if (!CollectionUtils.isEmpty(insertList)) {
            TransactionStatus transactionStatus = null;
            try {
                transactionStatus = TransactionUtils.newTransaction();
                for (List<InitMemberEntity> list : ListUtils.subList(insertList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    initMemberDao.addbatch(list);
                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                TransactionUtils.rollback(transactionStatus);
                throw new OwnerException("保存失败");
            }
        }
        return true;
    }

    @Override
    public PageModel<List<RdEmployeeHourModel>> getRdEmployeeHours(QueryRdEmployeeHourModel query, Integer companyId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(CollUtil.newArrayList("re.enumber"));
        }
        List<RdEmployeeHourModel> data = rdEmployeeDao.getRdEmployeeHours(page, query, companyId);
        if (!CollectionUtils.isEmpty(data)) {
            Map<String, RdEmployeeHourModel> dataMap = data.stream().collect(Collectors.toMap(a -> a.getEnumber(), b -> b, (o, n) -> n));
            int year = query.getYear();
            List<RdEmployeeHourProjectModel> projectData = rdEmployeeDao.getRdsAndEntryDate(dataMap.keySet(), year, companyId,
                    DateUtil.getYearFirstDay(year), DateUtil.getYearLastDay(year));
            if (!CollectionUtils.isEmpty(projectData)) {
                projectData.forEach(item -> {
                    RdEmployeeHourModel current = dataMap.get(item.getEnumber());
                    if (current != null) {
                        current.setRds(item.getRds());
                        current.setEntryDate(item.getEntryDate());
                        current.setRdHour(item.getRdHour());
                    }
                });
            }
        }
        return PageUtils.build(page, data);
    }

    /**
     * 加载rds和缺失月份
     * 查询用
     *
     * @param result
     * @param year
     * @param companyId
     */
    private void loadRdsAndLackMonth(List<? extends RdEmployeeModel> result, Integer year, Integer companyId) {
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        List<String> enumbers = result.stream().map(RdEmployeeModel::getEnumber).collect(Collectors.toList());
        List<FullYearProjectModel> enumberRdInfos = rdEmployeeDao.getRdTitles(enumbers, companyId, year, true);

        Map<String, FullYearProjectModel> enumberRdMap = AttDataUtils.getKeyAndRdLackMonthMap(enumberRdInfos, year);
        result.forEach(item -> {
            FullYearProjectModel model = enumberRdMap.get(item.getEnumber());
            if (null == model) {
                item.setLackMonth(Constant.DEFAULT_LACK_MONTH);
            } else {
                item.setLackMonth(model.getLackMonth());
                item.setRds(model.getRdTitle());
            }
        });
    }

    /**
     * 加载rds和缺失月份
     * 导出用
     *
     * @param year
     * @param companyId
     * @param result
     */
    private void loadRdsAndLackMonth(Integer year, Integer companyId, List<EmployeeExcel> result) {
        if (CollectionUtils.isEmpty(result)) {
            return;
        }
        List<String> enumbers = result.stream().map(EmployeeExcel::getEnumber).collect(Collectors.toList());
        List<FullYearProjectModel> enumberRdInfos = rdEmployeeDao.getRdTitles(enumbers, companyId, year, true);
        Map<String, FullYearProjectModel> enumberRdMap = AttDataUtils.getKeyAndRdLackMonthMap(enumberRdInfos, year);
        result.forEach(item -> {
            FullYearProjectModel model = enumberRdMap.get(item.getEnumber());
            if (null == model) {
                item.setLackMonth(Constant.DEFAULT_LACK_MONTH);
            } else {
                item.setLackMonth(model.getLackMonth());
                item.setRds(model.getRdTitle());
            }
        });

    }
}
