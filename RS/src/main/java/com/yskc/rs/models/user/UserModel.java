package com.yskc.rs.models.user;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@ApiModel("操作用户model")
public class UserModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotNull(message = "姓名不能为空")
    @NotBlank(message = "姓名不能为空")
    private String realName;
    private int gender;

    @NotNull(message = "联系电话不能为空")
    @NotBlank(message = "联系电话不能为空")
    private String tel;
    @NotNull(message = "请选择部门")
    @NotBlank(message = "请选择部门")
    private String depId;
    @NotNull(message = "职位不能为空")
    @NotBlank(message = "职位不能为空")
    private String postion;
    private String remark;

    private String password;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
