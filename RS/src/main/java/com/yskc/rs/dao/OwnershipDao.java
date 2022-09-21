package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.OwnershipEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-26 10:39:36
 */
@Repository
public interface OwnershipDao extends BaseMapper<OwnershipEntity> {

    List<OwnershipEntity> queryOwnershipList(@Param("companyId") Integer companyId);
}
