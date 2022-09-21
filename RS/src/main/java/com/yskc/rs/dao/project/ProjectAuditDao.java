package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectAuditEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-11-23 14:43
 * @Description: 项目审核dao
 */
@Repository
public interface ProjectAuditDao extends BaseMapper<ProjectAuditEntity> {
    /**
     * 获取审核状态
     *
     * @param companyId
     * @param year
     * @param moduleId
     * @return
     */
    ProjectAuditEntity getAudit(@Param("companyId") Integer companyId, @Param("year") Integer year,
                                @Param("moduleId") Integer moduleId);

    /**
     * 获取多个年审核状态
     *
     * @param companyId
     * @param years
     * @param moduleId
     * @return
     */
    List<ProjectAuditEntity> getYearAudit(@Param("companyId") Integer companyId, @Param("years") Set<Integer> years,
                                          @Param("moduleId") Integer moduleId);
}
