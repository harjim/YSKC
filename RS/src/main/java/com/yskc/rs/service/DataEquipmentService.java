package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.data.DataEquipmentEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.data.DataEquipmentModel;
import com.yskc.rs.models.data.DataEquipmentModifyModel;
import com.yskc.rs.models.equipment.EquipmentMinModel;
import com.yskc.rs.models.equipment.EquipmentModifyModel;
import com.yskc.rs.models.excel.DataEquipmentExcel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;

import java.util.List;

/**
 * 设备使用记录
 *
 * @author zhangdingfu
 */
public interface DataEquipmentService {
    /**
     * 获取设备使用
     *
     * @param companyId
     * @param queryEquipmentModel
     * @return
     */
    PageModel<List<DataEquipmentModel>> getDataEquitmentList(Integer companyId, QueryProjectEquipmentModel queryEquipmentModel);

    /**
     * 更新设备使用
     *
     * @param de
     * @param info
     * @return
     * @throws OwnerException
     */
    boolean update(DataEquipmentModel de, UserInfo info) throws OwnerException;

    /**
     * 删除设备信息
     *
     * @param companyId
     * @param de
     * @return
     * @throws OwnerException
     */
    boolean delete(Integer companyId, DataEquipmentEntity de) throws OwnerException;

    /**
     * 导入设备使用
     *
     * @param equipmentExcels
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean importInfo(UserInfo userInfo, List<DataEquipmentExcel> equipmentExcels) throws OwnerException;

    /**
     * 设置运行工时
     *
     * @param modifyModels
     * @param info
     * @return
     * @throws OwnerException
     */
    boolean setWorkHour(List<DataEquipmentModifyModel> modifyModels, UserInfo info) throws OwnerException;

    /**
     * 批量删除
     *
     * @param companyId
     * @param modifyModels
     * @return
     * @throws OwnerException
     */
    boolean dels(Integer companyId, List<DataEquipmentModifyModel> modifyModels) throws OwnerException;

    /**
     * 获取设备
     *
     * @param value
     * @param companyId
     * @return
     */
    List<EquipmentMinModel> getEquipment(String value, Integer companyId);

    /**
     * 保存数据
     *
     * @param equipmentEntity
     * @param userInfo
     * @return
     */
    Boolean save(DataEquipmentEntity equipmentEntity, UserInfo userInfo);

    /**
     * 更新设备类型
     *
     * @param info
     * @param modifyModel
     * @return
     */
    Boolean updateEtype(UserInfo info, EquipmentModifyModel modifyModel);

    /**
     * 导出设备使用
     *
     * @param queryEquipmentModel
     * @param info
     * @return
     */
    List<DataEquipmentExcel> exportDataEquipment(UserInfo info, QueryProjectEquipmentModel queryEquipmentModel);
}
