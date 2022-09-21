package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.HighTechIncomeDao;
import com.yskc.docservice.entity.rs.HighTechIncomeEntity;
import com.yskc.docservice.models.rs.HighTechIncomeModel;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.service.rs.HighTechIncomeService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.TransactionUtils;
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
 * @Author: hck
 * @DateTime: 2021/5/28 8:18
 * @Description:
 */
@Service
public class HighTechIncomeServiceImpl implements HighTechIncomeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HighTechIncomeDao highTechIncomeDao;

    @Override
    public boolean importIncome(RsUserInfo userInfo, List<HighTechIncomeModel> data) throws OwnerException {
        if(CollectionUtils.isEmpty(data)){
            throw new OwnerException("未读取到数据，请检查导入的模板是否为空，格式是否正确。");
        }
        List<HighTechIncomeEntity> insertList = new ArrayList<>();
        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();
        Integer companyId = userInfo.getCompanyId();
        Date now = new Date();
        for (HighTechIncomeModel incomeModel : data) {
            insertList.add(HighTechIncomeEntity.build(incomeModel, userId, msUserId, companyId, now));
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            for (List<HighTechIncomeEntity> currentInsert : ListUtils.subList(insertList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                highTechIncomeDao.addBatch(currentInsert);
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
