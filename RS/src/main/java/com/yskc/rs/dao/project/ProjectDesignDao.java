package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.ProjectDesignEntity;
import com.yskc.rs.models.UsedModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.aggregation.DesignExportModel;
import com.yskc.rs.models.aggregation.ExportTermModel;
import com.yskc.rs.models.projectdesign.ProjectDesignModel;
import com.yskc.rs.models.projectdesign.QueryProjectDesignModel;
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
 * @date 2019-07-15 11:06:55
 */
@Repository
public interface ProjectDesignDao extends BaseMapper<ProjectDesignEntity> {
    /**
     * 获取项目下的设计费用
     *
     * @param page
     * @param companyId
     * @param query
     * @param beginDate
     * @param endDate
     * @return
     */
    List<ProjectDesignModel> queryProjectDesign(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                                @Param("query") QueryProjectDesignModel query,
                                                @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    /**
     * 批量新增
     *
     * @param projectDesignEntities
     * @return
     */
    Integer addProjectDesignBatch(@Param("projectDesignEntities") List<ProjectDesignEntity> projectDesignEntities);

    /**
     * 获取设计费用汇总
     *
     * @param projectId
     * @param dateList
     * @return
     */
    List<ProjectDesignModel> getDesignSum(@Param("projectId") Integer projectId, @Param("dateList") List<Date> dateList);

    /**
     * 获取
     *
     * @param projectId
     * @param beginDate
     * @param endDate
     * @return
     */
    List<ProjectDesignModel> getDesignDate(@Param("projectId") Integer projectId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);


    /**
     * 获取费用
     *
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    BigDecimal getProjectDesign(@Param("companyId") Integer companyId, @Param("beginDate") Date beginDate,
                                @Param("endDate") Date endDate, @Param("query") QueryProjectDesignModel query);


    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    int deleteDesign(@Param("idList") List<Integer> idList);

    /**
     * 获取工单的费用
     *
     * @param companyId
     * @param projectId
     * @param date
     * @return
     */
    List<WorkSheetModel> queryuOutData(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("date") Date date);

    /**
     * 按月获取工单月份
     *
     * @param info
     * @param workSheetQuery
     * @return
     */
    List<WorkSheetMiniModel> getDataBymonth(@Param("info") UserInfo info, @Param("workSheetQuery") WorkSheetQuery workSheetQuery, @Param("endDate") Date date);

    /**
     * 设计费用归集导出数据
     * @param model
     * @return
     */
    List<DesignExportModel> exportFromDesign(@Param("model") ExportTermModel model);

    /**
     * 批量设置(不同的)研发费用
     * @param entities
     * @return
     */
    Integer updateList(@Param("entities") List<ProjectDesignEntity> entities);

    /**
     * 批量设置(相同的)研发费用
     * @param ids
     * @param date
     * @param userId
     * @param msUserId
     * @param rdAmount
     * @return
     */
    Integer updateAmounts(@Param("ids") List<Integer> ids, @Param("date") Date date, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("rdAmount") BigDecimal rdAmount);

    /**
     * 查询设计费用已归集的数据
     * @param ids
     * @param projectId
     * @return
     */
    List<UsedModel> getUsedList(@Param("ids") List<Integer> ids, @Param("projectId") Integer projectId);
}
