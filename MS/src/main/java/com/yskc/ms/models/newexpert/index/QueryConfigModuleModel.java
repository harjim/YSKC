package com.yskc.ms.models.newexpert.index;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * @DateTime: 2021/9/26 17:25
 * @Description:
 * @author: hsx
 */
public class QueryConfigModuleModel extends PageParams implements Serializable {

    private String moduleName;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
