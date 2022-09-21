package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.FlowInstanceProject;
import com.xxl.job.executor.entity.rs.ProjectAuditEntity;
import com.xxl.job.executor.models.flowInstance.FlowInstanceCompanyModel;
import com.xxl.job.executor.models.flowInstance.FlowInstanceModel;
import com.xxl.job.executor.models.flowInstance.FlowInstanceProjectModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-15 16:46
 * @Description: 流程实例项目dao层
 */
@Repository
public interface FlowInstanceProjectDao extends BaseMapper<FlowInstanceProject> {

    /**
     * 获取实例
     *
     * @param list
     * @return
     */
    List<FlowInstanceProjectModel> getInstances(@Param("list") List<ProjectAuditEntity> list);

    /**
     * 批量更新或插入
     *
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<FlowInstanceProject> list);

    /**
     * 获取公司信息
     *
     * @param list
     * @return
     */
    List<FlowInstanceCompanyModel> getCompanies(@Param("list") List<FlowInstanceModel> list);

}
