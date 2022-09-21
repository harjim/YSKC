package com.xxl.job.executor.service.jobhandler;

import cn.hutool.core.date.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.core.config.WeChatConfig;
import com.xxl.job.executor.dao.rs.CompanySettingDao;
import com.xxl.job.executor.dao.rs.EmployeeOpenidDao;
import com.xxl.job.executor.dao.rs.ProjectAttendanceDao;
import com.xxl.job.executor.dao.rs.PushAttendanceLogDao;
import com.xxl.job.executor.entity.rs.CompanySettingEntity;
import com.xxl.job.executor.entity.rs.EmployeeOpenidEntity;
import com.xxl.job.executor.entity.rs.PushAttendanceLog;
import com.xxl.job.executor.models.attendance.CompanyEnumbersModel;
import com.xxl.job.executor.models.attendance.TotalAttendanceModel;
import com.xxl.job.executor.models.wechat.AccountPeriodModel;
import com.xxl.job.executor.models.wechat.TemplateMsgModel;
import com.xxl.job.executor.utils.ToolUtils;
import com.xxl.job.executor.utils.WeChatUtils;
import com.yskc.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.service.jobhandler
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-28 10:36
 * @Description: 微信消息推送
 */
@Component
public class WeChatPushMsgJob {

    @Autowired
    private WeChatUtils weChatUtils;
    @Autowired
    private EmployeeOpenidDao employeeOpenidDao;
    @Autowired
    private ProjectAttendanceDao projectAttendanceDao;
    @Autowired
    private WeChatConfig weChatConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PushAttendanceLogDao pushAttendanceLogDao;
    @Autowired
    private CompanySettingDao companySettingDao;

