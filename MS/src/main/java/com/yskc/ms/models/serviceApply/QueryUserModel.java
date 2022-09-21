package com.yskc.ms.models.serviceApply;

import java.util.List;

/**
 * @program: ms
 * @description: 查询人员
 * @author: cyj
 * @create: 2022-08-11 17:58
 **/
public class QueryUserModel {
    private List<Integer> userIds;
    private String userName;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
