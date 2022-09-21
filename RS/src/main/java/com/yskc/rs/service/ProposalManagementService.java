package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.proposal.ProposalManagementModel;
import com.yskc.rs.models.proposal.QueryProposalModel;
import com.yskc.rs.models.UserInfo;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/7/14 11:10
 * @Description:
 */
public interface ProposalManagementService {
    /**
     * 保存提案
     * @param userInfo
     * @param model
     * @return
     */
    boolean saveProposal(UserInfo userInfo, ProposalManagementModel model) throws OwnerException;

    PageModel<List<ProposalManagementModel>> getList(Integer companyId, QueryProposalModel query);

    /**
     * 删除提案
     * @param models
     * @return
     */
    boolean delProposal(List<ProposalManagementModel> models) throws OwnerException;
}
