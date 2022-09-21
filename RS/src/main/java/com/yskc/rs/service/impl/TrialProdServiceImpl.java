package com.yskc.rs.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.project.ProjectDocFileDataDao;
import com.yskc.rs.entity.Dept;
import com.yskc.rs.entity.DocFileTrialEntity;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.TrialProdEntity;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.material.MaterialPlanModel;
import com.yskc.rs.models.material.QueryMaterialTrackModel;
import com.yskc.rs.models.trialprod.*;
import com.yskc.rs.service.TrialProdService;
import com.yskc.rs.service.exportFileImpl.TrialProductionDetailForm;
import com.yskc.rs.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;

@Service
public class TrialProdServiceImpl implements TrialProdService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TrialProdDao trialProdDao;
    @Autowired
    private RsConfig rsConfig;

    @Autowired
    private StageDao stageDao;

    @Autowired
    private DocFileTrialDao docFileTrialDao;

    @Autowired
    private MaterialDao materialDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private ProjectDocFileDataDao projectDocFileDataDao;
    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private DeptDao deptDao;

    @Override
    public boolean addTrial(UserInfo userInfo, TrialProdEntity entity) {
        Date now = new Date();
        entity.setZeroSecond();
        entity.setCompanyId(userInfo.getCompanyId());
        entity.setCreateTime(now);
        entity.setLastUpdateTime(now);
        entity.setCreatorId(userInfo.getUserId());
        entity.setMsCreatorId(userInfo.getMsUserId());
        entity.setLastUpdatorId(userInfo.getUserId());
        entity.setMsLastUpdatorId(userInfo.getMsUserId());
        return trialProdDao.insert(entity) > 0;
    }

    @Override
    public boolean editTrial(UserInfo userInfo, TrialProdEntity entity) {

        entity.setZeroSecond();
        entity.setLastUpdatorId(userInfo.getUserId());
        entity.setLastUpdateTime(new Date());
        entity.setMsLastUpdatorId(userInfo.getMsUserId());
        return trialProdDao.updateAllColumn(entity) > 0;

    }

    @Override
    public boolean delTrial(TrialProdEntity entity, UserInfo userInfo) throws OwnerException {
        DocFileTrialEntity docFileTrialEntity = docFileTrialDao.queryByTrialId(userInfo.getCompanyId(), entity.getId());
        if (docFileTrialEntity != null) {
            throw new OwnerException("该试制在文档中存在，无法删除");
        }
        return trialProdDao.deleteById(entity.getId()) > 0;
    }

    @Override
    public PageModel<List<TrialProdModel>> queryTrial(Integer companyId, QueryTrialModel queryTrialModel) {
        Pagination page = queryTrialModel.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("trialDate");
            page.setAscs(ascs);
        }
        return PageUtils.build(page, trialProdDao.queryTrial(page, companyId, queryTrialModel));
    }

    @Override
    public List<StageTrialShowModel> getSummaryList(Integer companyId, Integer projectId,Integer pDocFileId) {
        return TrialProductionDetailForm.buildTableData(trialProdDao.getTrialByProject(projectId, companyId));
    }

    @Override
    public List<StageTrialModel> getDocFileTrials(Integer companyId, Integer projectId, String stage) {
        //获取项目阶段起止时间
        StageEntity stageEntity = stageDao.queryEntityByKey(projectId, stage);
        Date startDate = stageEntity.getBeginDate();
        Date endDate = stageEntity.getEndDate();
        //获取阶段时间内所有未使用试制
        List<StageTrialModel> trialProdSumarryModels = trialProdDao.queryListByStage(companyId, projectId, startDate, endDate);
        for (StageTrialModel model : trialProdSumarryModels) {
            if (model.getStartTime() != null && model.getEndTime() != null) {
                model.setWorkHours(new BigDecimal(model.getMinute()).divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP));
            }
        }
        return trialProdSumarryModels;
    }


    @Override
    public PageModel<List<TrialProdPlanModel>> getTrialPlan(Integer companyId, QueryTrialPlanModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("t.trialDate");
            page.setAscs(ascs);
        }
        List<TrialProdPlanModel> trialProdPlanModels = trialProdDao.queryTrialPlan(page, companyId, query);
        for (TrialProdPlanModel model : trialProdPlanModels) {
            String begin = DateUtil.format(model.getBeginDate(), "yyyy/MM");
            String end = DateUtil.format(model.getEndDate(), "yyyy/MM");
            //项目起止月
            String beginAndEnd = MessageFormat.format("{0}-{1}", begin, end);
            model.setBeginAndEndDate(beginAndEnd);
            //试制时间段
            String startTime = DateUtil.format(model.getStartTime(), "HH:mm:ss");
            String endTime = DateUtil.format(model.getEndTime(), "HH:mm:ss");
            String planTime = "";
            if (model.getStartTime() != null && model.getEndTime() != null) {
                planTime = MessageFormat.format("{0}-{1}", startTime, endTime);
            }
            String trialDate = DateUtil.format(model.getTrialDate(), "yyyy-MM-dd");
            if (!StringUtils.isEmpty(trialDate)) {
                model.setPlanTime(MessageFormat.format("{0} {1}", trialDate, planTime));
                model.setTrialMonth(DateUtil.format(model.getTrialDate(), "yyyy-MM"));
            } else {
                model.setPlanTime("-");
            }
            //项目编号
            //model.setRdIndex(MessageFormat.format("{0}RD{1}",model.getBeginYear().toString(),Integer.valueOf(model.getRdIndex())>9?model.getRdIndex():"0"+model.getRdIndex()));

        }
        return PageUtils.build(page, trialProdPlanModels);
    }

    @Override
    public void exportTrialProdPlan(UserInfo userInfo, OutputStream out, QueryTrialPlanModel queryTrialPlanModel) throws OwnerException, ParseException {
        String templatePath = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "/试制计划模板.xlsx";
        if (StrUtil.isEmpty(templatePath)) {
            throw new OwnerException("导出失败，无法获取模板。");
        }
        String monthStr = "";
        if (queryTrialPlanModel.getMonth() != null) {
            queryTrialPlanModel.setMonthFirstDay(DateUtil.getMonthFirstDay(queryTrialPlanModel.getMonth()));
            queryTrialPlanModel.setMonthLastDay(DateUtil.getMonthLastDay(queryTrialPlanModel.getMonth()));
            Calendar cal = Calendar.getInstance();
            cal.setTime(queryTrialPlanModel.getMonth());
            int month = cal.get(Calendar.MONTH) + 1;
            monthStr = month + "月";
        }
        List<TrialProdPlanModel> trialProdPlanModels = trialProdDao.exportTrialPlanData(userInfo.getCompanyId(), queryTrialPlanModel);
        Integer countPlanPO = 0;
        Integer countActualPO = 0;
        BigDecimal countMainMaterial = new BigDecimal(0.00);
        BigDecimal countAuxMaterial = new BigDecimal(0.00);
        BigDecimal countFuel = new BigDecimal(0.00);
        BigDecimal countPower = new BigDecimal(0.00);
        BigDecimal countGas = new BigDecimal(0.00);
        BigDecimal countSpare = new BigDecimal(0.00);
        String unitStr = "";
        if (!CollectionUtils.isEmpty(trialProdPlanModels)) {
            unitStr = trialProdPlanModels.get(0).getUnit();
        }
        String unit = StringUtils.isEmpty(unitStr) ? "t" : trialProdPlanModels.get(0).getUnit();
        for (TrialProdPlanModel model : trialProdPlanModels) {
            model.setTrialMonth(model.getTrialDate() != null ? DateUtil.format(model.getTrialDate(), "yyyy-MM") : "-");
            countPlanPO += (model.getPlanPO() != null ? model.getPlanPO() : 0);
            countActualPO += (model.getActualPO() != null ? model.getActualPO() : 0);
            countMainMaterial = countMainMaterial.add(model.getMainMaterial() != null ? model.getMainMaterial() : new BigDecimal(0.00));
            countFuel = countFuel.add(model.getFuel() != null ? model.getFuel() : new BigDecimal(0.00));
            countAuxMaterial = countAuxMaterial.add(model.getAuxMaterial() != null ? model.getAuxMaterial() : new BigDecimal(0.00));
            countPower = countPower.add(model.getPower() != null ? model.getPower() : new BigDecimal(0.00));
            countGas = countGas.add(model.getGas() != null ? model.getGas() : new BigDecimal(0.00));
            countSpare = countSpare.add(model.getSpare() != null ? model.getSpare() : new BigDecimal(0.00));
            String begin = DateUtil.format(model.getBeginDate(), "yyyy/MM");
            String end = DateUtil.format(model.getEndDate(), "yyyy/MM");
            //项目起止月
            String beginAndEnd = MessageFormat.format("{0}-{1}", begin, end);
            model.setBeginAndEndDate(beginAndEnd);
            //试制时间段
            String startTime = DateUtil.format(model.getStartTime(), "HH:mm:ss");
            String endTime = DateUtil.format(model.getEndTime(), "HH:mm:ss");
            String planTime = "";
            if (model.getStartTime() != null && model.getEndTime() != null) {
                planTime = MessageFormat.format("{0}-{1}", startTime, endTime);
            }
            String trialDate = DateUtil.format(model.getTrialDate(), "yyyy-MM-dd");
            if (!StringUtils.isEmpty(trialDate)) {
                model.setPlanTime(MessageFormat.format("{0} {1}", trialDate, planTime));
            } else {
                model.setPlanTime("");
            }
            model.setRdIndex(model.getRdTitle());
            model.setBudget(new BigDecimal(model.getEstimateExpense() != null ? model.getEstimateExpense() : 0).divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
            model.setTrial(model.getTrialProd() ? "是" : "否");
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("unit", unit);
        dataMap.put("models", trialProdPlanModels);
        dataMap.put("countPlanPO", countPlanPO);
        dataMap.put("countActualPO", countActualPO);
        dataMap.put("countMainMaterial", countMainMaterial);
        dataMap.put("countAuxMaterial", countAuxMaterial);
        dataMap.put("countFuel", countFuel);
        dataMap.put("countPower", countPower);
        dataMap.put("countGas", countGas);
        dataMap.put("countSpare", countSpare);
        dataMap.put("year", queryTrialPlanModel.getYear());
        dataMap.put("month", monthStr);
        dataMap.put("companyName", userInfo.getCompanyName());
        YsExcelUtils.generalReport(dataMap, templatePath, (workbook) -> {
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
    public Map<String,Object> queryDocFileTrial(Integer companyId, Integer docFileId, Integer projectId, Date month) throws OwnerException {
        Map<String, Object> result = new HashMap<>();
        ProjectDocFileEntity projectDocFile = projectDocFileDao.selectById(docFileId);
        if (projectDocFile == null) {
            throw new OwnerException("文件已删除或不存在,请联系管理员！");
        }
        ProjectEntity projectEntity=projectDao.selectById(projectId);
        String deptName = null;
        if (projectEntity.getDeptId()!=null){
            Dept dept = deptDao.selectById(projectEntity.getDeptId());
            deptName = dept==null?null:dept.getDeptName();
        }

        //2021/6/15  验证报告地点数据来源项目的部门/车间/产线/工艺段
        List<String> strs=new ArrayList<>();
        if(!StringUtils.isEmpty(projectEntity.getDeptName()) && !Objects.equals(projectEntity.getDeptName(),"/")){
            strs.add(projectEntity.getDeptName());
        }
        if(!StringUtils.isEmpty(projectEntity.getWorkshop()) && !Objects.equals(projectEntity.getWorkshop(),"/")){
            strs.add(projectEntity.getWorkshop());
        }
        if(!StringUtils.isEmpty(projectEntity.getProductLine()) && !Objects.equals(projectEntity.getProductLine(),"/")){
            strs.add(projectEntity.getProductLine());
        }
        if(!StringUtils.isEmpty(projectEntity.getProcessSection()) && !Objects.equals(projectEntity.getProcessSection(),"/")){
            strs.add(projectEntity.getProcessSection());
        }
        String place=CollectionUtils.isEmpty(strs)?"": org.apache.commons.lang3.StringUtils.join(strs,"/");
        List<StageTrialModel> list ;
        if (month != null) {
            list = trialProdDao.getTrialByMonth(companyId, projectId, month);
        } else if (projectDocFile.getMonth() != null) {
            list = trialProdDao.getTrialByMonth(companyId, projectId, projectDocFile.getMonth());
        } else {
            list = trialProdDao.queryDocFileTrial(companyId, Arrays.asList(docFileId), projectId);
        }
        if(!CollectionUtils.isEmpty(list)){
            String finalDeptName = deptName;
            list.forEach(item->{
                if (finalDeptName!=null){
                    item.setDeptName(finalDeptName);
                }else {
                    item.setDeptName(place);
                }
            });
        }
        result.put("list", list);
        return result;
    }


    @Override
    public PageModel<List<MaterialPlanModel>> getTrialData(QueryMaterialTrackModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("acqDate");
            page.setAscs(ascs);
        }
        List<MaterialPlanModel> list = new ArrayList<>();
        if (query.getMonth() != null) {
            Date beginDate = DateUtil.getMonthFirstDay(query.getMonth());
            Date endDate = DateUtil.getMonthLastDay(query.getMonth());
            List<Integer> keys = Arrays.asList(20301, 20303, 20304);
            list = materialDao.getMaterialPlanData(page, query.getProjectId(), beginDate, endDate, keys, null);
        }
        return PageUtils.build(page, list);
    }
}
