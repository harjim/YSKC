package com.yskc.ms.config;

import com.yskc.ms.models.expert.ExpertConfig;
import com.yskc.ms.models.expert.UploadConfig;
import com.yskc.ms.models.login.LoginConfig;
import com.yskc.ms.models.xxljob.XxlJobConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 项目配置
 *
 * @author huronghua
 */
@Configuration
@Component
@ConfigurationProperties(prefix = "ms")
public class MsConfig {
    /**
     * 登录配置
     */
    private LoginConfig login;
    /**
     * 上传文件路径
     */
    private UploadConfig uploadConfig;

    private ExpertConfig expertConfig;

    private DingDingConfig dingDingConfig;

    private DingLoginCinfig dingLoginCinfig;

    private XxlJobConfig xxlJobConfig;


    public DingLoginCinfig getDingLoginCinfig() {
        return dingLoginCinfig;
    }

    public void setDingLoginCinfig(DingLoginCinfig dingLoginCinfig) {
        this.dingLoginCinfig = dingLoginCinfig;
    }

    public DingDingConfig getDingDingConfig() {
        return dingDingConfig;
    }

    public void setDingDingConfig(DingDingConfig dingDingConfig) {
        this.dingDingConfig = dingDingConfig;
    }

    public ExpertConfig getExpertConfig() {
        return expertConfig;
    }

    public void setExpertConfig(ExpertConfig expertConfig) {
        this.expertConfig = expertConfig;
    }

    public UploadConfig getUploadConfig() {
        return uploadConfig;
    }

    public void setUploadConfig(UploadConfig uploadConfig) {
        this.uploadConfig = uploadConfig;
    }

    public LoginConfig getLogin() {
        return login;
    }

    public void setLogin(LoginConfig login) {
        this.login = login;
    }

    public XxlJobConfig getXxlJobConfig() {
        return xxlJobConfig;
    }

    public void setXxlJobConfig(XxlJobConfig xxlJobConfig) {
        this.xxlJobConfig = xxlJobConfig;
    }
}
