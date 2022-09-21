package com.yskc.ms.models.rs;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/4/15 9:02
 */
public class RsDocFilesModel implements Serializable {

    private Integer id;//模板id

    private String docName;//模板名称

    private String templateName;//模板文件名

    private Boolean enabled;

    private Integer owner;

    private Boolean required;

    private Boolean multiple;

    private Integer defaultTemplateId;

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

    public Integer getDefaultTemplateId() {
        return defaultTemplateId;
    }

    public void setDefaultTemplateId(Integer defaultTemplateId) {
        this.defaultTemplateId = defaultTemplateId;
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

    private List<RsDocFileTemplateModel> rsDocFileTemplateModels;//模板下所有版本

    public List<RsDocFileTemplateModel> getRsDocFileTemplateModels() {
        return rsDocFileTemplateModels;
    }

    public void setRsDocFileTemplateModels(List<RsDocFileTemplateModel> rsDocFileTemplateModels) {
        this.rsDocFileTemplateModels = rsDocFileTemplateModels;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
