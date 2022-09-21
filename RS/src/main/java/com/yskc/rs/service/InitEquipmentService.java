package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.InitEquipmentExcel;
import com.yskc.rs.models.init.ImportProjectInfoModel;
import com.yskc.rs.models.init.InitUsedRoleModel;
import com.yskc.rs.models.init.equipment.BatchInitEquipmentModel;
import com.yskc.rs.models.init.equipment.InitEquipmentModel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-23 17:19
 * @Description: 初始化项目设备列表业务接口层
 */
public interface InitEquipmentService {
    /**
     * 获取项目设备列表
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<InitEquipmentModel>> getList(Integer companyId, QueryProjectEquipmentModel query);

    /**
     * 获取非当前项目设备列表
     *
     * @param companyId
     * @param model
     * @return
     */
    PageModel<List<InitEquipmentModel>> getEquipmentList(Integer companyId, QueryProjectEquipmentModel model);

    /**
     * 批量新增
     *
     * @param userInfo
     * @param batchInitEquipmentModel
     * @return
     * @throws OwnerException
     */
    boolean addList(UserInfo userInfo, BatchInitEquipmentModel batchInitEquipmentModel) throws OwnerException;

    /**
     * 删除设备清单-设备
     *
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    boolean del(InitEquipmentModel model, UserInfo info) throws OwnerException;

    /**
     * 更新项目作用
     *
     * @param model
     * @return
     */
    boolean updateEffect(InitUsedRoleModel model);

    /**
     * 导入设备清单
     *
     * @param userInfo
     * @param data
     * @param year
     * @param projectId
     * @return
     * @throws OwnerException
     */
    boolean importInitEquipment(UserInfo userInfo, List<InitEquipmentExcel> data, int year, Integer projectId) throws OwnerException;


    /**
     * 批量删除设备清单
     *
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    boolean delInitEquipments(InitUsedRoleModel model, UserInfo info) throws OwnerException;

    /**
     * 设置项目设备加入日期
     *
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean setEntryDate(UserInfo userInfo, InitUsedRoleModel model) throws OwnerException;

    /**
     * 检查项目设备是否已经归集研发费用
     *
     * @param model
     * @return
     */
    String checkRdUsed(InitUsedRoleModel model);

    /**
     * 引入项目资产清单
     * @param userInfo
     * @param model
     * @return
     */
    boolean importEquipments(UserInfo userInfo, ImportProjectInfoModel model) throws OwnerException;

    /**
     * 获取可引入项目设备年份
     * @param year
     * @param projectId
     * @return
     */
    List<Integer> getEquipmentYear(Integer year, Integer projectId);

    /**
     * 根据年和项目获取设备
     * @param companyId
     * @param model
     * @return
     */
    List<InitEquipmentModel> getEquList(Integer companyId, QueryProjectEquipmentModel model);
}
