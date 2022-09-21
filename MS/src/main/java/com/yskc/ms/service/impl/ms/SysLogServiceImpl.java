package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.es.EsLogDao;
import com.yskc.ms.dao.ms.OperationLogDao;
import com.yskc.ms.dao.ms.SysLogDao;
import com.yskc.ms.dao.ms.UserDao;
import com.yskc.ms.dao.rs.LogDao;
import com.yskc.ms.entity.ms.OperationLogEntity;
import com.yskc.ms.entity.ms.SysLog;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.log.QuerySysLogModel;
import com.yskc.ms.models.log.SysLogModel;
import com.yskc.ms.models.log.UserRsLogModel;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.models.user.OperationLogUserModel;
import com.yskc.ms.models.user.QueryUserListModel;
import com.yskc.ms.models.xxljob.XxlJobConfig;
import com.yskc.ms.service.ms.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-12 16:41
 * @Description: 日志业务实现层
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysLogDao sysLogDao;
    @Autowired
    private LogDao rsLogDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private EsLogDao esLogDao;
    @Autowired
    private OperationLogDao operationLogDao;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public PageModel<List<SysLogModel>> querySysLogList(QuerySysLogModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("logTime");
            page.setDescs(desc);
        }
        if (1 == query.getPageType()) {
            return PageUtils.build(page, sysLogDao.querySysLog(page, query, dataPerm));
        } else if (2 == query.getPageType()) {
            return PageUtils.build(page, rsLogDao.queryRsLog(page, query, dataPerm));
        } else {
            return PageUtils.build(page, esLogDao.queryEsLog(page, query, dataPerm));
        }
    }

    @Override
    public List<MiniUserModel> getDataByUserName(String userName, Integer pageType) {
        if (StrUtil.isEmpty(userName)) {
            return new ArrayList<>();
        }
        if (1 == pageType) {
            return sysLogDao.getDataByUserName(userName);
        } else if (2 == pageType) {
            return rsLogDao.getDataByRsUserName(userName);
        } else {
            return esLogDao.getDataByUserName(userName);
        }

    }

    @Override
    public void save(SysLog sysLog) {
        if (null != sysLog) {
            try {
                sysLogDao.insert(sysLog);
            } catch (Exception e) {
                logger.error("保存日志失败", e);
            }
        }
    }


    @Override
    public PageModel<List<UserRsLogModel>> getUserRsLog(QuerySysLogModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("logTime"));
        }
        if (query.getBeginDate() != null) {
            query.setBeginDate(DateUtil.beginOfMinute(query.getBeginDate()));
        }
        if (query.getEndDate() != null) {
            query.setEndDate(DateUtil.endOfMinute(query.getEndDate()));
        }
        // 加载区间，若存在时间，则置为起止时间
//        query.loadTerm();
        return PageUtils.build(page, rsLogDao.getUserLog(page, query));
    }

    @Override
    public PageModel<List<OperationLogUserModel>> getOperationLogUserList(QueryUserListModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("y.rsLastOperationTime");
            page.setDescs(desc);
        }
        // 加载区间，若存在时间，则置为起止时间
        query.loadTerm();
        List<OperationLogUserModel> result = userDao.getOperationLogUserList(page, query, dataPerm);
        if (!CollectionUtils.isEmpty(result)) {
            List<OperationLogEntity> operations = operationLogDao.getOperationCnt(query, result);
            if (!CollectionUtils.isEmpty(operations)) {
                Map<Integer, Integer> operationCntMap = operations.stream().collect(Collectors.toMap(k -> k.getUserId(), v -> v.getOperationCnt(), (o, n) -> n));
                result.forEach(item -> item.setOperationCnt(operationCntMap.get(item.getId())));
            }
        }
        return PageUtils.build(page, result);
    }

    @Override
    public boolean syncLog() {
        XxlJobConfig xxlJobConfig = msConfig.getXxlJobConfig();
        ResponseEntity<String> result = restTemplate.exchange(MessageFormat.format(Constant.XXL_JOB_TRIGGER, xxlJobConfig.getUrl(), xxlJobConfig.getRsOperationLogJobId()),
                HttpMethod.POST, null, String.class);
        if (null != result.getBody()) {
            return result.getBody().contains("200");
        }
        return false;
    }
}
