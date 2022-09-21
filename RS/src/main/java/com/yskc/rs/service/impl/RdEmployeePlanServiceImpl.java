package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.RdEmployeeDao;
import com.yskc.rs.dao.project.RdEmployeePlanDao;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.entity.project.RdEmployeePlanEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employeePlan.*;
import com.yskc.rs.models.excel.RdEmployeePlanExcel;
import com.yskc.rs.models.excel.RdPlanExcel;
import com.yskc.rs.models.init.member.ProjectEmployeeModel;
import com.yskc.rs.service.RdEmployeePlanService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
import com.yskc.rs.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
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

    @Autowired
    private RdEmployeePlanDao rdEmployeePlanDao;
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private RsConfig rsConfig;


    @Override
    public PageModel<List<PlanTimeModel>> getList(Integer companyId, QueryPlanModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("im.enumber");
            page.setAscs(ascs);
        }
        Integer year = query.getYear();
        String format = "{0}-{1}-{2}";
        List<PlanTimeModel> models = rdEmployeePlanDao.getList(query, companyId, page);
        if (!CollectionUtils.isEmpty(models)) {
            List<String> enumbers = models.stream().map(e -> e.getEnumber()).collect(Collectors.toList());
            Map<String, List<RdEmployeePlanModel>> dataMap = new HashMap<>();
            Date begin = DateUtil.getYearFirstDay(year);
            Date end = DateUtil.getYearLastDay(year);
            List<RdEmployeePlanModel> planModels = rdEmployeePlanDao.getByEnumbers(enumbers, companyId, query.getProjectId(), begin, end);
            if (!CollectionUtils.isEmpty(planModels)) {
                for (RdEmployeePlanModel planModel : planModels) {
                    String key = MessageFormat.format(format, planModel.getCompanyId(), planModel.getProjectId(), planModel.getEnumber());
                    if (!dataMap.containsKey(key)) {
                        List<RdEmployeePlanModel> list = new ArrayList<>();
                        dataMap.put(key, list);
                    }
                    dataMap.get(key).add(planModel);
                }
            }
            for (PlanTimeModel timeModel : models) {
                String dataKey = MessageFormat.format(format, timeModel.getCompanyId(), timeModel.getProjectId(), timeModel.getEnumber());
                if (dataMap.containsKey(dataKey)) {
                    BigDecimal total = new BigDecimal(0.00);
                    Map<String, RdEmployeePlanModel> planMap = new HashMap<>();
                    for (RdEmployeePlanModel planModel : dataMap.get(dataKey)) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(planModel.getMonth());
                        int month = cal.get(Calendar.MONTH) + 1;
                        planMap.put("m" + month, planModel);
                        total = total.add(planModel.getPlanTime());
                    }
                    timeModel.setDataMap(planMap);
                    timeModel.setTotalTime(total);
                }
            }
        }
        return PageUtils.build(page, models);
    }

    @Override
    public PageModel<List<RdEmployeePlanTotalModel>> getTotalList(Integer companyId, QueryPlanModel query) {
        Pagination page = query.getPagination();
        List<RdEmployeePlanTotalModel> list = rdEmployeePlanDao.getTotalList(page,companyId,query);
        if (!CollectionUtils.isEmpty(list)){
            List<Map<String, Object>> maps = rdEmployeePlanDao.getProjectPlanTime(list,companyId);
            Map<String, List<Map<String, Object>>> map = new HashMap<>();
            maps.forEach(item->{
                String key = (String) item.get("enumber")+item.get("month");
                ToolUtils.putAndAdd(map,key,item);
            });
            for (RdEmployeePlanTotalModel model : list) {
                String key = model.getEnumber()+DateUtil.format(model.getMonth(),"yyyy-MM-dd")+" 00:00:00.0";
                model.setUsedList(map.get(key));
            }
        }
        return PageUtils.build(page,list);
    }

    @Override
    public boolean save(UserInfo userInfo, List<RdEmployeePlanModel> models) throws OwnerException {
        Date date = new Date();
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<Integer> deleteIds = new ArrayList<>();
        List<RdEmployeePlanEntity> insertList = new ArrayList<>();
        for (RdEmployeePlanModel model : models) {
            if (model.getPlanTime() == null || model.getPlanTime().compareTo(BigDecimal.ZERO) == 0) {
                if (model.getId() != null) {
                    deleteIds.add(model.getId());
                }
                continue;
            }
            RdEmployeePlanEntity entity = new RdEmployeePlanEntity();
            BeanUtils.copyProperties(model, entity);
            entity.setCompanyId(userInfo.getCompanyId());
            entity.setCreateTime(date);
            entity.setLastUpdatorId(userInfo.getUserId());
            entity.setCreatorId(userInfo.getUserId());
            entity.setLastUpdateTime(date);
            entity.setMsCreatorId(userInfo.getMsUserId());
            entity.setMsLastUpdatorId(userInfo.getMsUserId());
            insertList.add(entity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (!CollectionUtils.isEmpty(insertList)) {
                rdEmployeePlanDao.insertOrUpdate(insertList);
            }
            if (!CollectionUtils.isEmpty(deleteIds)) {
                rdEmployeePlanDao.deleteBatchIds(deleteIds);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("saveRdEmployeePlan", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

    @Override
    public Boolean importPlan(UserInfo info, List<RdEmployeePlanExcel> data, int year, Integer projectId) throws OwnerException {
        //校验人员信息
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        int beginMonth;
        int endMonth;
        if (projectEntity.getBeginYear() < year && projectEntity.getEndYear() == year) {
            beginMonth = 1;
            endMonth = cn.hutool.core.date.DateUtil.month(projectEntity.getEndDate()) + 1;
        } else if (projectEntity.getBeginYear() == year && projectEntity.getEndYear() == year) {
            beginMonth = cn.hutool.core.date.DateUtil.month(projectEntity.getBeginDate()) + 1;
            endMonth = cn.hutool.core.date.DateUtil.month(projectEntity.getEndDate()) + 1;
        } else if (projectEntity.getBeginYear() == year && projectEntity.getEndYear() > year) {
            beginMonth = cn.hutool.core.date.DateUtil.month(projectEntity.getBeginDate()) + 1;
            endMonth = 12;
        } else if (projectEntity.getBeginYear() < year && projectEntity.getEndYear() > year) {
            beginMonth = 1;
            endMonth = 12;
        } else {
            throw new OwnerException("数据异常，请联系管理员！");
        }
        List<ProjectEmployeeModel> rdEmployees = rdEmployeeDao.getEmployeeList(info.getCompanyId(), Arrays.asList(projectId));
        Map<String, String> employeeMap = rdEmployees.stream().collect(Collectors.toMap(e -> e.getEnumber(), e -> e.getEname()));
        Date date = new Date();
        List<RdEmployeePlanEntity> rdPalns = new ArrayList<>();
        for (RdEmployeePlanExcel excel : data) {
            if (!employeeMap.containsKey(excel.getEnumber())) {
                throw new OwnerException(
                        MessageFormat.format(
                                "{0}-{1}研发人员名单中不存在工号为：{2}的研发人员，请核对信息后再导入",
                                projectEntity.getRdTitle(), projectEntity.getPname(), excel.getEnumber()));
            }
            if (!excel.getEname().equals(employeeMap.get(excel.getEnumber()))) {
                throw new OwnerException(MessageFormat.format("工号：{0}研发人员姓名{1}与录入信息{2}不符，请修改后再导入！",
                        excel.getEnumber(), excel.getEname(), employeeMap.get(excel.getEnumber())));
            }
            Map<String, Object> beanMap = BeanUtil.beanToMap(excel);
            String format = "m";
            for (int i = 1; i <= 12; i++) {
                if (i < beginMonth || i > endMonth) {
                    continue;
                }
                String month;
                if (i < 10) {
                    month = year + "-0" + i + "-01 00:00:00";
                } else {
                    month = year + "-" + i + "-01 00:00:00";
                }
                BigDecimal time = (BigDecimal) beanMap.get(format + i);
                if (null != time) {
                    RdEmployeePlanEntity entity = new RdEmployeePlanEntity();
                    entity.setCompanyId(info.getCompanyId());
                    entity.setMsLastUpdatorId(info.getMsUserId());
                    entity.setLastUpdateTime(date);
                    entity.setCreatorId(info.getUserId());
                    entity.setLastUpdatorId(info.getUserId());
                    entity.setCreateTime(date);
                    entity.setMsCreatorId(info.getMsUserId());
                    entity.setPlanTime(time);
                    entity.setMonth(cn.hutool.core.date.DateUtil.parseDateTime(month));
                    entity.setEnumber(excel.getEnumber());
                    entity.setProjectId(projectId);
                    rdPalns.add(entity);
                    if (time.compareTo(new BigDecimal(674)) > 0) {
                        throw new OwnerException("月工时分配峰值暂定为674时/月/人，如需调整请联系管理员");
                    }
                }

            }
        }
        if (CollectionUtils.isEmpty(rdPalns)) {
            return true;
        }
        return rdEmployeePlanDao.insertOrUpdate(rdPalns) > 0;
    }

    @Override
    public Boolean importPlanTime(UserInfo info, List<RdPlanExcel> data) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            return true;
        }
        Date date = new Date();
        Set<String> rdTitles = data.stream().map(e -> e.getRdTitle()).collect(Collectors.toSet());
        List<ProjectEntity> projects = projectDao.getByRdTitles(rdTitles, info.getCompanyId());
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
        List<ProjectEmployeeModel> rdEmployees = rdEmployeeDao.getEmployeeList(info.getCompanyId(), projectIds);
        Map<String, String> employeeMap = new HashMap<>();
        if (CollectionUtils.isEmpty(rdEmployees)) {
            throw new OwnerException("导入的研发人员在项目中不存在！");
        }
        for (ProjectEmployeeModel employee : rdEmployees) {
            employeeMap.put(MessageFormat.format(signKey, employee.getEnumber(), employee.getProjectId(), employee.getYear()),
                    employee.getEname());
        }
        Map<String, RdPlanExcel> repetition = new HashMap<>();
        List<RdEmployeePlanEntity> entityList = new ArrayList<>();
        int currentYear;
        for (RdPlanExcel plan : data) {
            if (!projectMap.containsKey(plan.getRdTitle())) {
                throw new OwnerException("RD：" + plan.getRdTitle() + "不存在，请检查");
            }
            currentYear = cn.hutool.core.date.DateUtil.year(plan.getMonth());
            ProjectEntity project = projectMap.get(plan.getRdTitle());
            String ekey = MessageFormat.format(signKey, plan.getEnumber(), project.getId(), currentYear);
            if (!employeeMap.containsKey(ekey)) {
                throw new OwnerException(
                        MessageFormat.format(
                                "{0}研发人员名单{2,number,#}年中不存在工号为：{1}的研发人员，请核对信息后再导入",
                                project.getRdTitle(), plan.getEnumber(), currentYear));
            }
            String enumber = employeeMap.get(ekey);
            if (!enumber.equals(plan.getEname())) {
                throw new OwnerException(MessageFormat.format("工号：{0}研发人员姓名:{1}与录入信息:{2}不符，请修改后再导入！",
                        plan.getEnumber(), plan.getEname(), enumber));
            }
            if (plan.getPlanTime().compareTo(new BigDecimal(672)) > 0) {
                throw new OwnerException("月工时分配峰值暂定为672时/月/人，如需调整请联系管理员");
            }
            Date month = cn.hutool.core.date.DateUtil.beginOfMonth(plan.getMonth());
            String rpKey = MessageFormat.format(signKey, plan.getRdTitle(), plan.getEnumber(), month);
            if (repetition.containsKey(rpKey)) {
                throw new OwnerException
                        (MessageFormat.format("存在重复数据：RD为{0}，月份：{1}，工号：{2}",
                                plan.getRdTitle(), cn.hutool.core.date.DateUtil.format(plan.getMonth(), "yyyy-MM"), plan.getEnumber()));
            } else {
                repetition.put(rpKey, plan);
            }
            if (plan.getMonth().compareTo(project.getBeginDate()) < 0 || plan.getMonth().compareTo(project.getEndDate()) > 0) {
                throw new OwnerException("RD:" + plan.getRdTitle() + ",月份：" + cn.hutool.core.date.DateUtil.format(plan.getMonth(), "yyyy-MM-dd") + ",不在项目周期内，不能导入！");
            }
            RdEmployeePlanEntity planEntity = new RdEmployeePlanEntity();
            planEntity.setCompanyId(info.getCompanyId());
            planEntity.setMsLastUpdatorId(info.getMsUserId());
            planEntity.setLastUpdateTime(date);
            planEntity.setCreatorId(info.getUserId());
            planEntity.setLastUpdatorId(info.getUserId());
            planEntity.setCreateTime(date);
            planEntity.setMsCreatorId(info.getMsUserId());
            planEntity.setPlanTime(plan.getPlanTime());
            planEntity.setMonth(cn.hutool.core.date.DateUtil.beginOfMonth(plan.getMonth()));
            planEntity.setEnumber(plan.getEnumber());
            planEntity.setProjectId(project.getId());
            entityList.add(planEntity);
        }
        if (CollectionUtils.isEmpty(entityList)) {
            return true;
        }
        return rdEmployeePlanDao.insertOrUpdate(entityList) > 0;
    }

    @Override
    public void exportPlan(Integer year, Date[] months, UserInfo userInfo, OutputStream out) throws OwnerException {
        List<String> sheetNames = new ArrayList<>();
        List<Map<String, Object>> resultMap = CollectionUtil.newArrayList(getExportData(months, year, userInfo, sheetNames));
        Map<Integer, List<Map<String, Object>>> finalData = new LinkedHashMap<>();
        finalData.put(0, resultMap);
        String templatePath = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "研发人员计划表模板.xls";
        YsExcelUtils.generalMoreSheetsReport(finalData, templatePath, workbook -> {
            if (workbook != null) {
                try {
                    YsExcelUtils.setSheetName(workbook, sheetNames);
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });

    }


    private List<Map<String, Object>> getExportData(Date[] months, Integer year, UserInfo userInfo, List<String> sheetNames) throws OwnerException {
        String companyName = userInfo.getCompanyName();
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<ExportRdEmployeePlanModel> planModels = rdEmployeePlanDao.getByMonths(userInfo.getCompanyId(), months, year);
        if (CollectionUtils.isEmpty(planModels)) {
            sheetNames.add("研发人员计划");
            dataList.add(getDefaultExport(year, companyName));
            return dataList;
        }
        Map<String, List<ExportRdEmployeePlanModel>> monthExport = new LinkedHashMap<>();
        planModels.forEach(item -> {
            String month = DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT);
            ToolUtils.putAndAdd(monthExport, month, item);
        });
        if (CollectionUtils.isEmpty(monthExport)) {
            sheetNames.add("研发人员计划");
            dataList.add(getDefaultExport(year, companyName));
        } else {
            monthExport.keySet().forEach(key -> {
                sheetNames.add(key);
                Map<String, Object> map = new HashMap<>();
                map.put("title", MessageFormat.format("{0}{1}研发人员计划", companyName, key));
                List<ExportRdEmployeePlanModel> list = monthExport.get(key);
                CollUtil.sort(list, new Comparator<ExportRdEmployeePlanModel>() {
                    @Override
                    public int compare(ExportRdEmployeePlanModel o1, ExportRdEmployeePlanModel o2) {
                        return o1.getRdTitle().compareTo(o2.getRdTitle());
                    }
                });
                map.put("list", list);
                dataList.add(map);
            });
        }
        CollUtil.reverse(dataList);
        return dataList;
    }

    Map<String, Object> getDefaultExport(Integer year, String companyName) {
        Map<String, Object> map = new HashMap<>();
        map.put("title", MessageFormat.format("{0,number,#}年{1}研发人员计划", year, companyName));
        map.put("list", new ArrayList<>());
        return map;
    }
}
