package com.yskc.rs.service.impl;

import com.yskc.rs.dao.YearCostDao;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.YearCost.YearCostExModel;
import com.yskc.rs.models.YearCost.YearCostModel;
import com.yskc.rs.service.YearCostService;
import com.yskc.rs.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: rs
 * @description: 年度成本服务层实现类
 * @author: cyj
 * @create: 2022-01-14 11:37
 **/
@Service
public class YearCostServiceImpl implements YearCostService {
    @Autowired
    private YearCostDao yearCostDao;

    @Override
    public YearCostExModel getYearCost(Integer year, Integer companyId) {
        List<YearCostModel> yearCost = yearCostDao.getListByYear(companyId,year);
        //按年分组存入Map
        YearCostExModel model = new YearCostExModel(yearCost,year,companyId);
        return model;
    }

    @Override
    public YearCostExModel getMonthCost( Integer year, Integer month, Integer companyId ) {
        return yearCostDao.getListByMonth(year, month, companyId);
    }

    @Override
    public List<YearCostExModel> getList(Integer companyId) {
        return yearCostDao.getList(companyId);
    }

    @Override
    public Boolean saveYearCost(YearCostExModel exmodel, UserInfo info) {
        List<YearCostModel> list = exmodel.getCosList();
        Date date = new Date();
        Integer id = info.getUserId();
        Integer msId = info.getMsUserId();
        List<YearCostModel> saveList = list.stream().filter(item->item.getCost()!=null).collect(Collectors.toList());
        for(YearCostModel model : saveList){
            model.setCreatorId(id);
            model.setMsCreatorId(msId);
            model.setLastUpdatorId(id);
            model.setMsLastUpdatorId(msId);
            model.setCreateTime(date);
            model.setLastUpdateTime(date);
        }
        List<YearCostModel> delList = list.stream().filter(item->item.getCost()==null).collect(Collectors.toList());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try{
            if(saveList.size()>0){
                yearCostDao.saveYearCost(saveList,info.getCompanyId());
            }
            if(delList.size()>0){
                yearCostDao.delYearCost(delList,info.getCompanyId());
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            TransactionUtils.rollback(transactionStatus);
            return false;
        }
    }

    @Override
    public Boolean delYearCost(List< Object > years, UserInfo info) {
        return yearCostDao.batchDelYearCost(years,info.getCompanyId())>0;
    }


}
