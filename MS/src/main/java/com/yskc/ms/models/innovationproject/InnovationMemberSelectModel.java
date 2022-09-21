package com.yskc.ms.models.innovationproject;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.innovationproject
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-26 15:10
 * @Description: 创新人员下拉
 */
public class InnovationMemberSelectModel {

    private Integer memberId;

    private String userName;

    private String realName;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

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
}
