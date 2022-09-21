package com.xxl.job.executor.service.jobhandler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.dao.ms.PatentDataDao;
import com.xxl.job.executor.entity.ms.PatentDataEntity;
import com.xxl.job.executor.models.patent.JobParamModel;
import com.xxl.job.executor.utils.BaiTenUtils;
import com.xxl.job.executor.utils.PatentStarUtils;
import com.xxl.job.executor.utils.SiPopUtils;
import com.yskc.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.service.jobhandler
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-10 10:55
 * @Description: 专利任务
 */
@Component
public class PatentJob {

    @Autowired
    private PatentDataDao patentDataDao;

    @XxlJob("siPopJob")
    public ReturnT<String> siPopJob(String param) throws Exception {
        JobParamModel jobParamModel = JsonUtils.jsonToPojo(param, JobParamModel.class);
        String[] queryArr = jobParamModel.getQueryArr().split(",");
        for (int i = 0; i < queryArr.length; i++) {
            String queryWord = queryArr[i];
            SiPopUtils siPopUtils = new SiPopUtils(queryWord);
            Integer pageSize = siPopUtils.getPageSize();
            int pageNum = jobParamModel.getPageNo() != null ? jobParamModel.getPageNo() : 1;
            for (; pageNum <= pageSize; pageNum++) {
                // 每100页 重新切换一次对象
                if (pageNum % 100 == 0) {
                    siPopUtils = new SiPopUtils(queryWord);
                }
                siPopUtils.getNextPage(pageNum);
                if (siPopUtils.checkSize()) {
                    patentDataDao.insertOrUpdate(siPopUtils.getDataList(), false, true);
                    XxlJobLogger.log(("当次保存" + siPopUtils.getDataList().size() + "条,当前页:" + pageNum));
                    siPopUtils.clearDataList();
                }
            }
            if (siPopUtils.checkSize()) {
                patentDataDao.insertOrUpdate(siPopUtils.getDataList(), false, true);
                XxlJobLogger.log(("当次保存" + siPopUtils.getDataList().size() + "条"));
                siPopUtils.clearDataList();
            }
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("baiTenJob")
    public ReturnT<String> baiTenJob(String param) throws Exception {
        String[] queryArr = param.split(",");
        for (int i = 0; i < queryArr.length; i++) {
            String queryWord = queryArr[i];
            Date today = DateUtil.beginOfDay(new Date());
            BaiTenUtils baiTenUtils = new BaiTenUtils(queryWord);
            String end = DateUtil.format(today, DatePattern.PURE_DATE_PATTERN);
            Date nextDate = null;
            while (nextDate == null || nextDate.before(today)) {
                String begin;
                if (nextDate == null) {
                    begin = DateUtil.format(DateUtil.parse("19800101", DatePattern.PURE_DATE_PATTERN), DatePattern.PURE_DATE_PATTERN);
                } else {
                    begin = DateUtil.format(nextDate, DatePattern.PURE_DATE_FORMAT);
                }
                Integer pageNum = baiTenUtils.getPageNum(1, begin, end);
                for (int page = 1; page <= pageNum; page++) {
                    nextDate = baiTenUtils.nextRangeDate(page, begin, end);
                    if (baiTenUtils.checkSize()) {
                        patentDataDao.insertOrUpdate(baiTenUtils.getDataList(), true, true);
                        XxlJobLogger.log(("当次保存" + baiTenUtils.getDataList().size() + "条，下一个爬取日期" + nextDate));
                        baiTenUtils.clearDataList();
                    }
                }
            }
            if (baiTenUtils.checkSize()) {
                patentDataDao.insertOrUpdate(baiTenUtils.getDataList(), true, true);
                XxlJobLogger.log(("当次保存" + baiTenUtils.getDataList().size() + "条"));
                baiTenUtils.clearDataList();
            }
        }
        return ReturnT.SUCCESS;
    }

    @XxlJob("patentStarJob")
    public ReturnT<String> patentStarJob(String param) throws Exception {
        JobParamModel jobParamModel = JsonUtils.jsonToPojo(param, JobParamModel.class);
        String[] queryArr = jobParamModel.getQueryArr().split(",");
        for (int i = 0; i < queryArr.length; i++) {
            String queryWord = queryArr[i];
            Date maxDate = patentDataDao.getMaxPublicDate(queryWord);
            PatentStarUtils patentUtil = new PatentStarUtils(maxDate, queryWord);
            int itemCount = patentUtil.loadItemCount();
            int remain = itemCount % Constant.ROW_LIMIT;
            int maxPage = itemCount / Constant.ROW_LIMIT;
            if (remain > 0) {
                maxPage += 1;
            }
            int page = jobParamModel.getPageNo() != null ? jobParamModel.getPageNo() : 1;
            for (; page <= maxPage; page++) {
                // 获取数据失败时，重新登录
                if (patentUtil.getRestart()) {
                    List<PatentDataEntity> temp = patentUtil.getDataList();
                    page--;
                    patentUtil = new PatentStarUtils(maxDate, queryWord);
                    patentUtil.setDataList(temp);
                    patentUtil.loadItemCount();
                }
                patentUtil.getNextPage(page);
                if (patentUtil.finish()) {
                    break;
                }
                if (patentUtil.checkSize()) {
                    patentDataDao.insertOrUpdate(patentUtil.getDataList(), true, false);
                    patentUtil.clearDataList();
                    XxlJobLogger.log(("当次保存" + Constant.MAX_ROW_DATA + "条,当前页：" + page));
                }
            }
            if (!CollectionUtils.isEmpty(patentUtil.getDataList())) {
                patentDataDao.insertOrUpdate(patentUtil.getDataList(), true, false);
                XxlJobLogger.log(("当次保存" + patentUtil.getDataList().size() + "条"));
                patentUtil.clearDataList();
            }
        }
        return ReturnT.SUCCESS;
    }
}
