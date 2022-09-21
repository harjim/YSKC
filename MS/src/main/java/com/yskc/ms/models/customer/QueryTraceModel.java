package com.yskc.ms.models.customer;

import com.yskc.ms.models.params.PageParams;

/**
 * @program: ms
 * @description: 获取跟进记录
 * @author: cyj
 * @create: 2022-06-06 09:52
 **/
public class QueryTraceModel extends PageParams {
    private Integer customerId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
