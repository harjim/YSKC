package com.xxl.job.executor.service.jobhandler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.core.config.XxlJobConfig;
import com.xxl.job.executor.dao.ms.*;
import com.xxl.job.executor.entity.ms.PushAuditEntity;
import com.xxl.job.executor.enums.FlowModuleTypeEnum;
import com.xxl.job.executor.models.ding.DingMsgModel;
import com.xxl.job.executor.models.flowInstance.AuditMsgModel;
import com.xxl.job.executor.models.flowInstance.AuditUserModel;
import com.xxl.job.executor.models.flowInstance.FlowInstanceModel;
import com.xxl.job.executor.models.policy.PolicySourceModel;
import com.xxl.job.executor.models.policy.SourceUserModel;
import com.xxl.job.executor.utils.DingMsgUtils;
import com.xxl.job.executor.utils.DingUtils;
import com.xxl.job.executor.utils.ToolUtils;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.enums.FlowNodeTypeEnum;
import com.yskc.common.utils.JsonUtils;
import com.yskc.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.service.jobhandler
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 16:39
 * @Description: 推送钉钉消息
 */
@Component
public class DingPushMsgJob {
    @Autowired
    private DingUtils dingUtils;
    @Autowired
    private PolicySourceDao policySourceDao;
    @Autowired
    private PolicySourceUserDao policySourceUserDao;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private XxlJobConfig xxlJobConfig;
    @Autowired
    private PushAuditDao pushAuditDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FlowInstanceDao flowInstanceDao;
    @Autowired
    private FlowInstanceJob flowInstanceJob;
    @Autowired
    private RedisUtils redisUtils;

