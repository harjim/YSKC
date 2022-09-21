package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.SummaryEntity;
import com.yskc.docservice.models.rs.project.ProjectBudgetModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: summary 汇总 dao层
 * @Author: zhangdingfu
 * @date: 2019-07-19
 */
@Repository
public interface SummaryDao extends BaseMapper<SummaryEntity> {
    /**
     * 插入或者更新
     *
     * @param summaryEntities
     */
    void insertOrUpdate(@Param("summaryEntities") List<SummaryEntity> summaryEntities);

    /**
     * 根据年获取归集数据
     *
     * @param projectId
     * @param year
     * @return
     */
    List<ProjectBudgetModel> getBudgetByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 获取项目归集数
     *
     * @param projectId
     * @param beginDate 开始归集月
     * @param endDate   结束归集月
     * @return
     */
    List<ProjectBudgetModel> getProjectSummary(@Param("projectId") Integer projectId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    /**
     * 获取项目所有的归集费用
     *
     * @param projectId
     * @return
     */
    BigDecimal getProjectRdFundsSum(@Param("projectId") Integer projectId);
}
