package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.ProjectYearInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/6/30 9:35
 * @Description:
 */
@Repository
public interface ProjectYearInfoDao extends BaseMapper<ProjectYearInfoEntity> {

    /**
     * 获取项目信息按年  （<=year）
     *
     * @param projectId
     * @param year
     * @return
     */
    List<ProjectYearInfoEntity> getInfoByYear(@Param("projectId") Integer projectId, @Param("year") Integer year);
}
