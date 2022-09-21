package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.entity.project.ProjectRdEquipmentEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.projectequipment.RdEquipmentResultModel;
import com.yskc.rs.models.projectfinaschedule.ProjectFinaScheduleModel;
import com.yskc.rs.models.projectrdequipment.BatchProjectRdEquipmentModel;
import com.yskc.rs.models.projectrdequipment.HighTechProjectRdEquipmentModel;
import com.yskc.rs.models.projectrdequipment.QueryProjectRdEquipmentModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-01 11:01
 * @Description: 项目设备研发折旧业务层接口
 */
public interface ProjectRdEquipmentService extends TrialAggService {
    /**
     * 获取项目设备研发折旧
     *
     * @param userInfo
     * @param query
     * @return
     */
    PageResult getList(UserInfo userInfo, QueryProjectRdEquipmentModel query);

    /**
     * 获取项目设备动力列表
     *
     * @param userInfo
     * @param query
     * @return
     */
    PageResult getEquipmentPowerList(UserInfo userInfo, QueryProjectRdEquipmentModel query);

    /**
     * 批量保存项目设备折旧费用
     *
     * @param userInfo
     * @param batchModel
     * @return
     * @throws OwnerException
     */
    Boolean saveList(UserInfo userInfo, BatchProjectRdEquipmentModel batchModel) throws OwnerException;

    /**
     * 设置电费
     *
     * @param userInfo
     * @param entity
     * @return
     * @throws OwnerException
     */
    Boolean setPowerUnitPrice(UserInfo userInfo, ProjectRdEquipmentEntity entity) throws OwnerException;


    PageModel<List<RdEquipmentResultModel>> queryList(UserInfo userInfo, QueryProjectRdEquipmentModel query);

    List<RdEquipmentResultModel> queryListByYear(UserInfo userInfo, QueryProjectRdEquipmentModel query);

    /**
     * 导入研发工时
     *
     * @param info
     * @param batchModel
     * @param year
     * @return
     * @throws OwnerException
     */
    Boolean importRdHour(UserInfo info, BatchProjectRdEquipmentModel batchModel, int year) throws OwnerException;

    /**
     * 插入summary
     *
     * @param now
     * @param projectIds
     * @param months
     * @param filterZero 不存入无归集的数据
     * @param userInfo
     */
    void insertSummary(Date now, List<Integer> projectIds, List<Date> months, UserInfo userInfo, Boolean filterZero);

    /**
     * 通过财务排期表【动力试验/试制实际工时】
     *
     * @param hourMap
     * @param month
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean aggByFinaScheduleHours(Map<Integer, ProjectFinaScheduleModel> hourMap, Date month, UserInfo userInfo) throws OwnerException;

    /**
     * 根据月份获取所有设备使用情况
     *
     * @param userInfo
     * @param query
     * @return
     */
    PageModel<List<RdEquipmentResultModel>> getMonthTotalProd(UserInfo userInfo, QueryProjectRdEquipmentModel query);

    /**
     * 获取高新进度财务部分 设备折旧明细表
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<HighTechProjectRdEquipmentModel>> getEquipments(Integer companyId, QueryProjectRdEquipmentModel query);
}
