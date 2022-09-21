package com.yskc.ms.service.impl.ms;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.dao.ms.FlowNodeDao;
import com.yskc.ms.dao.ms.FlowNodeUserDao;
import com.yskc.ms.entity.ms.FlowNodeEntity;
import com.yskc.ms.entity.ms.FlowNodeUserEntity;
import com.yskc.ms.entity.ms.ProjectMember;
import com.yskc.ms.entity.ms.ProjectMemberLogEntity;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.flow.FlowNodeModel;
import com.yskc.ms.models.flow.FlowNodeUserModel;
import com.yskc.ms.models.flow.SetNodeUserModel;
import com.yskc.ms.models.project.ProjectMemberModel;
import com.yskc.ms.service.ms.FlowNodeService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.lang.invoke.SwitchPoint;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/12/3 8:50
 * description:流程接口实现
 */
@Service
public class FlowNodeServiceImpl implements FlowNodeService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FlowNodeDao flowNodeDao;
    @Autowired
    private FlowNodeUserDao flowNodeUserDao;

    @Override
    public List<FlowNodeModel> getNodes(Integer flowId) {
        List<FlowNodeModel> nodes = flowNodeDao.getNodes(flowId);
        return nodes;
    }

    @Override
    public Map<Integer, List<FlowNodeUserModel>> getNodeUser(Integer flowId) {
        Map<Integer, List<FlowNodeUserModel>> dataMap = new HashMap<>();
        List<FlowNodeUserModel> nodeUsers = flowNodeUserDao.getFlowUser(flowId);
        if (!CollectionUtils.isEmpty(nodeUsers)) {
            for (FlowNodeUserModel model : nodeUsers) {
                if (!dataMap.containsKey(model.getNodeId())) {
                    List<FlowNodeUserModel> list = new ArrayList<>();
                    dataMap.put(model.getNodeId(), list);
                }
                dataMap.get(model.getNodeId()).add(model);
            }
        }
        return dataMap;
    }



    @Override
    public Boolean addFlowNode(FlowNodeModel model, UserInfo userInfo) throws OwnerException {
        Date date = new Date();
        switch (model.getType().intValue()) {
            case 1:
            case 2:
              return createNode(model, date, userInfo.getId());
            case 3:
                break;
        }

        return null;
    }

    private Boolean createNode(FlowNodeModel model, Date date, Integer userId) throws OwnerException{
        FlowNodeEntity flowNode = new FlowNodeEntity();
        BeanUtils.copyProperties(model, flowNode);
        flowNode.setCreateTime(date);
        flowNode.setCreatorId(userId);
        flowNode.setLastUpdateTime(date);
        flowNode.setLastUpdatorId(userId);
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            flowNodeDao.insert(flowNode);
            if (model.getPrevNode() != null) {
                FlowNodeEntity prevNode = flowNodeDao.selectById(model.getPrevNode());
                prevNode.setNextNode(flowNode.getId());
                prevNode.setLastUpdatorId(userId);
                prevNode.setLastUpdateTime(date);
                flowNodeDao.updateById(prevNode);
            }
            if (model.getNextNode() != null) {
                FlowNodeEntity nextNode = flowNodeDao.selectById(model.getNextNode());
                nextNode.setPrevNode(flowNode.getId());
                nextNode.setLastUpdateTime(date);
                nextNode.setLastUpdatorId(userId);
                flowNodeDao.updateById(nextNode);
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("添加节点失败，请联系管理员。");
        }
    }

    private Boolean createBantch(FlowNodeModel model,Integer userId,Date date){
        FlowNodeEntity flowNode = new FlowNodeEntity();
        BeanUtils.copyProperties(model, flowNode);
        flowNode.setCreateTime(date);
        flowNode.setCreatorId(userId);
        flowNode.setLastUpdateTime(date);
        flowNode.setLastUpdatorId(userId);
        return false;
    }
}

