package com.yskc.rs.service.impl;

import cn.hutool.core.date.DateUtil;
import com.yskc.rs.dao.ReportFormDao;
import com.yskc.rs.models.reportform.*;
import com.yskc.rs.service.ReportFormService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: rs
 * @description:
 * @author: cyj
 * @create: 2022-01-19 17:36
 **/
@Service
public class ReportFormServiceImpl implements ReportFormService {
    @Autowired
    private ReportFormDao reportFormDao;
    @Override
    public IndexModel getIndex(Integer year, Date month,Integer companyId) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获取年数据
        Map<String,Integer> map = reportFormDao.getIndex(companyId,year);
        Map<String,Integer> newMap = new HashMap<>();
        //年成本
        Map<String,BigDecimal>  yearCost = reportFormDao.getYearCost(companyId,year);
        ComputeModel model = new ComputeModel();
        Field[] f0 = ComputeModel.class.getDeclaredFields();
        //将年数据注入model
        if(map!=null&&!map.isEmpty()){
            map.entrySet().forEach(item->{
                if(item.getValue()!=null&&item.getValue()!=0){
                    newMap.put(item.getKey(), item.getValue());
                }
            });
            for(Field f:f0){
                String fieldName = f.getName();
                String methodName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                Method setMethod = ComputeModel.class.getMethod("set"+methodName,Integer.class);
                setMethod.invoke(model,newMap.get(fieldName));
            }
        }

