package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.project.ProjectFinaSchedule;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ProjectFinaScheduleExcel;
import com.yskc.rs.models.projectfinaschedule.ProjectFinaScheduleAggModel;
import com.yskc.rs.models.projectfinaschedule.ProjectFinaScheduleModel;
import com.yskc.rs.models.projectfinaschedule.QueryProjectFinaScheduleModel;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-17 14:18
 * @Description: 项目试验试制实际工时表业务接口层
 */
public interface ProjectFinaScheduleService {

    /**
     * 获取项目试验试制实际工时表
     *
     * @param query
     * @param companyId
     * @return
     */
    PageModel<List<ProjectFinaScheduleModel>> getList(QueryProjectFinaScheduleModel query, Integer companyId);

    /**
     * 保存试验试制实际工时表
     *
     * @param list
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean save(List<ProjectFinaSchedule> list, UserInfo userInfo) throws OwnerException;

    /**
     * 导入试验试制实际工时
     *
     * @param info
     * @param data
     * @return
     * @throws OwnerException
     */
    Boolean importFinaSchedule(UserInfo info, List<ProjectFinaScheduleExcel> data) throws OwnerException;

    /**
     * 获取导出试验试制实际工时
     *
     * @param query
     * @param companyId
     * @return
     */
    Map<String, Object> getExportFinaSchedule(QueryProjectFinaScheduleModel query, Integer companyId);

    /**
     * 试验试制实际工时归集费用
     *
     * @param agg
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean aggFee(ProjectFinaScheduleAggModel agg, UserInfo userInfo) throws OwnerException;

    /**
     * 删除
     *
     * @param ids
     * @return
     * @throws OwnerException
     */
    Boolean del(List<Integer> ids) throws OwnerException;

    /**
     * 添加
     *
     * @param entity
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean add(ProjectFinaSchedule entity, UserInfo userInfo) throws OwnerException;

    /**
     * 更新
     *
     * @param entity
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean edit(ProjectFinaSchedule entity, UserInfo userInfo) throws OwnerException;
}
