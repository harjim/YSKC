package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

/**
 * @program: ms
 * @description: service_apply_member
 * @author: cyj
 * @create: 2022-08-11 14:02
 **/
@TableName("service_apply_member")
public class ServiceMemberEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    private Integer serviceApplyId;
    private Integer mtype;
    private Integer userId;

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

    public Integer getMtype() {
        return mtype;
    }

    public void setMtype(Integer mtype) {
        this.mtype = mtype;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
