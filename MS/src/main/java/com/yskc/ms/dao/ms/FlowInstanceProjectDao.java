package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.FlowInstanceProject;
import com.yskc.ms.models.flowInstance.FlowInstanceInfoModel;
import com.yskc.ms.models.project.ProjectProgressModel;
import com.yskc.ms.models.projectAudit.AuditStatusModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/15 11:09
 * description:
 */
@Repository
public interface FlowInstanceProjectDao extends BaseMapper<FlowInstanceProject> {
    /**
     * 获取当前用户项目审核信息
     *
     * @param companyId
     * @param year
     * @param userId
     * @return
     */
    List<FlowInstanceProject> getProjectAudit(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("userId") Integer userId);

    /**
     * 获取审核状态
     *
     * @param companyId
     * @param year
     * @param moduleId
     * @return
     */
    AuditStatusModel getAuditStatus(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("moduleId") Integer moduleId);

    /**
     * 获取实例信息
     *
     * @param instanceId
     * @return
     */
    FlowInstanceInfoModel getInstanceInfo(@Param("instanceId") Integer instanceId);

    /**
     * 根据公司，年份查询数据
     *
     * @param instanceIds
     * @return
     */
    List<ProjectProgressModel> getAuditTotal(@Param("instanceIds") List<Integer> instanceIds);
}
