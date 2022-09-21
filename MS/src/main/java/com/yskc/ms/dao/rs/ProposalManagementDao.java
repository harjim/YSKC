package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.AuditProposalEntity;
import com.yskc.ms.entity.rs.ProposalManagement;
import com.yskc.ms.entity.rs.SysDocumentEntity;
import com.yskc.ms.models.ProposalManagementModel;
import com.yskc.ms.models.QueryProposalModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @DateTime: 2021/9/6 15:40
 * @Description:
 */
@Repository
public interface ProposalManagementDao extends BaseMapper<ProposalManagementModel> {


    List<ProposalManagementModel> getList(@Param("query") Pagination pagination, @Param("query") QueryProposalModel query);

    /**
     * 获取提案信息
     * @param id
     * @return
     */
    ProposalManagementModel getProposal(@Param("id") Integer id);

    List<AuditProposalEntity> getAuditInfo(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 获取提案
     * @param ids
     * @return
     */
    List<ProposalManagement> getProposals(@Param("ids") Set<Integer> ids);

    /**
     * 获取成果
     * @param ids
     * @return
     */
    List<SysDocumentEntity> getResults(@Param("ids") Set<Integer> ids);
}
