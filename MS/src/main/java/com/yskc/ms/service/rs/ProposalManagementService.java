package com.yskc.ms.service.rs;

import com.yskc.common.model.PageResult;
import com.yskc.ms.models.ProposalManagementModel;
import com.yskc.ms.models.QueryProposalModel;
import com.yskc.ms.models.UserInfo;

/**
 * @DateTime: 2021/9/6 16:45
 * @Description: 提案管理
 */
public interface ProposalManagementService {

    PageResult getList(QueryProposalModel query, UserInfo userInfo);

    /**
     * 获取提案管理
     *
     * @param id
     * @return
     */
    ProposalManagementModel getProposal(Integer id);

    Integer getProposalAuditInfo(Integer year, Integer companyId, Integer userId);
}
