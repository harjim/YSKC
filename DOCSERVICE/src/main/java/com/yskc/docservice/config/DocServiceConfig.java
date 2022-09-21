package com.yskc.docservice.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.yskc.common.freemarker.FreemarkerConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.config
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-16 10:09
 * @Description: 导入服务配置
 */
@Configuration
@ConfigurationProperties(prefix = "docservice")
public class DocServiceConfig {
    private String rsImportPath;
    private String msImportPath;
    private String msAdminName;
    private String resourcePath;
    /**
     * 过程文档html模板目录
     */
    private String templateDir;

    /**
     * 导入最大行数【默认为50000】
     */
    private Integer maxRow = 50000;

    public String getRsImportPath() {
        return rsImportPath;
    }

    public void setRsImportPath(String rsImportPath) {
        this.rsImportPath = rsImportPath;
    }

    public String getMsImportPath() {
        return msImportPath;
    }

    public void setMsImportPath(String msImportPath) {
        this.msImportPath = msImportPath;
    }

    public String getMsAdminName() {
        return msAdminName;
    }

    public void setMsAdminName(String msAdminName) {
        this.msAdminName = msAdminName;
    }

    public Integer getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(Integer maxRow) {
        this.maxRow = maxRow;
    }


    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getTemplateDir() {
        return templateDir;
    }

    public void setTemplateDir(String templateDir) {
        this.templateDir = templateDir;
    }

    @Bean(name = "freemarkerConfiguration")
    @Primary
    public freemarker.template.Configuration getFreemarkerConfiguration(DocServiceConfig docServiceConfig) throws SQLException {
        return FreemarkerConfiguration.getFreemarkerConfiguration(docServiceConfig.getTemplateDir());
    }
}
