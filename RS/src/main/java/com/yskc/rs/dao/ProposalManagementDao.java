package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.proposal.ProposalManagement;
import com.yskc.rs.models.proposal.ProposalManagementModel;
import com.yskc.rs.models.proposal.QueryProposalModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/14 11:02
 * @Description:
 */
@Repository
public interface ProposalManagementDao extends BaseMapper<ProposalManagement> {
    /**
     * 获取提案列表
     * @param companyId
     * @param page
     * @param query
     * @return
     */
    List<ProposalManagementModel> getList(@Param("companyId") Integer companyId, @Param("page") Pagination page, @Param("query") QueryProposalModel query);

    /**
     * 编辑提案信息
     * @param entity
     * @return
     */
    int edit(@Param("entity") ProposalManagement entity);
}
