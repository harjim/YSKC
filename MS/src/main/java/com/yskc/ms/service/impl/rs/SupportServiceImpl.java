package com.yskc.ms.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.dao.rs.SupportDao;
import com.yskc.ms.entity.rs.SupportEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.SupportModel;
import com.yskc.ms.service.rs.SupportService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by hck
 * on 2020/12/4 14:43
 * description:
 */
@Service
public class SupportServiceImpl implements SupportService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SupportDao supportDao;

    @Override
    public List<SupportModel> getSupport(Integer companyId, int year) {
        return supportDao.getSupport(year - 3, year, companyId);
    }

    @Override
    public Boolean deleteSupport(Integer id) {
        return supportDao.deleteById(id) > 0;
    }

    @Override
    public Boolean editSupport(List<SupportModel> models, UserInfo userInfo) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        Date now = new Date();
        List<SupportEntity> updateList = new ArrayList<>();
        List<SupportEntity> insertList = new ArrayList<>();
        models.forEach(item -> {
            if (null != item.getId()) {
                updateList.add(SupportEntity.buildUpdate(item, now, userInfo.getId()));
            } else {
                insertList.add(SupportEntity.buildInsert(item, now, userInfo.getId()));
            }
        });
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (!CollectionUtils.isEmpty(insertList)) {
                supportDao.insertList(insertList);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                supportDao.updateList(updateList);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }
}
