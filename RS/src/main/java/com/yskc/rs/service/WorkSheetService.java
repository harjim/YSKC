package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.voucher.VoucherAccount;
import com.yskc.rs.models.voucher.VoucherInfo;
import com.yskc.rs.models.workSheet.*;

import java.util.List;

/**
 * 工单服务
 *
 * @author huronghua
 */
public interface WorkSheetService {
    /**
     * 获取人员工单
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<WorkSheetModel>
     * @throws OwnerException
     */
    List<WorkSheetModel> getStaffWorkSheetList(UserInfo userInfo, WorkSheetQuery workSheetQuery) throws OwnerException;

    /**
     * 获取人员工单
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<MonthWorkSheetItem>
     * @throws OwnerException
     */
    List<WorkSheetMonthModel> getMonthWorkSheetList(UserInfo userInfo, WorkSheetQuery workSheetQuery) throws OwnerException;


    /**
     * 获取研发人员凭证
     * @param userInfo
     * @param workSheetQuery
     * @return
     * @throws OwnerException
     */
    List<VoucherInfo> getWorkVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery) throws OwnerException;
    /**
     * 获取设备工单
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<WorkSheetModel>
     * @throws OwnerException
     */
    List<WorkSheetModel> getEquipmentWorkSheetList(UserInfo userInfo, WorkSheetQuery workSheetQuery);

    /**
     * 获取能源工单
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<WorkSheetModel>
     * @throws OwnerException
     */
    List<WorkSheetModel> getEnergyWorkSheetList(UserInfo userInfo, WorkSheetQuery workSheetQuery) throws OwnerException;

    /**
     * 获取修理检测...工单
     *
     * @param info
     * @param workSheetQuery
     * @return List<WorkSheetModel>
     */
    List<WorkSheetModel> getInspectionList(UserInfo info, WorkSheetQuery workSheetQuery);

    /**
     * 获取月工单
     *
     * @param info
     * @param workSheetQuery
     * @return List<WorkSheetMonthModel>
     */
    List<WorkSheetMonthModel> getInspectionListByMonth(UserInfo info, WorkSheetQuery workSheetQuery);

    /**
     * 获取设计工单
     *
     * @param info
     * @param workSheetQuery
     * @return List<WorkSheetModel>
     */
    List<WorkSheetModel> getDesignList(UserInfo info, WorkSheetQuery workSheetQuery);

    /**
     * 通过月份获取设计工单数据
     *
     * @param info
     * @param workSheetQuery
     * @return List<WorkSheetMonthModel>
     */
    List<WorkSheetMonthModel> getDesignListByMonth(UserInfo info, WorkSheetQuery workSheetQuery);

    /**
     * 获取材料工单
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<WorkSheetModel>
     */
    List<WorkSheetModel> getMaterialWorkSheetList(UserInfo userInfo, WorkSheetQuery workSheetQuery);

    /**
     * 获取设备月工单
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<WorkSheetMonthModel>
     */
    List<WorkSheetMonthModel> getEquipmentWorkSheetMonthList(UserInfo userInfo, WorkSheetQuery workSheetQuery);

    /**
     * 获取能源月工单
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<WorkSheetMonthModel>
     * @throws OwnerException
     */
    List<WorkSheetMonthModel> getEnergyWorkSheetMonthList(UserInfo userInfo, WorkSheetQuery workSheetQuery) throws OwnerException;

    /**
     * 获取材料月工单
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<WorkSheetMonthModel>
     */
    List<WorkSheetMonthModel> getMaterialWorkSheetMonthList(UserInfo userInfo, WorkSheetQuery workSheetQuery);

    /**
     * 获取研发设备凭证
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<VoucherModel>
     */
    List<VoucherModel> getEquipmentVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery);

    /**
     * 获取研发能源凭证
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<VoucherModel>
     * @throws OwnerException
     */
    List<VoucherModel> getEnergyVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery) throws OwnerException;

    /**
     * 获取研发材料凭证
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<VoucherModel>
     */
    List<VoucherModel> getMaterialVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery);

    /**
     * 获取设计费用凭证
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<VoucherModel>
     */
    List<VoucherModel> getDesignListVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery);


    /**
     * 获取费用数据凭证
     *
     * @param userInfo
     * @param workSheetQuery
     * @return List<VoucherModel>
     */
    List<VoucherModel> getInspectionVoucherList(UserInfo userInfo, WorkSheetQuery workSheetQuery);

    /**
     * 保存凭证科目
     * @param userInfo
     * @param voucherAccount
     * @return
     * @throws OwnerException
     */
    Boolean saveVoucherAccount(UserInfo userInfo, VoucherAccount voucherAccount)throws OwnerException;
}
