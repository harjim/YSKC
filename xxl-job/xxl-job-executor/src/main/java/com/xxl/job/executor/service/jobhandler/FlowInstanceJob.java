package com.xxl.job.executor.service.jobhandler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.dao.ms.*;
import com.xxl.job.executor.dao.rs.ProjectAuditDao;
import com.xxl.job.executor.dao.rs.ProjectAuditDocFileDao;
import com.xxl.job.executor.entity.ms.*;
import com.xxl.job.executor.entity.rs.ProjectAuditEntity;
import com.xxl.job.executor.models.ProjectInfo.ProjectCustomerModel;
import com.xxl.job.executor.models.flow.FlowModeModel;
import com.xxl.job.executor.models.flowInstance.*;
import com.xxl.job.executor.models.userdept.UserDeptModel;
import com.xxl.job.executor.utils.ToolUtils;
import com.xxl.job.executor.utils.TransactionUtils;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.enums.FlowNodeTypeEnum;
import com.yskc.common.enums.FlowNodeUserEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.service.jobhandler
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-26 10:31
 * @Description: 流程实例job
 */
@Component
public class FlowInstanceJob {

    @Autowired
    private FlowInstanceUserDao flowInstanceUserDao;
    @Autowired
    private FlowInstanceProjectDao flowInstanceProjectDao;
    @Autowired
    private FlowInstanceDao flowInstanceDao;
    @Autowired
    private ProjectAuditDao projectAuditDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private FlowNodeDao flowNodeDao;
    @Autowired
    private FlowNodeUserDao flowNodeUserDao;
    @Autowired
    private AppUserRoleDao appUserRoleDao;
    @Autowired
    private UserDeptDao userDeptDao;
    @Autowired
    private ProjectAuditDocFileDao projectAuditDocFileDao;
    @Autowired
    private FlowInstanceDocFileDao flowInstanceDocFileDao;

