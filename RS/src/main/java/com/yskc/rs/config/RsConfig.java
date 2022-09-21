package com.yskc.rs.config;

import com.yskc.rs.models.config.OpenApiConfing;
import com.yskc.rs.models.config.LoginConfig;
import com.yskc.rs.models.config.UploadConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 项目配置
 * @author huronghua
 */
@Configuration
@Component
@ConfigurationProperties(prefix = "rs")
public class RsConfig {
    /**
     * 登录配置
     */
    private LoginConfig login;
    /**
     * 上传文件路径
     */
    private UploadConfig uploadConfig;

    /**
     * 链接服务配置
     */
    private OpenApiConfing openApiConfing;

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

    public OpenApiConfing getOpenApiConfing() {
        return openApiConfing;
    }

    public void setOpenApiConfing(OpenApiConfing openApiConfing) {
        this.openApiConfing = openApiConfing;
    }
}
