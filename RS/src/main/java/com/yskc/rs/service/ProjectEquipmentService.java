package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.project.ProjectRdHourConfig;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.projectattendance.RefreshAttendance;
import com.yskc.rs.models.projectequipment.*;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-17 14:35
 * @Description: 项目设备研发记录业务层接口
 */
public interface ProjectEquipmentService {
    /**
     * 获取设备研发运行工时
     *
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<ProjectEquipmentModel>> getList(Integer companyId, QueryProjectEquipmentModel query);

    /**
     * 刷新设备研发记录
     *
     * @param userInfo
     * @param fresh
     * @return
     * @throws OwnerException
     */
    Boolean refresh(UserInfo userInfo, RefreshAttendance fresh) throws OwnerException;

    /**
     * 保存设备研发使用记录
     *
     * @param userInfo
     * @param batch
     * @return
     * @throws OwnerException
     */
    Boolean saveList(UserInfo userInfo, BatchEquipmentModel batch) throws OwnerException;

    /**
     * 导出设备研发使用记录
     *
     * @param info
     * @param query
     * @param out
     * @return
     */
    void exportData(UserInfo info, QueryProjectEquipmentModel query, OutputStream out);

    /**
     * 获取对应月份天数是否存在人员研发记录
     *
     * @param query
     * @return
     */
    Map<Integer, Integer> getAttData(QueryProjectEquipmentModel query);

    /**
     * 获取项目研发工时配置
     *
     * @param rdHourConfig
     * @param companyId
     * @return
     */
    String getRdHourConfig(ProjectRdHourConfig rdHourConfig, Integer companyId);
}
