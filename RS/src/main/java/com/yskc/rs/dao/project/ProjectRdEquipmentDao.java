package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.ProjectRdEquipmentEntity;
import com.yskc.rs.models.aggregation.EquipmentExportModel;
import com.yskc.rs.models.aggregation.EquipmentPowerExportModel;
import com.yskc.rs.models.aggregation.ExportTermModel;
import com.yskc.rs.models.init.InitRdUsedModel;
import com.yskc.rs.models.project.RdUsedHourModel;
import com.yskc.rs.models.projectDocFile.ProjectAnalysisModel;
import com.yskc.rs.models.projectenergy.QueryProjectEnergy;
import com.yskc.rs.models.projectequipment.RdEquipmentResultModel;
import com.yskc.rs.models.projectrdequipment.HighTechProjectRdEquipmentModel;
import com.yskc.rs.models.projectrdequipment.ProjectRdEquipmentModel;
import com.yskc.rs.models.projectrdequipment.ProjectRdEquipmentTotalModel;
import com.yskc.rs.models.projectrdequipment.QueryProjectRdEquipmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-01 10:58
 * @Description: 项目设备研发折旧
 */
@Repository
public interface ProjectRdEquipmentDao extends BaseMapper<ProjectRdEquipmentEntity> {
    /**
     * 获取项目设备折旧列表
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<ProjectRdEquipmentModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryProjectRdEquipmentModel query);

    List<ProjectRdEquipmentModel> getEquipmentPowerList(Pagination page, Integer companyId, QueryProjectRdEquipmentModel query);

    /**
     * @param companyId
     * @param query
     * @return
     */
    ProjectRdEquipmentTotalModel getTotal(@Param("companyId") Integer companyId, @Param("query") QueryProjectRdEquipmentModel query);

    /**
     * 获取项目设备折旧费用月合计
     *
     * @param projectIds
     * @param months
     * @param year
     * @return
     */
    List<ProjectRdEquipmentTotalModel> getSummary(@Param("projectIds") List<Integer> projectIds,
                                                  @Param("months") List<Date> months, @Param("year") Integer year);

    /**
     * 获取使用过的项目设备研发工时
     *
     * @param companyId
     * @param query
     * @param ecodes
     * @return
     */
    List<RdUsedHourModel> getAllUsed(@Param("companyId") Integer companyId, @Param("query") QueryProjectRdEquipmentModel query, @Param("ecodes") List<String> ecodes);

    /**
     * 获取项目使用
     *
     * @param ids
     * @param projectId
     * @param end
     * @param year
     * @param begin
     * @return
     */
    List<InitRdUsedModel> getRdUsed(@Param("ids") List<Integer> ids, @Param("projectId") Integer projectId,
                                    @Param("end") Date end, @Param("year") Integer year,
                                    @Param("begin") Date begin);

    List<ProjectRdEquipmentModel> queryByProjectIdAndMonth(@Param("projectId") Integer projectId, @Param("month") Date month);

    /**
     * 项目设备使用情况(月)
     *
     * @param companyId
     * @param projectId
     * @param month
     * @param year
     * @return
     */
    List<RdEquipmentResultModel> queryList(@Param("page") Pagination page,
                                           @Param("companyId") Integer companyId,
                                           @Param("projectId") Integer projectId,
                                           @Param("month") Date month,
                                           @Param("year") Integer year);

    /**
     * 项目设备使用情况(月)
     *
     * @param companyId
     * @param projectId
     * @param month
     * @param year
     * @return
     */
    List<RdEquipmentResultModel> queryEquipmentList(@Param("companyId") Integer companyId,
                                                    @Param("projectId") Integer projectId,
                                                    @Param("month") Date month,
                                                    @Param("year") Integer year);

    /**
     * 项目设备使用情况(年)
     *
     * @param companyId
     * @param projectId
     * @return
     */
    List<RdEquipmentResultModel> queryYearList(@Param("companyId") Integer companyId,
                                               @Param("projectId") Integer projectId,
                                               @Param("year") Integer year);

