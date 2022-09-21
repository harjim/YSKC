package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.dao.rs.FinancialConditionDao;
import com.yskc.docservice.entity.rs.FinancialConditionEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.FinancialConditionExcel;
import com.yskc.docservice.service.rs.FinancialConditionService;
import com.yskc.docservice.utils.TransactionUtils;
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
 * @CreateTime: 2019-09-25 11:30
 * @Description: FinancialConditionServiceImpl
 */
@Service
public class FinancialConditionServiceImpl implements FinancialConditionService {
    @Autowired
    private FinancialConditionDao financialConditionDao;
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public boolean importFinancialCondition(RsUserInfo info, List<FinancialConditionExcel> excels) throws OwnerException {
        if (excels.size() == 0) {
            return true;
        }
        Date now = new Date();
        List<Integer> yearList = excels.stream().filter(a -> !StringUtils.isEmpty(a.getYear())).map(FinancialConditionExcel::getYear).collect(Collectors.toList());
        List<FinancialConditionEntity> entityList = yearList.size() == 0 ? new ArrayList<>() : financialConditionDao.getDataByTerm(info.getCompanyId(), yearList);
        Map<Integer, FinancialConditionEntity> entityMap = entityList.stream().collect(Collectors.toMap(FinancialConditionEntity::getYear, b -> b));
        List<FinancialConditionEntity> insertList = new ArrayList<>();
        List<FinancialConditionEntity> updateList = new ArrayList<>();
        for (int i = 0; i < excels.size(); i++) {
            FinancialConditionExcel excel = excels.get(i);
            FinancialConditionEntity entity = entityMap.get(excel.getYear());
            if (entity == null) {
                entity = setEntity(info, excel);
                entity.create(info.getUserId(), info.getMsUserId(), now);
                insertList.add(entity);
            } else {
                Integer id = entity.getId();
                entity = setEntity(info, excel);
                entity.setId(id);
                entity.update(info.getUserId(), info.getMsUserId(), now);
                updateList.add(entity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (!CollectionUtils.isEmpty(updateList)) {
                financialConditionDao.batchUpdate(updateList);
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                financialConditionDao.batchInsert(insertList);
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error("importFinancialCondition", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

    private FinancialConditionEntity setEntity(RsUserInfo info, FinancialConditionExcel conditionExcel) {

        FinancialConditionEntity entity = new FinancialConditionEntity();
        entity.setCompanyId(info.getCompanyId());
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
        entity.setNetAssets(conditionExcel.getTotalAssets() != null ? conditionExcel.getNetAssets() : BigDecimal.ZERO);
        entity.setTotalProfit(conditionExcel.getTotalProfit() != null ? conditionExcel.getTotalProfit() : BigDecimal.ZERO);
        return entity;
    }
}
