package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/10/13 8:36
 * description:申报方向
 */
@TableName("t_direction")
public class RsDirectionEntity implements Serializable {

    @TableId
    private Integer id;
    private String direction;//方向
    private Integer productId;
    private Date expiryDate;//截止时间
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private String appGuideUrl;//申请指南地址
    private Boolean hasStage;//是否配置阶段
    private String linkName;//联系人
    private String linkTel;//联系电话
    private String mainDirection;//主方向
    private String noticeNo;//通知文号
    private String noticeUrl;//通知地址
    private String policyPath;//政策文件路径
    private String publicItemUrl;//公示名单地址

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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

    public String getAppGuideUrl() {
        return appGuideUrl;
    }

    public void setAppGuideUrl(String appGuideUrl) {
        this.appGuideUrl = appGuideUrl;
    }

    public Boolean getHasStage() {
        return hasStage;
    }

    public void setHasStage(Boolean hasStage) {
        this.hasStage = hasStage;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getMainDirection() {
        return mainDirection;
    }

    public void setMainDirection(String mainDirection) {
        this.mainDirection = mainDirection;
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getNoticeUrl() {
        return noticeUrl;
    }

    public void setNoticeUrl(String noticeUrl) {
        this.noticeUrl = noticeUrl;
    }

    public String getPolicyPath() {
        return policyPath;
    }

    public void setPolicyPath(String policyPath) {
        this.policyPath = policyPath;
    }

    public String getPublicItemUrl() {
        return publicItemUrl;
    }

    public void setPublicItemUrl(String publicItemUrl) {
        this.publicItemUrl = publicItemUrl;
    }
}