    @XxlJob("createFlowInstanceJob")
    public ReturnT<String> createFlowInstanceJob(String param) {
        List<ProjectAuditEntity> audits = projectAuditDao.getAudits(FlowInstanceStatusEnum.SUBMIT.getStatus());
        if (CollectionUtils.isEmpty(audits)) {
            XxlJobLogger.log("当前不存在提交审核操作");
            return ReturnT.SUCCESS;
        }
        String keyFormat = "{0,number,#}_{1,number,#}";
        List<ProjectCustomerModel> companyYears = new ArrayList<>();
        Set<Integer> moduleIds = new HashSet<>();
        Set<Integer> userIds = new HashSet<>();
        for (ProjectAuditEntity audit : audits) {
            moduleIds.add(audit.getModuleId());
            userIds.add(audit.getMsLastUpdatorId());
            if (null == audit.getSourceProjectId()) {
                companyYears.add(new ProjectCustomerModel(audit.getCompanyId(), audit.getYear()));
            }
        }
        Map<String, Integer> projectIdMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(companyYears)) {
            // 通过company+year获取项目
            List<ProjectCustomerModel> projects = projectDao.getCompanyIdYear(companyYears);
            if (!CollectionUtils.isEmpty(projects)) {
                projects.forEach(item -> projectIdMap.put(MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear()), item.getId()));
            }
        }
        // 获取流程首节点
        List<FlowModeModel> flowNodes = flowNodeDao.getFirstNodeByModes(moduleIds);
        if (CollectionUtils.isEmpty(flowNodes)) {
            XxlJobLogger.log("未获取到任何流程。");
            return ReturnT.SUCCESS;
        }
        List<UserDeptModel> userDept = userDeptDao.getUserDept(userIds);
        Map<Integer, Integer> userDeptMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(userDept)) {
            userDept.forEach(item -> userDeptMap.put(item.getUserId(), item.getDeptId()));
        }
        Map<Integer, FlowModeModel> modeNodeMap = flowNodes.stream().collect(Collectors.toMap(a -> a.getModuleId(), b -> b, (o, n) -> n));
        // 获取存在的实例(更新用)
        List<FlowInstanceProjectModel> exitInstances = flowInstanceProjectDao.getInstances(audits);
        Map<String, FlowInstance> exitInstancesMap = new HashMap<>();
        List<Integer> oldInstanceIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(exitInstances)) {
            exitInstances.forEach(item -> {
                oldInstanceIds.add(item.getId());
                exitInstancesMap.put(MessageFormat.format(keyFormat, item.getProjectId(), item.getModuleId()),
                        new FlowInstance(item.getId(), null, null, null));
            });
        }
        Map<String, FlowInstance> flowInstanceMap = new HashMap<>();
        Map<String, FlowInstanceProject> flowInstanceProjectMap = new HashMap<>();
        Map<String, FlowInstance> updateInstanceMap = new HashMap<>();
        Date now = new Date();
        Integer status = FlowInstanceStatusEnum.ONGOING.getStatus();
        audits.forEach(item -> {
            Integer sourceProjectId = item.getSourceProjectId();
            if (null == sourceProjectId) {
                sourceProjectId = projectIdMap.get(MessageFormat.format(keyFormat, item.getCompanyId(), item.getYear()));
                item.setSourceProjectId(sourceProjectId);
            }
            FlowModeModel flowModule = modeNodeMap.get(item.getModuleId());
            if (null != flowModule && null != sourceProjectId) {
                item.setStatus(status);
                String key = MessageFormat.format(keyFormat, sourceProjectId, item.getModuleId());
                Integer deptId = userDeptMap.get(item.getMsLastUpdatorId());
                if (exitInstancesMap.containsKey(key)) {
                    // 重启实例
                    FlowInstance existInstance = exitInstancesMap.get(key);
                    existInstance.reboot(item.getMsLastUpdatorId(), now, flowModule, deptId);
                    updateInstanceMap.put(key, existInstance);
                } else {
                    flowInstanceMap.put(key, new FlowInstance(null, item.getMsLastUpdatorId(), now, flowModule, deptId));
                }
                flowInstanceProjectMap.put(key, new FlowInstanceProject(item, now, flowModule.getModuleId()));
            }
        });
        Collection<FlowInstance> flowInstances = flowInstanceMap.values();
        if (CollectionUtils.isEmpty(flowInstances) && CollectionUtils.isEmpty(exitInstancesMap)) {
            XxlJobLogger.log("未创建任何流程实例。");
            return ReturnT.SUCCESS;
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            // 删除存在的实例用户
            if (!CollectionUtils.isEmpty(oldInstanceIds)) {
                flowInstanceUserDao.deleteInstance(oldInstanceIds);
            }
            if (!CollectionUtils.isEmpty(flowInstances)) {
                flowInstanceDao.addBatch(new ArrayList<>(flowInstances));
            }
            if (!CollectionUtils.isEmpty(updateInstanceMap)) {
                flowInstanceDao.updateInstances(new ArrayList<>(updateInstanceMap.values()));
            }
            Map<Integer, List<FlowInstance>> nodeInstanceMap = new HashMap<>();
            flowInstanceProjectMap.values().forEach(item -> {
                String key = MessageFormat.format(keyFormat, item.getProjectId(), item.getModuleId());
                FlowInstance flowInstance;
                if (flowInstanceMap.containsKey(key)) {
                    flowInstance = flowInstanceMap.get(key);
                } else {
                    flowInstance = exitInstancesMap.get(key);
                }
                ToolUtils.putAndAddList(nodeInstanceMap, flowInstance.getCurNodeId(), flowInstance);
                item.setInstanceId(flowInstance.getId());
            });
            flowInstanceProjectDao.insertOrUpdate(new ArrayList<>(flowInstanceProjectMap.values()));
            insertInstanceUser(nodeInstanceMap, now);
            XxlJobLogger.log("插入实例数：" + flowInstances.size() + ",重启实例数: " + exitInstancesMap.size());
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            XxlJobLogger.log(e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            return ReturnT.FAIL;
        }
        // 更新审批进度(项目id)
        projectAuditDao.updateBatch(audits);
        return ReturnT.SUCCESS;
    }

    @XxlJob("createDocFileFlowInstanceJob")
    public ReturnT<String> createDocFileFlowInstanceJob(String param) {
        List<DocFileAuditInfoModel> submits = projectAuditDocFileDao.getAuditDocFile(FlowInstanceStatusEnum.SUBMIT.getStatus());
        if (CollectionUtils.isEmpty(submits)) {
            XxlJobLogger.log("当前不存在提交审核操作");
            return ReturnT.SUCCESS;
        }
        Set<Integer> docFileIds = new HashSet<>();
        Set<Integer> moduleIds = new HashSet<>();
        Set<Integer> userIds = new HashSet<>();
        submits.forEach(item -> {
            docFileIds.add(item.getDocFileId());
            moduleIds.add(item.getModuleId());
            userIds.add(item.getMsLastUpdatorId());
        });
        // 获取流程首节点
        List<FlowModeModel> flowNodes = flowNodeDao.getFirstNodeByModes(moduleIds);
        if (CollectionUtils.isEmpty(flowNodes)) {
            XxlJobLogger.log("未获取到任何流程。");
            return ReturnT.SUCCESS;
        }
        List<UserDeptModel> userDept = userDeptDao.getUserDept(userIds);
        Map<Integer, Integer> userDeptMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(userDept)) {
            userDept.forEach(item -> userDeptMap.put(item.getUserId(), item.getDeptId()));
        }
        Map<Integer, FlowModeModel> modeNodeMap = flowNodes.stream().collect(Collectors.toMap(FlowModeModel::getModuleId, b -> b, (o, n) -> n));
        // 获取存在的实例(更新用)
        List<FlowInstanceDocFileModel> exitInstances = flowInstanceDocFileDao.getInstances(docFileIds);
        Map<Integer, Integer> exitInstancesMap = new HashMap<>();
        List<Integer> oldInstanceIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(exitInstances)) {
            exitInstances.forEach(item -> {
                oldInstanceIds.add(item.getInstanceId());
                exitInstancesMap.put(item.getDocFileId(), item.getInstanceId());
            });
        }
        Map<Integer, FlowInstance> insertInstanceMap = new HashMap<>();
        Map<Integer, FlowInstance> updateInstanceMap = new HashMap<>();
        Map<Integer, FlowInstanceDocFile> flowInstanceDocFileMap = new HashMap<>();
        Date now = new Date();
        Integer ongoing = FlowInstanceStatusEnum.ONGOING.getStatus();
        submits.forEach(item -> {
            item.setStatus(ongoing);
            item.setSuggestion(StrUtil.EMPTY);
            FlowModeModel flowModule = modeNodeMap.get(item.getModuleId());
            if (null != flowModule) {
                Integer docFileId = item.getDocFileId();
                Integer deptId = userDeptMap.get(item.getMsLastUpdatorId());
                if (exitInstancesMap.containsKey(docFileId)) {
                    // 重启实例
                    updateInstanceMap.put(docFileId, new FlowInstance(exitInstancesMap.get(docFileId), item.getMsLastUpdatorId(), now, flowModule, deptId));
                } else {
                    insertInstanceMap.put(docFileId, new FlowInstance(null, item.getMsLastUpdatorId(), now, flowModule, deptId));
                }
                flowInstanceDocFileMap.put(docFileId, new FlowInstanceDocFile(item, now, flowModule.getModuleId()));
            }
        });
        if (CollectionUtils.isEmpty(insertInstanceMap) && CollectionUtils.isEmpty(updateInstanceMap)) {
            XxlJobLogger.log("未创建任何流程实例。");
            return ReturnT.SUCCESS;
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            Collection<FlowInstance> flowInstances = insertInstanceMap.values();
            // 删除存在的实例用户
            if (!CollectionUtils.isEmpty(oldInstanceIds)) {
                flowInstanceUserDao.deleteInstance(oldInstanceIds);
            }
            if (!CollectionUtils.isEmpty(flowInstances)) {
                flowInstanceDao.addBatch(new ArrayList<>(flowInstances));
            }
            if (!CollectionUtils.isEmpty(updateInstanceMap)) {
                flowInstanceDao.updateInstances(new ArrayList<>(updateInstanceMap.values()));
            }
            Map<Integer, List<FlowInstance>> nodeInstanceMap = new HashMap<>();
            flowInstanceDocFileMap.values().forEach(item -> {
                Integer docFileId = item.getDocFileId();
                FlowInstance flowInstance;
                if (insertInstanceMap.containsKey(docFileId)) {
                    flowInstance = insertInstanceMap.get(docFileId);
                } else {
                    flowInstance = updateInstanceMap.get(docFileId);
                }
                ToolUtils.putAndAddList(nodeInstanceMap, flowInstance.getCurNodeId(), flowInstance);
                item.setInstanceId(flowInstance.getId());
            });
            flowInstanceDocFileDao.insertOrUpdate(new ArrayList<>(flowInstanceDocFileMap.values()));
            insertInstanceUser(nodeInstanceMap, now);
            XxlJobLogger.log("插入实例数：" + flowInstances.size() + ",重启实例数: " + exitInstancesMap.size());
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            XxlJobLogger.log(e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            return ReturnT.FAIL;
        }
        // 更新审批进度
        projectAuditDocFileDao.updateDocFileAudit(submits, now);
        return ReturnT.SUCCESS;
    }


    public boolean insertInstanceUser(Map<Integer, List<FlowInstance>> nodeInstanceMap, Date now) {
        List<FlowNodeUserEntity> flowNodeUserList = flowNodeUserDao.getByNodeIds(nodeInstanceMap.keySet());
        if (CollectionUtils.isEmpty(flowNodeUserList)) {
            return false;
        }
        Map<Integer, Set<Integer>> roleNodeMap = new HashMap<>();
        Map<Integer, Map<Integer, Set<Integer>>> directionNodeMap = new HashMap<>();
        Map<String, Boolean> exitDirectionUserId = new HashMap<>();
        Map<String, Boolean> instanceUserFilterMap = new HashMap<>();
        String keyFormat = "{0,number,#}_{1,number,#}_{2,number,#}";
        List<FlowInstanceUser> instanceUsers = new ArrayList<>();
        flowNodeUserList.stream().forEach(item -> {
            Integer nodeId = item.getNodeId();
            Integer dataId = item.getDataId();
            if (FlowNodeUserEnum.isUser(item.getType()) && null != dataId) {
                nodeInstanceMap.get(nodeId).forEach(nodeInstance -> {
                    String key = MessageFormat.format(keyFormat, nodeInstance.getId(), nodeId, dataId);
                    if (!instanceUserFilterMap.containsKey(key)) {
                        instanceUsers.add(new FlowInstanceUser(nodeId, dataId, now, nodeInstance.getId(), nodeInstance.getLastSubmiter()));
                        instanceUserFilterMap.put(key, true);
                    }
                });
                return;
            }
            if (FlowNodeUserEnum.isRole(item.getType()) && null != dataId) {
                ToolUtils.putAndAddSet(roleNodeMap, dataId, nodeId);
                return;
            }
            if (FlowNodeUserEnum.isDirector(item.getType())) {
                nodeInstanceMap.get(nodeId).forEach(nodeInstance -> {
                    if (!directionNodeMap.containsKey(dataId)) {
                        directionNodeMap.put(dataId, new HashMap<>());
                    }
                    ToolUtils.putAndAddSet(directionNodeMap.get(dataId), nodeInstance.getDeptId(), nodeId);
                    exitDirectionUserId.put(MessageFormat.format(keyFormat, nodeInstance.getLastSubmiter(), nodeInstance.getDeptId()), true);
                });
            }
        });
        if (!roleNodeMap.isEmpty()) {
            List<AppUserRole> appUserRoles = appUserRoleDao.getByRoleIds(roleNodeMap.keySet());
            if (!CollectionUtils.isEmpty(appUserRoles)) {
                appUserRoles.forEach(item -> {
                    roleNodeMap.get(item.getRoleId()).forEach(nodeId ->
                            nodeInstanceMap.get(nodeId).forEach(nodeInstance -> {
                                String key = MessageFormat.format(keyFormat, nodeInstance.getId(), nodeId, item.getUserId());
                                if (!instanceUserFilterMap.containsKey(key)) {
                                    instanceUsers.add(new FlowInstanceUser(nodeId, item.getUserId(), now, nodeInstance.getId(), nodeInstance.getLastSubmiter()));
                                    instanceUserFilterMap.put(key, true);
                                }
                            }));
                });
            }
        }
        if (!directionNodeMap.isEmpty()) {
            for (Integer type : directionNodeMap.keySet()) {
                Map<Integer, Set<Integer>> userNodeMap = directionNodeMap.get(type);
                Map<Integer, UserDeptModel> currentDirectorMap = loadDirectorMap(userNodeMap.keySet(), null);
                if (currentDirectorMap.isEmpty()) {
                    continue;
                }
                for (int i = 1; i < type; i++) {
                    currentDirectorMap = loadDirectorMap(currentDirectorMap.keySet(), currentDirectorMap);
                    if (currentDirectorMap.isEmpty()) {
                        break;
                    }
                }
                for (Integer patentId : currentDirectorMap.keySet()) {
                    UserDeptModel current = currentDirectorMap.get(patentId);
                    current.getUserIds().forEach(userId -> {
                        if (exitDirectionUserId.containsKey(MessageFormat.format(keyFormat, userId, current.getDeptId()))) {
                            return;
                        }
                        userNodeMap.get(current.getDeptId()).forEach(nodeId ->
                                nodeInstanceMap.get(nodeId).forEach(nodeInstance -> {
                                    String key = MessageFormat.format(keyFormat, nodeInstance.getId(), nodeId, userId);
                                    if (!instanceUserFilterMap.containsKey(key)) {
                                        instanceUsers.add(new FlowInstanceUser(nodeId, userId, now, nodeInstance.getId(), nodeInstance.getLastSubmiter()));
                                        instanceUserFilterMap.put(key, true);
                                    }
                                }));
                    });
                }

            }
        }
        if (!CollectionUtils.isEmpty(instanceUsers)) {
            List<List<FlowInstanceUser>> dataList = CollUtil.split(instanceUsers, Constant.MAX_INSERT_OR_UPDATE);
            for (List<FlowInstanceUser> currentInsert : dataList) {
                flowInstanceUserDao.addBatch(currentInsert);
            }
        }
        return true;
    }

    public Map<Integer, UserDeptModel> loadDirectorMap(Set<Integer> deptIds, Map<Integer, UserDeptModel> defaultMap) {
        Map<Integer, UserDeptModel> result = new HashMap<>();
        List<UserDeptModel> userDeptModels = userDeptDao.getAdminUser(deptIds);
        if (CollectionUtils.isEmpty(userDeptModels)) {
            return result;
        }
        if (CollectionUtils.isEmpty(defaultMap)) {
            userDeptModels.forEach(item -> {
                Integer parentId = item.getParentId();
                if (null != parentId && parentId > 0) {
                    if (result.containsKey(parentId)) {
                        result.get(parentId).getUserIds().add(item.getUserId());
                    } else {
                        item.setUserIds(new HashSet<>());
                        item.getUserIds().add(item.getUserId());
                        result.put(parentId, item);
                    }
                }
            });
        } else {
            userDeptModels.forEach(item -> {
                Integer parentId = item.getParentId();
                if (result.containsKey(parentId)) {
                    result.get(parentId).getUserIds().add(item.getUserId());
                } else {
                    UserDeptModel currentUser = defaultMap.get(item.getDeptId());
                    currentUser.getUserIds().clear();
                    currentUser.getUserIds().add(item.getUserId());
                    result.put(parentId, currentUser);
                }
            });
        }
        return result;
//        if (CollectionUtils.isEmpty(defaultMap)) {
//            userDeptModels.forEach(item -> {
//                if (null != item.getParentId() && item.getParentId() > 0) {
//                    ToolUtils.putAndAddSet(result, item.getParentId(), item);
//                }
//            });
//        } else {
//            Map<Integer, Set<Integer>> tempResultMap = new HashMap<>();
//            userDeptModels.forEach(item -> {
//                Integer deptId = item.getParentId();
//                if (!tempResultMap.containsKey(deptId)) {
//                    tempResultMap.put(deptId, new HashSet<>());
//                }
//                tempResultMap.get(deptId).addAll(defaultMap.get(item.getDeptId()));
//            });
//            result.putAll(tempResultMap);
//        }
    }


    public boolean addInstanceUser(List<FlowInstanceModel> list, Date now) {
        String keyFormat = "{0}_{1}";
        List<FlowInstanceUser> exitInstanceNodes = flowInstanceUserDao.getExitInstanceNodes(list);
        Map<Integer, List<FlowInstance>> nodeInstanceMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(exitInstanceNodes)) {
            Map<String, Boolean> exitUserMap = new HashMap<>();
            exitInstanceNodes.forEach(item -> exitUserMap.put(MessageFormat.format(keyFormat, item.getInstanceId(), item.getNodeId()), true));
            list.forEach(item -> {
                if (!exitUserMap.containsKey(MessageFormat.format(keyFormat, item.getId(), item.getCurNodeId()))) {
                    ToolUtils.putAndAddList(nodeInstanceMap, item.getCurNodeId(), new FlowInstance(item.getId(), item.getCurNodeId(), item.getLastSubmiter(), item.getDeptId()));
                }
            });
        } else {
            list.forEach(item -> ToolUtils.putAndAddList(nodeInstanceMap, item.getCurNodeId(), new FlowInstance(item.getId(), item.getCurNodeId(), item.getLastSubmiter(), item.getDeptId())));
        }
        if (!CollectionUtils.isEmpty(nodeInstanceMap)) {
            return insertInstanceUser(nodeInstanceMap, now);
        }
        return false;
    }

    /**
     * 处理节点
     *
     * @param list
     * @param now
     * @return
     */
    public List<AuditMsgModel> handleNodes(List<FlowInstanceModel> list, Date now) {
        List<FlowInstanceUser> instanceUsers = flowInstanceUserDao.getUsers(list);
        List<FlowInstanceCompanyModel> instanceCompanies = flowInstanceProjectDao.getCompanies(list);
        Map<Integer, String> msgMap = new HashMap<>();
        String msgFormat = "{0}【{1,number,#}】加计扣除";
        Map<Integer, FlowInstanceCompanyModel> projectInstanceMap = new HashMap<>();
        instanceCompanies.forEach(item -> {
            msgMap.put(item.getInstanceId(), MessageFormat.format(msgFormat, item.getCompanyName(), item.getYear()));
            projectInstanceMap.put(item.getInstanceId(), item);
        });
        Map<Integer, List<FlowInstanceUser>> hasUserMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(instanceUsers)) {
            instanceUsers.forEach(item -> ToolUtils.putAndAddList(hasUserMap, item.getInstanceId(), item));
        }
        List<ProjectAuditEntity> updateNodes = new ArrayList<>();
        Map<Integer, AuditMsgModel> instanceMsgMap = new HashMap<>();
        List<FlowInstance> instances = new ArrayList<>();
        list.forEach(item -> {
            FlowInstanceCompanyModel projectInfo = projectInstanceMap.get(item.getId());
            if (null == projectInfo) {
                return;
            }
            Integer projectId = projectInfo.getProjectId();
            Integer lastUpdatorId = projectInfo.getLastUpdatorId();
            Integer nodeStatus = item.getNodeStatus();
            item.setLastId(lastUpdatorId);
            Set<Integer> userIds = null;
            Date time = null;
            boolean needMsg = true;
            if (FlowInstanceStatusEnum.isReject(nodeStatus)) {
                item.setStatus(nodeStatus);
            } else if (FlowInstanceStatusEnum.isDone(nodeStatus)) {
                if (null == item.getNextNode()) {
                    // 审核完成
                    item.setStatus(nodeStatus);
                } else {
                    // 下一个节点
                    item.setCurNodeId(item.getNextNode());
                    item.setStatus(0);
                    item.setNodeStatus(0);
                    time = now;
                }
            } else if (FlowInstanceStatusEnum.isActivate(nodeStatus)) {
                needMsg = false;
                item.setStatus(nodeStatus);
                time = now;
            } else {
                if (hasUserMap.containsKey(item.getId())) {
                    List<FlowInstanceUser> flowInstanceUsers = hasUserMap.get(item.getId());
                    userIds = flowInstanceUsers.stream().distinct().map(e -> e.getUserId()).collect(Collectors.toSet());
                    if (FlowNodeTypeEnum.isSendCopy(item.getType())) {
                        item.setLastUpdateTime(now);
                        if (null == item.getNextNode()) {
                            item.setStatus(1);
                            item.setNodeStatus(1);
                        } else {
                            item.setCurNodeId(item.getNextNode());
                            item.setStatus(0);
                            item.setNodeStatus(0);
                            time = now;
                        }
                    }
                } else {
                    if (null == item.getNextNode()) {
                        item.setStatus(1);
                        item.setNodeStatus(1);
                    } else {
                        needMsg = false;
                        item.setCurNodeId(item.getNextNode());
                        item.setStatus(0);
                        item.setNodeStatus(0);
                        time = now;
                    }
                }
            }
            instances.add(new FlowInstance(item, time, lastUpdatorId));
            updateNodes.add(new ProjectAuditEntity(item.getNodeStatus(), item.getModuleId(), projectId));
            if (needMsg) {
                if (!instanceMsgMap.containsKey(item.getId())) {
                    instanceMsgMap.put(item.getId(), new AuditMsgModel(nodeStatus, item.getModeName(), item.getNodeName(),
                            msgMap.get(item.getId()), lastUpdatorId, item.getId(), item.getCurNodeId(), userIds, item.getType()));
                }
            }
        });
        if (!CollectionUtils.isEmpty(instances)) {
            flowInstanceDao.updateInstances(instances);
        }
        if (!CollectionUtils.isEmpty(updateNodes)) {
            projectAuditDao.updateNodes(updateNodes, now);
        }
        List<AuditMsgModel> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(instanceMsgMap)) {
            result.addAll(instanceMsgMap.values());
        }
        return result;
    }

    public List<AuditDocFileMsgModel> handleDocFileNodes(List<FlowInstanceModel> list, Date now) {
        List<FlowInstanceUser> instanceUsers = flowInstanceUserDao.getUsers(list);
        List<Integer> instanceIds = list.stream().map(FlowInstanceModel::getId).collect(Collectors.toList());
        List<FlowInstanceDocFile> docFiles = flowInstanceDocFileDao.getDocFileIds(instanceIds);
        if (CollectionUtils.isEmpty(docFiles)) {
            return new ArrayList<>();
        }
        Map<Integer, Integer> instanceDocFileMap = docFiles.stream().collect(Collectors.toMap(FlowInstanceDocFile::getInstanceId, FlowInstanceDocFile::getDocFileId, (o, n) -> n));
        List<AuditDocFileModel> docFileInfos = projectAuditDocFileDao.getDocFileInfos(new ArrayList<>(instanceDocFileMap.values()));
        List<DocFileAuditInfoModel> docFileSuggestions = flowInstanceDocFileDao.getSuggestions(instanceIds);
        Map<Integer, DocFileAuditInfoModel> suggestionMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(docFileSuggestions)) {
            docFileSuggestions.forEach(item -> suggestionMap.put(item.getDocFileId(), item));
        }
        Map<Integer, String> msgMap = new HashMap<>();
        String msgFormat = "{0}【{1}】";
        Map<Integer, AuditDocFileModel> docInstanceMap = new HashMap<>();
        docFileInfos.forEach(item -> {
            msgMap.put(item.getDocFileId(), MessageFormat.format(msgFormat, item.getCompanyName(), item.getRdTitle()));
            docInstanceMap.put(item.getDocFileId(), item);
        });
        Map<Integer, List<FlowInstanceUser>> hasUserMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(instanceUsers)) {
            instanceUsers.forEach(item -> ToolUtils.putAndAddList(hasUserMap, item.getInstanceId(), item));
        }
        List<DocFileAuditInfoModel> updateNodes = new ArrayList<>();
        Map<Integer, AuditDocFileMsgModel> instanceMsgMap = new HashMap<>();
        List<FlowInstance> instances = new ArrayList<>();
        list.forEach(item -> {
            Integer docFileId = instanceDocFileMap.get(item.getId());
            AuditDocFileModel docFileInfo = docInstanceMap.get(docFileId);
            if (null == docFileInfo) {
                return;
            }
            DocFileAuditInfoModel docFileAuditInfo = suggestionMap.get(docFileId);
            Integer lastUpdatorId = docFileAuditInfo.getMsLastUpdatorId();
            Integer nodeStatus = item.getNodeStatus();
            item.setLastId(lastUpdatorId);
            Set<Integer> userIds = null;
            boolean needMsg = true;
            Date time = null;
            if (FlowInstanceStatusEnum.isReject(nodeStatus)) {
                item.setStatus(nodeStatus);
            } else if (FlowInstanceStatusEnum.isDone(nodeStatus)) {
                if (null == item.getNextNode()) {
                    // 审核完成
                    item.setStatus(nodeStatus);
                } else {
                    // 下一个节点
                    item.setCurNodeId(item.getNextNode());
                    item.setStatus(0);
                    item.setNodeStatus(0);
                    time = now;
                }
            } else if (FlowInstanceStatusEnum.isActivate(nodeStatus)) {
                needMsg = false;
                item.setStatus(nodeStatus);
                time = now;
            } else {
                if (hasUserMap.containsKey(item.getId())) {
                    List<FlowInstanceUser> flowInstanceUsers = hasUserMap.get(item.getId());
                    userIds = flowInstanceUsers.stream().distinct().map(e -> e.getUserId()).collect(Collectors.toSet());
                    if (FlowNodeTypeEnum.isSendCopy(item.getType())) {
                        item.setLastUpdateTime(now);
                        if (null == item.getNextNode()) {
                            item.setStatus(1);
                            item.setNodeStatus(1);
                        } else {
                            item.setCurNodeId(item.getNextNode());
                            item.setStatus(0);
                            item.setNodeStatus(0);
                            time = now;
                        }
                    }
                } else {
                    if (null == item.getNextNode()) {
                        item.setStatus(1);
                        item.setNodeStatus(1);
                    } else {
                        needMsg = false;
                        item.setCurNodeId(item.getNextNode());
                        item.setStatus(0);
                        item.setNodeStatus(0);
                        time = now;
                    }
                }
            }
            instances.add(new FlowInstance(item, time, lastUpdatorId));
            docFileAuditInfo.setStatus(item.getStatus());
            updateNodes.add(docFileAuditInfo);
            if (needMsg) {
                if (!instanceMsgMap.containsKey(item.getId())) {
                    instanceMsgMap.put(item.getId(), new AuditDocFileMsgModel(nodeStatus, item.getModeName(),
                            item.getNodeName(), msgMap.get(docFileId), lastUpdatorId, item.getId(), item.getCurNodeId(),
                            userIds, item.getType(), docFileInfo.getDocFileName()));
                }
            }
        });
        if (!CollectionUtils.isEmpty(instances)) {
            flowInstanceDao.updateInstances(instances);
        }
        if (!CollectionUtils.isEmpty(updateNodes)) {
            projectAuditDocFileDao.updateDocFileAudit(updateNodes, now);
        }
        List<AuditDocFileMsgModel> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(instanceMsgMap)) {
            result.addAll(instanceMsgMap.values());
        }
        return result;
    }

}
