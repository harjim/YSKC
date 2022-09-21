package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.AuditReportEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/29 17:51
 * @Description:
 */
@Repository
public interface AuditReportDao extends BaseMapper<AuditReportEntity> {
    /**
     * 获取不能提交审核的项目
     * @param projectIds
     * @param moduleId
     * @return
     */
    List<AuditReportEntity> getProjectAudits(@Param("projectIds") List<Integer> projectIds, @Param("moduleId") Integer moduleId);

    /**
     * 插入并更新项目审核
     * @param audits
     * @return
     */
    Integer insertOrUpdate(@Param("audits") List<AuditReportEntity> audits);

    Integer getProjectStatus(@Param("projectId") Integer projectId);
}
