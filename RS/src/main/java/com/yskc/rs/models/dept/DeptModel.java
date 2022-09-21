package com.yskc.rs.models.dept;

import com.baomidou.mybatisplus.annotations.TableId;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 组织架构
 *
 * @author zhangdingfu
 */
public class DeptModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer id;
    private String deptName;
    private int parentId;
    private int level;
    private String identity;
    @Size(max = 300, message = "备注不能超过300个字")
    private String remark;
    private int creatorId;
    private Date createTime;
    private int companyId;
    private List<DeptModel> children;
    private String fullDeptName;


    public String getFullDeptName() {
        return fullDeptName;
    }

    public void setFullDeptName(String fullDeptName) {
        this.fullDeptName = fullDeptName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public List<DeptModel> getChildren() {
        return children;
    }

    public void setChildren(List<DeptModel> children) {
        this.children = children;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
