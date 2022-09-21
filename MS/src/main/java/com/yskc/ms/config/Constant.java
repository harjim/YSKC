package com.yskc.ms.config;

import java.math.BigDecimal;

/**
 * 常量
 *
 * @author huronghua
 */
public class Constant {
    /**
     * 请求头token的key
     */
    public static final String TOKEN_KEY = "Access-Token";
    /**
     * 用户会话key
     */
    public static final String USER_SESSION_KEY = "UserSession";
    /**
     * 数据权限key,通过request.Attribute传值
     */
    public static final String DATA_PERMISSION_KEY = "DataPerm";
    /**
     * utf-8
     */
    public static final String UTF_8_KEY = "UTF-8";
    /**
     * JSON_CONTENT_TYPE
     */
    public static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    /**
     * 超级管理员权限
     */
    public static final Integer ADMIN_ROLE_IR = 1;

    /**
     * 1天时间常量
     */
    public static final Integer ONE_DAY_TIME = 60 * 60 * 24;

    /**
     * 七天时间常量
     */
    public static final Integer SEVEN_DAYS_TIME = ONE_DAY_TIME * 7;

    /**
     * 记录修改rs用户时间
     */
    public static final String REDIS_KEY_RS_USER_UPDATE_TIME = "RsUserUpdateTime:{0}";

    /**
     * rs端session
     */
    public static final String REDIS_KEY_RS_SESSION = "RsSession:{0}";

    /**
     * rs端角色
     */
    public static final String REDIS_KEY_RS_ROLE = "RsRole:{0}";

    /**
     * ms端角色
     */
    public static final String REDIS_KEY_ROLE = "MsRole:{0}";
    /**
     * 会话信息
     */
    public static final String REDIS_KEY_SESSION = "MsSession:{0}";

    /**
     * 用户角色
     */
    public static final String REDIS_KEY_MS_USER_ROLE = "MsUserRoleDept:{0}";

    /**
     * 序列号
     */
    public static final String REDIS_KEY_SN_NO = "SN:{0}";

    /**
     * rs项目状态过期时间：30分钟
     */
    public static final Integer RS_PROJECT_STATUS_TIMEOUT = 30 * 60;
    /**
     * rs项目状态
     */
    public static final String REDIS_KEY_PROJECT_STATUS = "RsProjectStatus:{0}";


    public static final String DING_BASE_URL = "https://oapi.dingtalk.com";

    /**
     * 获取access_token
     */
    public static final String DING_GETTOKEN = "https://oapi.dingtalk.com/gettoken?appkey={0}&appsecret={1}";

    /**
     * 获取部门列表
     */
    public static final String DING_DEPARTMENT_LIST = "https://oapi.dingtalk.com/department/list?access_token={0}&fetch_child=true";
    /**
     * 获取部门详情
     */
    public static final String DING_DEPARTMENT_INFO = "https://oapi.dingtalk.com/department/get?access_token={0}&id={1}";
    /**
     * 获取部门用户详情
     */
    public static final String DING_USER_LIST = "https://oapi.dingtalk.com/user/listbypage?access_token={0}&department_id={1}&offset={2}&size={3}";
    /**
     * 获取部门用户userid列表
     */
    public static final String DING_GET_DEPT_MEMBER = "https://oapi.dingtalk.com/user/getDeptMember?access_token={0}&deptId={1}";
    /**
     * 获取用户详情
     */
    public static final String DING_GET_USER_INFO = "https://oapi.dingtalk.com/user/get?access_token={0}&userid={1}";
    /**
     * 服务端通过临时授权码获取授权用户的个人信息
     */
    public static final String DING_GETUSERINFO_BYCODE = "https://oapi.dingtalk.com/sns/getuserinfo_bycode?signature={0}&timestamp={1}&accessKey={2}";
    /**
     * 获取企业员工人数
     */
    public static final String DING_GET_ORG_USER_COUNT = "https://oapi.dingtalk.com/user/get_org_user_count?access_token={0}&onlyActive=0";
    /**
     * 发送信息
     */
    public static final String DING_SEND_MSG = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2?access_token={0}";
    /**
     * 通过code登录，免密登录
     */
    public static final String DING_CODE_LOGIN = "https://oapi.dingtalk.com/topapi/v2/user/getuserinfo?access_token={0}";
    /**
     * 钉钉token
     */
    public static final String DING_TOKEN = "access_token";
    /**
     * 钉钉错误码
     */
    public static final String DING_ERRCODE = "errcode";

