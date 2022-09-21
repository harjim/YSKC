package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.RdEmployeeDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.dao.rs.project.ProjectRdAggConfigDao;
import com.yskc.docservice.dao.rs.project.RdEmployeePlanDao;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.entity.rs.project.ProjectRdAggConfig;
import com.yskc.docservice.entity.rs.project.RdEmployeePlanEntity;
import com.yskc.docservice.enums.EmployeeTypeEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.aggragation.AggDeeConfigDetailModel;
import com.yskc.docservice.models.rs.excel.ExcelResult;
import com.yskc.docservice.models.rs.excel.RdPlanExcel;
import com.yskc.docservice.models.rs.initmember.InitMemberModel;
import com.yskc.docservice.models.rs.rdplan.RdPlanAllocationInfoModel;
import com.yskc.docservice.models.rs.rdplan.RdPlanTableField;
import com.yskc.docservice.service.rs.CompanySettingService;
import com.yskc.docservice.service.rs.RdEmployeePlanService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.ToolUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/11/16 17:20
 * description:研发人员计划业务实现类
 */
@Service
public class RdEmployeePlanServiceImpl implements RdEmployeePlanService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final BigDecimal MAX_PLAN_TIME = new BigDecimal(672);

    @Autowired
    private RdEmployeePlanDao rdEmployeePlanDao;
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectRdAggConfigDao projectRdAggConfigDao;
    @Autowired
    private CompanySettingService companySettingService;

    @Override
    public Boolean importPlanTime(RsUserInfo info, List<RdPlanExcel> data) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            return true;
        }
        Integer companyId = info.getCompanyId();
        Set<String> rdTitles = data.stream().map(RdPlanExcel::getRdTitle).collect(Collectors.toSet());
        List<ProjectEntity> projects = projectDao.getByRdTitles(rdTitles, companyId);
        if (CollectionUtils.isEmpty(projects)) {
            throw new OwnerException("导入数据的RD不存在或已删除，请检查后导入！");
        }
        Map<String, ProjectEntity> projectMap = new HashMap<>();
        List<Integer> projectIds = new ArrayList<>();
        for (ProjectEntity project : projects) {
            projectMap.put(project.getRdTitle(), project);
            projectIds.add(project.getId());
        }
        String signKey = "{0}-{1}-{2}";
        List<InitMemberModel> rdEmployees = rdEmployeeDao.getEmployeeList(companyId, projectIds);
        if (CollectionUtils.isEmpty(rdEmployees)) {
            throw new OwnerException("导入的人员在项目中不存在！");
        }
        Map<String, String> employeeMap = new HashMap<>();
        for (InitMemberModel employee : rdEmployees) {
            employeeMap.put(MessageFormat.format(signKey, employee.getEnumber(), employee.getProjectId(), employee.getYear()),
                    employee.getEname());
        }
        Map<String, RdPlanExcel> repetition = new HashMap<>();
        List<RdEmployeePlanEntity> entityList = new ArrayList<>();
        Integer currentYear, msUserId = info.getMsUserId(), userId = info.getUserId();
        String enumber;
        Date now = new Date();
        for (RdPlanExcel plan : data) {
            if (!projectMap.containsKey(plan.getRdTitle())) {
                throw new OwnerException("RD：" + plan.getRdTitle() + "不存在，请检查");
            }
            enumber = plan.getEnumber();
            currentYear = cn.hutool.core.date.DateUtil.year(plan.getMonth());
            ProjectEntity project = projectMap.get(plan.getRdTitle());
            String ekey = MessageFormat.format(signKey, enumber, project.getId(), currentYear);
            if (!employeeMap.containsKey(ekey)) {
                throw new OwnerException(
                        MessageFormat.format(
                                "{0}研发人员名单{2,number,#}年中不存在工号为：{1}的研发人员，请核对信息后再导入！",
                                project.getRdTitle(), enumber, currentYear));
            }
            String ename = employeeMap.get(ekey);
            if (!ename.equals(plan.getEname())) {
                throw new OwnerException(MessageFormat.format("工号：{0}研发人员姓名:{1}与录入信息:{2}不符，请修改后再导入！",
                        enumber, plan.getEname(), ename));
            }
            Date month = DateUtil.getMonthFirstDay(plan.getMonth());
            if (plan.getPlanTime().compareTo(MAX_PLAN_TIME) > 0) {
                throw new OwnerException(MessageFormat.format("RD：{0}，月份：{1}，工号：{2}，月工时分配：{3}，超过最大可分配工时：{4}，请检查！",
                        plan.getRdTitle(), DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT), enumber,
                        plan.getPlanTime().stripTrailingZeros().toPlainString(), MAX_PLAN_TIME));
            }
            String rpKey = MessageFormat.format(signKey, plan.getRdTitle(), enumber, month);
            if (repetition.containsKey(rpKey)) {
                throw new OwnerException
                        (MessageFormat.format("存在重复数据：RD为{0}，月份：{1}，工号：{2}",
                                plan.getRdTitle(), DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT), enumber));
            } else {
                repetition.put(rpKey, plan);
            }
            Date endMonth = DateUtil.getMonthLastDay(month);
            if (endMonth.compareTo(project.getBeginDate()) < 0 || month.compareTo(project.getEndDate()) > 0) {
                throw new OwnerException("RD:" + plan.getRdTitle() + ",月份：" + DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT) + ",不在项目周期内，不能导入！");
            }
            entityList.add(RdEmployeePlanEntity.build(now, companyId, userId, msUserId, plan.getPlanTime(), month, enumber, project.getId()));
        }
        saveData(entityList, null);
        return true;
    }

    @Override
    public Boolean importAllocation(RsUserInfo info, ExcelResult<RdPlanExcel> excelResult, RdPlanTableField tableField) throws OwnerException {
        List<RdPlanExcel> data = excelResult.getData();
        if (CollectionUtils.isEmpty(data)) {
            return true;
        }
        Set<String> enumbers = new HashSet<>();
        RdPlanExcel curItem;
        int curYear, minYear = 0, maxYear = 0, companyId = info.getCompanyId();
        for (int i = 0; i < data.size(); i++) {
            curItem = data.get(i);
            enumbers.add(curItem.getEnumber());
            curYear = cn.hutool.core.date.DateUtil.year(curItem.getMonth());
            minYear = Math.min(curYear, minYear);
            maxYear = Math.max(curYear, maxYear);
        }
        List<InitMemberModel> rdEmployees = rdEmployeeDao.getTermEmployee(companyId, minYear, maxYear, enumbers);
        if (CollectionUtils.isEmpty(rdEmployees)) {
            throw new OwnerException("导入的人员不存在项目成员中，请检查！");
        }
        Map<String, List<InitMemberModel>> rdEmployeeMap = new HashMap<>();
        Set<Integer> projectIds = new HashSet<>();
        String keyFormat = "{0}_{1}";
        rdEmployees.forEach(item -> {
            ToolUtils.putAndAdd(rdEmployeeMap, MessageFormat.format(keyFormat, item.getYear(), item.getEnumber()), item);
            projectIds.add(item.getProjectId());
        });
        List<ProjectEntity> projects = projectDao.getInfoByIds(new ArrayList<>(projectIds));
        if (CollectionUtils.isEmpty(projects)) {
            throw new OwnerException("导入数据的RD不存在或已删除，请检查后导入！");
        }
        Map<Integer, ProjectEntity> projectMap = projects.stream().collect(Collectors.toMap(ProjectEntity::getId, b -> {
            b.setBeginDate(DateUtil.getMonthFirstDay(b.getBeginDate()));
            b.setEndDate(DateUtil.getMonthLastDay(b.getEndDate()));
            return b;
        }, (o, n) -> n));
        int hourBit = companySettingService.getEmployeeHourBit(companyId);
        List<RdEmployeePlanEntity> entityList = new ArrayList<>();
        HashSet<Integer> curProjectIds = new LinkedHashSet<>();
        Date month, now = new Date();
        Integer userId = info.getUserId(), msUserId = info.getMsUserId();
        BigDecimal hundred = new BigDecimal("100"), curPlanTime;
        Map<Integer, AggDeeConfigDetailModel> aggConfigs = tableField.getConfigMap();
        Map<Date, ProjectRdAggConfig> monthConfigs = new HashMap<>();
        List<String> noAllocation = new ArrayList<>(), overAllocation = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            curItem = data.get(i);
            if (curItem.getPlanTime().compareTo(MAX_PLAN_TIME) > 0) {
                throw new OwnerException(MessageFormat.format("第【{0,number,#}】行，月工时分配：{1}，超过最大可分配工时：{2}，请检查！", i + 2,
                        curItem.getPlanTime().stripTrailingZeros().toPlainString(), MAX_PLAN_TIME));
            }
            month = DateUtil.getMonthFirstDay(curItem.getMonth());
            curYear = cn.hutool.core.date.DateUtil.year(month);
            String enumber = curItem.getEnumber();
            List<InitMemberModel> members = rdEmployeeMap.get(MessageFormat.format(keyFormat, curYear, enumber));
            validTerm(curProjectIds, members, month, projectMap, curYear, enumber, i);
            if (curProjectIds.size() == 0) {
                noAllocation.add(MessageFormat.format("第{2,number,#}行月份：{0}，工号：{1}", com.yskc.common.utils.DateUtil.format(month, com.yskc.common.utils.DateUtil.DEFAULT_YYMM_FORMAT), enumber, i + 2));
                continue;
            }
            AggDeeConfigDetailModel curAggConfig = aggConfigs.get(members.get(0).getEtype());
            if (!monthConfigs.containsKey(month)) {
                monthConfigs.put(month, ProjectRdAggConfig.build(companyId, userId, msUserId, now, aggConfigs, tableField.getType(), month));
            }
            if (null == curAggConfig) {
                throw new OwnerException("获取工时分配配置失败，导入失败！");
            }
            curPlanTime = curAggConfig.getEtypeRatio().divide(hundred, 2, BigDecimal.ROUND_HALF_UP).multiply(curItem.getPlanTime());
            ArrayList<Integer> curProjectIdsList = new ArrayList<>(curProjectIds);
            List<BigDecimal> ratios = curAggConfig.getConfigs().get(Math.min(curAggConfig.getConfigs().size(), curProjectIdsList.size()) - 1);
            if (ratios.size() < curProjectIds.size()) {
                overAllocation.add(MessageFormat.format("第{2,number,#}行月份【{0}】工号【{1}】", com.yskc.common.utils.DateUtil.format(month, com.yskc.common.utils.DateUtil.DEFAULT_YYMM_FORMAT), enumber, i + 2));
            }
            for (int j = 0; j < ratios.size(); j++) {
                entityList.add(RdEmployeePlanEntity.build(now, companyId, userId, msUserId, ratios.get(j).divide(hundred, 2, BigDecimal.ROUND_HALF_UP).multiply(curPlanTime).setScale(hourBit,BigDecimal.ROUND_DOWN), month, enumber, curProjectIdsList.get(j)));
            }
        }
        saveData(entityList, RdPlanAllocationInfoModel.build(new ArrayList<>(monthConfigs.values()), enumbers, monthConfigs.keySet(), companyId));
        if (noAllocation.size() > 0) {
            excelResult.msg = MessageFormat.format("{0}；不存在可分配的项目。", String.join("；", noAllocation));
        }
        if (overAllocation.size() > 0) {
            excelResult.msg += MessageFormat.format("{0}；超过最大可分配项目30个，按30个项目分配。", String.join("；", overAllocation));
        }
        return true;
    }

    private void saveData(List<RdEmployeePlanEntity> entityList, RdPlanAllocationInfoModel allocationInfoModel) throws OwnerException {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            if (null != allocationInfoModel) {
                projectRdAggConfigDao.insertOrUpdate(allocationInfoModel.getConfigs());
                rdEmployeePlanDao.deleteData(allocationInfoModel);
            }
            List<List<RdEmployeePlanEntity>> subList = ListUtils.subList(entityList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
            for (List<RdEmployeePlanEntity> current : subList) {
                rdEmployeePlanDao.insertOrUpdate(current);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            throw new OwnerException("导入失败");
        }
    }

    /**
     * 验证区间，当前数据月份要在加入月份之后，项目周期之间
     *
     * @param curProjectIds
     * @param members
     * @param month
     * @param projectMap
     * @param curYear
     * @param enumber
     * @param row
     * @throws OwnerException
     */
    private void validTerm(Set<Integer> curProjectIds, List<InitMemberModel> members, Date month,
                           Map<Integer, ProjectEntity> projectMap, Integer curYear, String enumber, int row) throws OwnerException {
        curProjectIds.clear();
        if (null == members) {
            throw new OwnerException(MessageFormat.format("第{2,number,#}行，工号为：{1}的人员{0,number,#}年未参与任何项目，请核对信息后再导入！", curYear, enumber, row + 2));
        }
        if (EmployeeTypeEnum.notRdType(members.get(0).getEtype())) {
            throw new OwnerException(MessageFormat.format("第{0,number,#}行，工号为：{1}的人员在{2,number,#}年不是【研究人员、技术人员、辅助人员】，请核对信息后再导入！", row + 2, enumber, curYear));
        }
        Date curEntryDate;
        ProjectEntity curProject;
        for (InitMemberModel member : members) {
            curEntryDate = member.getEntryDate();
            // 如果存在进入日期且进入日期比当且月份大，则跳过个数统计
            if (curEntryDate != null && DateUtil.getMonthFirstDay(curEntryDate).compareTo(month) > 0) {
                continue;
            }
            curProject = projectMap.get(member.getProjectId());
            //如果存在项目且当前月份在项目周期内，则统计
            if (curProject != null && curProject.getBeginDate().compareTo(month) <= 0 && curProject.getEndDate().compareTo(month) >= 0) {
                curProjectIds.add(curProject.getId());
            }
        }
    }

}
