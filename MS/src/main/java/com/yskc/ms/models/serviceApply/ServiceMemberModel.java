package com.yskc.ms.models.serviceApply;

/**
 * @program: ms
 * @description: 服务人员表
 * @author: cyj
 * @create: 2022-08-11 09:29
 **/
public class ServiceMemberModel {
    private Integer id;
    private Integer serviceApplyId;
    private Integer mtype;
    private Integer userId;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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
