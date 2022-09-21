package com.yskc.rs.models.hightech;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 11:34
 * @Description:
 */
public class HighTechFileModel implements Serializable {

    private Integer id;

    private String filePath;

    private String fileName;

    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
