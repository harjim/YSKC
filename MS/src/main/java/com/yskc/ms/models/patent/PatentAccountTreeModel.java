package com.yskc.ms.models.patent;

import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-04 09:49
 * @Description: 专利账号TreeModel
 */
public class PatentAccountTreeModel implements Serializable {

    private Integer accountId;

    private String username;

    private String password;

    private Integer status;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
