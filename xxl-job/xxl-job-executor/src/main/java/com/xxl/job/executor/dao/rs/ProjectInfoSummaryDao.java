package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.ProjectInfoSummary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Author: hck
 * @DateTime: 2021/3/5 17:39
 * @Description:
 */
@Repository
public interface ProjectInfoSummaryDao extends BaseMapper<ProjectInfoSummary> {
    /**
     * 统计研发人员工时
     *
     * @param lastUpdateTime
     * @return
     */
    List<ProjectInfoSummary> countStaffHour(@Param("lastUpdateTime") Date lastUpdateTime);

    /**
     * 统计研发设备工时
     *
     * @param lastUpdateTime
     * @return
     */
    List<ProjectInfoSummary> countEquipmentHour(@Param("lastUpdateTime") Date lastUpdateTime);

    /**
     * 排期表统计
     *
     * @param lastUpdateTime
     * @return
     */
    List<ProjectInfoSummary> countYieldAmount(@Param("lastUpdateTime") Date lastUpdateTime);

    /**
     * 获取项目材料归集
     *
     * @param lastTime
     * @return
     */
    List<ProjectInfoSummary> getMaterialInfoSummary(@Param("lastTime") Date lastTime);

    /**
     * 获取项目信息
     *
     * @param projectIds
     * @return
     */
    List<ProjectInfoSummary> getByProject(@Param("projectIds") Set<Integer> projectIds);


    /**
     * 批量更新或插入
     *
     * @param insertOrUpdate
     * @return
     */
    Integer insertOrUpdate(@Param("insertOrUpdate") List<ProjectInfoSummary> insertOrUpdate);

    /**
     * 清空表
     */
    void truncateTable();
}
