package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.SysDictionaryDao;
import com.yskc.rs.dao.project.BudgetDao;
import com.yskc.rs.dao.project.ProjectYearInfoDao;
import com.yskc.rs.entity.project.BudgetEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.entity.project.ProjectYearInfoEntity;
import com.yskc.rs.enums.RsProjectStatusEnum;
import com.yskc.rs.models.SysDictionaryModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.project.BudgetAddandUpdateModel;
import com.yskc.rs.models.project.BudgetInfoModel;
import com.yskc.rs.models.project.BudgetModel;
import com.yskc.rs.models.project.ReportBudgetModel;
import com.yskc.rs.models.summary.TotalSummaryModel;
import com.yskc.rs.service.BudgetService;
import com.yskc.rs.service.exportFileImpl.CostBudgetForm;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: wangxing
 * @CreateTime: 2019-10-29 11:05
 * @Description: 项目资金预算业务层
 */
@Service
public class BudgetServiceImpl extends ServiceImpl<BudgetDao, BudgetEntity> implements BudgetService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BudgetDao budgetDao;
    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectYearInfoDao yearInfoDao;

    /**
     * @param projectId
     * @return
     */
    @Override
    public Map<String, Map<String,Object>> queryBudget(Integer projectId, Boolean fromBudget) {
        ProjectEntity project = projectDao.selectById(projectId);
        CostBudgetForm costBudgetForm = new CostBudgetForm();
       // Map<String, Object> result = costBudgetForm.getProjectBudget(project);
        Map<String, Map<String,Object>> result = new HashMap<>();
        if (fromBudget){
            result = costBudgetForm.getYearBudget(project,project.getEndYear(),project.getBeginYear(),project.getEndYear());
        }else {
            result = costBudgetForm.getYearSummary(project,project.getEndYear());
        }
        List<ProjectYearInfoEntity> yearInfos=yearInfoDao.getInfoByYear(projectId,project.getEndYear());

        Map<Integer,BigDecimal> budgetMap=new HashMap<>();
        if(!CollectionUtils.isEmpty(yearInfos)){
             budgetMap=yearInfos.stream().collect(Collectors.toMap(e->e.getYear(),e->e.getBudget()));
        }
        for (Integer yearKey:budgetMap.keySet()){
            String pYear=yearKey.toString();
            if(result.containsKey(pYear)) {
                Map<String, Object> map = result.get(pYear);
                map.put("budget",budgetMap.get(yearKey));
            }else {
                Map<String, Object> map=new HashMap<>();
                map.put("budget",budgetMap.get(yearKey));
                result.put(pYear,map);
            }
        }

        return result;
    }

    @Override
    public List<ReportBudgetModel> queryProjectBudget(Integer companyId, Integer projectId) {
        return budgetDao.queryProjectBudget(companyId, projectId);
    }

    @Override
    public BigDecimal queryTotalBudget(Integer projectId) {
        List<BudgetModel> list = budgetDao.queryAllBudget(projectId, true);
        return CollectionUtils.isEmpty(list) ? BigDecimal.ZERO : list.get(0).getValue().divide(Constant.TEN_THOUSAND, BigDecimal.ROUND_HALF_UP);
    }

    private Map<String, BigDecimal> getMax(Integer projectId, Date month, Integer companyId) {
        List<BudgetModel> total = budgetDao.querySourceBudget(companyId, projectId, null);
        Map<String, BigDecimal> result = new HashMap<>();
        if (CollectionUtils.isEmpty(total)) {
            return result;
        }
        Map<String, BigDecimal> otherMonth = budgetDao.getOtherMonth(companyId, projectId, month);
        if (CollectionUtils.isEmpty(otherMonth)) {
            otherMonth = new HashMap<>();
        }
        if (null == month) {
            for (String key : otherMonth.keySet()) {
                otherMonth.put(key, otherMonth.get(key).divide(Constant.TEN_THOUSAND, BigDecimal.ROUND_HALF_UP));
            }
            return otherMonth;
        }
        BigDecimal zero = BigDecimal.ZERO;
        for (BudgetModel m : total) {
            result.put(m.getSole(), m.getValue().subtract(otherMonth.getOrDefault(m.getSole(), zero)).divide(Constant.TEN_THOUSAND, BigDecimal.ROUND_HALF_UP));
        }
        return result;
    }


    @Override
    public boolean save(UserInfo userInfo, BudgetInfoModel model) throws OwnerException {
        Date date=new Date();
        if(model.getDataMap()==null){
            return true;
        }
        List<BudgetEntity> budgets=budgetDao.getByProject(model.getProjectId());
        List<BudgetEntity> inserts=new ArrayList<>();
        List<BudgetEntity> updates=new ArrayList<>();
        Map<String,BigDecimal> map=model.getDataMap();
        if(CollectionUtils.isEmpty(budgets)){
            for (String key:map.keySet()){
                BudgetEntity entity=new BudgetEntity(userInfo.getCompanyId(),model.getProjectId(),3,key,map.get(key));
                entity.create(userInfo.getUserId(),userInfo.getMsUserId(),date);
                inserts.add(entity);
            }
        }else {
            Map<String,BudgetEntity> dataMap=budgets.stream().collect(Collectors.toMap(e->e.getKey(),e->e));
            for (String key:model.getDataMap().keySet()){
                if(dataMap.containsKey(key)){
                    BudgetEntity entity=dataMap.get(key);
                    entity.setLastUpdateTime(date);
                    entity.setLastUpdatorId(userInfo.getUserId());
                    entity.setMsLastUpdatorId(userInfo.getMsUserId());
                    entity.setValue(model.getDataMap().get(key));
                    updates.add(entity);
                }else {
                    BudgetEntity entity=new BudgetEntity(userInfo.getCompanyId(),model.getProjectId(),3,key,map.get(key));
                    entity.create(userInfo.getUserId(),userInfo.getMsUserId(),date);
                    inserts.add(entity);
                }
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (!CollectionUtils.isEmpty(updates)) {
                budgetDao.updateBatch(updates);
            }
            if (!CollectionUtils.isEmpty(inserts)) {
                budgetDao.addBatch(inserts);
            }

            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error("save", ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    /**
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean update(UserInfo info, BudgetAddandUpdateModel model) throws OwnerException {
        Integer status = budgetDao.getBudgetStatus(model.getProjectId());
        if (status != null && status == RsProjectStatusEnum.FINALIZATION.getStatus()) {
            throw new OwnerException("保存失败，当前预算已审核。");
        }
        List<BudgetModel> list = model.getBudgetEntities();
        List<BudgetEntity> updateList = new ArrayList<>();
        List<BudgetEntity> insetList = new ArrayList<>();
        Date now = new Date();
        Integer userId = info.getId();
        Integer msUserId = info.getMsUserId();
        Integer companyId = info.getCompanyId();
        Integer projectId = model.getProjectId();
        Date month = model.getMonth();
        for (BudgetModel budgetModel : list) {
            BudgetEntity entity = new BudgetEntity();
            entity.setValue(budgetModel.getValue() == null ? BigDecimal.ZERO : budgetModel.getValue().multiply(Constant.TEN_THOUSAND));
            entity.setType(budgetModel.getType());
            entity.setKey(budgetModel.getKey());
            entity.setProjectId(projectId);
            entity.setMonth(month);
            entity.setLastUpdateTime(now);
            entity.setLastUpdatorId(userId);
            entity.setMsLastUpdatorId(msUserId);
            entity.setCompanyId(companyId);
            if (StringUtils.isEmpty(budgetModel.getId())) {
                entity.setCreateTime(now);
                entity.setCreatorId(userId);
                entity.setMsCreatorId(info.getMsUserId());
                insetList.add(entity);
            } else {
                entity.setId(budgetModel.getId());
                updateList.add(entity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (updateList.size() > 0) {
                budgetDao.updateBatch(updateList);
            }
            if (insetList.size() > 0) {
                budgetDao.addBatch(insetList);
            }
            if (month == null) {
                List<BudgetModel> models = budgetDao.queryAllBudget(model.getProjectId(), true);
                BigDecimal generalBudget = BigDecimal.ZERO;
                for (BudgetModel budgetModel : models) {
                    generalBudget = generalBudget.add(budgetModel.getValue());
                }
                projectDao.updateProjectFee(model.getProjectId(), generalBudget);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error("update", ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    /**
     * @return
     */
    @Override
    public List<SysDictionaryModel> getSysDictionaryModel() {
        return sysDictionaryDao.getDictionaryByBudgetType();
    }

    @Override
    public List<TotalSummaryModel> getSummaryBudget(Integer companyId, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        Date beginDate = cn.hutool.core.date.DateUtil.beginOfYear(calendar.getTime());
        Date endDate = cn.hutool.core.date.DateUtil.endOfYear(beginDate);
        List<TotalSummaryModel> totalSummaries = projectDao.getTotalSummary(companyId, year, beginDate, endDate);
        if (!CollectionUtils.isEmpty(totalSummaries)) {
            List<TotalSummaryModel> models = budgetDao.getBudgetByProjectIds(totalSummaries.stream().map(TotalSummaryModel::getId).collect(Collectors.toList()));
            Map<Integer, TotalSummaryModel> budgetMap = models.stream().collect(Collectors.toMap(TotalSummaryModel::getId, b -> b, (c, d) -> d));
            TotalSummaryModel total = new TotalSummaryModel();
            totalSummaries.forEach(summaryModel -> {
                TotalSummaryModel model = budgetMap.getOrDefault(summaryModel.getId(), new TotalSummaryModel());
                summaryModel.setSalaryValue(model.getSalaryValue() != null ? model.getSalaryValue() : BigDecimal.ZERO);
                summaryModel.setInsuranceValue(model.getInsuranceValue() != null ? model.getInsuranceValue() : BigDecimal.ZERO);
                summaryModel.setMaterialValue(model.getMaterialValue() != null ? model.getMaterialValue() : BigDecimal.ZERO);
                summaryModel.setStimulusValue(model.getStimulusValue() != null ? model.getStimulusValue() : BigDecimal.ZERO);
                summaryModel.setFuelValue(model.getFuelValue() != null ? model.getFuelValue() : BigDecimal.ZERO);
                summaryModel.setTrialProdValue(model.getTrialProdValue() != null ? model.getTrialProdValue() : BigDecimal.ZERO);
                summaryModel.setTrialTestValue(model.getTrialTestValue() != null ? model.getTrialTestValue() : BigDecimal.ZERO);
                summaryModel.setInspectionValue(model.getInspectionValue() != null ? model.getInspectionValue() : BigDecimal.ZERO);
                summaryModel.setProdValue(model.getProdValue() != null ? model.getProdValue() : BigDecimal.ZERO);
                summaryModel.setLabValue(model.getLabValue() != null ? model.getLabValue() : BigDecimal.ZERO);
                summaryModel.setSoftAmortizationValue(model.getSoftAmortizationValue() != null ? model.getSoftAmortizationValue() : BigDecimal.ZERO);
                summaryModel.setPatentAmortizationValue(model.getPatentAmortizationValue() != null ? model.getPatentAmortizationValue() : BigDecimal.ZERO);
                summaryModel.setOtherAmortizationValue(model.getOtherAmortizationValue() != null ? model.getOtherAmortizationValue() : BigDecimal.ZERO);
                summaryModel.setDesignValue(model.getDesignValue() != null ? model.getDesignValue() : BigDecimal.ZERO);
                summaryModel.setTechProcedureValue(model.getTechProcedureValue() != null ? model.getTechProcedureValue() : BigDecimal.ZERO);
                summaryModel.setClinicalTrialsValue(model.getClinicalTrialsValue() != null ? model.getClinicalTrialsValue() : BigDecimal.ZERO);
                summaryModel.setExploreValue(model.getExploreValue() != null ? model.getExploreValue() : BigDecimal.ZERO);
                summaryModel.setBookValue(model.getBookValue() != null ? model.getBookValue() : BigDecimal.ZERO);
                summaryModel.setRdProductionValue(model.getRdProductionValue() != null ? model.getRdProductionValue() : BigDecimal.ZERO);
                summaryModel.setCopyRightValue(model.getCopyRightValue() != null ? model.getCopyRightValue() : BigDecimal.ZERO);
                summaryModel.setBenefitsValue(model.getBenefitsValue() != null ? model.getBenefitsValue() : BigDecimal.ZERO);
                summaryModel.setTravelValue(model.getTravelValue() != null ? model.getTravelValue() : BigDecimal.ZERO);
                summaryModel.setOtherValue(model.getOtherValue() != null ? model.getOtherValue() : BigDecimal.ZERO);
                TotalSummaryModel.sum(summaryModel, total);
            });
            totalSummaries.add(total);
        }
        return totalSummaries;
    }

    @Override
    public Map<String, Object> queryAllBudget(Integer projectId) {
        List<BudgetModel> models = budgetDao.queryAllBudget(projectId, false);
        Map<String, BigDecimal> budgetMap = new HashMap<>();
        for (BudgetModel model : models) {
            Date month = model.getMonth();
            if (month == null) {
                budgetMap.put("总预算", model.getValue().divide(Constant.TEN_THOUSAND, BigDecimal.ROUND_HALF_UP));
            } else {
                budgetMap.put(DateUtil.format(month, DateUtil.DEFAULT_YYMM_FORMAT), model.getValue().divide(Constant.TEN_THOUSAND, BigDecimal.ROUND_HALF_UP));
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("budgetMap", budgetMap);
        result.put("status", budgetDao.getBudgetStatus(projectId));
        return result;
    }
}
