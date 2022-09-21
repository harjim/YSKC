package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectYearInfoEntity;
import com.yskc.rs.models.projectDocFile.ProjectAnalysisModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/6/30 9:35
 * @Description:
 */
@Repository
public interface ProjectYearInfoDao extends BaseMapper<ProjectYearInfoEntity> {
    /**
     * 插入并更新项目预算
     * @param list
     * @return
     */
    Boolean insertOrUpdate(@Param("list") List<ProjectYearInfoEntity> list);

    /**
     * 插入并更新项目负责人
     * @param list
     * @return
     */
    Boolean addBatchMaster(@Param("list") List<ProjectYearInfoEntity> list);

    /**
     * 批量更新项目负责人
     * @param list
     */
    Boolean updateBatch(@Param("list") List<ProjectYearInfoEntity> list);

    Map<String,String> getMapByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);

    ProjectYearInfoEntity getByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 获取项目信息
     * @param projectId
     * @return
     */
    List<ProjectYearInfoEntity> getByProject(@Param("projectId") Integer projectId);

    /**
     * 获取项目信息按年  （<=year）
     * @param projectId
     * @param year
     * @return
     */
    List<ProjectYearInfoEntity> getInfoByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 按年获取项目预算
     * @param projectIds
     * @param year
     * @return
     */
    List<ProjectYearInfoEntity> getInfoByProjects(@Param("projectIds") List<Integer> projectIds, @Param("year") Integer year);

    /**
     * 获取项目总预算
     * @param projectId
     * @return
     */
    BigDecimal getTotalBudget(@Param("projectId") Integer projectId);

    /**
     * 获取公司年总预算
     * @param projectIds
     * @param companyId
     * @param year
     * @return
     */
    BigDecimal getBudgetByYear(@Param("projectIds") List<Integer> projectIds, @Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取费用分析【获取预算费用+实际归集费用】
     * @param projectId
     * @return
     */
    ProjectAnalysisModel getCostAnalysis(@Param("projectId") Integer projectId);
}
