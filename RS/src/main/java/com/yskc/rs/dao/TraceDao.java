package com.yskc.rs.dao;

import com.yskc.rs.entity.TraceEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.models.material.AppMaterialModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-05 10:58:47
 */
@Repository
public interface TraceDao extends BaseMapper<TraceEntity> {

    void updateTrace(@Param("updateModels")List<AppMaterialModel> updateModels);

    void insertTrace(@Param("insertModels")List<AppMaterialModel> insertModels);

    void deleteByPid(@Param("pId")int pId);

    void deleteByPids(@Param("ids")List<Integer> ids);
}
