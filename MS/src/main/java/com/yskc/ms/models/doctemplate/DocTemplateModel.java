package com.yskc.ms.models.doctemplate;

import com.baomidou.mybatisplus.annotations.TableId;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.doctemplate
 * @Author: wangxing
 * @CreateTime: 2019-08-02 16:16
 * @Description: 文档模板Model
 */
public class  DocTemplateModel {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private String docName;
    /**
     *
     */
    private String version;
    /**
     *
     */
    private String content;
    /**
     *
     */
    private String templatePath;
    /**
     *
     */
    private Integer msCreatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Integer lastMsUpdatorId;
    /**
     *
     */
    private Date lastUpdateTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocName() {
        return docName;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setLastMsUpdatorId(Integer lastMsUpdatorId) {
        this.lastMsUpdatorId = lastMsUpdatorId;
    }

    public Integer getLastMsUpdatorId() {
        return lastMsUpdatorId;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }
}
