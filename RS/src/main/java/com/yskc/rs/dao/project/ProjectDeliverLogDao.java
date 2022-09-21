package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectDeliverLog;
import com.yskc.rs.models.hightech.QueryHighTechAuditModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2022/5/27 11:08
 * @Description:高新进度审核日志dao
 * @author: hsx
 */
@Repository
public interface ProjectDeliverLogDao extends BaseMapper<ProjectDeliverLog> {
    /**
     * 批量新增审核日志
     * @param logs
     * @return
     */
    int addLogs(List<ProjectDeliverLog> logs);

    /**
     * 添加审核日志
     * @param deliverId
     * @param model
     * @return
     */
    Boolean addDeliverLog(@Param("deliverId")Integer deliverId, @Param("model") QueryHighTechAuditModel model);
}
