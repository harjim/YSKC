package com.yskc.ms.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.dao.rs.OwnershipDao;
import com.yskc.ms.entity.rs.OwnershipEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.OwnershipModel;
import com.yskc.ms.service.rs.OwnershipService;
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
 * on 2020/12/4 14:42
 * description:股权架构接口实现类
 */
@Service
public class OwnershipServiceImpl implements OwnershipService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OwnershipDao ownershipDao;

    @Override
    public List<OwnershipModel> getOwnership(Integer companyId) {
        return ownershipDao.getByCompanyId(companyId);
    }

    @Override
    public Boolean deleteOwnership(Integer id) {
        return ownershipDao.deleteById(id) > 0;
    }

    @Override
    public Boolean editInfo(List<OwnershipModel> models, UserInfo userInfo) throws OwnerException {
        Date now = new Date();
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<OwnershipEntity> insertList = new ArrayList<>();
        List<OwnershipEntity> updateList = new ArrayList<>();
        models.forEach(item -> {
            if (null != item.getId()) {
                updateList.add(OwnershipEntity.buildUpdate(item, now, userInfo.getId()));
            } else {
                insertList.add(OwnershipEntity.buildInsert(item, now, userInfo.getId()));
            }
        });

        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (!CollectionUtils.isEmpty(insertList)) {
                ownershipDao.insertList(insertList);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                ownershipDao.updateList(updateList);
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
