package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/10/11 14:06
 * @Description: 研发意识管理
 * @author: hsx
 */
@TableName("p_sense")
public class SenseEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4750581337526343347L;

    @TableId
    private Integer id;

    private Integer type;

    private Date preachDate;

    private String rds;

    private String filePaths;

    private String remark;

    private Integer companyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getPreachDate() {
        return preachDate;
    }

    public void setPreachDate(Date preachDate) {
        this.preachDate = preachDate;
    }

    public String getRds() {
        return rds;
    }

    public void setRds(String rds) {
        this.rds = rds;
    }

    public String getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(String filePaths) {
        this.filePaths = filePaths;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
