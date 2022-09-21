package com.xxl.job.executor.core.config;

import java.math.BigDecimal;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.core.config
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 15:38
 * @Description: 常量
 */
public class Constant {
    public static final String PUSH_MSG = "推送人：{0}，推送对象：{1}，推送结果:{2}";
    /**
     * 钉钉token
     */
    public static final String DING_TOKEN = "access_token";

    /**
     * 钉钉错误码
     */
    public static final String DING_ERRCODE = "errcode";

    public static final String DING_ERRCODE_VALUE = "0";
    /**
     * 钉钉token过期时间
     */
    public static final long DING_TIME = 7100;
    /**
     * 获取access_token
     */
    public static final String DING_GETTOKEN = "https://oapi.dingtalk.com/gettoken?appkey={0}&appsecret={1}";

    public static final String DING_MSG_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token={0}";

    public static final Integer MAX_INSERT_OR_UPDATE = 2000;

    public static final String PATH_SEPARATOR = "/";

    public static final String HYPHEN = "-";

    public static final String DOP_DATE_FORMAT = "yyyy.MM.dd";

    public static final Integer ROW_LIMIT = 50;
    public static final Integer MAX_ROW_DATA = 1000;
    public static final int MIN_WORD_LENGTH = 50;
    public static final String WE_CHAT_ACCESS_TOKEN_KEY = "weChatAccessToken";
    public static final String WE_CHAT_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
    public static final String WE_CHAT_SUBSCRIBE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token={0}";
    public static final long WE_CHAT_ACCESS_TOKEN_TIMEOUT = 7100;
    public static final String FLOW_INSTANCE_TIME_KEY = "flowInstanceLastTime";
    public static final String BRACE_OPEN = "{";
    public static final String BRACE_CLOSE = "}";

    /**
     * 文本消息
     */
    public static final String DING_TEXT = "text";
    /**
     * 图片
     */
    public static final String DING_IMAGE = "image";
    /**
     * 语音
     */
    public static final String DING_VOICE = "voice";
    /**
     * 文件
     */
    public static final String DING_FILE = "file";
    /**
     * 链接
     */
    public static final String DING_LINK = "link";
    /**
     * OA消息
     */
    public static final String DING_OA = "oa";


    public static final BigDecimal TEN_THOUSAND = BigDecimal.valueOf(10000);


    public static final Integer RESOLUTION_FORM=3; //会议纪要
    public static final Integer RD_REPORT_FORM=22;  //RD人员设备折旧分配说明
    public static final Integer ANNUAL_REPORT_FORM=24;//项目年度技术总结
    public static final Integer PROJECT_REPORT_FORM=27;//项目立项报告
    public static final Integer CHECK_REPORT_FORM=33;//项目验收报告
    public static final Integer FINAL_COST_FORM=34;//项目决算报告
    public static final Integer NEW_REPORT_FORM=38;//查新报告

}
