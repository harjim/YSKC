package com.yskc.ms.service.impl.rs;

import com.yskc.ms.dao.rs.FinancialInfoDao;
import com.yskc.ms.entity.rs.FinancialInfoEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.FinanceInfoModel;
import com.yskc.ms.service.rs.FinancialInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/12/4 14:41
 * description:
 */
@Service
public class FinancialInfoServiceImpl implements FinancialInfoService {
    @Autowired
    private FinancialInfoDao financialInfoDao;

    @Override
    public List<FinanceInfoModel> getFinanceInfo(int companyId, int currentYear) {
        List<Integer> years = Arrays.asList(currentYear - 1, currentYear - 2, currentYear - 3);
        return financialInfoDao.getFinanceInfo(companyId, years);
    }

    @Override
    public Boolean editFinance(List<FinanceInfoModel> models, UserInfo userInfo) {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        Date now = new Date();
        List<FinancialInfoEntity> finances = new ArrayList<>();
        models.forEach(item -> {
            finances.add(FinancialInfoEntity.build(item, now, userInfo));
        });
        return financialInfoDao.insertOrUpdate(finances);
    }
}
