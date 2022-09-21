package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 16:44
 * @Description: 用户实体
 */
@TableName("ys_user")
public class UserEntity {
    @TableId
    private Integer id;
    private String userName;
    private String password;
    private String realName;
    private int gender;
    private String tel;
    private String depId;
    private String postion;
    private int status;
    private String avatar;
    private String remark;
    private Integer creatorId;
    private Date createTime;
    private Boolean reSetPassword;
    private String token;
    private Date rsLastOperationTime;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getReSetPassword() {
        return reSetPassword;
    }

    public void setReSetPassword(Boolean reSetPassword) {
        this.reSetPassword = reSetPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getRsLastOperationTime() {
        return rsLastOperationTime;
    }

    public void setRsLastOperationTime(Date rsLastOperationTime) {
        this.rsLastOperationTime = rsLastOperationTime;
    }
}
