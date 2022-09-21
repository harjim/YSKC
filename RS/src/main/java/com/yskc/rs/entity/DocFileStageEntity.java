package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

/**
 * @Author: hck
 * @DateTime: 2021/4/20 8:28
 * @Description:过程文档阶段配置
 */
@TableName("docFileStage")
public class DocFileStageEntity extends BaseEntity {

    @TableId
    private Integer id;

    private String stageKey;

    private Integer seq;

    private Integer docFileId;

    private Boolean autoAdd;

    private Boolean monthly;

    private Boolean mPrefix;

    private Boolean needEdit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public Boolean getAutoAdd() {
        return autoAdd;
    }

    public void setAutoAdd(Boolean autoAdd) {
        this.autoAdd = autoAdd;
    }

    public Boolean getMonthly() {
        return monthly;
    }

    public void setMonthly(Boolean monthly) {
        this.monthly = monthly;
    }

    public Boolean getmPrefix() {
        return mPrefix;
    }

    public void setmPrefix(Boolean mPrefix) {
        this.mPrefix = mPrefix;
    }

    public Boolean getNeedEdit() {
        return needEdit;
    }

    public void setNeedEdit(Boolean needEdit) {
        this.needEdit = needEdit;
    }
}
