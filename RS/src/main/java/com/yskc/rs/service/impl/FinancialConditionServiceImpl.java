package com.yskc.rs.service.impl;

import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.dao.FinancialConditionDao;
import com.yskc.rs.entity.FinancialConditionEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.FinancialConditionExcel;
import com.yskc.rs.service.FinancialConditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: wangxing
 * @CreateTime: 2019-09-25 11:30
 * @Description: FinancialConditionServiceImpl
 */
@Service
public class FinancialConditionServiceImpl implements FinancialConditionService {
    @Autowired
    private FinancialConditionDao financialConditionDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<FinancialConditionEntity> queryFinancialCond(Integer companyId, Integer year) {
        return financialConditionDao.queryFinancialCondition(companyId, year);
    }

    @Override
    public boolean addFinancialCond(UserInfo info, FinancialConditionEntity entity) throws OwnerException {
        List<Integer> years = new ArrayList<>();
        years.add(entity.getYear());
        // 判断是否已存在数据
        if (!CollectionUtils.isEmpty(financialConditionDao.getDataByTerm(info.getCompanyId(), years))) {
            throw new OwnerException(ErrorEnum.FINANCIALCONDITION_YEAR_EXSIT);
        }
        Integer userId = info.getId();
        Date date = new Date();
        entity.setCreatorId(userId);
        entity.setCreateTime(date);
        entity.setLastUpdatorId(userId);
        entity.setLastUpdateTime(date);
        entity.setCompanyId(info.getCompanyId());
        entity.setGrossOfIndustrial(entity.getGrossOfIndustrial() == null ? BigDecimal.ZERO : entity.getGrossOfIndustrial());
        entity.setAddedOfIndustrial(entity.getAddedOfIndustrial() == null ? BigDecimal.ZERO : entity.getAddedOfIndustrial());
        entity.setTotalFixedAssets(entity.getTotalFixedAssets() == null ? BigDecimal.ZERO : entity.getTotalFixedAssets());
        entity.setFixedAssetsOfInvestment(entity.getFixedAssetsOfInvestment() == null ? BigDecimal.ZERO : entity.getFixedAssetsOfInvestment());
        entity.setNetTotalCashFlow(entity.getNetTotalCashFlow() == null ? BigDecimal.ZERO : entity.getNetTotalCashFlow());
        entity.setNetCashFlowOfOperating(entity.getNetCashFlowOfOperating() == null ? BigDecimal.ZERO : entity.getNetCashFlowOfOperating());
        entity.setAssetLiabilityRatio(entity.getAssetLiabilityRatio() == null ? BigDecimal.ZERO : entity.getAssetLiabilityRatio());
        entity.setTotalExpenditureOfRD(entity.getTotalExpenditureOfRD() == null ? BigDecimal.ZERO : entity.getTotalExpenditureOfRD());
        entity.setLoanAmountOfGovernment(entity.getLoanAmountOfGovernment() == null ? BigDecimal.ZERO : entity.getLoanAmountOfGovernment());
        entity.setDueLoanOfGovernment(entity.getDueLoanOfGovernment() == null ? BigDecimal.ZERO : entity.getDueLoanOfGovernment());
        entity.setTotalTax(entity.getTotalTax() == null ? BigDecimal.ZERO : entity.getTotalTax());
        return financialConditionDao.insert(entity) > 0;


    }

    @Override
    public boolean updateFinancialCond(UserInfo info, FinancialConditionEntity entity) {
        entity.setLastUpdatorId(info.getId());
        entity.setLastUpdateTime(new Date());
        entity.setCompanyId(info.getCompanyId());
        return financialConditionDao.updateFinancial(entity) > 0;
    }

