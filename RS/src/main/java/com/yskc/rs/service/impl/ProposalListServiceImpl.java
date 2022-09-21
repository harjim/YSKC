package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.ProposalListDao;
import com.yskc.rs.entity.proposal.ProposalListEntity;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.proposal.ProposalListModel;
import com.yskc.rs.models.proposal.ProposalRelevanceModel;
import com.yskc.rs.models.proposal.QueryProposalModel;
import com.yskc.rs.service.ProposalListService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2022/3/24 16:35
 * @Description:
 * @author: hsx
 */
@Service
public class ProposalListServiceImpl implements ProposalListService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProposalListDao proposalListDao;

    @Override
    public boolean save(UserInfo userInfo, ProposalListModel model) {
        Date date = new Date();
        ProposalListEntity entity = new ProposalListEntity();
        BeanUtils.copyProperties(model,entity);
        if (null == entity.getId()) {
            entity.setCompanyId(userInfo.getCompanyId());
            entity.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
            return proposalListDao.insert(entity)>0;
        } else {
            entity.update(userInfo.getUserId(), userInfo.getMsUserId(), date);
            return proposalListDao.edit(entity)>0;
        }
    }

    @Override
    public PageModel<List<ProposalListModel>> getList(Integer companyId, QueryProposalModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("pl.createTime");
            page.setDescs(descs);
        }
        List<ProposalListModel> list = proposalListDao.getList(page,companyId, query);
        return PageUtils.build(page,list);
    }

    @Override
    public ProposalListModel getInfo(Integer id) {
        return proposalListDao.getInfo(id);
    }

    @Override
    public List<ProposalListModel> getByProjectId(Integer id) {
        List<ProposalListModel> list = proposalListDao.getByProjectId(id);
        list.forEach(item->{
            if (null == item.getProjectId()) {
                item.setRelevance(false);
            } else {
                item.setRelevance(true);
            }
        });
        return list;
    }

    @Override
    public boolean relevance(ProposalRelevanceModel model,UserInfo userInfo) throws Exception{
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            Integer projectId = model.getProjectId();
            proposalListDao.cancelRelevance(projectId,new Date(),userInfo);
            if (!CollectionUtils.isEmpty(model.getIds())) {
                proposalListDao.relevance(model, new Date(), userInfo);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("操作失败，请联系管理员.");
        }
    }

    @Override
    public boolean del(BatchDeleteModel model) {
        return proposalListDao.deleteBatchIds(model.getIds())>0;
    }
}
