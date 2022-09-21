package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.highTech.HighTechDao;
import com.yskc.rs.dao.highTech.HighTechIncomeDao;
import com.yskc.rs.entity.hightech.HighTechIncomeEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightech.BindHighTechModel;
import com.yskc.rs.models.hightech.HighTechIncomeModel;
import com.yskc.rs.models.hightech.QueryHighTechIncomeModel;
import com.yskc.rs.service.HighTechIncomeService;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.TransactionUtils;
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

    @Autowired
    private HighTechDao highTechDao;

    @Override
    public PageModel<List<HighTechIncomeModel>> getList(Integer companyId, QueryHighTechIncomeModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("bookDate"));
        }
        Date begin = query.getMonth(), end;
        if (null != begin) {
            begin = DateUtil.getMonthFirstDay(begin);
            end = DateUtil.getMonthLastDay(begin);
        } else {
            Integer year = query.getYear();
            if (null == year) {
                year = DateUtil.getCurrentYear();
            }
            begin = DateUtil.getYearFirstDay(year);
            end = DateUtil.getYearLastDay(year);
        }
        return PageUtils.build(page, highTechIncomeDao.getList(page, companyId, query, begin, end));
    }

    @Override
    public boolean add(UserInfo userInfo, HighTechIncomeModel income) {
        return highTechIncomeDao.insert(HighTechIncomeEntity.build(income, userInfo.getUserId(), userInfo.getMsUserId(), userInfo.getCompanyId(), new Date())) > 0;
    }

    @Override
    public boolean edit(UserInfo userInfo, HighTechIncomeModel income) {
        return highTechIncomeDao.updateById(HighTechIncomeEntity.build(income, userInfo.getUserId(), userInfo.getMsUserId(), null, new Date())) > 0;
    }

    @Override
    public boolean deleteList(List<Integer> ids) throws OwnerException {
        if (CollectionUtils.isEmpty(ids)) {
            throw new OwnerException("删除失败，请稍后再试。");
        }
        return highTechIncomeDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean importIncome(UserInfo userInfo, List<HighTechIncomeModel> data) throws OwnerException {
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
            transactionStatus = TransactionUtils.newTransaction();
            for (List<HighTechIncomeEntity> currentInsert : ListUtils.subList(insertList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                highTechIncomeDao.addBatch(currentInsert);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<HighTechIncomeModel> getExportIncome(Integer companyId, QueryHighTechIncomeModel query) {
        Date begin = query.getMonth(), end;
        if (null != begin) {
            begin = DateUtil.getMonthFirstDay(begin);
            end = DateUtil.getMonthLastDay(begin);
        } else {
            Integer year = query.getYear();
            if (null == year) {
                year = DateUtil.getCurrentYear();
            }
            begin = DateUtil.getYearFirstDay(year);
            end = DateUtil.getYearLastDay(year);
        }
        return highTechIncomeDao.getExportIncome(companyId, query, begin, end);
    }

    @Override
    public boolean bindHighTech(UserInfo userInfo, BindHighTechModel bind) throws OwnerException {
        List<Integer> ids = bind.getIds();
        if(CollectionUtils.isEmpty(ids)){
            throw new OwnerException("请先选择高品收入");
        }
        return highTechIncomeDao.updateHighTechId(ids,bind.getHighTechId(),new Date(),userInfo.getUserId(),userInfo.getMsUserId()) > 0;
    }
}
