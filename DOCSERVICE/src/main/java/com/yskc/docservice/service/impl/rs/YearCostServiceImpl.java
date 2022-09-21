package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.dao.rs.YearCostDao;
import com.yskc.docservice.entity.rs.MonthCostEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.MonthCostExcel;
import com.yskc.docservice.service.rs.YearCostService;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/docservice/service/impl/rs
 * @Author: hm
 * @CreateTime: 2022/8/16
 * @Description: 月度成本实现层
 */
@Service
public class YearCostServiceImpl implements YearCostService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private YearCostDao yearCostDao;

    /**
     * 导入
     * @params 
     * @return
     */
    @Override
    public void importMonthCost(RsUserInfo userInfo, List< MonthCostExcel > data) throws OwnerException {
        // 去重
        Map<String, MonthCostExcel> dataMap = new HashMap<>();
        data.forEach(item -> {
            String key = item.getYear() + item.getMonth();
            if (!dataMap.containsKey(key)) {
                dataMap.put(key, item);
            }
        });
        data = new ArrayList<>(dataMap.size());
        data.addAll(dataMap.values());

        List< MonthCostEntity > MonthCostList = new ArrayList<>();
        data.stream().
                filter(item -> StringUtils.hasText(item.getYear()) && StringUtils.hasText(item.getYear())).
                forEach(item -> {
                    MonthCostList.addAll(item.excelToEntity(userInfo.getUserId(), userInfo.getMsUserId(), userInfo.getCompanyId()));
                });
        List<MonthCostEntity> existMonthCostList = MonthCostList.stream().filter(item -> item.getCost() != null).collect(Collectors.toList());
        List<MonthCostEntity> existedMonthCostList = MonthCostList.stream().filter(item -> item.getCost() == null).collect(Collectors.toList());
        for (MonthCostEntity monthCostEntity : existMonthCostList) {
            Integer month = monthCostEntity.getMonth();
            Integer year = monthCostEntity.getYear();
            if (month > 12 || month < 1) {
                throw new OwnerException(year + "年不存在" + month + "月!");
            }
            if (monthCostEntity.getCost() < 0) {
                throw new OwnerException((year + "年金额不可为负数!"));
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (existMonthCostList.size() > 0) {
                yearCostDao.insertOrUpdate(existMonthCostList);
            }
            if (existedMonthCostList.size() > 0) {
                yearCostDao.deleteCost(existedMonthCostList);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        }catch (Exception e) {
            logger.error("导入月度成本失败", e);
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            throw new OwnerException("保存数据失败");
        }
    }
}
