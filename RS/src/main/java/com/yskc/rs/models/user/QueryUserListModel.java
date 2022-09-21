package com.yskc.rs.models.user;

import com.yskc.rs.models.params.PageParams;

/**
 * Created by hck
 * on 2020/6/9 11:28
 * description:
 */
public class QueryUserListModel extends PageParams {

    private String userName;

    private String realName;

    private String tel;

    private Integer gender;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
