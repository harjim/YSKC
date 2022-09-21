package com.yskc.rs.models.tech;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 项目other model
 *
 * @author zhangdingfu
 */
public class ProjectOtherModel {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Integer lastUpdatorId;
    /**
     *
     */
    private Date lastUpdateTime;
    /**
     *
     */
    private Integer projectId;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String areaCode;
    /**
     *
     */
    @Size(max = 300, message = "单位简介及建设依据不能超过300个字")
    private String synopsis;
    /**
     *
     */
    @Size(max = 300, message = "主要建设内容和目标不能超过300个字")
    private String targetAndContent;
    /**
     *
     */
    private String address;

    private String[] addressCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTargetAndContent() {
        return targetAndContent;
    }

    public void setTargetAndContent(String targetAndContent) {
        this.targetAndContent = targetAndContent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String[] addressCode) {
        this.addressCode = addressCode;
    }
}
