package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.AuditReportEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/5/12 14:25
 * @Description:
 */
@Repository
public interface AuditReportDao extends BaseMapper<AuditReportEntity> {
    /**
     * 获取项目查新报告
     *
     * @param projectId
     * @return
     */
    AuditReportEntity getByProject(@Param("projectId") Integer projectId);

    /**
     * 获取项目审核
     *
     * @param year
     * @param companyId
     * @return
     */
    List<AuditReportEntity> getAuditInfo(@Param("year") Integer year, @Param("companyId") Integer companyId);
}
