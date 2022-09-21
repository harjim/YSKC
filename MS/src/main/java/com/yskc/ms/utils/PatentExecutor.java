package com.yskc.ms.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.yskc.ms.entity.rs.RsPatentEntity;
import com.yskc.ms.enums.PatentActionEnum;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.PatentSyncModel;
import com.yskc.ms.models.patent.PatentUnionModel;
import com.yskc.ms.models.patent.PatentValidHeartModel;
import com.yskc.ms.models.patent.PatentValidParam;
import com.yskc.ms.service.rs.RsPatentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-07-01 09:58
 * @Description: 专利爬虫执行器
 */
@Component
public class PatentExecutor {

    @Autowired
    private RsPatentService rsPatentService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String INFO_KEY = "info";
    private final static String PATENT_IDS_KEY = "patentIds";
    private final static Integer CORE_SIZE = 1;

    private final static Integer HEART = 6;

    public Integer suspendStep = 0;

    public List<RsPatentEntity> suspendPatentList = null;

    public UserInfo suspendInfo;

    private Boolean isQuery = true;

    private PatentUtils patentUtils;

    private Queue<Map<String, Object>> patentQueue = new ConcurrentLinkedDeque<>();

    private Map<Integer, Boolean> existPatentMap = new HashMap<>();

    private ScheduledThreadPoolExecutor singleExecutorService;

    public PatentSyncModel syncCountModel = new PatentSyncModel();

    @PostConstruct
    public static PatentExecutor getInstance() {
        return PatentExecutor.SingletonClassInstance.patentExecutor;
    }

    public PatentSyncModel getSyncCountModel() {
        syncCountModel.total = existPatentMap.size();
        return syncCountModel;
    }

    /**
     * 静态实例
     */
    private static class SingletonClassInstance {
        private final static PatentExecutor patentExecutor = new PatentExecutor();
    }

    /**
     * 线程类
     */
    private class PatentThread extends Thread {
        @Override
        public void run() {
            try {
                if (isQuery) {
                    isQuery = false;
                    if (!executorSearch(suspendPatentList, suspendInfo)) {
                        return;
                    }
                    Map<String, Object> infoIdsMap = patentQueue.poll();
                    while (null != infoIdsMap) {
                        List<RsPatentEntity> patentList = rsPatentService.getAll((List<Integer>) infoIdsMap.get(PATENT_IDS_KEY), null);
                        if (!executorSearch(patentList, (UserInfo) infoIdsMap.get(INFO_KEY))) {
                            return;
                        }
                        infoIdsMap = patentQueue.poll();
                    }
                    if (syncCountModel.getStop() || !syncCountModel.getSuspend()) {
                        syncCountModel.setZeroAndState("同步完成", false, true, "");
                        suspendPatentList = null;
                        suspendStep = 0;
                        patentQueue.clear();
                        existPatentMap.clear();
                    }
                } else {
                    patentUtils.validLogin();
                }
            } catch (Exception e) {
                syncCountModel.setState("获取数据失败。", true, true, "");
                shutDown();
                logger.error(e.getMessage(), e);
            }
        }
    }

    private PatentExecutor() {
        patentUtils = new PatentUtils();
    }

    /**
     * 关闭心跳验证
     */
    public void shutDown() {
        if (null == singleExecutorService) {
            return;
        }
        singleExecutorService.shutdown();
        singleExecutorService.shutdownNow();
        singleExecutorService = null;
    }

    /**
     * 开启心跳验证
     */
    public void executorValidLogin() {
        shutDown();
        isQuery = true;
        ThreadFactory nameFactory = new ThreadFactoryBuilder().build();
        singleExecutorService = new ScheduledThreadPoolExecutor(CORE_SIZE, nameFactory);
        singleExecutorService.scheduleWithFixedDelay(new PatentThread(), 0, HEART, TimeUnit.MINUTES);
    }


    public Boolean validHeart(PatentValidHeartModel model, UserInfo info) {
        boolean result = null != singleExecutorService && !singleExecutorService.isShutdown();
        if (!result) {
            return false;
        }
        if (model.getHasSearch() && !isQuery) {
            addToPatentQueueAndExecutor(model.getIds(), info);
        }
        return true;
    }

    private void addToPatentQueueAndExecutor(List<Integer> patenIds, UserInfo info) {
        boolean executor = existPatentMap.isEmpty();
        if (!CollectionUtils.isEmpty(patenIds)) {
            List<Integer> ids = new ArrayList<>();
            patenIds.forEach(item -> {
                if (!existPatentMap.containsKey(item)) {
                    existPatentMap.put(item, true);
                    ids.add(item);
                }
            });
            Map<String, Object> infoIdsMap = new HashMap<>(2);
            infoIdsMap.put(INFO_KEY, info);
            infoIdsMap.put(PATENT_IDS_KEY, ids);
            patentQueue.add(infoIdsMap);
        }
        if (executor || syncCountModel.getSuspend()) {
            syncCountModel = new PatentSyncModel(new Date(), info.getRealName());
            executorValidLogin();
        }
    }

    /**
     * 退出专利登录
     *
     * @return
     */
    public boolean exit() {
        shutDown();
        return true;
    }

    /**
     * 暂停，挂起当前同步
     *
     * @return
     */
    public PatentSyncModel suspend() {
        syncCountModel.setSuspend(true);
        return syncCountModel;
    }

    /**
     * 终止当前同步
     *
     * @return
     */
    public PatentSyncModel stop() {
        syncCountModel.setStop(true);
        return syncCountModel;
    }

    /**
     * 开始同步（暂停时）
     *
     * @return
     */
//    public Boolean beginSync() {
//        syncCountModel.setSuspend(false);
//        executorValidLogin();
//        return true;
//    }
    public Object executor(PatentActionEnum action, Object... params) throws Exception {
        switch (action) {
            case GET_VALID:
                return patentUtils.getValid();
            case SET_VALID:
                PatentValidParam patentParam = (PatentValidParam) params[0];
                if (patentUtils.setValid(patentParam)) {
                    addToPatentQueueAndExecutor(patentParam.getIds(), (UserInfo) params[1]);
                }
                return true;

            default:
                return null;
        }
    }

    public void savePatent(PatentUnionModel patentUnionModel, UserInfo info) {
        rsPatentService.savePatent(patentUnionModel, info);
    }

    /**
     * 执行查询
     *
     * @param patentList
     * @throws Exception
     */
    public boolean executorSearch(List<RsPatentEntity> patentList, UserInfo info) throws Exception {
        if (CollectionUtils.isEmpty(patentList)) {
            syncCountModel.setState("", false, true, "");
            return true;
        }
        for (int i = suspendStep; i < patentList.size(); i++) {
            RsPatentEntity currentPatent = patentList.get(i);
            syncCountModel.syncCount++;
            if (syncCountModel.getStop()) {
                logger.info(MessageFormat.format("当前专利同步已被终止。操作人：{0}", info.getRealName()));
                return false;
            }
            if (syncCountModel.getSuspend()) {
                suspendStep = i;
                suspendPatentList = patentList;
                suspendInfo = info;
                syncCountModel.setState("当前同步已暂停。", false, false, currentPatent.getPatentNo());
                return false;
            }
            syncCountModel.setState("", false, false, currentPatent.getPatentNo());
            logger.info(MessageFormat.format("当前同步: 【{0}】,index【{1}】,size【{2}】",
                    currentPatent.getPatentNo(), syncCountModel.syncCount, existPatentMap.size()));
            savePatent(patentUtils.patentSearch(currentPatent, info.getId()), info);
        }
        return true;
    }

}
