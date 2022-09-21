package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.FlowInstanceRdFeeDao;
import com.yskc.ms.dao.ms.InnovationProjectDao;
import com.yskc.ms.dao.rs.*;
import com.yskc.ms.entity.ms.FlowInstanceRdFee;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.innovationproject.InnovationMemberModel;
import com.yskc.ms.models.projectAudit.*;
import com.yskc.ms.models.rdfunds.*;
import com.yskc.ms.service.ms.RdFeeAuditService;
import com.yskc.ms.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: ms
 * @description: 财务审批业务实现层
 * @author: cyj
 * @create: 2022-04-22 14:56
 **/
@Service
public class RdFeeAuditServiceImpl implements RdFeeAuditService {

    @Autowired
    private InnovationProjectDao innovationProjectDao;
    @Autowired
    private RsProjectDao rsProjectDao;
    @Autowired
    private FlowInstanceRdFeeDao flowInstanceRdFeeDao;
    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private ProjectEnergyDao projectEnergyDao;
    @Autowired
    private ProjectDesignDao projectDesignDao;
    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;
    @Autowired
    private MaterialDao materialDao;


    @Override
    public PageModel getList(QueryFinaAuditModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            List< String > desc = new ArrayList<>();
            desc.add("ongoingCnt");
            desc.add("operationTime");
            page.setDescs(desc);
        }
        List< FinaAuditModelGroup > list = flowInstanceRdFeeDao.getList(page, query, dataPerm);
        if (!CollectionUtils.isEmpty(list)) {
            loadList(list, dataPerm.getUserId());
        }
        return PageUtils.build(page, list);
    }

    @Override
    public List< ? extends RdFinaFundsModel > getRdFunds(QueryRdFundsModel model) {
        List< ? extends RdFinaFundsModel > funds = getFunds(model);
        List< Integer > ids = new ArrayList<>();
        if (!CollectionUtils.isEmpty(funds)) {
            funds.forEach(item -> {
                item.setStatus(FlowInstanceStatusEnum.NO_SUBMIT.getStatus());
                ids.add(item.getId());
            });
        }
        if (!CollectionUtils.isEmpty(ids)) {
            List< RdFinaFundsModel > models = flowInstanceRdFeeDao.getAuditData(ids, model.getUserId());
            if (!CollectionUtils.isEmpty(models)) {
                Map< Integer, RdFinaFundsModel > map = new HashMap<>();
                /*List<Integer> instanceIds = new ArrayList<>();*/
                models.forEach(item -> {
                    /*instanceIds.add(item.getInstanceId());*/
                    map.put(item.getId(), item);
                });

                //获取审核人姓名
                /*List<FlowInstanceModel> realName = flowInstanceDao.getRealName(instanceIds);
                Map<Integer, String> realNameMap = new HashMap<>();
                if (!CollectionUtils.isEmpty(realName)) {
                    for (FlowInstanceModel instanceModel : realName) {
                        realNameMap.put(instanceModel.getId(), instanceModel.getRealName());
                    }
                }*/

                funds.forEach(item -> {
                    RdFinaFundsModel rdFinaFundsModel = map.get(item.getId());
                    if (null != rdFinaFundsModel) {
                        item.setStatus(rdFinaFundsModel.getStatus());
                        item.setInstanceId(rdFinaFundsModel.getInstanceId());
                        item.setHasPermision(rdFinaFundsModel.getHasPermision());
                        item.setAuditUsers(rdFinaFundsModel.getAuditUsers());
                    }
                    /*if (realNameMap.containsKey(item.getInstanceId())) {
                        item.setAuditUsers(realNameMap.get(item.getInstanceId()));
                    }*/
                });
            }
        }
        return funds;
    }

    @Override
    public PageModel< List< EquipmentFeesModel > > getEquipmentFees(QueryRdFeeModel model) {
        Pagination page = model.getPagination();
        Date month = model.getMonth();
        Date monthLastDay = DateUtil.getMonthLastDay(month);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(month);
        int year = calendar.get(Calendar.YEAR);
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(CollUtil.newArrayList("ecode"));
        }
        List< EquipmentFeesModel > list = projectRdEquipmentDao.getCollectionList(page, model, year, monthLastDay);
        return PageUtils.build(page, list);
    }

    @Override
    public PageModel< List< EnergyFeesModel > > getEnergyFees(QueryRdFeeModel model) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List< String > desc = new ArrayList<>();
            desc.add("occDate");
            page.setDescs(desc);
        }
        List< EnergyFeesModel > list = projectEnergyDao.getCollectionList(page, model, DateUtil.getMonthLastDay(model.getMonth()));
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> {
                BigDecimal rdAmount = item.getRdAmount();
                BigDecimal unitPrice = item.getUnitPrice();
                if (null != rdAmount && null != unitPrice && unitPrice.compareTo(BigDecimal.ZERO) != 0) {
                    item.setRdQuantity(rdAmount.divide(unitPrice, 2, BigDecimal.ROUND_HALF_UP));
                }
            });
        }
        return PageUtils.build(page, list);
    }

    @Override
    public PageModel< List< DesignFeesModel > > getDesignFees(QueryRdFeeModel model) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List< String > desc = new ArrayList<>();
            desc.add("designDate");
            page.setDescs(desc);
        }
        Date endDate = com.yskc.common.utils.DateUtil.getMonthLastDay(model.getMonth());
        List< DesignFeesModel > designModels = projectDesignDao.queryProjectDesign(page, model, endDate);
        designModels.forEach(item -> {
            item.setdFee(item.getdFee());
            item.setRdAmount(item.getRdAmount());
        });
        return PageUtils.build(page, designModels);
    }

    @Override
    public PageModel< List< EquipmentPowerFeesModel > > getEquipmentPowerFees(QueryRdFeeModel model) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List< String > desc = new ArrayList<>();
            desc.add("ecode");
            page.setDescs(desc);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getMonth());
        int year = calendar.get(Calendar.YEAR);
        List< EquipmentPowerFeesModel > list = projectRdEquipmentDao.getEquipmentPowerFees(page, model, year);
        list.forEach(item -> {
            if (item.getPowerUnitPrice() != null && item.getRdHour() != null && item.getUsagePower() != null) {
                item.setPowerRate(item.getPowerUnitPrice().multiply(item.getRdHour()).multiply(item.getUsagePower()).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        });
        return PageUtils.build(page, list);
    }

    @Override
    public PageModel< List< RdFeeEmployeeModel > > getEmployeeFees(QueryRdFeeModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(CollUtil.newArrayList("enumber"));
        }
        String year = DateUtil.format(query.getMonth(), "yyyy");
        Date monthEnd = DateUtil.getMonthLastDay(query.getMonth());
        List< RdFeeEmployeeModel > list = projectRdEmployeeDao.getEmployeeFees(page, year, query, monthEnd);
        return PageUtils.build(page, list);
    }

    @Override
    public PageModel< List< RdFeeMaterialModel > > getMaterialFees(QueryRdFeeModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("acqDate"));
        }
        Date monthEnd = DateUtil.getMonthLastDay(query.getMonth());
        List< RdFeeMaterialModel > list = materialDao.getMaterialFeesData(page, query, monthEnd);
        return PageUtils.build(page, list);
    }

    @Override
    public PageModel< List< RdFeeInspectionModel > > getInspectionFees(QueryRdFeeModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("accDate"));
        }
        Date monthEnd = DateUtil.getMonthLastDay(query.getMonth());
        List< RdFeeInspectionModel > list = rsProjectDao.getInspectionFeesData(page, query, monthEnd);
        return PageUtils.build(page, list);
    }

    @Override
    public Map< String, Object > getAuditCnt(QueryRdFeeModel query, Integer userId) {
        Map< String, Object > map = flowInstanceRdFeeDao.getAuditCnt(query, userId);
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    @Override
    public List< ? extends RdFinaFundsModel > getRdFund(QueryRdFundsModel model) {
        Integer rdFeeId = model.getRdFeeId();
        FlowInstanceRdFee rdFee = flowInstanceRdFeeDao.getByRdFeeId(rdFeeId);
        List< ? extends RdFinaFundsModel > funds = new ArrayList<>();
        if (null != rdFee) {
            Date month = rdFee.getMonth();
            model.setProjectId(rdFee.getRsProjectId());
            model.setMonth(rdFee.getMonth());
            model.setCompanyId(rdFee.getCompanyId());
            funds = getFunds(model);
            if (!CollectionUtils.isEmpty(funds)) {
                funds.forEach(item -> item.setMonth(month));
            }
        }
        return funds;
    }

    @Override
    public List< RdFundsAllModel > getRdFundByMonth(QueryRdFundsModel model) {
        Date startMonth = model.getMonth();
        Date endMonth = DateUtil.addMonths(startMonth, 1);
        Integer companyId = model.getCompanyId();
        List< RdFundsAllModel > projectList = rsProjectDao.getProjectListByMonth(startMonth, endMonth, companyId);
        List< RdFundsAllModel > fundsList = rsProjectDao.getProjectFundsAll(model.getMonth(), companyId);
        /* 合计行 */
        RdFundsAllModel totalFundsModel = new RdFundsAllModel();
        totalFundsModel.setPname("合计");
        BigDecimal[] total = new BigDecimal[14];
        if (!CollectionUtils.isEmpty(projectList) && !CollectionUtils.isEmpty(fundsList)) {
            /* 合计 */
            int i = 0;
            for (int j = 0; j < 14; j++) {
                total[j] = BigDecimal.ZERO;
            }
            Integer projectSize = projectList.size();
            for (RdFundsAllModel funds : fundsList) {
                if (i >= projectSize) {
                    break;
                }
                RdFundsAllModel project = projectList.get(i);
                Integer fundsId = funds.getId();
                Integer projectId = project.getId();
                int k = i;
                while (!fundsId.equals(projectId)) {
                    k++;
                    project = projectList.get(k);
                    projectId = project.getId();
                    if (k >= projectSize || projectId > fundsId) {
                        break;
                    }
                }
                if (k >= projectSize) {
                    continue;
                }
                i = k;
                projectList.set(i, funds);
                total[0] = funds.getC10000().add(total[0]);
                total[1] = funds.getC20000().add(total[1]);
                total[2] = funds.getC20100().add(total[2]);
                total[3] = funds.getC20200().add(total[3]);
                total[4] = funds.getC20300().add(total[4]);
                total[5] = funds.getC20500().add(total[5]);
                total[6] = funds.getC20600().add(total[6]);
                total[7] = funds.getC20700().add(total[7]);
                total[8] = funds.getC30000().add(total[8]);
                total[9] = funds.getC40000().add(total[9]);
                total[10] = funds.getC50000().add(total[10]);
                total[11] = funds.getC60400().add(total[11]);
                total[12] = funds.getC69900().add(total[12]);
                total[13] = funds.getTotalAmount().add(total[13]);
                i++;
            }
        }
        totalFundsModel.setC10000(total[0]);
        totalFundsModel.setC20000(total[1]);
        totalFundsModel.setC20100(total[2]);
        totalFundsModel.setC20200(total[3]);
        totalFundsModel.setC20300(total[4]);
        totalFundsModel.setC20500(total[5]);
        totalFundsModel.setC20600(total[6]);
        totalFundsModel.setC20700(total[7]);
        totalFundsModel.setC30000(total[8]);
        totalFundsModel.setC40000(total[9]);
        totalFundsModel.setC50000(total[10]);
        totalFundsModel.setC60400(total[11]);
        totalFundsModel.setC69900(total[12]);
        totalFundsModel.setTotalAmount(total[13]);
        projectList.add(totalFundsModel);
        return projectList;
    }

    private void loadList(List< FinaAuditModelGroup > list, Integer userId) {
        String keyFormat = "{0}_{1}";
        Map< String, List< FinaAuditMonthModel > > companyYearFundMap = new HashMap<>();
        List< FinaAuditMonthModel > finaAuditMonthModelList = flowInstanceRdFeeDao.getFundsAudits(list, userId);
        if (!CollectionUtils.isEmpty(finaAuditMonthModelList)) {
            finaAuditMonthModelList.forEach(item -> ToolUtil.putAndAdd(companyYearFundMap, MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear()), item));
        }
        List< InnovationMemberModel > memberList = innovationProjectDao.queryMemberByIds(list.stream().map(a -> a.getId()).collect(Collectors.toList()));
        Map< String, List< String > > mapMember = new HashMap<>();
        for (InnovationMemberModel ml : memberList) {
            if (ml.getRealName() == null) {
                continue;
            }
            ToolUtil.putAndAdd(mapMember, MessageFormat.format(keyFormat, ml.getInnovationId(), ml.getmType()), ml.getRealName());
        }
        for (FinaAuditModelGroup item : list) {
            item.loadFundAndAudit(companyYearFundMap.get(MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear())));
            // String sign = MessageFormat.format("{0}_{1}", p.getCompanyId(), p.getYear());
            // 技术人员
            String tk = MessageFormat.format(keyFormat, item.getId(), MemberTypeEnum.Tech.getId());
            // 财务人员
            String fk = MessageFormat.format(keyFormat, item.getId(), MemberTypeEnum.FINANCE.getId());
            // 研发部门层级
            if (mapMember.containsKey(tk)) {
                item.setTechRealName(String.join(",", mapMember.get(tk)));
            }
            if (mapMember.containsKey(fk)) {
                item.setFinanceRealName(String.join(",", mapMember.get(fk)));
            }
        }
    }


    private List< ? extends RdFinaFundsModel > getFunds(QueryRdFundsModel model) {
        Date month = model.getMonth();
        Date monthLastDay = DateUtil.getMonthLastDay(month);
        List< ? extends RdFinaFundsModel > list;
        if (10000 == model.getRdType()) {
            list = rsProjectDao.getRdMemberFunds(model, monthLastDay);
        } else if (30000 == model.getRdType()) {
            list = rsProjectDao.getRdEquipmentFunds(model, monthLastDay);
        } else {
            list = rsProjectDao.getRdFunds(model, monthLastDay);
        }
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> {
                if (10000 == model.getRdType() || 30000 == model.getRdType()) {
                    item.addTotal();
                }
                item.setRdType(model.getRdType());
            });
        }
        return list;
    }
}
