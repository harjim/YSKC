package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.FlowNodeTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.entity.ms.*;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.flow.*;
import com.yskc.ms.models.role.AppRoleModel;
import com.yskc.ms.service.ms.FlowService;
import com.yskc.ms.service.rs.KafkaQueueService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/12/15 11:31
 * description:
 */
@Service
public class FlowServiceImpl implements FlowService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FlowDao flowDao;
    @Autowired
    private FlowNodeDao flowNodeDao;
    @Autowired
    private FlowBranchDao flowBranchDao;
    @Autowired
    private FlowNodeUserDao flowNodeUserDao;
    @Autowired
    private FlowInstanceDao flowInstanceDao;
    @Autowired
    private AppRoleDao appRoleDao;
    @Autowired
    private FlowInstanceUserDao flowInstanceUserDao;
    @Autowired
    private KafkaQueueService kafkaQueueService;

    @Override
    public PageModel<List<FlowModel>> getList(QueryFlowModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            page.setDescs(desc);
        }
        List<FlowModel> flows = flowDao.getList(page, query);
        if (!CollectionUtils.isEmpty(flows)) {
            List<Integer> flowIds = flows.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<FlowInstance> flowInstances = flowInstanceDao.getByFlow(flowIds);
            Map<Integer, FlowInstance> dataMap = new HashMap<>();
            for (FlowInstance data : flowInstances) {
                if (!dataMap.containsKey(data.getFlowId())) {
                    dataMap.put(data.getFlowId(), data);
                }
            }
            for (FlowModel model : flows) {
                if (dataMap.containsKey(model.getId())) {
                    model.setSign(false);
                } else {
                    model.setSign(true);
                }
            }

        }
        return PageUtils.build(page, flows);
    }

    @Override
    public Boolean addFlow(FlowEntity entity) {
        entity.setCreateTime(new Date());
        return flowDao.insert(entity) > 0;
    }

    @Override
    public List<FlowNodeTreeModel> getFlowInfo(Integer flowId) {
        return getInfo(flowId);
    }


    private List<FlowNodeTreeModel> getInfo(Integer flowId) {
        List<FlowNodeTreeModel> modelList = flowNodeDao.getByFlow(flowId);
        if (CollectionUtils.isEmpty(modelList)) {
            return new ArrayList<>();
        }

        List<FlowNodeTreeModel> list = new ArrayList<>();
        //节点排序
        Map<Integer, FlowNodeTreeModel> sortNodeMap = new HashMap<>();
        FlowNodeTreeModel firstNode = null;
        List<Integer> allNodeId = new ArrayList<>();
        for (FlowNodeTreeModel treeModel : modelList) {
            allNodeId.add(treeModel.getNodeId());
            sortNodeMap.put(treeModel.getNodeId(), treeModel);
            if (treeModel.getPrevNode() == null) {
                firstNode = treeModel;
            }
        }
        list.add(firstNode);
        for (int i = 0; i < modelList.size() - 1; i++) {
            FlowNodeTreeModel sortModel = list.get(i);
            if (sortNodeMap.containsKey(sortModel.getNextNode())) {
                list.add(sortNodeMap.get(sortModel.getNextNode()));
            }
        }

        List<FlowNodeUserModel> nodeUsers = flowNodeUserDao.getNodeUser(allNodeId);
        Map<Integer, List<NodeAuditorModel>> userMap = new HashMap<>();//节点用户列表
        Map<Integer, Integer> nodeUserType = new HashMap<>();//节点用户审核类型
        Map<Integer,String> remarkMap = new HashMap<>();//节点用户备注
        if (!CollectionUtils.isEmpty(nodeUsers)) {
            for (FlowNodeUserModel nodeUser : nodeUsers) {
                NodeAuditorModel auditor = new NodeAuditorModel();
                auditor.setLevel(nodeUser.getLevel());
                auditor.setDataId(nodeUser.getDataId());
                String userName = nodeUser.getUserName();

                if (nodeUser.getType() == 2) {
                    switch (nodeUser.getDataId()) {
                        case 1:
                            userName = "一级主管";
                            break;
                        case 2:
                            userName = "二级主管";
                            break;
                        case 3:
                            userName = "三级主管";
                            break;
                        case 4:
                            userName = "四级主管";
                            break;
                        case 5:
                            userName = "五级主管";
                            break;
                        case 6:
                            userName = "六级主管";
                            break;
                        case 7:
                            userName = "七级主管";
                            break;
                        case 8:
                            userName = "八级主管";
                            break;
                        case 9:
                            userName = "九级主管";
                            break;
                        case 10:
                            userName = "十级主管";
                            break;
                    }
                }
                auditor.setUserName(userName);
                if (!nodeUserType.containsKey(nodeUser.getNodeId())) {
                    nodeUserType.put(nodeUser.getNodeId(), nodeUser.getType());
                }
                if (!userMap.containsKey(nodeUser.getNodeId())) {
                    List<NodeAuditorModel> user = new ArrayList<>();
                    userMap.put(nodeUser.getNodeId(), user);
                }
                userMap.get(nodeUser.getNodeId()).add(auditor);
                remarkMap.put(nodeUser.getNodeId(), nodeUser.getRemark());
            }
        }
        List<Integer> nodeIds = new ArrayList<>();
        for (FlowNodeTreeModel model : list) {
            if (isBranchNode(model.getType())) {
                nodeIds.add(model.getNodeId());
            }
        }
        Map<Integer, List<FlowBranchTree>> dataMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(nodeIds)) {
            List<FlowBranchTree> branches = flowBranchDao.getByNode(nodeIds);
            for (FlowBranchTree branch : branches) {

                if (branch.getFlowId() != null) {
                    List<FlowNodeTreeModel> childFlow = getInfo(branch.getFlowId());
                    branch.setNewFlow(childFlow);
                }
                if (!dataMap.containsKey(branch.getNodeId())) {
                    List<FlowBranchTree> newArr = new ArrayList<>();
                    dataMap.put(branch.getNodeId(), newArr);
                }
                dataMap.get(branch.getNodeId()).add(branch);
            }
        }
        for (FlowNodeTreeModel model : list) {
            Integer nodeId = model.getNodeId();
            if (dataMap.containsKey(nodeId)) {
                model.setList(dataMap.get(nodeId));
            }
            model.setUserType(nodeUserType.get(model.getNodeId()));
            model.setUserList(userMap.get(model.getNodeId()));
            model.setRemark(remarkMap.get(model.getNodeId()));
        }
        return list;
    }

    @Override
    public List<FlowNodeTreeModel> saveFlow(List<FlowNodeTreeModel> models, UserInfo userInfo) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            return new ArrayList<>();
        }
        Integer flowId = models.get(0).getFlowId();
        Date date = new Date();
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            saveNodeData(models, userInfo.getId(), date, flowId, null);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return getInfo(flowId);
        } catch (OwnerException e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("保存失败，请联系管理员。");
        }
    }

    @Override
    public Boolean delFlow(Integer flowId) throws OwnerException {
        List<FlowInstance> flowInstances = flowInstanceDao.getByFlow(Arrays.asList(flowId));
        if (!CollectionUtils.isEmpty(flowInstances)) {
            throw new OwnerException("该流程已应用，不能删除！");
        }
        List<FlowNodeTreeModel> flowNodes = flowNodeDao.getByFlow(flowId);
        if (CollectionUtils.isEmpty(flowNodes)) {
            return flowDao.deleteById(flowId) > 0;
        }
        List<Integer> nodeIds = flowNodes.stream().map(e -> e.getNodeId()).collect(Collectors.toList());
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);

            List<FlowBranch> branches = flowBranchDao.getNodeBranch(nodeIds);
            if (!CollectionUtils.isEmpty(branches)) {
                List<Integer> branchIds = branches.stream().map(e -> e.getId()).collect(Collectors.toList());
                delNodeBranch(branchIds);
            }
            //删除节点分支
            flowBranchDao.delByNode(nodeIds);
            //删除分支用户
            flowNodeUserDao.delByNodes(nodeIds);
            //删除原有节点
            flowNodeDao.delByFlow(flowId);
            //删除流程
            flowDao.deleteById(flowId);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("保存失败，请联系管理员。");
        }
    }

    /**
     * 保存节点数据
     *
     * @param models
     * @param userId
     * @param date
     * @param flowId
     * @throws OwnerException
     */
    private void saveNodeData(List<FlowNodeTreeModel> models, Integer userId, Date date, Integer flowId, Integer parentId) throws OwnerException {
        List<FlowNodeTreeModel> existNodes = flowNodeDao.getByFlow(flowId);
        if (CollectionUtils.isEmpty(models)) {
            return;
        }
        List<Integer> saveNodeIds = models.stream().filter(e -> e.getNodeId() != null && e.getNodeId() > 0).map(e -> e.getNodeId()).collect(Collectors.toList());
        List<Integer> delNodeIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(existNodes)) {
            delNodeIds = existNodes.stream().map(e -> e.getNodeId()).collect(Collectors.toList());
            delNodeIds.removeAll(saveNodeIds);
        }
        if (!CollectionUtils.isEmpty(delNodeIds)) {
            List<FlowInstance> flowInstances = flowInstanceDao.getByNode(delNodeIds);
            if (!CollectionUtils.isEmpty(flowInstances)) {
                throw new OwnerException("不能删除正处于审核的的流程节点！");
            }
            //删除节点分支
            flowBranchDao.delByNode(delNodeIds);
            //删除原有节点
            flowNodeDao.deleteBatchIds(delNodeIds);
            //删除原有节点用户
            flowNodeUserDao.delByNodes(delNodeIds);
        }
        //删除流程的所有审核人员
        List<FlowNodeUserEntity> nodeUsers = flowNodeUserDao.getUserByFlow(flowId);
        if (!CollectionUtils.isEmpty(nodeUsers)) {
            List<Integer> nodeUserIds = nodeUsers.stream().map(e -> e.getId()).collect(Collectors.toList());
            flowNodeUserDao.delByFlowId(nodeUserIds);
        }
        addOrUpdateNode(models, userId, date, flowId, saveNodeIds, parentId);

    }

    private void  addOrUpdateNode(List<FlowNodeTreeModel> models, Integer userId, Date date, Integer flowId,
                                 List<Integer> saveNodeIds, Integer parentId) throws OwnerException {
        List<FlowNodeEntity> changeFlowNodes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(saveNodeIds)) {
            changeFlowNodes = flowNodeDao.selectBatchIds(saveNodeIds);
        }
        Map<Integer, FlowNodeEntity> existMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(changeFlowNodes)) {
            existMap = changeFlowNodes.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        }
        List<FlowNodeEntity> sortNodes = new ArrayList<>();
        List<FlowNodeUserEntity> nodeUsers = new ArrayList<>();
        for (FlowNodeTreeModel model : models) {
            FlowNodeEntity entity;
            if (model.getNodeId() != null) {
                entity = existMap.get(model.getNodeId());
                if (entity == null) {
                    entity = new FlowNodeEntity(userId, date, flowId, model.getNodeName(), model.getType(), model.getAuditType(), parentId,
                                                model.getNodeExpired(),model.getNodeNumber(),model.getSkip());
                    flowNodeDao.insert(entity);
                } else {
                    BeanUtils.copyProperties(model, entity);
                    entity.setLastUpdateTime(date);
                    entity.setMode(model.getAuditType());
                    entity.setLastUpdatorId(userId);
                    entity.setFlowId(flowId);
                    entity.setParentId(parentId);
                    flowNodeDao.updateFlow(entity);
                }
            } else {
                entity = new FlowNodeEntity(userId, date, flowId, model.getNodeName(), model.getType(), model.getAuditType(), parentId,
                                            model.getNodeExpired(),model.getNodeNumber(),model.getSkip());
                flowNodeDao.insert(entity);
            }
            sortNodes.add(entity);
            if (isBranchNode(model.getType())) {
                branchNode(model, date, userId, entity.getId());
            } else {
                if (CollectionUtils.isEmpty(model.getUserList())) {
                    String userType = FlowNodeTypeEnum.isAudit(model.getType()) ? "审核人" : "抄送人";
                    throw new OwnerException(MessageFormat.format("节点：{0}未设置{1}", model.getNodeName(), userType));
                }
                List<Integer> userIds = model.getUserList().stream().map(e -> e.getDataId()).collect(Collectors.toList());
                List<FlowNodeUserEntity> userList = insertUsers(userIds, model.getUserType(), entity.getId(), userId, date,
                        model.getUserList().get(0).getLevel(), model.getRemark());
                if (!CollectionUtils.isEmpty(userList)) {
                    nodeUsers.addAll(userList);
                }
            }
        }
        if (!CollectionUtils.isEmpty(sortNodes)) {
            sortNode(sortNodes);
        }
        if (!CollectionUtils.isEmpty(nodeUsers)) {
            flowNodeUserDao.insertList(nodeUsers);
        }

    }

    //处理分支
    private void branchNode(FlowNodeTreeModel model, Date date, Integer userId, Integer nodeId) throws OwnerException {
        List<FlowBranch> insertList = new ArrayList<>();
        List<FlowBranch> updateList = new ArrayList<>();
        List<FlowBranchTree> list = model.getList();
        if (CollectionUtils.isEmpty(list)) {
            throw new OwnerException("分支节点分支不能为空！");
        }
        List<Integer> branchIds = list.stream().filter(e -> e.getBranchId() != null).map(e -> e.getBranchId()).collect(Collectors.toList());
        List<FlowBranch> branches = flowBranchDao.getNodeBranch(Arrays.asList(nodeId));
        if (!CollectionUtils.isEmpty(branches)) {
            List<Integer> existBranchIds = branches.stream().map(e -> e.getId()).collect(Collectors.toList());
            existBranchIds.removeAll(branchIds);
            if (!CollectionUtils.isEmpty(existBranchIds)) {
                delNodeBranch(existBranchIds);
            }
        }
        Map<Integer, FlowBranch> branchMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(branchIds)) {
            List<FlowBranch> flowBranches = flowBranchDao.selectBatchIds(branchIds);
            branchMap = flowBranches.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        }
        for (FlowBranchTree branch : list) {
            // 并行分支不需要设条件
            if (FlowNodeTypeEnum.isParallel(model.getType())){
                branch.setCondition("");
            } else if (StringUtils.isEmpty(branch.getCondition())) {
                throw new OwnerException("请指定分支条件后再保存！");
            }
            Integer flowId = null;
            FlowBranch flowBranch;
            List<FlowNodeTreeModel> treeModels = branch.getNewFlow();
            if (branch.getBranchId() != null) {
                if (!branchMap.containsKey(branch.getBranchId())) {
                    throw new OwnerException("分支节点已删除或不存在，数据异常，请联系管理员！");
                }
                flowBranch = branchMap.get(branch.getBranchId());
                BeanUtils.copyProperties(branch, flowBranch);
                flowBranch.setLastUpdateTime(date);
                flowBranch.setLastUpdatorId(userId);
                if (!CollectionUtils.isEmpty(treeModels)) {
                    if (flowBranch.getFlowId() == null) {
                        FlowEntity flowEntity = new FlowEntity(branch.getNewFlowName(), 1, branch.getRemark(), date);
                        flowDao.insert(flowEntity);
                        flowBranch.setFlowId(flowEntity.getId());
                    } else {
                        FlowEntity flowEntity = new FlowEntity();
                        flowEntity.setId(flowBranch.getFlowId());
                        flowEntity.setName(branch.getNewFlowName());
                        flowDao.updateById(flowEntity);
                    }
                }
                updateList.add(flowBranch);
                flowId = flowBranch.getFlowId();
            } else {
                if (!CollectionUtils.isEmpty(treeModels)) {
                    FlowEntity flowEntity = new FlowEntity(branch.getNewFlowName(), 1, branch.getRemark(), date);
                    flowDao.insert(flowEntity);
                    flowId = flowEntity.getId();
                }
                flowBranch = new FlowBranch(userId, date, nodeId, branch.getCondition(), flowId, branch.getSeq());
                insertList.add(flowBranch);
            }
            if (!CollectionUtils.isEmpty(treeModels)) {
                saveNodeData(treeModels, userId, date, flowId, nodeId);
            }
        }
        if (!CollectionUtils.isEmpty(insertList)) {
            flowBranchDao.insertList(insertList);
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            flowBranchDao.updateList(updateList);
        }
    }

    /**
     * 维护节点上下关系
     *
     * @param list
     */
    private void sortNode(List<FlowNodeEntity> list) {
        int index = 0;
        for (FlowNodeEntity entity : list) {
            if (index > 0 && index <= list.size()) {
                entity.setPrevNode(list.get(index - 1).getId());
            }
            if (index >= 0 && index < list.size() - 1) {
                entity.setNextNode(list.get(index + 1).getId());
            }
            index += 1;
        }
        flowNodeDao.updateList(list);
    }

    /**
     * 删除分支节点集合
     *
     * @param branchIds
     */
    private void delNodeBranch(List<Integer> branchIds) throws OwnerException {
        flowBranchDao.deleteBatchIds(branchIds);
        List<FlowNodeEntity> flowNodes = flowNodeDao.getNodeByBranch(branchIds);
        if (!CollectionUtils.isEmpty(flowNodes)) {
            List<Integer> delNodeIds = flowNodes.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<FlowInstance> flowInstances = flowInstanceDao.getByNode(delNodeIds);
            if (!CollectionUtils.isEmpty(flowInstances)) {
                throw new OwnerException("存在正处于审核的的流程节点，不能删除！");
            }
            flowNodeDao.deleteBatchIds(delNodeIds);
            flowNodeUserDao.delByNodes(delNodeIds);
            for (FlowNodeEntity node : flowNodes) {
                if (isBranchNode(node.getType())) {
                    List<FlowBranch> branches = flowBranchDao.getNodeBranch(Arrays.asList(node.getId()));
                    if (!CollectionUtils.isEmpty(branches)) {
                        List<Integer> existBranchIds = branches.stream().map(e -> e.getId()).collect(Collectors.toList());
                        delNodeBranch(existBranchIds);
                    }
                }
            }
        }

    }

    private List<FlowNodeUserEntity> insertUsers(List<Integer> userIds, Integer type, Integer nodeId, Integer uid, Date date,Integer level,String remark) {
        List<FlowNodeUserEntity> insertList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userIds)) {
            for (Integer userId : userIds) {
                FlowNodeUserEntity nodeUser = new FlowNodeUserEntity();
                nodeUser.setDataId(userId);
                nodeUser.setNodeId(nodeId);
                nodeUser.setType(type);
                nodeUser.setCreatorId(uid);
                nodeUser.setCreateTime(date);
                nodeUser.setLevel(level);
                nodeUser.setRemark(remark);
                insertList.add(nodeUser);
            }
        }
        return insertList;
    }

    @Override
    public Boolean setFlowUser(SetNodeUserModel model, UserInfo userInfo) throws OwnerException {
        Date date = new Date();

        List<Integer> userIds = model.getUserIds();
        List<FlowNodeUserEntity> insertList = insertUsers(userIds, model.getType(), model.getNodeId(), userInfo.getId(), date,null, model.getRemark());
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            List<FlowNodeUserModel> nodeUsers = flowNodeUserDao.getNodeUser(Arrays.asList(model.getNodeId()));
            if (!CollectionUtils.isEmpty(nodeUsers)) {
                flowNodeUserDao.delByNodes(Arrays.asList(model.getNodeId()));
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                flowNodeUserDao.insertList(insertList);
            }
            if (model.getAuditType() != null) {
                FlowNodeEntity flowNode = flowNodeDao.selectById(model.getNodeId());
                flowNode.setMode(model.getAuditType());
                flowNode.setLastUpdateTime(date);
                flowNode.setLastUpdatorId(userInfo.getId());
                flowNodeDao.updateById(flowNode);
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("设置节点用户，请联系管理员。");
        }
    }

    @Override
    public List<AppRoleModel> getRoleList(String roleName) {
        List<AppRoleModel> list = appRoleDao.getRoleList(roleName);
        return list;
    }

    /**
     * 判断是否是分支节点
     * @param nodeType
     * @return
     */
    static private boolean isBranchNode(Integer nodeType){
        return FlowNodeTypeEnum.isBranch(nodeType) || FlowNodeTypeEnum.isParallel(nodeType);
    }
}
