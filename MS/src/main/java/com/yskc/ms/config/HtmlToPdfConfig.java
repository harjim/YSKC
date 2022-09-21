package com.yskc.ms.config;

import com.yskc.common.utils.HtmlToPdfUtils;
import com.yskc.ms.models.expert.UploadConfig;
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
    private MsConfig msConfig;

    @Bean("htmlToPdfUtils")
    public HtmlToPdfUtils htmlToPdfUtils(){
        UploadConfig uploadConfig = msConfig.getUploadConfig();
        HtmlToPdfUtils htmlToPdfUtils = new HtmlToPdfUtils(uploadConfig.getResourcePath(),uploadConfig.getDocPath());
        htmlToPdfUtils.setGenerateByPdf7(uploadConfig.getGenerateByPdf7());
        return htmlToPdfUtils;
    }

}
