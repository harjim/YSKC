package com.yskc.ms.service.impl.ms;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.ms.dao.ms.DeptDao;
import com.yskc.ms.dao.ms.ProjectSummaryDataDao;
import com.yskc.ms.models.GroupRAndDManagementModel;
import com.yskc.ms.models.TotalCostModel;
import com.yskc.ms.service.ms.GroupRAndDManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @DateTime: 2022/1/20 13:43
 * @Description:
 * @author: hsx
 */
@Service
public class GroupRAndDManagementServiceImpl implements GroupRAndDManagementService {

    @Autowired
    private ProjectSummaryDataDao projectSummaryDataDao;
    @Autowired
    private DeptDao deptDao;

    @Override
    public Map<String, Object> getTableData(Date date) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        TotalCostModel totalCast = new TotalCostModel();
        List<GroupRAndDManagementModel> allDept = deptDao.getAllDept();
        if (null != date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Integer year = calendar.get(Calendar.YEAR);
            List<GroupRAndDManagementModel> tableData = projectSummaryDataDao.getTableData(date,year);
            totalCast = projectSummaryDataDao.getTotalCast(date, year);
            calendar.add(Calendar.YEAR, -1);
            Date lastYear = calendar.getTime();
            //查出去年的研发费用
            --year;
            List<GroupRAndDManagementModel> rdExpense = projectSummaryDataDao.getRDExpense(lastYear, year);
            HashMap<Integer, GroupRAndDManagementModel> map = new HashMap<>();
            HashMap<Integer, GroupRAndDManagementModel> dataMap = new HashMap<>();
            rdExpense.forEach(item->{
                map.put(item.getId(), item);
            });
            //研发费同期比 和 本年研发费累计与上年数比  赋值
            tableData.forEach(item->{
                Integer id = item.getId();
                if (map.containsKey(id)) {
                    GroupRAndDManagementModel managementModel = map.get(id);
                    //设置研发费同期比%
                    if (null != item.getAmountTST() && null != managementModel.getAmountTST()) {
                        BigDecimal amountTST = new BigDecimal(item.getAmountTST());
                        BigDecimal modelAmountTST = new BigDecimal(managementModel.getAmountTST());
                        if (modelAmountTST.compareTo(BigDecimal.ZERO) != 0) {
                            String string = (amountTST.subtract(modelAmountTST)).divide(modelAmountTST,4,BigDecimal.ROUND_HALF_UP).toString();
                            item.setAmountTST(percentageConversion(string));
                        } else {
                            item.setAmountTST(null);
                        }
                    } else {
                        item.setAmountTST(null);
                    }

                    //设置本年研发费累计与上年数比%
                    if (null != item.getAmountCompare() && null != managementModel.getAmountCompare()) {
                        BigDecimal amountCompare = new BigDecimal(item.getAmountCompare());
                        BigDecimal modelAmountCompare = new BigDecimal(managementModel.getAmountCompare());
                        if (modelAmountCompare.compareTo(BigDecimal.ZERO) != 0) {
                            String amountString = amountCompare.divide(modelAmountCompare,4,BigDecimal.ROUND_HALF_UP).toString();
                            item.setAmountCompare(percentageConversion(amountString));
                        } else {
                            item.setAmountCompare(null);
                        }
                    } else {
                        item.setAmountCompare(null);
                    }
                }else{
                    item.setAmountTST(null);
                    item.setAmountCompare(null);
                }
                if (null != item.getaDivideR()) {
                    item.setaDivideR(item.getaDivideR().setScale(2,BigDecimal.ROUND_HALF_UP));
                }
                if (null != item.getaKDivideR()) {
                    item.setaKDivideR(item.getaKDivideR().setScale(2,BigDecimal.ROUND_HALF_UP));
                }
                dataMap.put(item.getId(), item);
            });
            allDept.forEach(item->{
                Integer id = item.getId();
                if (dataMap.containsKey(id)) {
                    GroupRAndDManagementModel model = dataMap.get(id);
                    BeanUtil.copyProperties(model,item);
                }
            });
        }
        resultMap.put("data", allDept);
        resultMap.put("footer", totalCast);
        return resultMap;
    }

    //百分比转换方法
    private String percentageConversion(String string) {
        if (null == string) {
            return null;
        }
        Double aDouble = new Double(string);
        DecimalFormat df = new DecimalFormat("0.00%");
        string = df.format(aDouble);
        return string;
    }
}
