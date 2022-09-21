package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.models.docFile.DocFileDataSaveModel;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-26 10:05
 * @Description: 项目文档表
 */
@TableName("p_docFile_data")
public class ProjectDocFileDataEntity implements Serializable {

    private Integer id;

    private Integer pDocFileId;

    private String data;
    private Integer creatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date lastUpdateTime;
    /**
     *
     */
    private Integer lastUpdatorId;

    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    /**
     * 字长
     */
    private Integer wordLength;
    /**
     * 已填项数
     */
    private Integer filledItems;
    /**
     * 总项数
     */
    private Integer totalItems;


    public static ProjectDocFileDataEntity build(DocFileDataSaveModel dataModel) {
        ProjectDocFileDataEntity entity = new ProjectDocFileDataEntity();
        entity.id = dataModel.getId();
        entity.pDocFileId = dataModel.getpDocFileId();
        entity.data = dataModel.getData();
        entity.wordLength = dataModel.getWordLength();
        entity.filledItems = dataModel.getFilledItems();
        entity.totalItems = dataModel.getTotalItems();
        return entity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpDocFileId() {
        return pDocFileId;
    }

    public void setpDocFileId(Integer pDocFileId) {
        this.pDocFileId = pDocFileId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public Integer getWordLength() {
        return wordLength;
    }

    public void setWordLength(Integer wordLength) {
        this.wordLength = wordLength;
    }

    public Integer getFilledItems() {
        return filledItems;
    }

    public void setFilledItems(Integer filledItems) {
        this.filledItems = filledItems;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }
}
