package com.yskc.rs.dao;

import com.yskc.rs.entity.DocOperatorEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-20 10:28:10
 */
@Repository
public interface DocOperatorDao extends BaseMapper<DocOperatorEntity> {

    DocOperatorEntity queryOperator(@Param("companyId") Integer companyId, @Param("id") Integer id);
}
