package com.yskc.ms.models.newexpert.expert;


/**
 * @DateTime: 2021/9/24 10:47
 * @Description:
 * @author: hsx
 */
public class UserFilesModel {

    private Integer id;

    private Integer userId;

    private String filename;

    private String filePath;

    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
