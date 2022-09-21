package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.SenseEntity;
import com.yskc.rs.models.SenseModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2021/10/11 14:27
 * @Description:
 * @author: hsx
 */
@Repository
public interface SenseDao extends BaseMapper<SenseEntity> {

    Integer sensDuplicateChecking(@Param("type") Integer type, @Param("preachDate") Date preachDate, @Param("companyId") Integer companyId, @Param("id") Integer id);

    List<SenseEntity> getSenseByCompanyId(@Param("companyId") Integer companyId);

    Integer updateSenseById(@Param("entity") SenseEntity entity);
}
