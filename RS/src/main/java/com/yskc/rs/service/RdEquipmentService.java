package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.RdEquipmentEntity;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.KeysAndIdsModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.equipment.EquipmentModel;
import com.yskc.rs.models.equipment.EquipmentModifyModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.excel.EquipmentExcel;
import com.yskc.rs.models.projectequipment.DocEquipmentModel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import com.yskc.rs.models.rdequipment.AddRdEquipmentModel;
import com.yskc.rs.models.rdequipment.RdEquipmentModel;

import java.io.OutputStream;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-02 09:14
 * @Description: 研发设备业务层接口
 */
public interface RdEquipmentService {
    /**
     * 获取研发设备
     *
     * @param userInfo
     * @param queryModel
     * @return
     */
    PageModel<List<RdEquipmentModel>> getList(UserInfo userInfo, QueryEquipmentModel queryModel);

    /**
     * 获取设备列表
     *
     * @param userInfo
     * @param queryModel
     * @return
     */
    PageModel<List<EquipmentModel>> getEquipmentList(UserInfo userInfo, QueryEquipmentModel queryModel);

    /**
     * 删除研发设备
     *
     * @param info
     * @param rdEquipmentEntity
     * @return
     * @throws OwnerException
     */
    Boolean delete(UserInfo info, RdEquipmentEntity rdEquipmentEntity) throws OwnerException;

    /**
     * 批量删除研发设备
     *
     * @param info
     * @param deleteModel
     * @return
     * @throws OwnerException
     */
    Boolean delRdEquipments(UserInfo info, BatchDeleteModel deleteModel) throws OwnerException;

    /**
     * 批量添加研发设备
     *
     * @param userInfo
     * @param addRdEquipmentModel
     * @return
     * @throws OwnerException
     */
    Boolean addRdEquipments(UserInfo userInfo, AddRdEquipmentModel addRdEquipmentModel)throws OwnerException;

    /**
     * 更新设备类型
     *
     * @param info
     * @param modifyModel
     * @return
     * @throws OwnerException
     */
    Boolean updateEtype(UserInfo info, EquipmentModifyModel modifyModel)throws OwnerException;

    /**
     * 设置研发部门
     *
     * @param modifyModel
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean setRdDept(EquipmentModifyModel modifyModel,UserInfo info)throws OwnerException;


    /**
     * 导出数据
     *
     * @param companyId
     * @param query
     * @return
     */
    List<EquipmentExcel> deriveRdEquipment(Integer companyId, QueryEquipmentModel query);

    /**
     * 后端导出数据
     * @param companyId
     * @param query
     * @param out
     * @param path
     * @throws OwnerException
     */
    void exportRdEquipment(Integer companyId, QueryEquipmentModel query, OutputStream out, String path)throws OwnerException;
    /**
     * 获取项目设备列表
     *
     * @param userInfo
     * @param query
     * @return
     */
    PageModel<List<DocEquipmentModel>> getDocEquipmentList(UserInfo userInfo, QueryProjectEquipmentModel query);

    /**
     * 导入研发设备
     * @param info
     * @param equipmentExcelList
     * @param year
     * @return
     * @throws OwnerException
     */
    String importRdEquipment(UserInfo info, List<EquipmentExcel> equipmentExcelList, Integer year) throws OwnerException;

    /**
     * 分配研发设备至项目
     * @param userInfo
     * @param keysAndIds
     * @return
     * @throws OwnerException
     */
    Boolean setProjectEquipment(UserInfo userInfo, KeysAndIdsModel keysAndIds) throws OwnerException;
}
