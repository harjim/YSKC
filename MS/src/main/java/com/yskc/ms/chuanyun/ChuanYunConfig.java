package com.yskc.ms.chuanyun;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 氚云配置
 * @author huronghua
 */
@Configuration
@Component
@ConfigurationProperties(prefix = "chuanyun")
public class ChuanYunConfig {
    private String url;
    private String engineCode;
    private String engineSecret;
    private Integer spiltCount;

    public Integer getSpiltCount() {
        return spiltCount;
    }

    public void setSpiltCount(Integer spiltCount) {
        this.spiltCount = spiltCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    public String getEngineSecret() {
        return engineSecret;
    }

    public void setEngineSecret(String engineSecret) {
        this.engineSecret = engineSecret;
    }
}