    public static final String DING_ERRMSG = "errmsg";

    public static final String DING_ERRCODE_VALUE = "0";
    public static final String DING_ERRCODE_TOKEN_FAILL_VALUE = "40014";
    /**
     * 钉钉token过期时间
     */
    public static final long DING_TIME = 7100;

    public static final String DING_DEPARTMENT = "department";

    public static final String DING_USERLIST = "userlist";

    public static final String DING_USER_IDS = "userIds";

    /**
     * 导入数据默认保存路径
     */
    public static final String IMPORT_FILE_NAME_FORMAT = "{0}/";
    public static final String IMPORT_IMAGE_FILE_NAME_FORMAT = "{0}/{1}/{2}";
    public static final String IMPORT_IMAGE_FILE_INDEX_PAGE = "{0}/{1}";

    public static final String EXCEL_XLS = "xls";

    public static final String EXCEL_XLSX = "xlsx";
    public static final String EXCEL_DOC = "doc";

    public static final String EXCEL_DOCX = "docx";
    public static final String EXCEL_PPT = "ppt";

    public static final String EXCEL_PPTX = "pptx";
    public static final String EXCEL_PDF = "pdf";

    public static final String PATH_SEPARATOR = "/";

    public static final String HYPHEN = "-";


    public static final String DING_TEXT = "text";

    public static final BigDecimal TEN_THOUSAND = BigDecimal.valueOf(10000);

    public static final long SECOND = 60000;

    public static final int MIN_WORD_LENGTH = 50;

    public static final String HTML_TEMPLATE_DIR = "/static/htmlTemplate/0/";
    public static final String TEMPLATE_DIR = "/static/template/";
    public static final String AGG_TEMPLATE_DIR = TEMPLATE_DIR + "aggregation/";
    public static final String IMAGES_DIR = "/static/images";
    /**
     * 建设事项
     */
    public static final String BUILD_TEMPLATE_DIR = TEMPLATE_DIR + "build/";
    /**
     * kafka topic
     */
    public static final String TOPIC_PUSH_AUDIT = "pushAudit";
    public static final String TOPIC_ACTIVATE_AUDIT = "activateAudit";
    public static final String TOPIC_SUBMIT_AUDIT = "submitAudit";
    public static final String APPOINT_AUDIT_USER = "appointAuditUser";
    public static final String TOPIC_REVOKE_AUDIT = "revokeAudit";
    /**
     * 确认专利购买完成
     */
    public static final String TOPIC_PATENT_DEMAND_DONE = "patentDemandDone";
    /**
     * 默认最大的插入或者更新数量
     */
    public static final Integer MAX_INSERT_OR_UPDATE_COUNT = 2000;
    /**
     * 过程文件id
     */
    public static final Integer RESOLUTION_FORM = 3; //会议纪要
    public static final Integer RD_REPORT_FORM = 22;  //RD人员设备折旧分配说明
    public static final Integer ANNUAL_REPORT_FORM = 24;//项目年度技术总结
    public static final Integer PROJECT_REPORT_FORM = 27;//项目立项报告
    public static final Integer CHECK_REPORT_FORM = 33;//项目验收报告
    public static final Integer FINAL_COST_FORM = 34;//项目决算报告
    public static final Integer NEW_REPORT_FORM = 38;//查新报告

    public static final String XXL_JOB_TRIGGER = "{0}/jobinfo/trigger?id={1,number,#}";
    /**
     * 评分修改时限天数
     */
    public static final Integer SCORE_EDIT_DAYS = -1;
}
