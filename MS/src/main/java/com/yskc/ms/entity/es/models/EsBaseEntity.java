package com.yskc.ms.entity.es.models;

import java.util.Date;

/**
 * @DateTime: 2021/9/26 15:43
 * @Description:
 * @author: hsx
 */
public class EsBaseEntity {

    private Integer creatorId;            // 创建人

    private Integer lastUpdatorId;        //最后更新人

    private Date createTime;                //创建时间

    private Date lastUpdateTime;            //修改时间

    /**
     * 新建
     *
     * @param userId
     * @param now
     */
    public void create(Integer userId, Date now) {
        this.creatorId = userId;
        this.createTime = now;
        this.update(userId, now);
    }

    /**
     * 更新
     *
     * @param userId
     * @param now
     */
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
