package com.yskc.rs.service;

import com.yskc.common.model.PageModel;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.proposal.ProposalListModel;
import com.yskc.rs.models.proposal.ProposalRelevanceModel;
import com.yskc.rs.models.proposal.QueryProposalModel;

import java.util.List;

/**
 * @DateTime: 2022/3/24 16:35
 * @Description:
 * @author: hsx
 */
public interface ProposalListService {

    /**
     * 保存提案列表信息
     * @param userInfo
     * @param model
     * @return
     */
    boolean save(UserInfo userInfo, ProposalListModel model);

    /**
     * 获取提案列表信息
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<ProposalListModel>> getList(Integer companyId, QueryProposalModel query);

    /**
     * 删除填列表数据
     * @param model
     * @return
     */
    boolean del(BatchDeleteModel model);

    /**
     * 获取提案列表信息详情
      * @param id
     * @return
     */
    ProposalListModel getInfo(Integer id);

    /**
     * 根据项目Id获取关联的列表数据
     * @param id
     * @return
     */
    List<ProposalListModel> getByProjectId(Integer id);

    /**
     * 关联项目
     * @param model
     * @param userInfo
     * @return
     */
    boolean relevance(ProposalRelevanceModel model,UserInfo userInfo)throws Exception;
}
