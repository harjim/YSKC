package com.yskc.rs.service.exportFileImpl;

import cn.hutool.core.util.StrUtil;
import com.yskc.rs.service.ExportDocFileService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: hck
 * @DateTime: 2021/4/10 9:41
 * @Description:过程文档业务工厂类
 */
@Component
public class DocFileExportBeanFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DocFileExportBeanFactory.applicationContext=applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static ExportDocFileService getBean(String name){
        if (applicationContext == null){
            return null;
        }
        ExportDocFileService exportDocFileService=null;
        try{
            exportDocFileService=(ExportDocFileService)applicationContext.getBean(StrUtil.lowerFirst(name));
        }finally {
            return exportDocFileService;
        }
    }
}
