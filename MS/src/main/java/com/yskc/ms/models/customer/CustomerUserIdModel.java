package com.yskc.ms.models.customer;

import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.customer
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-20 08:54
 * @Description: 客户及用户id
 */
public class CustomerUserIdModel implements Serializable {

    private Integer userId;
    private Integer customerId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
