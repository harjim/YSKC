package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectAuditEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


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
}
