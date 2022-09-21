package com.yskc.ms.models.customer;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.customer
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-20 08:49
 * @Description: 客户业务员
 */
public class CustomerOwnerUserModel implements Serializable {
    private Integer userId;
    private List<Integer> customerIds;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }

    public CustomerOwnerUserModel(Integer userId, List<Integer> customerIds) {
        this.userId = userId;
        this.customerIds = customerIds;
    }

    public CustomerOwnerUserModel(Integer userId) {
        this.userId = userId;
    }

    public CustomerOwnerUserModel() {
    }
}
