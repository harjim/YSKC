package com.yskc.rs.models.config;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.connect
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-09 11:12
 * @Description: 链接配置
 */
public class OpenApiConfing {

    private String msApiUrl;

    private String domain;

    public String getMsApiUrl() {
        return msApiUrl;
    }

    public void setMsApiUrl(String msApiUrl) {
        this.msApiUrl = msApiUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
