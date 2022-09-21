package com.yskc.ms.service.ms;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.projectAudit.*;
import com.yskc.ms.models.rdfunds.*;

import java.util.List;
import java.util.Map;

/**
 * @Description: 财务审批业务接口层
 */
public interface RdFeeAuditService {

    /**
     * 获取财务审批数据列表
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel getList(QueryFinaAuditModel query, DataPermModel dataPerm);

    /**
     * 获取RD费用
     * @param model
     * @return
     */
    List<? extends RdFinaFundsModel> getRdFunds(QueryRdFundsModel model);

    /**
     * 设备归集费用查询
     * @param model
     * @return
     */
    PageModel<List<EquipmentFeesModel>> getEquipmentFees(QueryRdFeeModel model);

    /**
     * 动力归集费用查询
     * @param model
     * @return
     */
    PageModel<List<EnergyFeesModel>> getEnergyFees(QueryRdFeeModel model);

    /**
     * 设计归集费用查询
     * @param model
     * @return
     */
    PageModel<List<DesignFeesModel>> getDesignFees(QueryRdFeeModel model);

    /**
     * 设备用电归集费用查询
     * @param model
     * @return
     */
    PageModel<List<EquipmentPowerFeesModel>> getEquipmentPowerFees(QueryRdFeeModel model);

    /**
     * 据项目月份返回人员数据
     * @param query
     * @return
     */
    PageModel<List<RdFeeEmployeeModel>> getEmployeeFees(QueryRdFeeModel query);

    /**
     * 据项目月份返回材料数据
     * @param query
     * @return
     */
    PageModel<List<RdFeeMaterialModel>> getMaterialFees(QueryRdFeeModel query);

    /**
     * 据项目月份返回其他数据
     * @param query
     * @return
     */
    PageModel<List<RdFeeInspectionModel>> getInspectionFees(QueryRdFeeModel query);

    /**
     * 用户可审核数统计
     * @param query
     * @param userId
     * @return
     */
    Map<String,Object> getAuditCnt(QueryRdFeeModel query,Integer userId);

    /**
     * 获取归集审批信息
     * @param model
     * @return
     */
    List<? extends RdFinaFundsModel> getRdFund(QueryRdFundsModel model);

    List< RdFundsAllModel> getRdFundByMonth(QueryRdFundsModel model);
}
