package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.ProjectAuditEntity;
import com.xxl.job.executor.models.audit.ProjectAuditCntModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: zhangdingfu
 * @CreateTime: 2020-11-23 14:43
 * @Description: 项目审核dao
 */
@Repository
public interface ProjectAuditDao extends BaseMapper<ProjectAuditEntity> {
    /**
     * 获取审核
     *
     * @param status
     * @return
     */
    List<ProjectAuditEntity> getAudits(@Param("status") Integer status);

    /**
     * 批量更新
     *
     * @param audits
     * @return
     */
    int updateBatch(@Param("audits") List<ProjectAuditEntity> audits);

    /**
     * 更新节点
     *
     * @param audits
     * @param now
     * @return
     */
    int updateNodes(@Param("audits") List<ProjectAuditEntity> audits,@Param("now") Date now);

    /**
     * 获取最后更新数据
     *
     * @param lastTime
     * @return
     */
    List<Integer> getLastProjectIds(@Param("lastTime") Date lastTime);

    /**
     * 获取审核统计
     *
     * @param projectIds
     * @return
     */
    List<ProjectAuditCntModel> getAuditCnt(@Param("projectIds") List<Integer> projectIds);
}
