package com.yskc.rs.entity.proposal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.yskc.rs.entity.tech.BaseEntity;

/**
 * @Author: hck
 * @DateTime: 2021/7/14 9:43
 * @Description:提案管理
 */
@TableName("proposal_management")
public class ProposalManagement extends BaseEntity {

    @TableField
    private Integer id;

    private Integer companyId;

    private Integer year;

    private String filePath;

    private String title;

    private String remark;
    @TableField(strategy= FieldStrategy.IGNORED)
    private Integer type;

    private Integer projectId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
