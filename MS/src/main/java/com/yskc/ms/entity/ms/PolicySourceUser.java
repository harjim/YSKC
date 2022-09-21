package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 10:10
 * @Description: 政策来源用户
 */
@TableName("policySourceUser")
public class PolicySourceUser {
    @TableId
    private Integer id;
    private Integer userId;
    private Integer sourceId;
    private Date createTime;

    public static PolicySourceUser build(Date now, Integer userId, Integer sourceId) {
        PolicySourceUser sourceUser = new PolicySourceUser();
        sourceUser.createTime = now;
        sourceUser.userId = userId;
        sourceUser.sourceId = sourceId;
        return sourceUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }
}
