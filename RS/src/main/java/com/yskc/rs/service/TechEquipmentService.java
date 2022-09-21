package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.QueryEquipmentModel;
import com.yskc.rs.models.tech.TechEquipmentModel;

import java.io.OutputStream;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/18 11:11
 * @Description:
 */
public interface TechEquipmentService {
    /**
     * 备案清单
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<TechEquipmentModel>> getList(Integer companyId, QueryEquipmentModel query);

    /**
     * 编辑
     *
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    Boolean edit(UserInfo userInfo, TechEquipmentModel model) throws OwnerException;

    /**
     * 批量删除备案清单
     *
     * @param deleteModel
     * @return
     */
    Boolean deletes(BatchDeleteModel deleteModel);

    /**
     * 保存备案清单
     *
     * @param models
     * @return
     */
    Boolean add(TechEquipmentModel models, UserInfo userInfo);

    /**
     * 导入备案清单
     *
     * @param info
     * @param data
     */
    Boolean importEquipment(UserInfo info, List<TechEquipmentModel> data, Integer beianId);

    /**
     * 导出备案清单
     * @param beianId
     * @param info
     * @param out
     */
    void exportEquipment(Integer beianId, UserInfo info, OutputStream out) throws OwnerException;
}
