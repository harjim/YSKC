package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectTrialConfigEntity;
import com.yskc.rs.models.projectyieldconfig.RefreshYieldConfigModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-21 13:48
 * @Description: 试制生成配置dao层
 */
@Repository
public interface ProjectTrialConfigDao extends BaseMapper<ProjectTrialConfigEntity> {
    /**
     * 更新或插入
     *
     * @param configEntity
     * @return
     */
    int insertOrUpdate(@Param("entity") ProjectTrialConfigEntity configEntity);

    /**
     * 获取刷新研发试制计划配置
     * @param companyId
     * @return
     */
    RefreshYieldConfigModel getTrialConfig(@Param("companyId") Integer companyId);
}
