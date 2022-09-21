package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.company.CompanyStageEntity;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.models.project.ProjectModel;
import com.yskc.rs.models.stage.QueryStageModel;
import com.yskc.rs.models.stage.StageModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-08 11:28:54
 */
@Repository
public interface StageDao extends BaseMapper<StageEntity> {

    List<StageModel> queryStage(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    List<StageModel> getByProjectIds(@Param("companyId") Integer companyId, @Param("projectIds") List<Integer> projectIds);

    List<StageModel> queryStageList(@Param("companyId") Integer companyId, @Param("query") QueryStageModel query);

    /**
     * 查询c_stage获取stageName
     * @param companyId
     * @return
     */
    List<Map<String,Object>> getCStage(@Param("companyId") Integer companyId);
    /**
     *查询项目立项阶段
     * @param companyId
     * @return
     */
    List<CompanyStageEntity> getStageList(@Param("companyId") Integer companyId);

    /**
     * 修改/添加 项目立项阶段
     * @param companyId
     * @param list
     * @return
     */
    Boolean savaStage(@Param("companyId") Integer companyId, @Param("list")List<CompanyStageEntity> list);

    /**
     * 修改/添加 项目立项阶段
     * @param companyId
     * @param list
     * @return
     */
    Boolean updateStage(@Param("companyId") Integer companyId, @Param("list")List<CompanyStageEntity> list);

    /**
     * 修改/添加 项目立项阶段
     * @param companyId
     * @param list
     * @param projectId
     * @return
     */
    Boolean updateProjectStage(@Param("companyId") Integer companyId, @Param("list")List<CompanyStageEntity> list, @Param("projectId") Integer projectId);

    /**
     * 查询同一项目下项目阶段名称是否存在
     *
     * @param companyId
     * @param projectId
     * @param stageNames
     * @return
     */
    List<StageEntity> getStageByName(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("stageNames") List<String> stageNames);

    /**
     * 批量新增
     *
     * @param entities
     * @return
     */
    Integer addBatch(@Param("entities") List<StageEntity> entities);

    /**
     * 批量修改
     *
     * @param entities
     * @return
     */
    Integer updateBatch(@Param("entities") List<StageEntity> entities);

    /**
     * 根据projectId,阶段类型查出阶段
     *
     * @param projectId
     * @param queryList
     * @return
     */
    List<StageEntity> queryStageByTypeList(@Param("projectId") Integer projectId, @Param("queryList") List<String> queryList);

    /**
     * 根据projectId,阶段类型删除阶段
     *
     * @param projectId
     * @param delKey
     */
    void deleteByTypeList(@Param("projectId") Integer projectId, @Param("delKey") List<String> delKey);

    StageEntity queryEntityByKey(@Param("projectId") Integer projectId, @Param("stageKey") String stageKey);

    /**
     * 获取日期（若stage不存在日期，取项目开始日期）
     *
     * @param projectId
     * @param stage
     * @return
     */
    ProjectModel getBeginDate(@Param("projectId") Integer projectId, @Param("stage") String stage);

    /**
     * 获取阶段信息
     *
     * @param projectId
     * @param pDocFileId
     * @return
     */
    StageEntity getStageInfo(@Param("projectId") Integer projectId, @Param("pDocFileId") Integer pDocFileId);

    /**
     * 获取项目阶段
     *
     * @param projectId
     * @return
     */
    List<StageEntity> getStagesByProject(@Param("projectId") Integer projectId);

    /**
     * 获取文档阶段
     *
     * @param projectId
     * @param pDocFileId
     * @return
     */
    StageEntity getByDocFile(@Param("projectId") Integer projectId, @Param("pDocFileId") Integer pDocFileId);

    /**
     * 获取项目处于某年期间的阶段(阶段开始日期计算)
     *
     * @param year
     * @param projectId
     * @return
     */
    List<StageEntity> getByYears(@Param("year") Integer year, @Param("projectId") Integer projectId);

    /**
     * 获取年最后一个阶段
     *
     * @param projectId
     * @param year
     * @return
     */
    StageEntity getStageByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);
}
