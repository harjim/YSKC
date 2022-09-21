package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;
import com.yskc.rs.models.UserInfo;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/2 15:52
 * @Description:专利资料
 */
@TableName("p_patent_file")
public class PatentFileEntity extends BaseEntity {
    @TableId
    private Integer id;

    private Integer patentPlanId;

    private String fileName;

    private String filePath;

    private Integer fileType;

    private String patentNo;

    public static PatentFileEntity build(String fileName, String filePath, Integer fileType, UserInfo userInfo, String patentNo) {
        PatentFileEntity entity = new PatentFileEntity();
        entity.create(userInfo.getUserId(),userInfo.getMsUserId(),new Date());
        entity.patentNo = patentNo;
        entity.fileName = fileName;
        entity.filePath = filePath;
        entity.fileType = fileType;
        return entity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatentPlanId() {
        return patentPlanId;
    }

    public void setPatentPlanId(Integer patentPlanId) {
        this.patentPlanId = patentPlanId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }
}
