package com.yskc.rs.service.impl;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.dao.company.CompanyHolidayDao;
import com.yskc.rs.entity.company.CompanyHoliday;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.CompanyHolidayModel;
import com.yskc.rs.service.CompanyHolidayService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-05 09:02
 * @Description: 节假日业务实现层
 */
@Service
public class CompanyHolidayServiceImpl implements CompanyHolidayService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyHolidayDao companyHolidayDao;

    @Override
    public List<CompanyHolidayModel> getHolidays(Integer year, Integer companyId) {
        return companyHolidayDao.getHolidays(DateUtil.getYearFirstDay(year), DateUtil.getYearLastDay(year), companyId);
    }

    @Override
    public Boolean saveHoliday(List<CompanyHolidayModel> list, UserInfo userInfo) throws OwnerException {
        List<CompanyHoliday> insertOrUpdate = new ArrayList<>();
        Date now = new Date();
        List<Integer> deleteIds = new ArrayList<>();
        Integer companyId = userInfo.getCompanyId();
        Integer msUserId = userInfo.getMsUserId();
        Integer userId = userInfo.getUserId();
        list.forEach(item -> {
            if (null != item.getId() && StringUtils.isEmpty(item.getHolidays())) {
                deleteIds.add(item.getId());
            } else {
                insertOrUpdate.add(CompanyHoliday.build(item, companyId, msUserId, userId, now));
            }
        });
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            if (!CollectionUtils.isEmpty(deleteIds)) {
                companyHolidayDao.deleteBatchIds(deleteIds);
            }
            if (!CollectionUtils.isEmpty(insertOrUpdate)) {
                companyHolidayDao.insertOrUpdate(insertOrUpdate);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("保存失败");
        }
    }
}
