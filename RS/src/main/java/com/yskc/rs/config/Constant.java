package com.yskc.rs.config;

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
     * 请求头CompanyId的key
     */
    public static final String COMPANYID_KEY = "CompanyId";
    /**
     * 用户会话key
     */
    public static final String USER_SESSION_KEY = "UserSession";
    /**
     * utf-8
     */
    public static final String UTF_8_KEY = "UTF-8";
    /**
     * JSON_CONTENT_TYPE
     */
    public static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    /**
     * 导入数据默认保存路径
     */
    public static final String IMPORT_FILE_NAME_FORMAT = "{0}/{1}/";
    public static final String IMPORT_IMAGE_FILE_NAME_FORMAT = "{0}/{1}/{2}";

    /**
     * 全天 24 小时
     */
    public static final Integer DAY_HOUR = 24;
    /**
     * .docx后缀名
     */
    public static final String DOCX_NAME = ".docx";

    /**
     * .doc后缀名
     */
    public static final String DOC_NAME = ".doc";
    /**
     * 默认工时字符串
     */
    public static final String DEFAULT_HOUR_ATT_DATA = "0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0";

    /**
     * 默认设备折旧工时
     */
    public static final String DEFAULT_HOUR_EQU_DATA = "24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24";
    /**
     * 默认最大的插入或者更新数量
     */
    public static final Integer MAX_INSERT_OR_UPDATE_COUNT = 2000;

    /**
     * MS用户登录的session超时时间，4小时
     */
    public static final Integer MS_USER_SESSION_TIMEOUT = 60 * 60 * 4;

    /**
     * rs项目状态过期时间：30分钟
     */
    public static final Integer RS_PROJECT_STATUS_TIMEOUT = 30 * 60;
    /**
     * RS用户登录的session超时时间，1天
     */
    public static final Integer RS_USER_SESSION_TIMEOUT = 24 * 60 * 60;

    /**
     * 修改用户时间
     */
    public static final String REDIS_KEY_USER_UPDATE_TIME = "RsUserUpdateTime:{0}";

    /**
     * 会话信息
     */
    public static final String REDIS_KEY_SESSION = "RsSession:{0}";

    public static final String REDIS_KEY_SESSION_COMPANY = "RsSessionCom:{0}";

    public static final String REDIS_KEY_ROLE = "RsRole:{0}";

    public static final String REDIS_KEY_PROJECT_STATUS = "RsProjectStatus:{0}";

    public static final String REDIS_PROJECT_AUDIT = "projectAudit:{0}_{1}";

    public static final String REDIS_COMPANY_SETTING_KEY = "companySetting:{0}";

    public static final String REDIS_USER_LOGIN_COMPANY_KEY = "UserLoginCompany:{0}_{1}";

    public static final String EXCEL_XLS = "xls";

    public static final String EXCEL_XLSX = "xlsx";
    public static final String EXCEL_DOC = "doc";

    public static final String EXCEL_DOCX = "docx";
    public static final String EXCEL_PPT = "ppt";

    public static final String EXCEL_PPTX = "pptx";
    public static final String EXCEL_PDF = "pdf";

    public static final String PATH_SEPARATOR = "/";
    public static final String IMPORT_DEPT_ERROR = "第{0}行，请检查部门名称【{1}】是否是一个完整的路径！例如：一级部门/二级部门";
    public static final String ATTENDANCE_TIME_ERROR = "工号【{0}】，姓名【{1}】,出勤日期【{2}】,{3}";
    public static final String HYPHEN = "-";
    public static final long SECOND = 60000;
    public static final BigDecimal TEN_THOUSAND = BigDecimal.valueOf(10000);

    /**
     * 保留小数位
     */
    public static final Integer MAX_SCALE = 16;

    public static final String WE_CHAT_JS_CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
    public static final String WE_CHAT_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
    public static final String WE_CHAT_SUBSCRIBE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token={0}";
    public static final long WE_CHAT_ACCESS_TOKEN_TIMEOUT = 7100;
    public static final String WE_CHAT_OPENID = "Openid";
    public static final String WE_CHAT_ACCESS_TOKEN_KEY = "weChatAccessToken";
    public static final String WE_CHAT_OPENID_KEY = "Openid:{0}";
    public static final String EMPLOYEE_OPENID = "EmployeeOpenid";
    public static final String HTML_TEMPLATE_DIR = "/static/htmlTemplate/0/";
    public static final String TEMPLATE_DIR = "/static/template/";
    public static final String TEMPLATE_DOCUMENT_DIR = "/static/template/document/";
    public static final String AGG_TEMPLATE_DIR = TEMPLATE_DIR + "aggregation/";
    public static final String BUILD_TEMPLATE_DIR = TEMPLATE_DIR + "build/";
    public static final String INTRO_TEMPLATE_DIR = TEMPLATE_DIR + "intro/";
    public static final String IMAGES_DIR = "/static/images";

    /**
     * kafka主题
     */
    public static final String TOPIC_SUBMIT_AUDIT = "submitAudit";
    /**
     * 过程文件id
     */
    public static final Integer RESOLUTION_FORM=3; //立项决议
    public static final Integer RD_REPORT_FORM=22;  //RD人员设备折旧分配说明
    public static final Integer ANNUAL_REPORT_FORM=24;//项目年度技术总结
    public static final Integer PROJECT_REPORT_FORM=27;//项目立项报告
    public static final Integer CHECK_REPORT_FORM=33;//项目验收报告
    public static final Integer FINAL_COST_FORM=34;//项目决算报告
    public static final Integer NEW_REPORT_FORM=38;//查新报告
    public static final Integer UTILITY_REPORT_FORM=50;//查新报告
    public final static String NOTHING = "无";
    public final static String DEFAULT_LACK_MONTH = "1,2,3,4,5,6,7,8,9,10,11,12";
    public final static int MAX_MONTH = 11;
}
