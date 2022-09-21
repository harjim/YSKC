package com.yskc.ms.models.patent;

import cn.hutool.core.collection.CollUtil;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import java.util.*;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2019-10-25 16:22
 * @Description: 知识产权(专利)配置
 */
public class PatentConfig {

    private static final Map<String, NameValuePair> LOGIN_MAP = new LinkedHashMap<>();

    public static final Map<String, String> BASE_HEADER = new LinkedHashMap<>();

    public static final Map<String, String> LOGIN_HEADER = new LinkedHashMap<>();

    static {
        LOGIN_MAP.put("usertype", new NameValuePair("usertype", "3"));
        LOGIN_MAP.put("logout_flag", new NameValuePair("logout_flag", "false"));
        LOGIN_MAP.put("userrole", new NameValuePair("userrole", "3"));
        LOGIN_MAP.put("language", new NameValuePair("language", "zh"));
        LOGIN_MAP.put("vidcode", new NameValuePair("vidcode", "1"));
        LOGIN_MAP.put("logintype", new NameValuePair("logintype", "0"));
    }

    static {
        BASE_HEADER.put("User-Agent",
                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
        BASE_HEADER.put("Accept-Language", "zh-CN,zh;q=0.8");
        BASE_HEADER.put("Accept", "*/*");
    }

    static {
        LOGIN_HEADER.putAll(BASE_HEADER);
        LOGIN_HEADER.put("X-Requested-With", "XMLHttpRequest");
        LOGIN_HEADER.put("Content-Type", "application/x-www-form-urlencoded");
        LOGIN_HEADER.put("Referer", "http://cpquery.cnipa.gov.cn/");
    }

    public static List<NameValuePair> getLoginParams(String userName, String password) {
        LOGIN_MAP.put("username", new NameValuePair("username", userName));
        LOGIN_MAP.put("password", new NameValuePair("password", password));
        return CollUtil.newArrayList(LOGIN_MAP.values());
    }
}
