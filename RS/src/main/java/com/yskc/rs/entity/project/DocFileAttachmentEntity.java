package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.yskc.rs.entity.tech.BaseEntity;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/6/17 14:34
 * @Description:记录及附件
 */
@TableName("p_docFile_attachment")
public class DocFileAttachmentEntity extends BaseEntity {
    @TableId
    private Integer id;

    private Integer projectId;

    private Integer companyId;
    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer docFileId;//过程文件id

    private String fileName;

    private String filePath;

    private Date uploadTime;//上传时间
    private Integer fileType;

    public static DocFileAttachmentEntity build(Integer docFileId, Integer companyId, Integer projectId, Date now, int userId, int msUserId, String fileName, String filePath) {
        DocFileAttachmentEntity entity = new DocFileAttachmentEntity();
        entity.docFileId = docFileId;
        entity.projectId = projectId;
        entity.companyId = companyId;
        entity.fileName = fileName;
        entity.filePath = filePath;
        entity.create(userId,msUserId,now);
        return entity;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
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

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}
