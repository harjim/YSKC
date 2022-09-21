package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.models.role.MenuPermModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 会话表
 * @author huronghua
 */
@TableName("sys_session")
public class SysSession implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer userId;
    private String token;
    private Date createTime;
    private Date updateTime;
    private Date expireTime;
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
