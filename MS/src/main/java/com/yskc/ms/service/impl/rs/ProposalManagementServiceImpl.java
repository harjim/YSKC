package com.yskc.ms.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.FlowInstanceProposalDao;
import com.yskc.ms.dao.rs.ProposalManagementDao;
import com.yskc.ms.entity.ms.FlowInstanceProposal;
import com.yskc.ms.entity.rs.AuditProposalEntity;
import com.yskc.ms.models.ProposalManagementModel;
import com.yskc.ms.models.QueryProposalModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.service.rs.ProposalManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @DateTime: 2021/9/6 16:46
 * @Description:提案管理
 */
@Service
public class ProposalManagementServiceImpl implements ProposalManagementService {

    @Autowired
    private ProposalManagementDao proposalManagementDao;

    @Autowired
    private FlowInstanceProposalDao flowInstanceProposalDao;

    @Override
    public PageResult getList(QueryProposalModel query, UserInfo userInfo) {

        Pagination pagination = query.getPagination();
        if (CollectionUtils.isEmpty(pagination.getAscs()) && CollectionUtils.isEmpty(pagination.getDescs())) {
            pagination.setDescs(CollUtil.newArrayList( "pm.createTime"));
        }
        List<ProposalManagementModel> list = proposalManagementDao.getList(pagination,query);
        if (!CollectionUtils.isEmpty(list)) {
            List<Integer> proposalIds = list.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<FlowInstanceProposal> proposalAudits = flowInstanceProposalDao.getProposalAuditStatus(proposalIds, userInfo.getId());
            Map<Integer, FlowInstanceProposal> permissionMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(proposalAudits)) {
                permissionMap = proposalAudits.stream().collect(Collectors.toMap(e -> e.getProposalId(), e -> e));
            }
            for (ProposalManagementModel pModel : list) {
                pModel.setHasPermission(permissionMap.containsKey(pModel.getId()));
            }
        }
        return PageUtils.buildPageResult(pagination, list);
    }

    @Override
    public ProposalManagementModel getProposal(Integer id) {
        return proposalManagementDao.getProposal(id);
    }

    @Override
    public Integer getProposalAuditInfo(Integer year, Integer companyId, Integer userId) {
        List<AuditProposalEntity> proposalAudits = proposalManagementDao.getAuditInfo(year, companyId);
        Integer proposalAuditNum = 0;
        if (!CollectionUtils.isEmpty(proposalAudits)) {
            List<Integer> projectIds = proposalAudits.stream().map(e -> e.getProposalId()).collect(Collectors.toList());
            List<FlowInstanceProposal> reports = flowInstanceProposalDao.getProposalAudits(projectIds, userId);
            proposalAuditNum = CollectionUtils.isEmpty(reports) ? 0 : reports.size();
        }
        return proposalAuditNum;
    }
}