    @Override
    public boolean importFinancialCondition(UserInfo info, List<FinancialConditionExcel> excels) throws OwnerException {
        if (excels.size() == 0) {
            return true;
        }
        List<Integer> yearList = excels.stream().filter(a -> !StringUtils.isEmpty(a.getYear())).map(FinancialConditionExcel::getYear).collect(Collectors.toList());
        List<FinancialConditionEntity> entityList = yearList.size() == 0 ? new ArrayList<>() : financialConditionDao.getDataByTerm(info.getCompanyId(), yearList);
        Map<Integer, FinancialConditionEntity> entityMap = entityList.stream().collect(Collectors.toMap(FinancialConditionEntity::getYear, b -> b));
        try {
            for (int i = 0; i < excels.size(); i++) {
                FinancialConditionExcel excel = excels.get(i);
                FinancialConditionEntity entity = entityMap.get(excel.getYear());
                if (entity == null) {
                    entity = setEntity(info, excel);
                    financialConditionDao.insert(entity);
                } else {
                    Integer id = entity.getId();
                    Integer year = entity.getYear();
                    entity = setEntity(info, excel);
                    entity.setId(id);
                    entity.setYear(year);
                    financialConditionDao.updateById(entity);

                }
            }

        } catch (Exception ex) {
            logger.error("importFinancialCondition", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;

    }

    @Override
    public boolean delFinancialCondition(Integer id) {
        return financialConditionDao.deleteById(id) > 0;
    }


    private FinancialConditionEntity setEntity(UserInfo info, FinancialConditionExcel conditionExcel) {

        FinancialConditionEntity entity = new FinancialConditionEntity();
        entity.setCompanyId(info.getCompanyId());
        entity.setLastUpdateTime(new Date());
        entity.setLastUpdatorId(info.getId());
        entity.setCreateTime(new Date());
        entity.setCreatorId(info.getId());
        entity.setYear(conditionExcel.getYear());
        entity.setAddedOfIndustrial(conditionExcel.getAddedOfIndustrial() != null ? conditionExcel.getAddedOfIndustrial() : BigDecimal.ZERO);
        entity.setAssetLiabilityRatio(conditionExcel.getAssetLiabilityRatio() != null ? conditionExcel.getAssetLiabilityRatio() : BigDecimal.ZERO);
        entity.setBusinessIncome(conditionExcel.getBusinessIncome() != null ? conditionExcel.getBusinessIncome() : BigDecimal.ZERO);
        entity.setDueLoanOfGovernment(conditionExcel.getDueLoanOfGovernment() != null ? conditionExcel.getDueLoanOfGovernment() : BigDecimal.ZERO);
        entity.setFixedAssetsOfInvestment(conditionExcel.getFixedAssetsOfInvestment() != null ? conditionExcel.getFixedAssetsOfInvestment() : BigDecimal.ZERO);
        entity.setCorporateIncomeTax(conditionExcel.getCorporateIncomeTax() != null ? conditionExcel.getCorporateIncomeTax() : BigDecimal.ZERO);
        entity.setGrossOfIndustrial(conditionExcel.getGrossOfIndustrial() != null ? conditionExcel.getGrossOfIndustrial() : BigDecimal.ZERO);
        entity.setLoanAmountOfGovernment(conditionExcel.getLoanAmountOfGovernment() != null ? conditionExcel.getLoanAmountOfGovernment() : BigDecimal.ZERO);
        entity.setMainBusinessIncome(conditionExcel.getMainBusinessIncome() != null ? conditionExcel.getMainBusinessIncome() : BigDecimal.ZERO);
        entity.setNetCashFlowOfOperating(conditionExcel.getNetCashFlowOfOperating() != null ? conditionExcel.getNetCashFlowOfOperating() : BigDecimal.ZERO);
        entity.setNetTotalCashFlow(conditionExcel.getNetTotalCashFlow() != null ? conditionExcel.getNetTotalCashFlow() : BigDecimal.ZERO);
        entity.setTotalFixedAssets(conditionExcel.getTotalFixedAssets() != null ? conditionExcel.getTotalFixedAssets() : BigDecimal.ZERO);
        entity.setTotalTax(conditionExcel.getTotalTax() != null ? conditionExcel.getTotalTax() : BigDecimal.ZERO);
        entity.setDueLoanOfGovernment(conditionExcel.getDueLoanOfGovernment() != null ? conditionExcel.getDueLoanOfGovernment() : BigDecimal.ZERO);
        entity.setTotalExpenditureOfRD(conditionExcel.getTotalExpenditureOfRD() != null ? conditionExcel.getTotalExpenditureOfRD() : BigDecimal.ZERO);
        entity.setNetProfit(conditionExcel.getNetProfit() != null ? conditionExcel.getNetProfit() : BigDecimal.ZERO);
        entity.setTotalAssets(conditionExcel.getTotalAssets() != null ? conditionExcel.getTotalAssets() : BigDecimal.ZERO);
        entity.setNetAssets(conditionExcel.getTotalAssets() != null ? conditionExcel.getTotalAssets() : BigDecimal.ZERO);
        entity.setTotalProfit(conditionExcel.getTotalProfit() != null ? conditionExcel.getTotalProfit() : BigDecimal.ZERO);
        return entity;
    }

    @Override
    public List<FinancialConditionEntity> getFinancialCond(Integer companyId, Integer[] years) {
        List<Integer> yearList = Arrays.asList(years);
        return financialConditionDao.getDataByTerm(companyId, yearList);
    }

    @Override
    public boolean delYear(Integer companyId, Integer year) {
        return financialConditionDao.deleteByYear(companyId, year);
    }


    @Override
    public FinancialConditionEntity getDataByTerm(Integer companyId, List<Integer> years) {
        List<FinancialConditionEntity> entityList = financialConditionDao.getDataByTerm(companyId, years);
        FinancialConditionEntity conditionEntity = new FinancialConditionEntity();
        if (!StringUtils.isEmpty(entityList)) {
            for (FinancialConditionEntity entity : entityList) {
                conditionEntity = entity;
            }
        } else {
            return null;
        }
        return conditionEntity;
    }
}
