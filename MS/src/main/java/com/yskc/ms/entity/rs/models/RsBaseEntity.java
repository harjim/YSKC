package com.yskc.ms.entity.rs.models;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.tech
 * @Author: zhangdingfu
 * @CreateTime: 2021-03-18 10:59
 * @Description: 基类，复用类。
 * <p>赋值createId，msCreateId等字段的代码的公用类。该基类只能由继承了tablebase或存在相同属性的实体类继承。</p>
 * <p>该基类不可添加tablebase没有的属性。并提供简单的方法调用。不可添加复杂逻辑。</p>
 */
public class RsBaseEntity {

    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    /**
     * 新建
     *
     * @param userId
     * @param msUserId
     * @param now
     */
    public void create(Integer userId, Integer msUserId, Date now) {
        this.creatorId = userId;
        this.msCreatorId = msUserId;
        this.createTime = now;
        this.update(userId, msUserId, now);
    }

    /**
     * 更新
     *
     * @param userId
     * @param msUserId
     * @param now
     */
    public void update(Integer userId, Integer msUserId, Date now) {
        this.lastUpdatorId = userId;
        this.msLastUpdatorId = msUserId;
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

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }
}
