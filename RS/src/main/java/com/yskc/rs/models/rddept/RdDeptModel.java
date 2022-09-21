package com.yskc.rs.models.rddept;

import javax.validation.constraints.NotBlank;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rddept
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-08 17:12
 * @Description: 研发部门操作model
 */
public class RdDeptModel {

    private Integer id;
    private Integer parentId;
    @NotBlank(message = "部门名称不能为空")
    private String deptName;
    private Integer level;

    private String thisIdentity;
    private Integer year;

    private Integer textDirection;//文字方向 0 竖向 1 横向

    private Integer nodeType;//节点类型 0 普通 1委员会节点

    private Integer align;// 对齐方式，0 左 1中 2右

    public Integer getAlign() {
        return align;
    }

    public void setAlign(Integer align) {
        this.align = align;
    }

    public Integer getTextDirection() {
        return textDirection;
    }

    public void setTextDirection(Integer textDirection) {
        this.textDirection = textDirection;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getThisIdentity() {
        return thisIdentity;
    }

    public void setThisIdentity(String thisIdentity) {
        this.thisIdentity = thisIdentity;
    }
}
