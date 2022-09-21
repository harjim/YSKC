package com.yskc.ms.models.serviceApply;

import com.yskc.ms.models.params.PageParams;

import java.util.Date;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-22 11:49
 **/
public class QueryMobileApply extends PageParams {
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
