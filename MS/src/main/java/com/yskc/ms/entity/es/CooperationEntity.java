package com.yskc.ms.entity.es;



import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/11/12 8:34
 * @Description:合作意向
 * @author: hsx
 */
@TableName("e_cooperation")
public class CooperationEntity implements Serializable {

    private static final long serialVersionUID = 6152272627568106624L;

    @TableId
    private Integer id;

    //意向对象(自己不能对自己有意向)
    private Integer intentionId;

    //单位名称
    private String unitName;

    // 姓名
    private String fullname;

    //联系方式->电话
    private String linkInfo;

    //邮件
    private String email;

    //备注
    private String remark;

    // 0:未沟通,1:达成合作
    private Integer status;

    // 意向信息(意向内容：达成合作填写)
    private String information;

    private Integer type;

    private Date createTime;

    private Date lastUpdateTime;

    private Integer creatorId;

    private Integer msLastUpdatorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntentionId() {
        return intentionId;
    }

    public void setIntentionId(Integer intentionId) {
        this.intentionId = intentionId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLinkInfo() {
        return linkInfo;
    }

    public void setLinkInfo(String linkInfo) {
        this.linkInfo = linkInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public void update(Date date, Integer id) {
        this.lastUpdateTime = date;
        this.msLastUpdatorId = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
