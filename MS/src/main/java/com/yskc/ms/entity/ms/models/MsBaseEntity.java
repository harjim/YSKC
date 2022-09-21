package com.yskc.ms.entity.ms.models;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/8 16:31
 * @Description:
 */
public class MsBaseEntity {

    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;

    /**
     * 新建
     * 已废弃，请使用{@link com.yskc.ms.utils.ToolUtil#entityCreate}
     *
     * @param userId
     * @param now
     */
    @Deprecated
    public void create(Integer userId, Date now) {
        this.creatorId = userId;
        this.createTime = now;
        this.update(userId, now);
    }

    /**
     * 更新
     * 已废弃，请使用{@link com.yskc.ms.utils.ToolUtil#entityUpdate}
     * @param userId
     * @param now
     */
    @Deprecated
    public void update(Integer userId, Date now) {
        this.lastUpdatorId = userId;
        this.lastUpdateTime = now;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
