package com.xxl.job.executor.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.core.config
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-28 10:47
 * @Description: 微信配置
 */
@Configuration
@Component
@ConfigurationProperties(prefix = "wx")
public class WeChatConfig {

    private String appid;
    private String secret;
    private String msgTemplateId;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMsgTemplateId() {
        return msgTemplateId;
    }

    public void setMsgTemplateId(String msgTemplateId) {
        this.msgTemplateId = msgTemplateId;
    }

}
