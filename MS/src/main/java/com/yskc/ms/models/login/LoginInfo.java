package com.yskc.ms.models.login;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 登录模型
 * @author huronghua
 */
@ApiModel("登录请求model")
public class LoginInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    private String userName;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    private String password;

    private String loginTmpCode;
    private Integer loginType;

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public String getLoginTmpCode() {
        return loginTmpCode;
    }

    public void setLoginTmpCode(String loginTmpCode) {
        this.loginTmpCode = loginTmpCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
