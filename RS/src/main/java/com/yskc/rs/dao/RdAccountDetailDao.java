package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.RdAccountDetailEntity;
import com.yskc.rs.models.rdaccountdetail.QueryRdAccountDetailModel;
import com.yskc.rs.models.rdaccountdetail.RdAccountDetailModel;
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
     * 获取研发费用明细
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<RdAccountDetailModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryRdAccountDetailModel query);

    /**
     * 批量添加研发费用明细
     * @param rdAccountDetails
     * @return
     */
    int addList(@Param("rdAccountDetails")List<RdAccountDetailEntity> rdAccountDetails);
}
