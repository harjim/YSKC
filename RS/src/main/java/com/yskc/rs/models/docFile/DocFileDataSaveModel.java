package com.yskc.rs.models.docFile;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.docFile
 * @Author: zhangdingfu
 * @CreateTime: 2020-11-25 08:25
 * @Description: 保存过程文件model
 */
public class DocFileDataSaveModel {
    private Integer id;
    private String data;
    private Integer pDocFileId;
    private Integer filledItems;
    private Integer totalItems;
    private Integer projectId;
    private String stageKey;
    private Integer docFileId;
    private Integer wordLength;
    private Date month;//查询数据月份 //文档属于年来源
    private List<Integer> trialProdIds;//试制记录id，预览和导出验证报告时只回显该list里对应的数据
    private Boolean finished;


    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getpDocFileId() {
        return pDocFileId;
    }

    public void setpDocFileId(Integer pDocFileId) {
        this.pDocFileId = pDocFileId;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public Integer getWordLength() {
        return wordLength;
    }

    public void setWordLength(Integer wordLength) {
        this.wordLength = wordLength;
    }

    public List<Integer> getTrialProdIds() {
        return trialProdIds;
    }

    public void setTrialProdIds(List<Integer> trialProdIds) {
        this.trialProdIds = trialProdIds;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
