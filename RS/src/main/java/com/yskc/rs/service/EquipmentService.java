package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.equipment.EquipmentModel;
import com.yskc.rs.models.equipment.EquipmentModifyModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.excel.EquipmentExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-09 09:21
 * @Description: 设备业务层接口
 */
public interface EquipmentService {

    /**
     * 查询公司所有设备
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<EquipmentModel>> queryAll(Integer companyId, QueryEquipmentModel query);

    /**
     * 导入数据
     *
     * @param info
     * @param equipmentExcels
     * @return
     * @throws OwnerException
     */
    String importEquipment(UserInfo info, List<EquipmentExcel> equipmentExcels) throws OwnerException;

    /**
     * 导出数据
     *
     * @param compantyId
     * @param query
     * @return
     */
    List<EquipmentExcel> exportEquipment(Integer compantyId, QueryEquipmentModel query);

    /**
     * 删除数据
     *
     * @param companyId
     * @param id
     * @return
     * @throws OwnerException
     */
    Boolean deleteById(Integer companyId, Integer id) throws OwnerException;

    /**
     * 检查ecode是否重复
     *
     * @param companyId
     * @param ecode
     * @param id
     * @return
     */
    Boolean checkEcode(Integer companyId, String ecode, Integer id);

    /**
     * 添加设备
     *
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    Boolean addEquipment(UserInfo info, EquipmentModel model) throws OwnerException;

    /**
     * 更新设备
     *
     * @param currentUserId
     * @param info
     * @param model
     * @return
     */
    Boolean updateEquipment(Integer currentUserId, UserInfo info, EquipmentModel model);


    /**
     * 批量修改类型
     *
     * @param info
     * @param model
     * @return
     */
    boolean updateEquipmentEtype(UserInfo info, EquipmentModifyModel model);



    /**
     * 批量删除数据
     *
     * @param companyId
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean delEquipments(Integer companyId, EquipmentModifyModel model) throws OwnerException;
}
