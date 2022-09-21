package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.rs.models.RsBaseEntity;
import com.yskc.ms.models.UserInfo;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/2 15:52
 * @Description:专利资料
 */
@TableName("p_patent_file")
public class PatentFileEntity extends RsBaseEntity {
    @TableId
    private Integer id;

    private Integer patentPlanId;

    private String fileName;

    private String filepath;
    //1：受理通知书 2：实质审查资料 3：授权通知书 4：知识产权证书 5：缴费收据 6：其他
    private Integer fileType;

    private String patentNo;

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public PatentFileEntity() {
    }

    public PatentFileEntity(Integer patentPlanId, String fileName, String filePath, Integer fileType, UserInfo userInfo, String patentNo) {
        this.patentPlanId = patentPlanId;
        this.fileName = fileName;
        this.filepath = filePath;
        this.fileType = fileType;
        this.patentNo = patentNo;
        create(-1, userInfo.getId(), new Date());
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

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }
}
