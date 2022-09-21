package com.yskc.rs.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.project.ProjectDeliverDao;
import com.yskc.rs.dao.project.ProjectDeliverLogDao;
import com.yskc.rs.entity.hightech.ProjectDeliverEntity;
import com.yskc.rs.entity.project.ProjectDeliverLog;
import com.yskc.rs.enums.DeliverTypeEnum;
import com.yskc.rs.enums.HighTechProgressNodeEnum;
import com.yskc.rs.models.hightech.*;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.dao.project.ProjectAuditOptDao;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightechprogress.*;
import com.yskc.rs.models.project.DeliverLogModel;
import com.yskc.rs.service.HighTechProgressService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.*;

import java.util.stream.Collectors;

/**
 * @program: rs
 * @description: 高新进度Service层
 * @author: cyj
 * @create: 2022-05-19 09:08
 **/
@Service
public class HighTechProgressServiceImpl implements HighTechProgressService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProjectAuditOptDao projectAuditOptDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectDeliverDao projectDeliverDao;
    @Autowired
    private ProjectDeliverLogDao projectDeliverLogDao;

    @Override
    public Boolean allot(HighTechAllotModel model, UserInfo info) throws OwnerException{
        if (CollectionUtils.isEmpty(model.getProjectIds())) {
            throw new OwnerException("更新数据为空，操作失败");
        }
        Date now = new Date();
        return projectAuditOptDao.updateBatch(model,info,now)>0;
    }

    @Override
    public List<DeliverLogModel> getLogList(QueryHighTechAuditModel model) throws OwnerException{
        if (model.getCompanyId()==null || model.getProjectId() == null || model.getMonth() == null || model.getDeliverType() == null) {
            throw new OwnerException("获取日志信息失败");
        }
        ProjectDeliverEntity nodeModel = projectDeliverDao.getNodeByModel(model);
        if (null == nodeModel || null == nodeModel.getId() || null == nodeModel.getCompanyId()) {
            return new ArrayList<>();
        }
        return projectDeliverDao.getLogList(nodeModel.getId(),nodeModel.getCompanyId());
    }

    @Override
    public Map<String, List<HighTechProjectModel>> getProjectList(QueryHighTechFileModel model, UserInfo userInfo) {
        Integer node = model.getNode();
        Integer audit = model.getAudit();
        //根据传来的审核状态查询对应数据
        if (HighTechProgressNodeEnum.AREA.getNode()== node && 1 == audit) {
            model.setStatus(FlowInstanceStatusEnum.DONE.getStatus());
        } else {
            if (1 == audit) {
                model.setNode(HighTechProgressNodeEnum.getNext(node).getNode());
            }
            if (0 == audit && HighTechProgressNodeEnum.AREA.getNode() == node) {
                model.setStatus(FlowInstanceStatusEnum.ONGOING.getStatus());
            }
        }
        List<HighTechProjectModel> highTechProjects = projectDao.getHighTechProjects(model,userInfo.getCompanyIds());
        Map<String, List<HighTechProjectModel>> map = new HashMap<>();
        if (CollectionUtils.isEmpty(highTechProjects)) {
            return map;
        }
        for (HighTechProjectModel fileModel : highTechProjects) {
            ToolUtils.putAndAdd(map,fileModel.getCompanyName(),fileModel);
        }
        return map;
    }

    @Override
    public Boolean submitsAudit(TechAuditBatchModel model, UserInfo userInfo) throws OwnerException{
        List<Integer> ids = model.getIds();
        if (CollectionUtils.isEmpty(ids)) {
            throw new OwnerException("提交数据为空！");
        }
        List<ProjectDeliverEntity> list = projectDeliverDao.selectByIds(model.getIds());
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        Date now = new Date();
        Integer roleType = userInfo.getRoleType();
        Integer userSource = userInfo.getUserSource();
        for (ProjectDeliverEntity entity : list) {
            if (!HighTechProgressNodeEnum.hasNode(entity.getNode(), roleType, userSource)) {
                throw new OwnerException("选取数据存在无条件审核的项目，审核失败");
            }
        }
        Integer status = model.getStatus();
        if (status== FlowInstanceStatusEnum.REJECT.getStatus()){
            setStatus(list,false,userInfo,now);
        }else if (status==FlowInstanceStatusEnum.DONE.getStatus()){
            setStatus(list,true,userInfo,now);
        }else {
            throw new OwnerException("材料状态有误！");
        }
        List<ProjectDeliverLog> logs = new ArrayList<>();
        String suggestion = model.getSuggestion();
        String realName = userInfo.getRealName();
        for (ProjectDeliverEntity entity : list) {
            ProjectDeliverLog log = new ProjectDeliverLog(entity.getCompanyId(), entity.getId(),
                    status, suggestion, entity.getNode(), now,realName);
            logs.add(log);

        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            projectDeliverDao.updateBatch(list);
            projectDeliverLogDao.addLogs(logs);
            TransactionUtils.commit(transactionStatus);
        }catch (Exception e){
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(),e);
            throw new OwnerException("更新状态或插入日志失败！");
        }
        return true;
    }

    @Override
    public PageModel<List<HighTechProgressModel>> getList(List<Integer> companyIds, QueryHighTechProgModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("p.rdTitle");
            page.setAscs(ascs);
        }
        Date beginDate = null;
        Date endDate = null;
        if (query.getYear() != null) {
            DateTime dateTime = DateUtil.parse(query.getYear() + "-01-01");
            beginDate = DateUtil.beginOfYear(dateTime);
            endDate = DateUtil.endOfYear(beginDate);
        }else {return null;}
        List<HighTechProgressModel> progressList = projectDao.gethtProgressList(page, companyIds, query.getYear());

        if (!CollectionUtils.isEmpty(progressList)){
            Map<Integer,HighTechProgressModel> progressMap = progressList.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e));
            Set<Integer> projectIds = progressMap.keySet();
            List<ProjectDeliverEntity> list = projectDeliverDao.getNodeList(beginDate,endDate, projectIds);
            for (ProjectDeliverEntity model : list) {
                if (model.getNode()==null){
                    model.setNode(HighTechProgressNodeEnum.DEFAULT.getNode());
                }
                Integer key = model.getProjectId();
                HighTechProgressModel progressModel = progressMap.get(key);
                Map<Integer, ProjectDeliverEntity> finaNodeMap = progressModel.getFinaNodeMap();
                Map<Integer, ProjectDeliverEntity> techNodeMap = progressModel.getTechNodeMap();
                Integer month = DateUtil.month(model.getMonth())+1;
                Integer deliverType = model.getDeliverType();
                    if (deliverType.equals(DeliverTypeEnum.TECH_PROJECT.getDeliverType())){
                        progressModel.setpApplication(model);
                    }else if (deliverType.equals(DeliverTypeEnum.TECH_CHECK.getDeliverType())){
                        progressModel.setpAccept(model);
                    }else if (deliverType.equals(DeliverTypeEnum.FINA_MONTH.getDeliverType())){
                        if (CollectionUtils.isEmpty(finaNodeMap)) {
                            finaNodeMap = new HashMap<>();
                            progressModel.setFinaNodeMap(finaNodeMap);
                        }
                            finaNodeMap.put(month, model);
                    }else if (deliverType.equals(DeliverTypeEnum.TECH_MONTH.getDeliverType())){
                        if (CollectionUtils.isEmpty(techNodeMap)) {
                            techNodeMap = new HashMap<>();
                            progressModel.setTechNodeMap(techNodeMap);
                        }
                            techNodeMap.put(month, model);
                    }
            }
        }
        return PageUtils.build(page,progressList);
    }

    @Override
    public List<HighTechTotalModel> getTotalList(List<Integer> companyIds, Integer year) {
        Date beginDate = null;
        Date endDate = null;
        if (year != null) {
            DateTime dateTime = DateUtil.parse(year + "-01-01");
            beginDate = DateUtil.beginOfYear(dateTime);
            endDate = DateUtil.endOfYear(beginDate);
        }
        //0为未审，1为已审
        Integer audit = 1;
        Integer noAudit = 0;
        Map<Integer,Map<Integer,Map<Integer, Integer>>> ysAuditMap = new HashMap<>();
        Map<Integer,Map<Integer,Map<Integer, Integer>>> facAuditMap = new HashMap<>();
        Map<Integer,Map<Integer,Map<Integer, Integer>>> areaAuditMap = new HashMap<>();
        List<HighTechAmountModel> totalList = projectDeliverDao.getTotalList(beginDate, endDate, companyIds);
        for (HighTechAmountModel model : totalList) {
            //0：立项材料，1：月度轨迹材料，2：年度轨迹材料，3：验收材料，4：财务归集
            Integer deliverType = model.getDeliverType();
            Integer month = DateUtil.month(model.getMonth())+1;
            Integer node = model.getNode();
            Integer amount = model.getAmount();
            HighTechProgressNodeEnum nodeEnum = HighTechProgressNodeEnum.getById(node);
            //审核已通过
            if (model.getStatus()== FlowInstanceStatusEnum.DONE.getStatus()){
                addTotal(ysAuditMap,audit,deliverType,month, amount);
                addTotal(facAuditMap,audit,deliverType,month, amount);
                addTotal(areaAuditMap,audit,deliverType,month, amount);
            }else {
                //判断节点
                switch (Objects.requireNonNull(nodeEnum)) {
                    case DEFAULT:
                        addTotal(ysAuditMap,noAudit,deliverType,month, amount);
                        break;
                    case FACTORY:
                        addTotal(ysAuditMap,audit,deliverType,month, amount);
                        addTotal(facAuditMap,noAudit,deliverType,month, amount);
                        break;
                    case AREA:
                        addTotal(ysAuditMap,audit,deliverType,month, amount);
                        addTotal(facAuditMap,audit,deliverType,month, amount);
                        addTotal(areaAuditMap,noAudit,deliverType,month, amount);
                        break;
                    default:break;
                }
            }
        }
        List<HighTechTotalModel> result = new ArrayList<>();
        allTotal(result,ysAuditMap.get(noAudit),noAudit,HighTechProgressNodeEnum.DEFAULT.getNode());
        allTotal(result,ysAuditMap.get(audit),audit,HighTechProgressNodeEnum.DEFAULT.getNode());
        allTotal(result,facAuditMap.get(noAudit),noAudit,HighTechProgressNodeEnum.FACTORY.getNode());
        allTotal(result,facAuditMap.get(audit),audit,HighTechProgressNodeEnum.FACTORY.getNode());
        allTotal(result,areaAuditMap.get(noAudit),noAudit,HighTechProgressNodeEnum.AREA.getNode());
        allTotal(result,areaAuditMap.get(audit),audit,HighTechProgressNodeEnum.AREA.getNode());
        return result;
    }

    public void allTotal(List<HighTechTotalModel> result,
                         Map<Integer,Map<Integer, Integer>> totalMap,Integer audit,Integer node){
        HighTechTotalModel highTechTotalModel = new HighTechTotalModel();
        highTechTotalModel.setNode(node);
        highTechTotalModel.setAudit(audit);
        if (!CollectionUtils.isEmpty(totalMap)){
            if (!CollectionUtils.isEmpty(totalMap.get(DeliverTypeEnum.TECH_PROJECT.getDeliverType()))){
                highTechTotalModel.setpApplication(totalMap.get(DeliverTypeEnum.TECH_PROJECT.getDeliverType()).get(1));
            }
            if (!CollectionUtils.isEmpty(totalMap.get(DeliverTypeEnum.TECH_CHECK.getDeliverType()))){
                highTechTotalModel.setpAccept(totalMap.get(DeliverTypeEnum.TECH_CHECK.getDeliverType()).get(1));
            }
            if (!CollectionUtils.isEmpty(totalMap.get(DeliverTypeEnum.TECH_MONTH.getDeliverType()))){
                highTechTotalModel.setTechMap(totalMap.get(DeliverTypeEnum.TECH_MONTH.getDeliverType()));
            }
            if (!CollectionUtils.isEmpty(totalMap.get(DeliverTypeEnum.FINA_MONTH.getDeliverType()))){
                highTechTotalModel.setFinaMap(totalMap.get(DeliverTypeEnum.FINA_MONTH.getDeliverType()));
            }
        }
        result.add(highTechTotalModel);
    }

    public void addTotal(Map<Integer,Map<Integer,Map<Integer, Integer>>> auditMap,
                         Integer audit, Integer key, Integer month, Integer amount){
        Map<Integer,Map<Integer, Integer>> map = new HashMap<>();
        Map<Integer, Integer> mapByMonth = new HashMap<>();
        mapByMonth.put(month, amount);
        map.put(key,mapByMonth);
        if (CollectionUtils.isEmpty(auditMap)){
            if (auditMap.containsKey(audit)){
                auditMap.get(audit).putAll(map);
            }else {
                auditMap.put(audit,map);
            }
        }else {
            if (auditMap.containsKey(audit)){
                //audit为1表示已审，0为未审
                if (auditMap.get(audit).containsKey(key)){
                    if (auditMap.get(audit).get(key).containsKey(month)){
                        Integer allAmount = auditMap.get(audit).get(key).get(month);
                        allAmount+=amount;
                        auditMap.get(audit).get(key).put(month,allAmount);
                    }else {
                        auditMap.get(audit).get(key).put(month, amount);
                    }
                }else {
                    auditMap.get(audit).put(key,mapByMonth);
                }
            }else {
                auditMap.put(audit,map);
            }

        }
    }
    @Override
    public Boolean submitAudit(QueryHighTechAuditModel query, UserInfo info) throws OwnerException {
        Integer status = query.getStatus();
        QueryHighTechAuditModel defaultQuery = new QueryHighTechAuditModel();
        defaultQuery.setStatus(query.getStatus());
        defaultQuery.setNode(query.getNode());
        defaultQuery.setAuditUser(info.getRealName());
        defaultQuery.setCompanyId(query.getCompanyId());
        defaultQuery.setSuggestion(query.getSuggestion());
        Date date = new Date();
        ProjectDeliverEntity nodeModel = projectDeliverDao.getNodeByModel(query);
        if (nodeModel!=null){
            Integer deliverId = nodeModel.getId();
            //材料驳回
            if (status== FlowInstanceStatusEnum.REJECT.getStatus()){
                //材料回到最初即优赛
                query.setStatus(FlowInstanceStatusEnum.ONGOING.getStatus());
                query.setNode(FlowInstanceStatusEnum.ONGOING.getStatus());
            }else if (status==FlowInstanceStatusEnum.DONE.getStatus()){
                HighTechProgressNodeEnum next = HighTechProgressNodeEnum.getNext(query.getNode());
                if (next!=null){
                    Integer nextNode = next.getNode();
                    //到下一个节点进行中
                    query.setNode(nextNode);
                    query.setStatus(FlowInstanceStatusEnum.ONGOING.getStatus());
                }else {
                    //材料通过
                    query.setStatus(FlowInstanceStatusEnum.DONE.getStatus());
                }
            }else {
                throw new OwnerException("材料状态有误！");
            }
            TransactionStatus transactionStatus = null;
            try {
                transactionStatus = TransactionUtils.newTransaction();
                projectDeliverDao.updateNode(query,info.getUserId(), info.getMsUserId(), date);
                projectDeliverLogDao.addDeliverLog(deliverId,defaultQuery);
                TransactionUtils.commit(transactionStatus);
            }catch (Exception e){
                TransactionUtils.rollback(transactionStatus);
                logger.error(e.getMessage(),e);
                throw new OwnerException("更新状态或插入日志失败！");
            }
            return true;
        }else {
            throw new OwnerException("未查到此条数据！");
        }
    }

    /**
     * 改变数据当前节点以及状态
     * @param list
     * @param push
     * @param info
     * @param now
     */
    private void setStatus(List<ProjectDeliverEntity> list, Boolean push,UserInfo info,Date now) {
        for (ProjectDeliverEntity entity : list) {
            if (push) {
                HighTechProgressNodeEnum next = HighTechProgressNodeEnum.getNext(entity.getNode());
                if (next != null) {
                    Integer nextNode = next.getNode();
                    //到下一个节点进行中
                    entity.setNode(nextNode);
                    entity.setStatus(FlowInstanceStatusEnum.ONGOING.getStatus());
                } else {
                    //材料通过
                    entity.setStatus(FlowInstanceStatusEnum.DONE.getStatus());
                }
            } else {
                //材料回到最初即优赛
                entity.setStatus(FlowInstanceStatusEnum.ONGOING.getStatus());
                entity.setNode(FlowInstanceStatusEnum.ONGOING.getStatus());
            }
            entity.update(info.getUserId(), info.getMsUserId(), now);
        }
    }
}
