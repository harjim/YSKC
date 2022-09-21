package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.dao.rs.ProjectDocFileDao;
import com.yskc.ms.entity.ms.*;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.FormAuditModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.flow.FlowListModel;
import com.yskc.ms.models.flow.QueryFlowModel;
import com.yskc.ms.models.flow.SetFlowMasterModel;
import com.yskc.ms.models.flowInstance.FlowInstanceActivateModel;
import com.yskc.ms.models.flowInstance.FlowInstanceInfoModel;
import com.yskc.ms.models.flowInstance.FlowInstanceModel;
import com.yskc.ms.models.params.MyAuditParams;
import com.yskc.ms.models.projectAudit.BatchAuditModel;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.service.ms.FlowInstanceService;
import com.yskc.ms.service.rs.KafkaQueueService;
import com.yskc.ms.utils.ToolUtil;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlowInstanceServiceImpl implements FlowInstanceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 记录及附件docFile表 id
     */
    private final static Integer APPENDIX_ID = 15;
    @Autowired
    private FlowInstanceDao flowInstanceDao;
    @Autowired
    private FlowInstanceProjectDao flowInstanceProjectDao;
    @Autowired
    private FlowInstanceReportDao flowInstanceReportDao;
    @Autowired
    private FlowInstanceDocFileDao flowInstanceDocFileDao;
    @Autowired
    private FlowInstancePatentDao flowInstancePatentDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;
    @Autowired
    private FlowInstanceProposalDao flowInstanceProposalDao;
    @Autowired
    private FlowInstanceAchievementDao flowInstanceAchievementDao;
    @Autowired
    private FlowInstanceUserDao flowInstanceUserDao;
    @Autowired
    private KafkaQueueService kafkaQueueService;
    @Autowired
    private FlowInstanceLogDao flowInstanceLogDao;
    @Autowired
    private FlowInstanceRdFeeDao flowInstanceRdFeeDao;
    @Autowired
    private FlowCurNodeDao flowCurNodeDao;
    @Autowired
    private FlowInstanceFormDao flowInstanceFormDao;
    @Autowired
    private ServiceApplyDao serviceApplyDao;

    @Override
    public PageModel<List<FlowInstanceModel>> getList(MyAuditParams params, Integer userId) {
        Pagination pagination = params.getPagination();
        if (CollectionUtils.isEmpty(pagination.getDescs()) && CollectionUtils.isEmpty(pagination.getAscs())) {
//            pagination.setAscs(CollUtil.newArrayList("nodeStatus"));
            pagination.setDescs(CollUtil.newArrayList("lastUpdateTime"));
        }
        if (params.getBeginDate() != null) {
            params.setBeginDate(DateUtil.beginOfDay(params.getBeginDate()));
        }
        if (params.getEndDate() != null) {
            params.setEndDate(DateUtil.beginOfDay(params.getEndDate()));
        }
        return PageUtils.build(pagination, flowInstanceDao.getList(pagination, params, userId));
    }

    @Override
    public FlowInstanceInfoModel getInstanceInfo(Integer id, Integer type) {
        if (null == id || null == type) {
            return null;
        }
        // 项目年份审核
        if (1 == type) {
            return flowInstanceProjectDao.getInstanceInfo(id);
            // 文档审核
        } else if (2 == type) {
            FlowInstanceInfoModel result = flowInstanceDocFileDao.getInstanceInfo(id);
            if (null != result) {
                DataModel docFileInfoModel = projectDocFileDao.getDocData(result.getDocFileId(), APPENDIX_ID);
                if (null != docFileInfoModel) {
                    result.setTemplateName(docFileInfoModel.getTemplateName());
                    result.setData(docFileInfoModel.getData());
                }
            }
            return result;
            // 查新报告
        } else if (3 == type || 5 == type) {
            return flowInstanceReportDao.getInstanceInfo(id);
        } else if (4 == type) {
            return flowInstancePatentDao.getInstanceInfo(id);
        } else if (6 == type) {
            return flowInstanceProposalDao.getInstanceInfo(id);
        } else if (7 == type) {
            return flowInstanceAchievementDao.getInstanceInfo(id);
        } else if (8 == type) {
            return flowInstanceRdFeeDao.getInstanceInfo(id);
        } else if (9 == type) {
            return flowInstanceFormDao.getInstanceInfo(id);
        }
        return null;
    }

    @Override
    public PageModel<List<FlowListModel>> search(QueryFlowModel query, DataPermModel dataPerm) throws OwnerException {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("fi.lastUpdateTime");
            page.setDescs(desc);
        }
        List<FlowListModel> list = flowInstanceDao.search(query, dataPerm, page);
        if (!CollectionUtils.isEmpty(list)) {
            List<FlowListModel> nodeCntList = flowInstanceDao.getNodeCnt(list);
            if (!CollectionUtils.isEmpty(nodeCntList)) {
                Map<Integer, Integer> instanceNodeCntMap = nodeCntList.stream().collect(Collectors.toMap(a -> a.getId(), b -> b.getNodeCnt(), (o, n) -> n));
                list.forEach(item -> item.setNodeCnt(instanceNodeCntMap.get(item.getId())));
            }
        }
        return PageUtils.build(page, list);
    }

    @Override
    public Boolean deliverMaster(SetFlowMasterModel model, UserInfo info) throws OwnerException {
        Date date = new Date();
        List<Integer> instanceIds = model.getInstanceIds();
        List<Integer> userIds = model.getUserIds();
        if (CollectionUtils.isEmpty(instanceIds) || CollectionUtils.isEmpty(userIds)) {
            throw new OwnerException("被转交的流程或人员不存在！");
        }
        Integer nodeId = model.getNodeId();
        List<FlowInstanceUser> instanceUsers = flowInstanceUserDao.getByInstance(instanceIds, nodeId);
        if (CollectionUtils.isEmpty(instanceUsers)) {
            throw new OwnerException("不存在进行中的流程，请稍后重试！");
        }
        Map<Integer, List<Integer>> instanceUserMap = new HashMap<>();
        instanceUsers.forEach(item -> ToolUtil.putAndAdd(instanceUserMap, item.getInstanceId(), item.getUserId()));
        List<Integer> finallyInstanceIds = new ArrayList<>();
        Map<Integer, Boolean> newUserMap = userIds.stream().collect(Collectors.toMap(a -> a, b -> Boolean.TRUE, (o, n) -> n));
        instanceUserMap.keySet().forEach(instanceId -> {
            List<Integer> oldUsers = instanceUserMap.get(instanceId);
            if (newUserMap.size() != oldUsers.size()) {
                finallyInstanceIds.add(instanceId);
                return;
            }
            for (Integer oldUserId : oldUsers) {
                if (!newUserMap.containsKey(oldUserId)) {
                    finallyInstanceIds.add(instanceId);
                    return;
                }
            }
        });

        if (!CollectionUtils.isEmpty(finallyInstanceIds)) {
            kafkaQueueService.sendAudit(Constant.APPOINT_AUDIT_USER, finallyInstanceIds, info.getId(), userIds, nodeId);
            flowInstanceDao.updateTime(date, finallyInstanceIds);
        }
        return true;
    }

    @Override
    public Boolean activates(FlowInstanceActivateModel model, UserInfo info) throws OwnerException {
        List<Integer> ids = model.getInstanceIds();
        List<FlowInstance> list = flowInstanceDao.getInstanceInfos(ids, FlowInstanceStatusEnum.DONE.getStatus());
        if (CollectionUtils.isEmpty(list) || list.size() < ids.size()) {
            throw new OwnerException("所选流程存在未结束流程,请重新选择再激活！");
        }
        Date now = new Date();
        String suggestion = model.getSuggestion();
        Integer activateStatus = FlowInstanceStatusEnum.ACTIVATE.getStatus(), userId = info.getId();
        List<FlowInstanceLog> logs = new ArrayList<>();
        list.forEach(item -> logs.add(new FlowInstanceLog(item.getId(), activateStatus, suggestion, userId, now, item.getCurNodeId(), null)));
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            flowInstanceDao.updateAllNodes(activateStatus, ids, userId, now);
            flowInstanceLogDao.insertAll(logs);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("激活失败。");
        }
        kafkaQueueService.sendAudit(Constant.TOPIC_ACTIVATE_AUDIT, ids, userId);
        return true;
    }

    @Override
    public boolean cancelOrRecall(Integer userId, Integer nodeStatus, String constant, BatchAuditModel batchAudit) throws OwnerException {
        List<FlowInstanceUser> instanceUsers = new ArrayList<>();
        if (nodeStatus.equals(FlowInstanceStatusEnum.NO_SUBMIT.getStatus())) {
            //撤回审核
            instanceUsers = flowCurNodeDao.getRecallNode(batchAudit.getInstanceIds());
            if (CollectionUtils.isEmpty(instanceUsers)) {
                throw new OwnerException("所选流程已全部审核，撤回失败！");
            }
        } else if (nodeStatus.equals(FlowInstanceStatusEnum.CANCEL.getStatus())) {
            //取消审核
            instanceUsers = flowCurNodeDao.getCancelNode(batchAudit.getInstanceIds());
            if (CollectionUtils.isEmpty(instanceUsers)) {
                throw new OwnerException("所选流程都不是进行中或完成状态，取消失败！");
            }
        }

        String suggestion = batchAudit.getSuggestion();
        List<FlowInstanceLog> flowInstanceLogs = new ArrayList<>();
        Date now = new Date();
        List<Integer> instanceIds = new ArrayList<>();
        List<FlowCurNodeEntity> curNodes = new ArrayList<>();
        instanceUsers.forEach(item -> {
            Integer instanceId = item.getInstanceId();
            Integer nodeId = item.getNodeId();
            instanceIds.add(instanceId);
            flowInstanceLogs.add(new FlowInstanceLog(instanceId, nodeStatus, suggestion, userId, now, nodeId, item.getLastSubmiter()));
            curNodes.add(new FlowCurNodeEntity(instanceId, nodeId, nodeStatus));
        });
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            flowInstanceLogDao.addBatch(flowInstanceLogs);
            flowCurNodeDao.updateList(curNodes, userId, now, false, null);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL, e.getMessage());
        }
        kafkaQueueService.sendAudit(constant, instanceIds, userId);
        return true;
    }
    @Override
    public Integer submitForm(Integer modelId, Integer userId, Integer moduleId) throws OwnerException {
        FlowInstanceFormEntity instanceForm = new FlowInstanceFormEntity();
        instanceForm.setFormId(modelId);
        instanceForm.setModuleId(moduleId);
        instanceForm = flowInstanceFormDao.selectOne(instanceForm);
        if (instanceForm!=null&&!FlowInstanceStatusEnum.canModify(instanceForm.getStatus())) {
            throw new OwnerException("此项数据已提交，提交失败！");
        }
        if (instanceForm == null){
            instanceForm = new FlowInstanceFormEntity();
            instanceForm.setStatus(FlowInstanceStatusEnum.SUBMIT.getStatus());
            instanceForm.setFormId(modelId);
            instanceForm.setModuleId(moduleId);
            ToolUtil.entityCreate(instanceForm, userId, new Date());
            flowInstanceFormDao.insert(instanceForm);
        }else {
            instanceForm.setStatus(FlowInstanceStatusEnum.SUBMIT.getStatus());
            ToolUtil.entityUpdate(instanceForm,userId,new Date());
            flowInstanceFormDao.updateById(instanceForm);
        }
        return instanceForm.getId();
    }

    @Override
    public Boolean audit(FormAuditModel model, UserInfo info) throws OwnerException {
        Integer instanceId = model.getInstanceId(), userId = info.getId();
        List<FlowInstanceUser> flowInstanceUserList = serviceApplyDao.getNodeUser(instanceId, userId);
        if (CollectionUtils.isEmpty(flowInstanceUserList)) {
            throw new OwnerException("无审核权限，审核失败！");
        }
        List<FlowInstanceLog> logList = new ArrayList<>();
        List<FlowCurNodeEntity> nodeEntities = new ArrayList<>();
        Integer status = model.getPass() ? FlowInstanceStatusEnum.DONE.getStatus() : FlowInstanceStatusEnum.REJECT.getStatus();
        Date now = new Date();
        for (FlowInstanceUser flowInstanceUser : flowInstanceUserList) {
            Integer nodeId = flowInstanceUser.getNodeId();
            FlowInstanceLog log = new FlowInstanceLog(instanceId, status, model.getSuggestion(),
                    userId, now, nodeId, null);
            logList.add(log);

            FlowCurNodeEntity flowCurNodeEntity = new FlowCurNodeEntity();
            flowCurNodeEntity.setCurNodeId(nodeId);
            flowCurNodeEntity.setInstanceId(instanceId);
            flowCurNodeEntity.setNodeStatus(status);
            nodeEntities.add(flowCurNodeEntity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);

        try {
            flowInstanceUserDao.updateStatus(flowInstanceUserList.stream().map(FlowInstanceUser::getId).collect(Collectors.toList()),
                    model.getPass(), now, userId);
            if (!CollectionUtils.isEmpty(logList)) {
                flowInstanceLogDao.addBatch(logList);
            }
            if (!CollectionUtils.isEmpty(nodeEntities)) {
                flowCurNodeDao.updateList(nodeEntities, userId, now, null, "");
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("审核失败！");
        }
        kafkaQueueService.sendAudit(Constant.TOPIC_PUSH_AUDIT, Arrays.asList(instanceId), userId);
        return true;
    }

}
