package com.yskc.ms.models.patent;

import com.yskc.ms.models.params.PageParams;

/**
 * Created by hck
 * on 2020/6/5 16:02
 * description:
 */
public class QueryPatentListModel extends PageParams {

    private String username;


    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }
}
