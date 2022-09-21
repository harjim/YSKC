package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.FlowModuleEntity;
import com.yskc.ms.models.projectAudit.AuditStatusModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/18 17:06
 * description:
 */
@Repository
public interface FlowModuleDao extends BaseMapper<FlowModuleEntity> {
    //获取项目流程信息
    List<AuditStatusModel> getByProject(@Param("projectId") Integer projectId);
}
