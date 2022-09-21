package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.entity.ms.InnovationMember;
import com.yskc.ms.entity.ms.Product;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.GroupRAndDManagementModel;
import com.yskc.ms.models.TotalCostModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.innovationproject.BatchMasterStaffModel;
import com.yskc.ms.models.innovationproject.InnovationMemberModel;
import com.yskc.ms.models.innovationproject.InnovationProjectModel;
import com.yskc.ms.models.project.QueryProjectProgressModel;
import com.yskc.ms.models.rdfunds.QueryFundsModel;
import com.yskc.ms.service.ms.InnovationProjectService;
import com.yskc.ms.utils.ToolUtil;
import com.yskc.ms.utils.TransactionUtils;
import com.yskc.ms.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * @Author: hck
 * @DateTime: 2021/7/24 9:57
 * @Description:
 */
@Service
public class InnovationProjectServiceImpl implements InnovationProjectService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InnovationProjectDao innovationProjectDao;
    @Autowired
    private InnovationMemberDao innovationMemberDao;
    @Autowired
    private ProjectMemberDao projectMemberDao;
    @Autowired
    private ProjectMemberLogDao projectMemberLogDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProjectSummaryDataDao projectSummaryDataDao;


    @Override
    public PageResult getList(QueryProjectProgressModel query, UserInfo userInfo,
                              DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(CollUtil.newArrayList("operationTime"));
        }
        List<InnovationProjectModel> list = innovationProjectDao.getList(page, query, dataPerm);
        loadList(list);
        return PageUtils.buildPageResult(page, list, innovationProjectDao.getTotal(query, dataPerm));
    }

    @Override
    public Boolean setProjectTechIds(BatchMasterStaffModel batch, UserInfo userInfo) throws OwnerException {
        return saveMember(MemberTypeEnum.Tech, batch, userInfo);
    }

    @Override
    public Boolean setFinanceIds(BatchMasterStaffModel batch, UserInfo userInfo) throws OwnerException {
        return saveMember(MemberTypeEnum.FINANCE, batch, userInfo);
    }

    @Override
    public PageResult getTableData(QueryFundsModel model, DataPermModel dataPerm) {
        List<GroupRAndDManagementModel> tableData = new ArrayList<>();
        TotalCostModel totalCostModel = new TotalCostModel();
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setAscs(Arrays.asList("fd1.month"));
        }
        if (0 == page.getSize()) {
            page.setSize(10);
        }
        if (null != model.getMonth()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(model.getMonth());
            int year = calendar.get(Calendar.YEAR);
            model.setYear(year);
            tableData = innovationProjectDao.getTableData(page, model, dataPerm);
            totalCostModel = innovationProjectDao.getTotalCast(page, model, dataPerm);
            if (!CollectionUtils.isEmpty(tableData)) {
                //获取去年当月的时间
                calendar.add(Calendar.YEAR, -1);
                Date lastYear = calendar.getTime();
                //查出去年的研发费用
                model.setMonth(lastYear);
                model.setYear(year - 1);
                List<Integer> customerIds = tableData.stream().map(a -> a.getId()).distinct().collect(Collectors.toList());
                List<GroupRAndDManagementModel> rdExpense = innovationProjectDao.getRDExpense(model, customerIds);
                HashMap<Integer, GroupRAndDManagementModel> map = new HashMap<>();
                rdExpense.forEach(item -> {
                    map.put(item.getId(), item);
                });
                //研发费同期比 和 本年研发费累计与上年数比  赋值
                tableData.forEach(item -> {
                    Integer id = item.getId();
                    if (map.containsKey(id)) {
                        GroupRAndDManagementModel managementModel = map.get(id);
                        //设置研发费同期比%
                        if (null != item.getAmountTST() && null != managementModel.getAmountTST()) {
                            BigDecimal amountTST = new BigDecimal(item.getAmountTST());
                            BigDecimal modelAmountTST = new BigDecimal(managementModel.getAmountTST());
                            if (modelAmountTST.compareTo(BigDecimal.ZERO) != 0) {
                                String string = (amountTST.subtract(modelAmountTST)).divide(modelAmountTST, 4, ROUND_HALF_UP).toString();
                                item.setAmountTST(percentageConversion(string));
                            }
                        } else {
                            item.setAmountTST(null);
                        }

                        //设置本年研发费累计与上年数比%
                        if (null != item.getAmountCompare() && null != managementModel.getAmountCompare()) {
                            BigDecimal amountCompare = new BigDecimal(item.getAmountCompare());
                            BigDecimal modelAmountCompare = new BigDecimal(managementModel.getAmountCompare());
                            if (modelAmountCompare.compareTo(BigDecimal.ZERO) != 0) {
                                String amountString = amountCompare.divide(modelAmountCompare, 4, ROUND_HALF_UP).toString();
                                item.setAmountCompare(percentageConversion(amountString));
                            }
                        } else {
                            item.setAmountCompare(null);
                        }
                    } else {
                        item.setAmountTST(null);
                        item.setAmountCompare(null);
                    }
                    if (null != item.getaDivideR()) {
                        item.setaDivideR(item.getaDivideR().setScale(2, ROUND_HALF_UP));
                    }
                    if (null != item.getaKDivideR()) {
                        item.setaKDivideR(item.getaKDivideR().setScale(2, ROUND_HALF_UP));
                    }
                });
            }
        }
        setTechAndFinanceName(tableData);

        return PageUtils.buildPageResult(page, tableData,totalCostModel);
    }
    
    @Override
    public void getTableExport(QueryFundsModel model, DataPermModel dataPerm, OutputStream out, String path){
        List<GroupRAndDManagementModel> tableData = new ArrayList<>();
        TotalCostModel totalCostModel = new TotalCostModel();

        if (null != model.getMonth()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(model.getMonth());
            int year = calendar.get(Calendar.YEAR);
            model.setYear(year);
            tableData = innovationProjectDao.getTableData( model, dataPerm);
            totalCostModel = innovationProjectDao.getTotalCast(model, dataPerm);
            if (!CollectionUtils.isEmpty(tableData)) {
                //获取去年当月的时间
                calendar.add(Calendar.YEAR, -1);
                Date lastYear = calendar.getTime();
                //查出去年的研发费用
                model.setMonth(lastYear);
                model.setYear(year - 1);
                List<Integer> customerIds = tableData.stream().map(a -> a.getId()).distinct().collect(Collectors.toList());
                List<GroupRAndDManagementModel> rdExpense = innovationProjectDao.getRDExpense(model, customerIds);
                HashMap<Integer, GroupRAndDManagementModel> map = new HashMap<>();
                rdExpense.forEach(item -> {
                    map.put(item.getId(), item);
                });
                //研发费同期比 和 本年研发费累计与上年数比  赋值
                tableData.forEach(item -> {
                    Integer id = item.getId();
                    if (map.containsKey(id)) {
                        GroupRAndDManagementModel managementModel = map.get(id);
                        //设置研发费同期比%
                        if (null != item.getAmountTST() && null != managementModel.getAmountTST()) {
                            BigDecimal amountTST = new BigDecimal(item.getAmountTST());
                            BigDecimal modelAmountTST = new BigDecimal(managementModel.getAmountTST());
                            if (modelAmountTST.compareTo(BigDecimal.ZERO) != 0) {
                                String string = (amountTST.subtract(modelAmountTST)).divide(modelAmountTST, 4, ROUND_HALF_UP).toString();
                                item.setAmountTST(percentageConversion(string));
                            }
                        } else {
                            item.setAmountTST(null);
                        }

                        //设置本年研发费累计与上年数比%
                        if (null != item.getAmountCompare() && null != managementModel.getAmountCompare()) {
                            BigDecimal amountCompare = new BigDecimal(item.getAmountCompare());
                            BigDecimal modelAmountCompare = new BigDecimal(managementModel.getAmountCompare());
                            if (modelAmountCompare.compareTo(BigDecimal.ZERO) != 0) {
                                String amountString = amountCompare.divide(modelAmountCompare, 4, ROUND_HALF_UP).toString();
                                item.setAmountCompare(percentageConversion(amountString));
                            }
                        } else {
                            item.setAmountCompare(null);
                        }
                    } else {
                        item.setAmountTST(null);
                        item.setAmountCompare(null);
                    }
                    if (null != item.getaDivideR()) {
                        item.setaDivideR(item.getaDivideR().setScale(2, ROUND_HALF_UP));
                    }
                    if (null != item.getaKDivideR()) {
                        item.setaKDivideR(item.getaKDivideR().setScale(2, ROUND_HALF_UP));
                    }
                    if (null != item.getAmount()) {
                        item.setAmount(item.getAmount().setScale(2, ROUND_HALF_UP));
                    }
                    if (null != item.getAmount2()) {
                        item.setAmount2(item.getAmount2().setScale(2, ROUND_HALF_UP));
                    }
                    if (null != item.getMaterial()) {
                        item.setMaterial(item.getMaterial().setScale(2, ROUND_HALF_UP));
                    }
                    if (null != item.getMaterial2()) {
                        item.setMaterial2(item.getMaterial2().setScale(2, ROUND_HALF_UP));
                    }
                    if (null != item.getaMinusK()) {
                        item.setaMinusK(item.getaMinusK().setScale(2, ROUND_HALF_UP));
                    }
                    if (null != item.getRevenue()) {
                        item.setRevenue(item.getRevenue().setScale(2, ROUND_HALF_UP));
                    }if (null != item.getRevenue2()) {
                        item.setRevenue2(item.getRevenue2().setScale(2, ROUND_HALF_UP));
                    }

                });
            }
        }
        setTechAndFinanceName(tableData);

        Map<String, Object> dataMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar c = Calendar.getInstance();
        c.setTime(model.getMonth());
        c.add(Calendar.YEAR,1);
        String month = sdf.format(c.getTime());
        dataMap.put("month",month);
        dataMap.put("tableData", tableData);
        YsExcelUtils.generalReport(dataMap, path, (workbook) -> {
            if (workbook != null) {
                try {
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });


    }


    private Boolean saveMember(MemberTypeEnum memberEnum, BatchMasterStaffModel batch, UserInfo userInfo) throws OwnerException {
        List<Integer> innovationIds = batch.getInnovationIds();
        if (innovationProjectDao.getExistProjectId(innovationIds) == null) {
            throw new OwnerException("不存在高新或加计扣除项目。");
        }
        Date now = new Date();
        Integer masterId = batch.getMasterId();
        Integer mType = memberEnum.getId();
        Integer createId = userInfo.getId();
        List<Integer> memberIds = CollectionUtils.isEmpty(batch.getUserIds()) ? new ArrayList<>() : batch.getUserIds();
        List<InnovationMember> innovationMembers = new ArrayList<>();
        innovationIds.forEach(iId -> {
            innovationMembers.add(InnovationMember.build(iId, now, masterId, mType, createId, true));
            memberIds.forEach(mId -> {
                // 当辅助人员存在负责人的时候，跳过该人员。
                if (mId.equals(masterId)) {
                    return;
                }
                innovationMembers.add(InnovationMember.build(iId, now, mId, mType, createId, false));
            });
        });
//        List<ProjectMember> projectMembers = new ArrayList<>();
//        List<ProjectMemberLogEntity> logEntities = new ArrayList<>();
//        List<Integer> projectIds = new ArrayList<>();
//        projectCustomerList.forEach(pm -> {
//            Integer projectId = pm.getId();
//            projectIds.add(projectId);
//            projectMembers.add(ProjectMember.build(userInfo, now, mType, masterId, projectId, pm.getCustomerId()));
//            logEntities.add(ProjectMemberLogEntity.build(projectId, pm.getCustomerId(), mType, masterId, createId, now));
//            memberIds.forEach(mId -> {
//                // 当辅助人员存在负责人的时候，跳过该人员。
//                if (mId.equals(masterId)) {
//                    return;
//                }
//                projectMembers.add(ProjectMember.build(userInfo, now, mType, mId, projectId, pm.getCustomerId()));
//                logEntities.add(ProjectMemberLogEntity.build(projectId, pm.getCustomerId(), mType, mId, createId, now));
//            });
//        });
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            innovationMemberDao.deleteMembers(innovationIds, CollUtil.newArrayList(mType));
            innovationMemberDao.addBatch(innovationMembers);
//            projectMemberDao.deleteMember(CollUtil.newArrayList(mType), projectIds);
//            projectMemberDao.addMemberList(projectMembers);
//            projectMemberLogDao.addLogs(logEntities);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存失败");
        }
        return true;
    }

    private void loadList(List<InnovationProjectModel> list) {
        if (!CollectionUtils.isEmpty(list)) {
            List<Integer> ids = new ArrayList<>();
            Set<Integer> productIds = new HashSet<>();
            Map<String, List<Integer>> productTypeMap = new HashMap<>();
            Integer curProductId;
            String types;
            for (InnovationProjectModel pModel : list) {
                types = pModel.getTypes();
                ids.add(pModel.getId());
                if (productTypeMap.containsKey(types)) {
                    continue;
                }
                for (String cur : types.split(",")) {
                    curProductId = Integer.valueOf(cur);
                    ToolUtil.putAndAdd(productTypeMap, types, curProductId);
                    productIds.add(curProductId);
                }
            }
            Map<Integer, String> productNameMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(productIds)) {
                List<Product> products = productDao.getProductNames(productIds);
                products.forEach(item -> productNameMap.put(item.getId(), item.getProductName()));
            }
            List<InnovationMemberModel> memberList = innovationProjectDao.queryMemberByIds(ids);
            Map<String, List<String>> mapMember = new HashMap<>();
            String keyFormat = "{0}_{1}";
            for (InnovationMemberModel ml : memberList) {
                if (ml.getRealName() == null) {
                    continue;
                }
                ToolUtil.putAndAdd(mapMember, MessageFormat.format(keyFormat, ml.getInnovationId(), ml.getmType()), ml.getRealName());
            }
            for (InnovationProjectModel p : list) {
                // String sign = MessageFormat.format("{0}_{1}", p.getCompanyId(), p.getYear());
                // 技术人员
                String tk = MessageFormat.format(keyFormat, p.getId(), MemberTypeEnum.Tech.getId());
                // 财务人员
                String fk = MessageFormat.format(keyFormat, p.getId(), MemberTypeEnum.FINANCE.getId());
                // 研发部门层级
                if (mapMember.containsKey(tk)) {
                    p.setTechRealName(String.join(",", mapMember.get(tk)));
                }
                if (mapMember.containsKey(fk)) {
                    p.setFinanceRealName(String.join(",", mapMember.get(fk)));
                }
                List<Integer> ptIds = productTypeMap.get(p.getTypes());
                if (!CollectionUtils.isEmpty(ptIds)) {
                    List<String> productNames = new ArrayList<>(ptIds.size());
                    ptIds.forEach(item -> {
                        if (productNameMap.containsKey(item)) {
                            productNames.add(productNameMap.get(item));
                        }
                    });
                    p.setTypesStr(String.join(",", productNames));
                }
            }
        }
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

    private void setTechAndFinanceName(List<GroupRAndDManagementModel> list) {
        if (!CollectionUtils.isEmpty(list)) {
            List<Integer> ids = new ArrayList<>();
            for (GroupRAndDManagementModel pModel : list) {
                ids.add(pModel.getInnovationId());
            }
            List<InnovationMemberModel> memberList = innovationProjectDao.queryMemberByIds(ids);
            Map<String, List<String>> mapMember = new HashMap<>();
            String keyFormat = "{0}_{1}";
            for (InnovationMemberModel ml : memberList) {
                if (ml.getRealName() == null) {
                    continue;
                }
                ToolUtil.putAndAdd(mapMember, MessageFormat.format(keyFormat, ml.getInnovationId(), ml.getmType()), ml.getRealName());
            }
            for (GroupRAndDManagementModel p : list) {
                // 技术人员
                String tk = MessageFormat.format(keyFormat, p.getInnovationId(), MemberTypeEnum.Tech.getId());
                // 财务人员
                String fk = MessageFormat.format(keyFormat, p.getInnovationId(), MemberTypeEnum.FINANCE.getId());
                // 研发部门层级
                if (mapMember.containsKey(tk)) {
                    p.setTechRealName(String.join(",", mapMember.get(tk)));
                }
                if (mapMember.containsKey(fk)) {
                    p.setFinanceRealName(String.join(",", mapMember.get(fk)));
                }
            }
        }
    }
}
