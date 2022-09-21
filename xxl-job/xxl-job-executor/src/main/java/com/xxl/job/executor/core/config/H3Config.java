package com.xxl.job.executor.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "h3")
public class H3Config {
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
