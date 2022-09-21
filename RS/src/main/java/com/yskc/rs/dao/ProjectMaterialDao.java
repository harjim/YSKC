package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.ProjectMaterialEntity;
import com.yskc.rs.models.aggregation.ExportTermModel;
import com.yskc.rs.models.aggregation.MaterialExportModel;
import com.yskc.rs.models.material.AppMaterialModel;
import com.yskc.rs.models.material.QueryMaterialTrackModel;
import com.yskc.rs.models.project.DocMaterialModel;
import com.yskc.rs.models.project.QueryProjectMaterialModel;
import com.yskc.rs.models.projectmaterial.ProjectRdMaterialTotalModel;
import com.yskc.rs.models.workSheet.WorkSheetMaterialModel;
import com.yskc.rs.models.workSheet.WorkSheetMiniModel;
import com.yskc.rs.models.workSheet.WorkSheetQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-16 14:44:47
 */
@Repository
public interface ProjectMaterialDao extends BaseMapper<ProjectMaterialEntity> {

    /**
     * 查询项目使用物料
     *
     * @param query
     * @param companyId
     * @param page
     * @param startDate
     * @param endDate
     * @return
     */
    List<AppMaterialModel> queryMaterialDataIdList(@Param("page") Pagination page,
                                                   @Param("companyId") Integer companyId,
                                                   @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                                   @Param("query") QueryProjectMaterialModel query);

    /**
     * 获取物料汇总
     *
     * @param companyId
     * @param startDate
     * @param endDate
     * @param query
     * @return
     */
    BigDecimal getProjectMaterialSum(@Param("companyId") Integer companyId,
                                     @Param("startDate") Date startDate, @Param("endDate") Date endDate,
                                     @Param("query") QueryProjectMaterialModel query);

    boolean delMaterial(@Param("projectId") Integer projectId, @Param("materialDataId") Integer materialDataId, @Param("rdType") Integer rdType);

    ProjectMaterialEntity queryProjectMaterialByMaterialIdAndProjectId(@Param("companyId") Integer companyId, @Param("materialDataId") Integer materialDataId, @Param("projectId") Integer projectId, @Param("rdType") Integer rdType);

    List<ProjectMaterialEntity> queryProjectMaterialByMaterialDataId(@Param("companyId") Integer companyId, @Param("materialDataId") Integer materialDataId);

    List<AppMaterialModel> queryMaterialListByProjectId(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    BigDecimal queryMaterialListByProjectIdAndDateAndType(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("rdType") Integer rdType);

    Integer updateUsedById(@Param("projectMaterialEntities") List<ProjectMaterialEntity> projectMaterialEntities);

    List<DocMaterialModel> getDocMaterialList(@Param("page") Pagination page, @Param("query") QueryProjectMaterialModel query, @Param("companyId") Integer companyId);

    List<AppMaterialModel> queryMaterialTrack(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryMaterialTrackModel query);

    Integer editMaterialTrack(@Param("model") List<AppMaterialModel> model);

    /**
     * 查询物料id
     * @param materialIds
     * @return
     */
    List<Integer> selectByMaterialIds(@Param("materialIds") List<Integer> materialIds);

    List<WorkSheetMaterialModel> getMaterialWorkSheetList(@Param("companyId") Integer companyId, @Param("query") WorkSheetQuery query, @Param("type") Integer type);

    Integer addBatch(@Param("insertMaterialEntities") List<ProjectMaterialEntity> insertMaterialEntities);

    Integer updateBatch(@Param("updateMaterialEntities") List<ProjectMaterialEntity> updateMaterialEntities);

    List<ProjectMaterialEntity> queryPMaterialList(@Param("companyId") Integer companyId, @Param("materialIdList") List<Integer> materialIdList, @Param("projectId") Integer projectId, @Param("type") Integer type);

    List<WorkSheetMiniModel> getMaterialWorkSheetMonthList(@Param("companyId") Integer companyId, @Param("query") WorkSheetQuery workSheetQuery, @Param("type") Integer type, @Param("begin") Date begin, @Param("end") Date end);

    List<MaterialExportModel> exportFromMaterial(@Param("model") ExportTermModel model);

    /**
     * 获取物料归集total
     *
     * @param projectIds
     * @param minDate
     * @param maxDate
     * @return
     */
    List<ProjectRdMaterialTotalModel> getRdMaterialTotal(@Param("projectIds") List<Integer> projectIds,
                                                         @Param("minDate") Date minDate, @Param("maxDate") Date maxDate);

    /**
     * @param ids
     * @return
     */
    List<AppMaterialModel> getByIds(@Param("ids") List<Integer> ids);

    /**
     * 设置损耗率
     *
     * @param dataList
     * @return
     */
    int setDepreciationRatio(@Param("dataList") List<AppMaterialModel> dataList);

    /**
     * 获取物料列表
     *
     * @param begin
     * @param end
     * @param projectId
     * @param rdType
     * @return
     */
    List<AppMaterialModel> getMaterialList(@Param("begin") Date begin, @Param("end") Date end,
                                           @Param("projectId") Integer projectId, @Param("rdType") Integer rdType);

    /**
     * 更新试制量/总产量
     *
     * @param list
     * @return
     */
    int updateYield(@Param("list") List<AppMaterialModel> list);

}
