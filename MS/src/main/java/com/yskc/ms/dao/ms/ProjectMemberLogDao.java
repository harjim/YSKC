package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ProjectMemberLogEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/8/26 8:45
 * description:
 */
@Repository
public interface ProjectMemberLogDao extends BaseMapper<ProjectMemberLogEntity> {
    /**
     * 批量添加日志
     * @param entities
     * @return
     */
    Integer addLogs(@Param("entities") List<ProjectMemberLogEntity> entities);
}
