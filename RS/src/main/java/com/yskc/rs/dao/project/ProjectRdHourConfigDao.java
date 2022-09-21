package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectRdHourConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-04-08 17:35
 * @Description: 项目研发工时配置
 */
@Repository
public interface ProjectRdHourConfigDao extends BaseMapper<ProjectRdHourConfig> {
    /**
     * 获取研发工时配置
     *
     * @param rdHourConfig
     * @param companyId
     * @param type
     * @return
     */
    String getRdHourConfig(@Param("config") ProjectRdHourConfig rdHourConfig,@Param("companyId") Integer companyId);

    /**
     * 插入或更新
     * @param projectRdHourConfig
     * @return
     */
    int insertOrUpdate(@Param("c") ProjectRdHourConfig projectRdHourConfig);
}
