package com.yskc.rs.config;

import com.yskc.common.utils.HtmlToPdfUtils;
import com.yskc.rs.models.config.UploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.config
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-12 14:04
 * @Description: 网页转pdf配置
 */
@Configuration
public class HtmlToPdfConfig {

    @Autowired
    private RsConfig rsConfig;

    @Bean("htmlToPdfUtils")
    public HtmlToPdfUtils htmlToPdfUtils() {
        UploadConfig uploadConfig = rsConfig.getUploadConfig();
        HtmlToPdfUtils htmlToPdfUtils = new HtmlToPdfUtils(uploadConfig.getResourcePath(), uploadConfig.getDocPath());
//        HtmlToPdfUtils htmlToPdfUtils = new HtmlToPdfUtils(uploadConfig.getResourcePath(),uploadConfig.getDocPath(),fontPath,fontBdPath);
        htmlToPdfUtils.setGenerateByPdf7(uploadConfig.getGenerateByPdf7());
        return htmlToPdfUtils;
    }
}