    @XxlJob("pushAttendanceJob")
    public ReturnT pushAttendanceJob(String param) throws Exception {
        Date now = new Date();
        Date month = DateUtil.beginOfMonth(now);
        // 存放上月，当月，下月月份
        List<Date> months = new ArrayList<>(3);
        months.add(month);
        months.add(DateUtil.offsetMonth(month, -1));
        months.add(DateUtil.offsetMonth(month, 1));
        List<CompanySettingEntity> accountPeriods = companySettingDao.getAccountPeriods(months);
        if (CollectionUtils.isEmpty(accountPeriods)) {
            XxlJobLogger.log("未获取任何公司在上月，当月，下月规划工时。");
            return ReturnT.SUCCESS;
        }
        Map<Integer, AccountPeriodModel> accountPeriodMap = new HashMap<>();
        accountPeriods.forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            if (!StringUtils.isEmpty(item.getAccountPeriod())) {
                map = JsonUtils.jsonToPojo(item.getAccountPeriod(), HashMap.class);
            }
            Date m = month;
            AccountPeriodModel accountPeriod = getAccountPeriod(m, map);
            // 日期大于区间结束日期
            if (m.compareTo(accountPeriod.getEnd()) > 0) {
                // 下移一月
                m = DateUtil.offsetMonth(m, 1);
                accountPeriod = getAccountPeriod(m, map);
            }
            accountPeriod.setCompanyId(item.getCompanyId());
            accountPeriod.setMonth(m);
            accountPeriodMap.put(item.getCompanyId(), accountPeriod);
        });

        List<TotalAttendanceModel> totalAttendances = projectAttendanceDao.getMonthHour(new ArrayList<>(accountPeriodMap.values()));
        if (CollectionUtils.isEmpty(totalAttendances)) {
            XxlJobLogger.log("未获取任何推送数据");
            return ReturnT.SUCCESS;
        }
        String keyFormat = "{0}_{1}";
        Map<Integer, CompanyEnumbersModel> companyEnumbersMap = new HashMap<>();
        Map<String, TotalAttendanceModel> totalMap = new HashMap<>();
        totalAttendances.forEach(item -> {
            Integer companyId = item.getCompanyId();
            totalMap.put(MessageFormat.format(keyFormat, companyId, item.getEnumber()), item);
            if (!companyEnumbersMap.containsKey(companyId)) {
                companyEnumbersMap.put(companyId, CompanyEnumbersModel.build(companyId));
            }
            companyEnumbersMap.get(companyId).getEnumbers().add(item.getEnumber());
        });
        List<EmployeeOpenidEntity> openids = employeeOpenidDao.getOpenid(new ArrayList<>(companyEnumbersMap.values()));
        if (CollectionUtils.isEmpty(openids)) {
            XxlJobLogger.log("当前不存在可推送人员");
            return ReturnT.SUCCESS;
        }
        String token = weChatUtils.getWeChatAccessToken();
        if (StringUtils.isEmpty(token)) {
            XxlJobLogger.log("获取token失败");
            return ReturnT.FAIL;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<PushAttendanceLog> insertPushMsg = new ArrayList<>();
        for (EmployeeOpenidEntity openid : openids) {
            TotalAttendanceModel totalAttendance = totalMap.get(MessageFormat.format(keyFormat, openid.getCompanyId(), openid.getEnumber()));
            if (null == totalAttendance) {
                continue;
            }
            if (null == totalAttendance.getRdHour()) {
                totalAttendance.setRdHour(BigDecimal.ZERO);
            }
            Map<String, Map<String, Object>> data = new HashMap<>();
            data.put("phrase1", ToolUtils.getValueMap("研发考勤"));
            data.put("thing6", ToolUtils.getValueMap("当月计划应打工时：" + totalAttendance.getPlanTime()));
            data.put("thing11", ToolUtils.getValueMap("已打：" + totalAttendance.getRdHour() + "，未打：" + totalAttendance.getPlanTime().subtract(totalAttendance.getRdHour())));
            data.put("thing10", ToolUtils.getValueMap(DateUtil.formatDate(accountPeriodMap.get(openid.getCompanyId()).getEnd())));
            HttpEntity<Object> request = new HttpEntity<>(TemplateMsgModel.build(openid.getOpenid(), weChatConfig.getMsgTemplateId(), "pages/index/index", data), headers);
            Object obj = restTemplate.exchange(MessageFormat.format(Constant.WE_CHAT_SUBSCRIBE_MSG_URL, token), HttpMethod.POST, request, Object.class);
            XxlJobLogger.log(MessageFormat.format("推送公司：{0}，推送工号：{1}，openId：{2}，推送结果：{3}",
                    openid.getCompanyId(), openid.getEnumber(), openid.getOpenid(), obj.toString()));
            Thread.sleep(20);
            insertPushMsg.add(new PushAttendanceLog(now, openid.getCompanyId(), openid.getEnumber(), month, obj.toString(), data.toString()));
            if (Constant.MAX_INSERT_OR_UPDATE.equals(insertPushMsg.size())) {
                try {
                    pushAttendanceLogDao.addBatch(insertPushMsg);
                    insertPushMsg.clear();
                } catch (Exception e) {
                    XxlJobLogger.log(e);
                }
            }
        }
        if (!CollectionUtils.isEmpty(insertPushMsg)) {
            pushAttendanceLogDao.addBatch(insertPushMsg);
        }
        return ReturnT.SUCCESS;
    }

    public AccountPeriodModel getAccountPeriod(Date month, Map<String, Object> accountPeriod) {
        Date monthBegin = com.yskc.common.utils.DateUtil.getMonthFirstDay(month);
        Date monthEnd;
        if (CollectionUtils.isEmpty(accountPeriod)) {
            monthEnd = com.yskc.common.utils.DateUtil.getMonthLastDay(monthBegin);
        } else {
            int accountMonth = Integer.parseInt(accountPeriod.get("accountMonth").toString());
            // 月份时间默认为1号，获取的开始日期减1，即 2021-01-01 + (1 -1) = 2021-01-01
            int day = Integer.parseInt(accountPeriod.get("day").toString()) - 1;
            // 0 上月
            if (accountMonth == 0) {
                // 结束时间为当月 + day - 1
                monthEnd = cn.hutool.core.date.DateUtil.endOfDay(cn.hutool.core.date.DateUtil.offsetDay(monthBegin, day - 1));
                // 开始时间为上月 + day
                monthBegin = cn.hutool.core.date.DateUtil.offsetDay(cn.hutool.core.date.DateUtil.offsetMonth(monthBegin, -1), day);
            } else {
                // 结束时间为下月 + day -1
                monthEnd = cn.hutool.core.date.DateUtil.endOfDay(
                        cn.hutool.core.date.DateUtil.offsetDay(
                                cn.hutool.core.date.DateUtil.offsetMonth(monthBegin, 1),
                                day - 1)
                );
                // 开始时间为当月 +day
                monthBegin = cn.hutool.core.date.DateUtil.offsetDay(monthBegin, day);
            }
        }
        AccountPeriodModel result = new AccountPeriodModel();
        result.setBegin(monthBegin);
        result.setEnd(monthEnd);
        return result;
    }


}