    /**
     * 设备折旧费用
     *
     * @param term
     * @param companyId
     * @param year
     * @return
     */
    List<EquipmentExportModel> exportFromEquipment(@Param("term") ExportTermModel term,
                                                   @Param("companyId") Integer companyId,
                                                   @Param("year") Integer year);

    /**
     * 查询设备动力归集费用
     *
     * @param model
     * @param companyId
     * @param year
     * @return
     */
    List<EquipmentPowerExportModel> getEquipmentPowerData(@Param("model") ExportTermModel model,
                                                          @Param("companyId") Integer companyId,
                                                          @Param("year") Integer year);

    /**
     * 获取最大工时
     *
     * @param query
     * @return
     */
    ProjectRdEquipmentModel getMaxRdHour(@Param("query") QueryProjectEnergy query);

    /**
     * 获取研发项目设备列表
     *
     * @param ids
     * @param year
     * @return
     */
    List<ProjectRdEquipmentModel> getRdEquipmentList(@Param("ids") List<Integer> ids, @Param("year") Integer year);

    /**
     * 获取月项目设备动力
     *
     * @param month
     * @param projectId
     * @return
     */
    BigDecimal getMonthPowerTotal(@Param("month") Date month, @Param("projectId") Integer projectId);

    /**
     * 获取使用过的sum
     *
     * @param companyId
     * @param projectId
     * @param months
     * @param ecodes
     * @return
     */
    List<ProjectRdEquipmentModel> getUsedSum(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId,
                                             @Param("months") Set<Date> months, @Param("ecodes") Set<String> ecodes);

    /**
     * 获取ecode 及 id
     *
     * @param projectId
     * @param months
     * @param ecodes
     * @return
     */
    List<ProjectRdEquipmentEntity> getEcodeId(@Param("projectId") Integer projectId, @Param("months") Set<Date> months,
                                              @Param("ecodes") Set<String> ecodes);

    /**
     * 获取月使用费用
     *
     * @param month
     * @param companyId
     * @return
     */
    List<ProjectRdEquipmentModel> getUsedByMonth(@Param("month") Date month, @Param("companyId") Integer companyId);

    /**
     * 获取存在的数据
     *
     * @param projectIds
     * @param month
     * @param companyId
     * @return
     */
    List<ProjectRdEquipmentModel> getExist(@Param("projectIds") Set<Integer> projectIds, @Param("month") Date month,
                                           @Param("companyId") Integer companyId);

    /**
     * 获取项目进度分析 预计，实际参与研发设备数
     *
     * @param companyId
     * @param projectId
     * @return
     */
    ProjectAnalysisModel countAnalysisEquipment(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    /**
     * 根据月份获取所有设备使用情况
     *
     * @param page
     * @param companyId
     * @param month
     * @param year
     * @return
     */
    List<RdEquipmentResultModel> getMonthTotalProd(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                                   @Param("month") Date month, @Param("year") Integer year);

    /**
     * 插入或更新
     *
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<ProjectRdEquipmentEntity> list);

    /**
     * 批量更新
     *
     * @param list
     * @param now
     * @param userId
     * @param msUserId
     * @return
     */
    int updateBatch(@Param("list") List<ProjectRdEquipmentEntity> list, @Param("now") Date now, @Param("userId") Integer userId,
                    @Param("msUserId") Integer msUserId);

    /**
     * 删除归集数据
     *
     * @param companyId
     * @param month
     * @return
     */
    int deleteData(@Param("companyId") Integer companyId,@Param("month") Date month);

    /**
     * 获取高新进度财务部分 设备折旧明细表
     * @param companyId
     * @param model
     * @param page
     * @return
     */
    List<HighTechProjectRdEquipmentModel> getEquipments(@Param("companyId") Integer companyId, @Param("model") QueryProjectRdEquipmentModel model,@Param("page") Pagination page);
}
