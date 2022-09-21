package com.yskc.ms.models.techsummary;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.techsummary
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-26 10:28
 * @Description: 技改阶段文件model
 */
public class TechStageFilesModel {

    private Integer id;

    private Integer stageSeq;

    private Integer seq;

    private String stageKey;

    private String itemName;

    private Integer itemType;

    private String pattern;

    private String filePath;

    private String fileName;

    private Boolean required;

    private Integer stageListId;
    private List<String> filePaths;
    private List<String> fileNames;

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getStageSeq() {
        return stageSeq;
    }

    public void setStageSeq(Integer stageSeq) {
        this.stageSeq = stageSeq;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public Integer getStageListId() {
        return stageListId;
    }

    public void setStageListId(Integer stageListId) {
        this.stageListId = stageListId;
    }
    public void loadFile(TechStageFilesModel item) {
        if (this.fileNames == null) {
            this.fileNames = new ArrayList<>();
        }
        if (this.filePaths == null) {
            this.filePaths = new ArrayList<>();
        }
        this.fileNames.add(item.fileName);
        this.filePaths.add(item.filePath);
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }
}
