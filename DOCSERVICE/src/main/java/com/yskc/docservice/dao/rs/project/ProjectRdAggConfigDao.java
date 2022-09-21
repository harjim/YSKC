package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectRdAggConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-05-21 11:24
 * @Description: 项目研发归集配置Dao层
 */
@Repository
public interface ProjectRdAggConfigDao extends BaseMapper<ProjectRdAggConfig> {
    /**
     * 通过归集类型，公司id获取配置
     *
     * @param type
     * @param month
     * @param companyId
     * @return
     */
    String getConfig(@Param("type") Integer type, @Param("month") Date month, @Param("companyId") Integer companyId);

    /**
     * 插入或更新
     *
     * @param configs
     * @return
     */
    int insertOrUpdate(@Param("configs") List<ProjectRdAggConfig> configs);
}
