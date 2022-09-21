package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.BuildConfigEntity;
import com.yskc.rs.models.buildconfig.BuildConfigDocModel;
import com.yskc.rs.models.buildconfig.BuildConfigModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildConfigDao extends BaseMapper<BuildConfigEntity> {
    List<BuildConfigModel> getList(@Param("companyId")Integer companyId, @Param("year")Integer year);
    Integer batchSave(@Param("list")List<BuildConfigModel> list);
    List<BuildConfigDocModel> getConfigList(@Param("companyId")Integer companyId, @Param("year")Integer year);
}
