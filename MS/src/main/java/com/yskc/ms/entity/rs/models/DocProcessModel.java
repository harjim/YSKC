package com.yskc.ms.entity.rs.models;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs.models
 * @Author: wangxing
 * @CreateTime: 2019-08-05 14:49
 * @Description: 过程管理model
 */
public class DocProcessModel {
    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private String processName;
    /**
     *
     */
    private Integer type;
    /**
     *
     */
    private String remark;
    /**
     *
     */
    private Integer msCreatorId;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     *
     */
    private Integer lastMsUpdatorId;

    private int seq;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private  Date lastUpdateTime;

    private List<DocProcessTemplateListModel> templateListModels;


    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getId() {
        return id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLastMsUpdatorId() {
        return lastMsUpdatorId;
    }

    public void setLastMsUpdatorId(Integer lastMsUpdatorId) {
        this.lastMsUpdatorId = lastMsUpdatorId;
    }

    public List<DocProcessTemplateListModel> getTemplateListModels() {
        return templateListModels;
    }

    public void setTemplateListModels(List<DocProcessTemplateListModel> templateListModels) {
        this.templateListModels = templateListModels;
    }
}