    @XxlJob("pushPolicy")
    public ReturnT<String> pushPolicy(String param) throws Exception {
        Date now = new Date();
        Date dayBegin = DateUtil.offsetDay(now,-1);
        List<PolicySourceModel> data = policySourceDao.getNewDataSource(dayBegin);
        if (CollectionUtils.isEmpty(data)) {
            XxlJobLogger.log(MessageFormat.format("日期：{0}，当日无最新爬取的数据，不进行任何推送。", DateUtil.formatDate(dayBegin)));
            return ReturnT.SUCCESS;
        }
        List<Integer> sourceIds = new ArrayList<>();
        data.forEach(item -> sourceIds.add(item.getId()));
        Integer userId = null;
        if (!StringUtils.isEmpty(param) && NumberUtil.isNumber(param)) {
            userId = Integer.valueOf(param);
        }
        List<SourceUserModel> subUsers = policySourceUserDao.getSubscription(sourceIds, userId);
        if (CollectionUtils.isEmpty(subUsers)) {
            XxlJobLogger.log(MessageFormat.format("日期：{0}，当日爬取的信息无订阅者，不进行任何推送。", DateUtil.formatDate(dayBegin)));
            return ReturnT.SUCCESS;
        }
        Map<Integer, Map<String, Integer>> userSourceMap = new HashMap<>();
        subUsers.forEach(item -> {
            Integer key = item.getSourceId();
            if (!userSourceMap.containsKey(key)) {
                userSourceMap.put(key, new LinkedHashMap<>());
            }
            userSourceMap.get(key).put(item.getDingUserId(), item.getUserId());
        });
        String token = dingUtils.getAccessToken();
        if (StringUtils.isEmpty(token)) {
            XxlJobLogger.log("获取token失败。");
            return ReturnT.FAIL;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request;
        Object obj;
        for (PolicySourceModel source : data) {
            if (!userSourceMap.containsKey(source.getId())) {
                XxlJobLogger.log(MessageFormat.format("当前对象：{0}，无订阅人员。", source.toString()));
                continue;
            }
            // 订阅人员map，key-->dingUserId，value-->userId
            Map<String, Integer> sourceUserMap = userSourceMap.get(source.getId());
            try {
                String json = DingMsgModel.buildToUser(xxlJobConfig.getAgentId(),
                        DingMsgUtils.buildLinkMsg(MessageFormat.format("{0}-{1}", source.getSname(), source.getStype()),
                                MessageFormat.format("时间：{0}\n有新的政策公布啦，点击链接前去查看详情吧。", DateUtil.formatDateTime(now)),
                                source.getTypeUrl(),
                                xxlJobConfig.getPicUrl()),
                        String.join(",", sourceUserMap.keySet()));
                request = new HttpEntity<>(json, headers);
                obj = restTemplate.exchange(MessageFormat.format(Constant.DING_MSG_URL, token), HttpMethod.POST, request, Object.class);
                XxlJobLogger.log(MessageFormat.format(Constant.PUSH_MSG, sourceUserMap.values(), source.toString(), obj.toString()));
            } catch (Exception e) {
                XxlJobLogger.log(e);
                XxlJobLogger.log(MessageFormat.format(Constant.PUSH_MSG, sourceUserMap.values(), source.toString(), "失败"));
            }
            // 消息发送间隔50ms
            Thread.sleep(50);
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("pushProjectAudit")
    public ReturnT<String> pushProjectAudit(String param) throws Exception {
        Date now = new Date();
        Date lastTime = redisUtils.get(Constant.FLOW_INSTANCE_TIME_KEY, Date.class);
        if (null == lastTime) {
            lastTime = pushAuditDao.getLastTime();
        }
        List<FlowInstanceModel> flowInstances = flowInstanceDao.getLastInstance(lastTime);
        if (CollectionUtils.isEmpty(flowInstances)) {
            XxlJobLogger.log("未获取任何审批数据。");
            return ReturnT.SUCCESS;
        }
        // 按模块，类型区分不同模块的审批处理方式
        Map<Integer, List<FlowInstanceModel>> moduleTypeMap = new HashMap<>();
        flowInstances.forEach(item -> ToolUtils.putAndAddList(moduleTypeMap, item.getModuleType(), item));
        List<AuditMsgModel> msgList = new ArrayList<>();
        for (Integer moduleType : moduleTypeMap.keySet()) {
            if (FlowModuleTypeEnum.isRdTech(moduleType)) {
                msgList.addAll(flowInstanceJob.handleNodes(moduleTypeMap.get(moduleType), now));
            }
            if(FlowModuleTypeEnum.isRdDocFile(moduleType)){
                msgList.addAll(flowInstanceJob.handleDocFileNodes(moduleTypeMap.get(moduleType), now));
            }
        }
        redisUtils.set(Constant.FLOW_INSTANCE_TIME_KEY, now);
        if (msgList.isEmpty()) {
            XxlJobLogger.log("不存在可发送的消息。");
        } else {
            Set<Integer> userIds = new HashSet<>();
            msgList.forEach(item -> {
                if (!CollectionUtils.isEmpty(item.getUserIds())) {
                    userIds.addAll(item.getUserIds());
                }
                userIds.add(item.getInitiatorId());
            });
            List<AuditUserModel> auditUsers = userDao.getByUserIds(userIds);
            if (CollectionUtils.isEmpty(auditUsers)) {
                XxlJobLogger.log("不存在可接受消息的人员。");
            } else {
                String token = dingUtils.getAccessToken();
                Map<Integer, AuditUserModel> auditUserMap = auditUsers.stream().collect(Collectors.toMap(a -> a.getUserId(), b -> b, (o, n) -> n));
                List<PushAuditEntity> insertList = sendAndLoadAuditMsg(auditUserMap, token, msgList, now);
                if (!CollectionUtils.isEmpty(insertList)) {
                    pushAuditDao.addBatch(insertList);
                }
            }
        }
        List<FlowInstanceModel> needAddUserList = new ArrayList<>();
        flowInstances.forEach(item -> {
            if (FlowInstanceStatusEnum.isOngoing(item.getStatus())) {
                needAddUserList.add(item);
            }
        });
        // 插入实例用户
        if (!CollectionUtils.isEmpty(needAddUserList)) {
            if (flowInstanceJob.addInstanceUser(needAddUserList, now)) {
                pushProjectAudit(null);
            }
        }
        return ReturnT.SUCCESS;
    }

    List<PushAuditEntity> sendAndLoadAuditMsg(Map<Integer, AuditUserModel> auditUserMap, String token, List<AuditMsgModel> msgList, Date now) throws Exception {
        List<PushAuditEntity> insertList = new ArrayList<>();
        Map<String, String> head = new HashMap<>();
        head.put("bgcolor", "FFCCCCCC");
        head.put("text", "项目审核");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request;
        for (AuditMsgModel msg : msgList) {
            Set<String> sendDingUser = new HashSet<>();
            Set<Integer> sendUser = new HashSet<>();
            Integer status = msg.getStatus();
            AuditUserModel auditUser = auditUserMap.get(msg.getInitiatorId());
            Boolean isSendCopy = FlowNodeTypeEnum.isSendCopy(msg.getType());
            String state = FlowInstanceStatusEnum.isDone(status) ? "审核通过" : FlowInstanceStatusEnum.isReject(status) ? "已驳回" : "待审核";
            if (isSendCopy) {
                if (!CollectionUtils.isEmpty(msg.getUserIds())) {
                    msg.getUserIds().forEach(userId -> {
                        String dingUserId = auditUserMap.get(userId).getDingUserId();
                        if (!StringUtils.isEmpty(dingUserId)) {
                            sendDingUser.add(dingUserId);
                            sendUser.add(userId);
                        }
                    });
                }
            }
            if (FlowInstanceStatusEnum.isDone(status) || FlowInstanceStatusEnum.isReject(status)) {
                if (!StringUtils.isEmpty(auditUser.getDingUserId())) {
                    sendDingUser.add(auditUser.getDingUserId());
                    sendUser.add(auditUser.getUserId());
                }
            } else {
                if (!CollectionUtils.isEmpty(msg.getUserIds())) {
                    msg.getUserIds().forEach(userId -> {
                        String dingUserId = auditUserMap.get(userId).getDingUserId();
                        if (!StringUtils.isEmpty(dingUserId)) {
                            sendDingUser.add(dingUserId);
                            sendUser.add(userId);
                        }
                    });
                }
            }
            if (sendDingUser.isEmpty()) {
                XxlJobLogger.log(MessageFormat.format("实例id：{0}，推送用户不存在dingUserId", msg.getInstanceId()));
                continue;
            }
            List<Map<String, String>> form = msg.buildMsgForm(auditUser.getRealName(),isSendCopy,state);
            try {
                request = new HttpEntity<>(DingMsgModel.buildToUser(xxlJobConfig.getAgentId(),
                        DingMsgUtils.buildOAMsg(head, form, xxlJobConfig.getProjectProgressUrl()), String.join(",", sendDingUser)
                ), headers);
                String msgStr = JsonUtils.objectToJson(form);
                Object obj = restTemplate.exchange(MessageFormat.format(Constant.DING_MSG_URL, token), HttpMethod.POST, request, Object.class);
                XxlJobLogger.log(MessageFormat.format(Constant.PUSH_MSG, sendUser, msg, obj.toString()));
                insertList.add(new PushAuditEntity(msg.getInstanceId(), msg.getNodeId(), msgStr, now, msg.getStatus(), sendUser));
            } catch (Exception e) {
                XxlJobLogger.log(e);
                XxlJobLogger.log(MessageFormat.format("实例id：{0}，推送结果失败", msg.getInstanceId()));
                continue;
            }
            Thread.sleep(50);
        }
        return insertList;
    }

}
