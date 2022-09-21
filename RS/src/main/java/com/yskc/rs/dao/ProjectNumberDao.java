package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.ProjectNumberEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectNumberDao extends BaseMapper<ProjectNumberEntity> {

    ProjectNumberEntity queryByYear(@Param("companyId") Integer companyId, @Param("year") Integer year);
}
