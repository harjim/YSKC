package com.yskc.docservice.config;

import java.math.BigDecimal;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.config
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-15 11:42
 * @Description: 常量
 */
public class Constant {

    /**
     * utf-8
     */
    public static final String UTF_8_KEY = "UTF-8";
    /**
     * 请求头token的key
     */
    public static final String TOKEN_KEY = "Access-Token";
    /**
     * JSON_CONTENT_TYPE
     */
    public static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    /**
     * 请求头CompanyId的key
     */
    public static final String COMPANYID_KEY = "CompanyId";
    /**
     * 用户会话key
     */
    public static final String USER_SESSION_KEY = "UserSession";

    /**
     * 会话信息
     */
    public static final String REDIS_KEY_RS_SESSION = "RsSession:{0}";

    public static final String REDIS_KEY_RS_ROLE = "RsRole:{0}";
    /**
     * 修改用户时间
     */
    public static final String REDIS_KEY_USER_UPDATE_TIME = "RsUserUpdateTime:{0}";

    public static final String REDIS_USER_LOGIN_COMPANY_KEY = "UserLoginCompany:{0}_{1}";
    /**
     * MS用户登录的session超时时间，4小时
     */
    public static final Integer MS_USER_SESSION_TIMEOUT = 60 * 60 * 4;
    /**
     * RS用户登录的session超时时间，1天
     */
    public static final Integer ONE_DAY_TIME = 24 * 60 * 60;


    /**
     * 会话信息
     */
    public static final String REDIS_KEY_MS_SESSION = "MsSession:{0}";
    /**
     * 用户角色
     */
    public static final String REDIS_KEY_MS_USER_ROLE = "MsUserRoleDept:{0}";

    public static final String REDIS_KEY_MS_ROLE = "MsRole:{0}";


    public  static final String PATH_SEPARATOR = "/";

    public static final String HYPHEN = "-";

    public static final String MS_LOGIN = "ms";
    public static final String RS_LOGIN = "rs";
    public static final String IMPORT_FILE_NAME_FORMAT = "{0}/{1}/";
    public static final Integer MAX_INSERT_OR_UPDATE_COUNT = 2000;
    public static final String IMPORT_DEPT_ERROR = "第{0}行，请检查部门名称【{1}】是否是一个完整的路径！例如：一级部门/二级部门";
    public static final String ATTENDANCE_TIME_ERROR = "工号【{0}】，姓名【{1}】,出勤日期【{2}】,{3}";
    public static final String REDIS_COMPANY_SETTING_KEY = "companySetting:{0}";
    /**
     * 保留小数位
     */
    public static final Integer MAX_SCALE = 16;
    public final static int MAX_MONTH = 11;
    public final static String NOTHING = "无";


    /**
     * 全天 24 小时
     */
    public static final Integer DAY_HOUR = 24;
    /**
     * 默认设备折旧工时
     */
    public static final String DEFAULT_HOUR_EQU_DATA = "24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24";

    public static final String REDIS_KEY_PROJECT_STATUS = "RsProjectStatus:{0}";

    /**
     * rs项目状态过期时间：30分钟
     */
    public static final Integer RS_PROJECT_STATUS_TIMEOUT = 30 * 60;

    /**
     * 过程文件模板路径
     */
    public static final String RS_HTML_TEMPLATE_PATH = "..\\resource\\static\\htmlTemplate\\1";

    public static final BigDecimal TEN_THOUSAND = BigDecimal.valueOf(10000);

    public static final String HTML_TEMPLATE_DIR = "/static/htmlTemplate/0/";
}
