package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.ProposalManagementDao;
import com.yskc.rs.dao.project.AuditProposalDao;
import com.yskc.rs.entity.proposal.ProposalManagement;
import com.yskc.rs.models.proposal.ProposalManagementModel;
import com.yskc.rs.models.proposal.QueryProposalModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.ProposalManagementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: hck
 * @DateTime: 2021/7/14 11:11
 * @Description:
 */
@Service
public class ProposalManagementServiceImpl implements ProposalManagementService {

    @Autowired
    private ProposalManagementDao proposalManagementDao;
    @Autowired
    private AuditProposalDao auditProposalDao;


    @Override
    public boolean saveProposal(UserInfo userInfo, ProposalManagementModel model) throws OwnerException {
        if(null==model){
            throw new OwnerException("数据异常，请联系管理员！");
        }
        Date date=new Date();
        if(null==model.getId()){
            ProposalManagement proposal=new ProposalManagement();
            BeanUtils.copyProperties(model,proposal);
            proposal.setCompanyId(userInfo.getCompanyId());
            proposal.create(userInfo.getUserId(),userInfo.getMsUserId(),date);
           return proposalManagementDao.insert(proposal)>0;
        }else {
            Integer status = auditProposalDao.selectByProposalId(model.getId());
            if (FlowInstanceStatusEnum.canModify(status)) {
                ProposalManagement proposal=proposalManagementDao.selectById(model.getId());
                BeanUtils.copyProperties(model,proposal);
                proposal.update(userInfo.getUserId(),userInfo.getMsUserId(),date);
                return proposalManagementDao.edit(proposal)>0;
            }
            throw new OwnerException("该提案处于审核中，不能编辑！");
        }
    }

    @Override
    public PageModel<List<ProposalManagementModel>> getList(Integer companyId, QueryProposalModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("pm.createTime");
            page.setDescs(descs);
        }
        List<ProposalManagementModel> list = proposalManagementDao.getList(companyId, page, query);
        //未进行提交操作的file默认为未提交状态
        for (ProposalManagementModel proposalManagementModel : list) {
            if (Objects.equals(null, proposalManagementModel.getStatus())) {
                proposalManagementModel.setStatus(FlowInstanceStatusEnum.NO_SUBMIT.getStatus());
            }
        }
        return PageUtils.build(page, list);
    }

    @Override
    public boolean delProposal(List<ProposalManagementModel> models) throws OwnerException {
        if(CollectionUtils.isEmpty(models)){
            throw new OwnerException("请选择要删除的提案！");
        }
        List<Integer> ids=models.stream().map(e->e.getId()).collect(Collectors.toList());
        List<Integer> statusList = auditProposalDao.selectByIds(ids);
        for (Integer status : statusList) {
            if (!FlowInstanceStatusEnum.canModify(status)) {
                throw new OwnerException("删除列表中存在提案处于审核，不能删除！");
            }
        }
        return proposalManagementDao.deleteBatchIds(ids)>0;
    }

}
