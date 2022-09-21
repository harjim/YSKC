package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.ProjectInspectionEntity;
import com.yskc.rs.models.UsedModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.aggregation.ExportTermModel;
import com.yskc.rs.models.aggregation.OtherExportModel;
import com.yskc.rs.models.hightech.QueryInspectionModel;
import com.yskc.rs.models.projectinspection.*;
import com.yskc.rs.models.workSheet.WorkSheetMiniModel;
import com.yskc.rs.models.workSheet.WorkSheetModel;
import com.yskc.rs.models.workSheet.WorkSheetQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-17 09:13:12
 */
@Repository
public interface ProjectInspectionDao extends BaseMapper<ProjectInspectionEntity> {
    /**
     * 获取项目检测修理
     *
     * @param page
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    List<ProjectInspectionModel> queryProjectInspection(@Param("page") Pagination page,
                                                        @Param("companyId") Integer companyId,
                                                        @Param("beginDate") Date beginDate,
                                                        @Param("endDate") Date endDate,
                                                        @Param("query") QueryProjectInspectionModel query
    );

    /**
     * 获取高新进度其他费用明细表
     * @param page
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    List<HighTechInspectionModel> getProjectInspection(@Param("page") Pagination page,
                                                       @Param("beginDate") Date beginDate,
                                                       @Param("endDate") Date endDate,
                                                       @Param("query") QueryInspectionModel query
    );

    /**
     * 批量新增
     *
     * @param projectInspectionEntities
     * @return
     */
    Integer addInspectionBatch(@Param("projectInspectionEntities") List<ProjectInspectionEntity> projectInspectionEntities);

    /**
     * 获取检修费用汇总
     *
     * @param projectId
     * @param dateList
     * @return
     */
    List<ProjectInspectionModel> getInspectionSum(@Param("projectId") Integer projectId, @Param("dateList") List<Date> dateList);

    /**
     * 获取
     *
     * @param projectId
     * @param beginDate
     * @param endDate
     * @param types
     * @return
     */
    List<ProjectInspectionModel> getInspectionDate(@Param("projectId") Integer projectId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate, @Param("types") List<Integer> types);


    /**
     * 获取费用
     *
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    BigDecimal getProjectInspectionSum(@Param("companyId") Integer companyId,
                                       @Param("beginDate") Date beginDate,
                                       @Param("endDate") Date endDate,
                                       @Param("query") QueryProjectInspectionModel query);

    /**
     * 查询工单数据
     *
     * @param projectId
     * @param companyId
     * @param types
     * @return
     */
    List<WorkSheetModel> queryWorkOrderData(@Param("projectId") Integer projectId, @Param("companyId") Integer companyId, @Param("date") Date date, @Param("types") List<Integer> types);

    /**
     * 按月查询工单数据
     *
     * @param info
     * @param workSheetQuery
     * @param endDate
     * @return
     */
    List<WorkSheetMiniModel> queryWorkOrderByMonth(@Param("info") UserInfo info, @Param("workSheetQuery") WorkSheetQuery workSheetQuery, @Param("endDate") Date endDate);

    /**
     * 获取摊销费用sum
     *
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    SumProjectAmortizationModel getAmortizationSum(@Param("companyId") Integer companyId,
                                                   @Param("beginDate") Date beginDate,
                                                   @Param("endDate") Date endDate,
                                                   @Param("query") QueryProjectInspectionModel query);

    /**
     * 获取其他费用类型
     *
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    OtherSumModel getOtherSum(@Param("companyId") Integer companyId,
                              @Param("beginDate") Date beginDate,
                              @Param("endDate") Date endDate,
                              @Param("query") QueryProjectInspectionModel query);

    /**
     * 其他费用归集费用导出数据
     * @param model
     * @return
     */
    List<OtherExportModel> ExportFromOther(@Param("model") ExportTermModel model);

    /**
     * 批量设置研发费用（费用一致）
     * @param ids
     * @param date
     * @param userId
     * @param msUserId
     * @param amount
     * @return
     */
    Integer updateList(@Param("ids") List<Integer> ids, @Param("date") Date date, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("amount") BigDecimal amount);

    /**
     * 批量设置研发费用（费用不一致）
     * @param list
     * @return
     */
    Integer updateRdAmounts(@Param("list") List<ProjectInspectionEntity> list);

    List<UsedModel> getUsedList(@Param("ids") List<Integer> ids,@Param("projectId")Integer projectId);
}
