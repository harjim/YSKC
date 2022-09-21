package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectInfoSummaryEntity;
import com.yskc.rs.models.project.ProjectInfoSummaryModel;
import com.yskc.rs.models.projectinfosummary.MaterialFeeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/8 8:58
 * @Description:
 */
@Repository
public interface ProjectInfoSummaryDao extends BaseMapper<ProjectInfoSummaryEntity> {
    /**
     * 获取项目信息汇总
     *
     * @param projectIds
     * @param firstDay
     * @param lastDay
     * @return
     */
    List<ProjectInfoSummaryModel> getInfoSummary(@Param("projectIds") List<Integer> projectIds, @Param("firstDay") Date firstDay, @Param("lastDay") Date lastDay);

    /**
     * 获取项目每个月的信息汇总
     *
     * @param projectIds
     * @param firstDay
     * @param lastDay
     * @param type
     * @param col
     * @return
     */
    List<ProjectInfoSummaryEntity> getSummaryByMonth(@Param("projectIds") List<Integer> projectIds,
                                                     @Param("firstDay") Date firstDay, @Param("lastDay") Date lastDay,
                                                     @Param("type")Integer type,@Param("col") String col);

    /**
     * 获取研发材料归集费用
     *
     * @param type
     * @param companyId
     * @param begin
     * @param end
     * @return
     */
    List<MaterialFeeModel> getMaterialSummary(@Param("type") int type,@Param("companyId") Integer companyId,
                                              @Param("begin") Date begin,@Param("end") Date end);
}
