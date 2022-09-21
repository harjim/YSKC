package com.yskc.rs.entity.company;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/11 15:56
 * @Description:删除表数据备份
 */
@TableName("c_backup_data")
public class BackupDataEntity {
    @TableId
    private Integer id;

    private Integer companyId;

    private String tableName;

    private String data;

    private Integer creatorId;

    private Date createTime;

    private Integer msCreatorId;

    private Integer type;

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

    public BackupDataEntity() {
    }

    public BackupDataEntity(Integer companyId, String tableName, String data, Integer creatorId, Date createTime,
                            Integer msUserId, Integer type) {
        this.companyId = companyId;
        this.tableName = tableName;
        this.data = data;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.msCreatorId = msUserId;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
