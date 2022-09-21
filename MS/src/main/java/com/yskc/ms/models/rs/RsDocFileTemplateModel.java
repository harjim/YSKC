package com.yskc.ms.models.rs;

import java.io.Serializable;
import java.util.Date;

public class RsDocFileTemplateModel implements Serializable {

    private Integer id;

    private Integer docFileId;

    private String templateName;

    private Date createTime;

    private Date lastUpdateTime;

    private Integer defaultTemplateId;

    private Integer msLastUpdatorId;

    private Boolean enabled;

    private Integer owner;

    private Boolean required;

    private Boolean multiple;

    private Boolean defaultTemplate;

    private Boolean needEdit;

    private Integer uniqueness;//0 不限制 1阶段唯一 2 月份唯一 3年唯一 4项目唯一

    public Integer getUniqueness() {
        return uniqueness;
    }

    public void setUniqueness(Integer uniqueness) {
        this.uniqueness = uniqueness;
    }

    public Boolean getNeedEdit() {
        return needEdit;
    }

    public void setNeedEdit(Boolean needEdit) {
        this.needEdit = needEdit;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    public Boolean getDefaultTemplate() {
        return defaultTemplate;
    }

    public void setDefaultTemplate(Boolean defaultTemplate) {
        this.defaultTemplate = defaultTemplate;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public Integer getDefaultTemplateId() {
        return defaultTemplateId;
    }

    public void setDefaultTemplateId(Integer defaultTemplateId) {
        this.defaultTemplateId = defaultTemplateId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
