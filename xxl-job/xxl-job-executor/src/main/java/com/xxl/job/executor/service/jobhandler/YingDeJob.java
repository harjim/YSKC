package com.xxl.job.executor.service.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.dao.rs.ProjectAuditDocFileDao;
import com.xxl.job.executor.dao.rs.ProjectAuditRdFeeDao;
import com.xxl.job.executor.dao.rs.ProjectDeliverDao;
import com.xxl.job.executor.models.hightechprogress.HighTechSubmitModel;
import com.xxl.job.executor.models.hightechprogress.ProjectDeliverModel;
import com.xxl.job.executor.utils.ListUtils;
import com.xxl.job.executor.utils.ToolUtils;
import com.xxl.job.executor.utils.TransactionUtils;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @DateTime: 2022/5/21 9:40
 * @Description:
 * @author: hsx
 */
@Component
public class YingDeJob {

    @Autowired
    private ProjectAuditDocFileDao projectAuditDocFileDao;

    @Autowired
    private ProjectAuditRdFeeDao projectAuditRdFeeDao;

    @Autowired
    private ProjectDeliverDao projectDeliverDao;

    @XxlJob("yingDeAuditSubmitJob")
    public ReturnT<String> yingDeAuditSubmitJob(String param) {
        if (StringUtils.isEmpty(param)) {
            XxlJobLogger.log("任务执行失败，缺少任务参数。");
            return ReturnT.FAIL;
        }
        HighTechSubmitModel model = JsonUtils.jsonToPojo(param, HighTechSubmitModel.class);
        Date lastTime = ToolUtils.getBeforeTime(new Date(), model.getTime(), model.getRange());
        if (null == model.getLastTime()) {
            model.setLastTime(lastTime);
        }
        List<ProjectDeliverModel> docFileList = projectAuditDocFileDao.getList(model);
        List<ProjectDeliverModel> rdFeeList = projectAuditRdFeeDao.getList(model);
        if (CollectionUtils.isEmpty(docFileList) && CollectionUtils.isEmpty(rdFeeList)) {
            return ReturnT.SUCCESS;
        }
        Date date = DateUtil.parse("0001-01-01", "yyyy-MM-dd");
        Map<String, ProjectDeliverModel> docFileMap = new HashMap<>();
        Map<String, ProjectDeliverModel> rdFeeMap = new HashMap<>();

        if (!CollectionUtils.isEmpty(docFileList)) {
            for (ProjectDeliverModel deliverModel : docFileList) {
                Integer deliverType = deliverModel.getDeliverType();
                if ((deliverType == 0 || deliverType == 3) || null == deliverModel.getMonth()) {
                    deliverModel.setMonth(date);
                }
                deliverModel.setNode(0);
                deliverModel.setStatus(0);
                deliverModel.setType(0);
                StringBuilder key = new StringBuilder();
                key.append(deliverModel.getProjectId()).append(deliverModel.getMonth()).append(deliverModel.getDeliverType());
                docFileMap.put(key.toString(), deliverModel);
            }
        }

        if (!CollectionUtils.isEmpty(rdFeeList)) {
            for (ProjectDeliverModel deliverModel : rdFeeList) {
                if (null == deliverModel.getMonth()) {
                    deliverModel.setMonth(date);
                }
                deliverModel.setType(1);
                deliverModel.setNode(0);
                deliverModel.setStatus(0);
                deliverModel.setDeliverType(4);
                StringBuilder key = new StringBuilder();
                key.append(deliverModel.getProjectId()).append(deliverModel.getMonth());
                rdFeeMap.put(key.toString(), deliverModel);
            }
        }
        List<ProjectDeliverModel> countList = new ArrayList<>();
        countList.addAll(docFileMap.values());
        countList.addAll(rdFeeMap.values());

        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            List<List<ProjectDeliverModel>> lists = ListUtils.subList(countList, Constant.MAX_INSERT_OR_UPDATE);
            Date now = new Date();
            for (List<ProjectDeliverModel> list : lists) {
                projectDeliverDao.addOrUpdate(list,now);
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            XxlJobLogger.log("更新数据失败!");
            XxlJobLogger.log(e);
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            return ReturnT.FAIL;
        }
    }
}
