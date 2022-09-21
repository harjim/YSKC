package com.yskc.ms.entity.es;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.es.models.EsBaseEntity;

import java.io.Serializable;

/**
 * @DateTime: 2021/9/23 15:37
 * @Description:
 * @author: hsx
 */
@TableName("user_files")
public class UserFilesEntity extends EsBaseEntity implements Serializable {

    private static final long serialVersionUID = 3398576421195560265L;

    @TableId
    private Integer id;

    private Integer userId;

    private String fileName;

    private String filePath;

    private Integer type;           // 文件类型 0：半身证件照，1：身份证附件，2学历证明，3职称证书，4：其他证明资料

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
