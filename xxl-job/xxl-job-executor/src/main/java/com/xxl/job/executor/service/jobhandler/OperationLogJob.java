package com.xxl.job.executor.service.jobhandler;

import cn.hutool.core.collection.CollUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.dao.ms.OperationLogDao;
import com.xxl.job.executor.dao.ms.UserDao;
import com.xxl.job.executor.dao.rs.RsSysLogDao;
import com.xxl.job.executor.entity.ms.OperationLog;
import com.xxl.job.executor.entity.ms.UserEntity;
import com.xxl.job.executor.utils.ToolUtils;
import com.xxl.job.executor.utils.TransactionUtils;
import com.yskc.common.enums.DataSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.service.jobhandler
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-30 09:51
 * @Description: 操作日志job
 */
@Component
public class OperationLogJob {

    @Autowired
    private OperationLogDao operationLogDao;
    @Autowired
    private UserDao UserDao;
    @Autowired
    private RsSysLogDao rsSysLogDao;

    @XxlJob("rsOperationLogJob")
    public ReturnT<String> rsOperationLogJob(String params) {
        Date lastTime = ToolUtils.offsetDay(params, new Date());
        List<OperationLog> operationLogList = rsSysLogDao.getLastOperation(lastTime);
        if (CollectionUtils.isEmpty(operationLogList)) {
            XxlJobLogger.log("当次无操作记录，退出job。");
            return ReturnT.SUCCESS;
        }
        Map<Integer, UserEntity> userLastOperationMap = new HashMap<>();
        operationLogList.forEach(item -> {
            Integer userId = item.getUserId();
            if (!userLastOperationMap.containsKey(userId)) {
                UserEntity user = new UserEntity();
                user.setId(userId);
                userLastOperationMap.put(userId, user);
            }
            // 查询按日志时间正序，默认认为下一个时间比上一个时间大
            userLastOperationMap.get(userId).setRsLastOperationTime(item.getLastOperationTime());
        });
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            for(List<OperationLog> insertOrUpdate : CollUtil.split(operationLogList, Constant.MAX_INSERT_OR_UPDATE)){
                operationLogDao.insertOrUpdate(insertOrUpdate);
            }
            UserDao.updateRsLastOperationTime(new ArrayList<>(userLastOperationMap.values()));
            XxlJobLogger.log("当次插入日志操作统计数:"+operationLogList.size()+",当次更新用户最后操作RS时间数:"+userLastOperationMap.size());
            TransactionUtils.commit(DataSourceEnum.MS,transactionStatus);
            return ReturnT.SUCCESS;
        }catch (Exception e){
            TransactionUtils.rollback(DataSourceEnum.MS,transactionStatus);
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        }

    }

}