        //单月数据
        Map<String,BigDecimal> monthData = reportFormDao.getMonthData(companyId,year,month,0);
        //月累计
        Map<String,BigDecimal> monthCount = reportFormDao.getMonthData(companyId,year,month,1);
        //去年单月数据
        Map<String,BigDecimal> lastMonthData = reportFormDao.getLastYearData(companyId,year-1,month,0);
        //去年月累计
        Map<String,BigDecimal> lastMonthCount = reportFormDao.getLastYearData(companyId,year-1,month,1);
        IndexModel index = new IndexModel();
        if(model!=null){
            if(model.getRevenueFcst()!=null&&model.getRevenueFcst()!=0){
                index.getTotalIndex().setRevenueFcst(formatN(model.getRevenueFcst().doubleValue()));
            }
            if(model.getSalesRdFee()!=null&&model.getSalesRdFee()!=0){
                index.getTotalIndex().setSalesRdFee(formatN(model.getSalesRdFee().doubleValue()));
            }
            if(model.getRdFee()!=null&&model.getRdFee()!=0){
                index.getTotalIndex().setRdFee(formatN(model.getRdFee().doubleValue()));
            }
            BeanUtils.copyProperties(model,index.getTotalIndex());
            BeanUtils.copyProperties(model,index.getTechIndex());
        }
        BigDecimal revenue = null;
        BigDecimal m_revenue = null;
        BigDecimal amount = null;
        BigDecimal m_amount = null;
        BigDecimal material = null;
        BigDecimal m_material = null;
        BigDecimal l_amount = null;
        BigDecimal lm_amount = null;
        Field[] f1 = AnalyizeIndexModel.class.getDeclaredFields();
        Field[] f2 = CostModel.class.getDeclaredFields();
        if(monthData!=null){
            if(monthData.get("revenue")!=null){
                revenue = monthData.get("revenue");
            }
            if(monthData.get("amount")!=null){
                amount = monthData.get("amount");
            }
            if(monthData.get("material")!=null){
                material = monthData.get("material");
            }
            //项目占比分析指标
            Map<String,String> m1 = new HashMap<>();
            BigDecimal finalAmount = amount;
            monthData.entrySet().forEach(item->{
                if(item.getValue()!=null&&item.getValue().compareTo(BigDecimal.ZERO)!=0&&finalAmount!=null&&finalAmount.compareTo(BigDecimal.ZERO)!=0){
                    m1.put(item.getKey(),formatNumber(item.getValue().multiply(BigDecimal.valueOf(1000000)).divide(finalAmount,2, BigDecimal.ROUND_HALF_UP)));
                }else {
                    m1.put(item.getKey(),null);
                }
            });
            if(m1!=null){
                for(Field f:f1){
                    String fieldName = f.getName();
                    String methodName= fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                    Method setMethod=AnalyizeIndexModel.class.getMethod("set"+methodName,String.class);
                    setMethod.invoke(index.getProportIndex(),m1.get(fieldName));
                }
            }
            //成本分析指标
            Map<String,String> m2 = new HashMap<>();
            if(yearCost!=null){
                if(monthCount!=null){
                    BigDecimal a = monthCount.get("amount");
                    yearCost.entrySet().forEach(item->{
                        BigDecimal y = item.getValue();
                        if(a!=null&&a.compareTo(BigDecimal.ONE)!=0&&y!=null&&y.compareTo(BigDecimal.ZERO)!=0){
                            m2.put(item.getKey(),formatNumber(a.divide(BigDecimal.valueOf(100)).divide(y,2, BigDecimal.ROUND_HALF_UP)));
                        }else {
                            m2.put(item.getKey(),null);
                        }
                    });
                }

            }
            if(m2!=null){
                for(Field f:f1){
                    String fieldName = f.getName();
                    String methodName= fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                    Method setMethod=AnalyizeIndexModel.class.getMethod("set"+methodName,String.class);
                    setMethod.invoke(index.getCostIndex(),m2.get(fieldName));
                }
            }
        }
        if(monthCount!=null){
            if(monthCount.get("revenue")!=null){
                m_revenue = monthCount.get("revenue");
            }
            if(monthCount.get("amount")!=null){
                m_amount = monthCount.get("amount");
            }
            if(monthCount.get("material")!=null){
                m_material = monthCount.get("material");
            }
        }
        if(lastMonthData!=null){
            if(lastMonthData.get("amount")!=null){
                l_amount = lastMonthData.get("amount");
            }
        }
        if(lastMonthCount!=null){
            if(lastMonthCount.get("amount")!=null){
                lm_amount = lastMonthCount.get("amount");
            }
        }
        Integer rdFee = model.getRdFee();
        Integer salesRdFee = model.getSalesRdFee();
        Integer revenueFcst = model.getRevenueFcst();
        Integer rdEmployeeCount = model.getRdEmployeeCount();
        Integer employeeAmount = model.getEmployeeAmount();
        //项目总标准
        if(revenue!=null&&revenue.compareTo(BigDecimal.ZERO)!=0){
            index.getTotalIndex().setRevenue(formatN(revenue));
        }
        if(m_revenue!=null&&m_revenue.compareTo(BigDecimal.ZERO)!=0) {
            index.getTotalIndex().setM_revenue(formatN(m_revenue));
        }
        //研发费用归集指标 & 项目管理指标
        if(amount!=null&&amount.compareTo(BigDecimal.ZERO)!=0) {
            index.getRdFundsIndex().setAmount(amount.divide(BigDecimal.valueOf(10000),2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(material!=null&&material.compareTo(BigDecimal.ZERO)!=0) {
            index.getRdFundsIndex().setMaterial(material.divide(BigDecimal.valueOf(10000),2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(m_material!=null&&m_material.compareTo(BigDecimal.ZERO)!=0) {
            index.getRdFundsIndex().setM_material(m_material.divide(BigDecimal.valueOf(10000),2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(m_amount!=null&&m_amount.compareTo(BigDecimal.ZERO)!=0){
            index.getRdFundsIndex().setM_amount(m_amount.divide(BigDecimal.valueOf(10000),2, BigDecimal.ROUND_HALF_UP).doubleValue());
            if(m_material!=null&&m_material.compareTo(BigDecimal.ZERO)!=0&&m_revenue!=null&&m_revenue.compareTo(BigDecimal.ZERO)!=0) {
                //m_revenue单位为万元
                String e = formatNumber(m_amount.divide(BigDecimal.valueOf(100)).divide(m_revenue,2, BigDecimal.ROUND_HALF_UP));
                BigDecimal f = m_amount.subtract(m_material);
                index.getRdManageIndex().setMaterialIRdFunds(formatNumber(m_material.divide(BigDecimal.valueOf(100)).divide(m_amount,2, BigDecimal.ROUND_HALF_UP)));
                index.getRdFundsIndex().setTotalAmountIRevunue(e);
                index.getRdManageIndex().setAmountIRevenue(e);
                if(f.compareTo(BigDecimal.ZERO)!=0){
                    index.getRdFundsIndex().setEstimatedCost(f.divide(BigDecimal.valueOf(10000),2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    index.getRdFundsIndex().setAxAmountIRevunue(formatNumber(f.divide(BigDecimal.valueOf(100)).divide(m_revenue,2, BigDecimal.ROUND_HALF_UP)));
                    index.getRdManageIndex().setExAmountIRevunue(formatNumber(f.divide(BigDecimal.valueOf(100)).divide(m_revenue,2, BigDecimal.ROUND_HALF_UP)));
                    if(salesRdFee!=null&&salesRdFee!=0) {
                        BigDecimal c = new BigDecimal(salesRdFee*10000);
                        index.getRdManageIndex().setExAmountIFcst(formatNumber(f.multiply(BigDecimal.valueOf(100)).divide(c,2, BigDecimal.ROUND_HALF_UP)));
                    }
                }
            }
            if(lm_amount!=null&&lm_amount.compareTo(BigDecimal.ZERO)!=0){
                String d = formatNumber(m_amount.multiply(BigDecimal.valueOf(100)).divide(lm_amount,2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(100)));
                index.getRdFundsIndex().setTotalAmountIncrease(d);
                index.getRdManageIndex().setTotalAmountIncrease(d);
                if(amount!=null&&amount.compareTo(BigDecimal.ZERO)!=0){
                    String e = formatNumber(amount.multiply(BigDecimal.valueOf(100)).divide(l_amount,2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(100)));
                    index.getRdFundsIndex().setAmountIncrease(e);
                    index.getRdManageIndex().setAmountIncrease(e);
                }
            }
            if(rdFee!=null&&rdFee!=0){
                BigDecimal a = new BigDecimal(rdFee*10000);
                index.getRdManageIndex().setAmountIRdFee(formatNumber(m_amount.multiply(BigDecimal.valueOf(100)).divide(a,2, BigDecimal.ROUND_HALF_UP)));
                if(salesRdFee!=null&&salesRdFee!=0) {
                    index.getRdManageIndex().setRdFeeSales(formatNumber((double) (rdFee / salesRdFee)));
                }
            }
            if(salesRdFee!=null&&salesRdFee!=0){
                BigDecimal b = new BigDecimal(salesRdFee*10000);
                index.getRdManageIndex().setAmountISalesFee(formatNumber(m_amount.multiply(BigDecimal.valueOf(100)).divide(b,2, BigDecimal.ROUND_HALF_UP)));
            }
        }
        if(rdEmployeeCount!=null&&rdEmployeeCount!=0&&employeeAmount!=null&&employeeAmount!=0){
            BigDecimal d = BigDecimal.valueOf(rdEmployeeCount).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(employeeAmount),2,BigDecimal.ROUND_HALF_UP);
            index.getRdManageIndex().setMember(formatNumber(d));
        }
        //企业成本总额
        if(yearCost!=null){
            for(Field f:f2){
                String fieldName = f.getName();
                String methodName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
                Method setMethod = CostModel.class.getMethod("set"+methodName,String.class);
                BigDecimal d = yearCost.get(fieldName);
                if(d!=null&&d.compareTo(BigDecimal.ZERO)!=0){
                    setMethod.invoke(index.getCost(),formatN(d));
                }
            }
        }
        return index;
    }

    @Override
    public List<MonthReportModel> getMonthReport(Integer year, Integer companyId) {
        List<Map<String, Object>> monthReport = reportFormDao.getMonthReport(companyId, year);
        List<MonthReportModel> collect = monthReport.stream().filter(Objects::nonNull).map(item -> {
            BigDecimal amount = (BigDecimal) item.get("amount");
            BigDecimal m_amount = (BigDecimal) item.get("m_amount");
            BigDecimal revenue = (BigDecimal) item.get("revenue");
            BigDecimal m_revenue = (BigDecimal) item.get("m_revenue");
            BigDecimal material = (BigDecimal) item.get("material");
            BigDecimal m_material = (BigDecimal) item.get("m_material");
            BigDecimal l_amount = (BigDecimal) item.get("l_amount");
            BigDecimal lm_amount = (BigDecimal) item.get("lm_amount");
            Date month = (Date) item.get("month");
            MonthReportModel model = new MonthReportModel();
            if(amount != null&&amount.compareTo(BigDecimal.ZERO)!=0){
                model.setAmount(formatN(amount.divide(BigDecimal.valueOf(10000),2,BigDecimal.ROUND_HALF_UP).doubleValue()));
            }
            if(m_amount != null&&m_amount.compareTo(BigDecimal.ZERO)!=0){
                model.setM_amount(formatN(m_amount.divide(BigDecimal.valueOf(10000),2,BigDecimal.ROUND_HALF_UP).doubleValue()));
            }
            if(revenue != null&&revenue.compareTo(BigDecimal.ZERO)!=0){
                model.setRevenue(formatN(revenue));
            }
            if(m_revenue != null&&m_revenue.compareTo(BigDecimal.ZERO)!=0){
                model.setM_revenue(formatN(m_revenue));
            }
            if(material != null&&material.compareTo(BigDecimal.ZERO)!=0){
                model.setMaterial(formatN(material.divide(BigDecimal.valueOf(10000),2,BigDecimal.ROUND_HALF_UP).doubleValue()));
            }
            if(m_material != null&&m_material.compareTo(BigDecimal.ZERO)!=0){
                model.setM_material(formatN(m_material.divide(BigDecimal.valueOf(10000),2,BigDecimal.ROUND_HALF_UP).doubleValue()));
            }
            if(month != null){
                model.setMonth(DateUtil.month(month));
            }
            if(amount!=null&&amount.compareTo(BigDecimal.ZERO)!=0&&l_amount!=null&&l_amount.compareTo(BigDecimal.ZERO)!=0){
                model.setAmountIncrease(formatNumber(amount.multiply(BigDecimal.valueOf(100)).divide(l_amount,2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(100))));
            }
            if(m_amount!=null){
                if (m_material!=null){
                    BigDecimal a = m_amount.subtract(m_material);
                    if(a.compareTo(BigDecimal.ZERO)!=0){
                        model.setExAmount(formatN(a.divide(BigDecimal.valueOf(10000),2,BigDecimal.ROUND_HALF_UP).doubleValue()));
                    }
                    if((a.compareTo(BigDecimal.ZERO)!=0&&m_revenue!=null&&m_revenue.compareTo(BigDecimal.ZERO)!=0)){
                        model.setExAmountIRevenue(formatNumber(a.divide(BigDecimal.valueOf(100)).divide(m_revenue,2, BigDecimal.ROUND_HALF_UP)));
                    }
                }
                if (m_amount.compareTo(BigDecimal.ZERO)!=0){
                    if(m_revenue!=null&&m_revenue.compareTo(BigDecimal.ZERO)!=0){
                        model.setAmountIRevenue(formatNumber(m_amount.divide(BigDecimal.valueOf(100)).divide(m_revenue,2, BigDecimal.ROUND_HALF_UP)));
                    }
                    if(lm_amount!=null&&lm_amount.compareTo(BigDecimal.ZERO)!=0){
                        model.setmAmountIcnrease(formatNumber(m_amount.multiply(BigDecimal.valueOf(100)).divide(lm_amount,2, BigDecimal.ROUND_HALF_UP).subtract(BigDecimal.valueOf(100))));
                    }
                }
            }
            return model;
        }).collect(Collectors.toList());
        return collect;
    }
    public String formatNumber(BigDecimal t){
        DecimalFormat decimalFormat = new DecimalFormat("0.00#");
        if(t.compareTo(BigDecimal.valueOf(0.01))<0){
            return null;
        }
        return decimalFormat.format(t)+"%";
    }
    public <T> String formatN(T t){
        DecimalFormat decimalFormat = new DecimalFormat("0.00#");
        return decimalFormat.format(t);
    }
    public String formatNumber(Double t){
        DecimalFormat decimalFormat = new DecimalFormat("0.00#");
        if(t<0.01){
            return null;
        }
        return decimalFormat.format(t)+"%";
    }

}
