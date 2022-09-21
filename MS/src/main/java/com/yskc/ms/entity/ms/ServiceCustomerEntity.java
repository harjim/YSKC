package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

/**
 * @program: ms
 * @description: service_apply_customer
 * @author: cyj
 * @create: 2022-08-11 14:05
 **/
@TableName("service_apply_customer")
public class ServiceCustomerEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    private Integer serviceApplyId;
    private Integer customerId;
    private String causes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceApplyId() {
        return serviceApplyId;
    }

    public void setServiceApplyId(Integer serviceApplyId) {
        this.serviceApplyId = serviceApplyId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCauses() {
        return causes;
    }

    public void setCauses(String causes) {
        this.causes = causes;
    }
}
