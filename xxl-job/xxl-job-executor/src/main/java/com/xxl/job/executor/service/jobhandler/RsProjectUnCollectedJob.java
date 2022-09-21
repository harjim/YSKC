package com.xxl.job.executor.service.jobhandler;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.dao.ms.MsUnCollectedDao;
import com.xxl.job.executor.dao.rs.RsUnCollectedDao;
import com.xxl.job.executor.models.rs_project_uncollected.RsProjectUnCollectedModel;
import com.xxl.job.executor.utils.TransactionUtils;
import com.yskc.common.enums.DataSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: xxl-job
 * @description: 未归集项目job
 * @author: cyj
 * @create: 2021-12-10 16:21
 **/
@Component
public class RsProjectUnCollectedJob {
    @Autowired
    private RsUnCollectedDao rsUnCollectedDao;
    @Autowired
    private MsUnCollectedDao msUnCollectedDao;
    @XxlJob("rsProjectUncollectedJob")
    public ReturnT<String> rsProjectUncollectedJob(String param) {
        Date now = new Date();
        List<RsProjectUnCollectedModel> data = rsUnCollectedDao.getList();
        List<Integer> delIds = new ArrayList<>();
        //只保留有年份的记录进行删除条件的筛选
        if(data.size()>0){
            delIds = msUnCollectedDao.getCollectedIds(data.stream().filter(item -> item.getYears().size() > 0).collect(Collectors.toList()));
        }
        if (CollectionUtils.isEmpty(data)&&CollectionUtils.isEmpty(delIds)) {
            XxlJobLogger.log("未获取任何数据，退出当前job。");
            return ReturnT.FAIL;
        }
        //求差集，取未归集年份
        for(RsProjectUnCollectedModel model : data){
            List<Integer> bgyears = new ArrayList<>();
            List<Integer> years = model.getYears();
            Integer beginYear = model.getBeginYear();
            int i=0;
            Integer endYear = model.getEndYear();
            do{
                bgyears.add(beginYear+i);
                i++;
            }while (i<=(endYear-beginYear));
            if(years.size()!=0){
                bgyears.removeAll(years);
            }
            model.setYears(bgyears);
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            if(data.size()>0){
                Integer num = msUnCollectedDao.insertUncollectedRds(data,now);
                XxlJobLogger.log(MessageFormat.format("已插入/更新记录数：{0}", num));
            }
            if (delIds.size()>0) {
                Integer del = msUnCollectedDao.delCollected(delIds);
                XxlJobLogger.log(MessageFormat.format("已删除归集记录数：{0}", del));
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            XxlJobLogger.log("插入数据失败!");
            XxlJobLogger.log(e);
            return ReturnT.FAIL;
        }
    }
}
