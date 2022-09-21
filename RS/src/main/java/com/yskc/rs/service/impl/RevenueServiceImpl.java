package com.yskc.rs.service.impl;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.dao.RevenueDao;
import com.yskc.rs.entity.RevenueEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.revenuemanage.AnnualRevenueModel;
import com.yskc.rs.models.revenuemanage.RevenueModel;
import com.yskc.rs.service.RevenueService;
import com.yskc.rs.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

/**
 * @DateTime: 2022/1/14 8:41
 * @Description:
 * @author: hsx
 */
@Service
public class RevenueServiceImpl implements RevenueService {

    @Autowired
    private RevenueDao revenueDao;

    @Override
    public List<AnnualRevenueModel> getList(Integer year, Integer companyId) {
        List<RevenueModel> list = revenueDao.getByYearAndCompanyId(year, companyId);
        Map<Integer, List<RevenueModel>> map = new TreeMap<>();
        List<AnnualRevenueModel> annualList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            if (null == year) {
                list.forEach(item -> {
                    Integer entityYear = item.getYear();
                    if (!map.containsKey(entityYear)) {
                        List<RevenueModel> modelList = new ArrayList<>();
                        modelList.add(item);
                        map.put(entityYear, modelList);
                    } else {
                        map.get(entityYear).add(item);
                    }
                });
                map.keySet().forEach(item->{
                    AnnualRevenueModel annualModel = new AnnualRevenueModel();
                    annualModel.setYear(item);
                    map.get(item).forEach(model->{
                        buildAnnualModel(annualModel,model);
                    });
                    annualList.add(annualModel);
                });
            } else {
                AnnualRevenueModel annualModel = new AnnualRevenueModel();
                annualModel.setYear(year);
                list.forEach(item->{
                    buildAnnualModel(annualModel,item);
                });
                annualList.add(annualModel);
            }
        }
        return annualList;
    }

    @Override
    public Boolean add(AnnualRevenueModel model, UserInfo info) throws Exception{
        Integer year = model.getYear();
        List<RevenueEntity> entityList = revenueDao.checkout(year, info.getCompanyId());
        if (!CollectionUtils.isEmpty(entityList)) {
            throw new OwnerException("该年份下已存在数据，请勿重复添加！");
        }
        Date now = new Date();
        List<RevenueEntity> list = getRevenueList(model);
        list.forEach(item->{
            item.create(info.getUserId(), info.getMsUserId(), now);
            item.setCompanyId(info.getCompanyId());
        });

        return revenueDao.batchInsert(list)>0;
    }

    @Override
    public Boolean edit(AnnualRevenueModel model, UserInfo info) throws Exception{
        Date now = new Date();
        Integer year = model.getYear();
        Integer companyId = info.getCompanyId();
        List<RevenueEntity> revenueList = getRevenueList(model);
        revenueList.forEach(item->{
            item.create(info.getUserId(), info.getMsUserId(), now);
            item.setCompanyId(info.getCompanyId());
        });
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            revenueDao.del(year, companyId);
            revenueDao.batchInsert(revenueList);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionUtils.rollback(transactionStatus);
            return false;
        }
   }

    @Override
    public Boolean del(Integer year, Integer companyId) {
        return revenueDao.del(year, companyId) > 0;
    }

    @Override
    public AnnualRevenueModel getData(Integer year, Integer companyId) {
        List<RevenueModel> list = revenueDao.getByYearAndCompanyId(year, companyId);
        AnnualRevenueModel model = new AnnualRevenueModel();
        if (!CollectionUtils.isEmpty(list)) {
            model.setYear(year);
            list.forEach(item->{
                buildAnnualModel(model,item);
            });
        }
        return model;
    }

    /**
     * 将单个月份的数据组合为年度总数据
     * @param annualModel
     * @param model
     */
    void buildAnnualModel(AnnualRevenueModel annualModel,RevenueModel model) {
        Integer month = model.getMonth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
        BigDecimal revenue = model.getRevenue();
        switch (month) {
            case 1:
                annualModel.setFeb(revenue);
                break;
            case 2:
                annualModel.setMar(revenue);
                break;
            case 3:
                annualModel.setJan(revenue);
                break;
            case 4:
                annualModel.setApr(revenue);
                break;
            case 5:
                annualModel.setMay(revenue);
                break;
            case 6:
                annualModel.setJun(revenue);
                break;
            case 7:
                annualModel.setJul(revenue);
                break;
            case 8:
                annualModel.setAug(revenue);
                break;
            case 9:
                annualModel.setSept(revenue);
                break;
            case 10:
                annualModel.setOct(revenue);
                break;
            case 11:
                annualModel.setNov(revenue);
                break;
            case 12:
                annualModel.setDec(revenue);
                break;
            default:
                break;
        }
    }

    /**
     * 将年度营收类拆分为按月份的RevenueEntity list
     * @param model
     * @return
     * @throws Exception
     */
    public List<RevenueEntity> getRevenueList(AnnualRevenueModel model) throws Exception {
        List<RevenueEntity> list = new ArrayList<>();

        //获取AnnualRevenueModel各个参数的值，根据月份生成相应list
        Field[] f=AnnualRevenueModel.class.getDeclaredFields();
        for(int i=1;i<f.length;i++){
            String attributeName=f[i].getName();
            //将属性名的首字母变为大写，为执行set/get方法做准备
            String methodName=attributeName.substring(0,1).toUpperCase()+attributeName.substring(1);
            Object result;
            try{
                Method getMethod=AnnualRevenueModel.class.getMethod("get"+methodName);
                result=getMethod.invoke(model);
            }catch(NoSuchMethodException e){
                result=f[i].get(model);
            }
            if (null == result) {
                continue;
            }
            RevenueEntity revenue = new RevenueEntity();
            revenue.setYear(model.getYear());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (i > 9) {
                revenue.setMonth(simpleDateFormat.parse(model.getYear() + "-" + i + "-01"));
            } else {
                revenue.setMonth(simpleDateFormat.parse(model.getYear() + "-0" + i + "-01"));
            }
            revenue.setRevenue((BigDecimal) result);
            list.add(revenue);
        }
        return list;
    }

}
