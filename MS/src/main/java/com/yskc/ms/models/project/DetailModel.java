package com.yskc.ms.models.project;

import java.util.Date;

/**
 * Created by hck
 * on 2020/5/15 13:58
 */
public class DetailModel {

    private String userName;

    private Date operationTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }
}
