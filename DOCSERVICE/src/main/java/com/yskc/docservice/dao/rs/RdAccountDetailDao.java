package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.RdAccountDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-30 10:49:31
 */
@Repository
public interface RdAccountDetailDao extends BaseMapper<RdAccountDetailEntity> {

    /**
     * 批量添加研发费用明细
     * @param rdAccountDetails
     * @return
     */
    int addList(@Param("rdAccountDetails")List<RdAccountDetailEntity> rdAccountDetails);
}
