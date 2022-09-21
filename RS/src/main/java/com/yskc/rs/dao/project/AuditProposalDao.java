package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.proposal.ProposalAuditEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/9/3 15:28
 * @Description:
 */
@Repository
public interface AuditProposalDao extends BaseMapper<ProposalAuditEntity> {

    /**
     * 获取不能提交审核的提案
     * @param proposalIds
     * @param moduleId
     * @return
     */
    List<ProposalAuditEntity> getProposalAudits(@Param("proposalIds") List<Integer> proposalIds, @Param("moduleId") Integer moduleId);

    /**
     * 插入并更新提案审核
     * @param audits
     * @return
     */
    Integer insertOrUpdate(@Param("audits") List<ProposalAuditEntity> audits);

    /**
     * 根据Id批量查询提案审批状态
     * @param ids
     * @return
     */
    List<Integer> selectByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据Id查询提案审批状态
     * @param id
     * @return
     */
    Integer selectByProposalId(@Param("id") Integer id);
}
