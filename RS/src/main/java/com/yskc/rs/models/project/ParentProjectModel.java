package com.yskc.rs.models.project;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/18 9:17
 * description:
 */
public class ParentProjectModel implements Serializable {

    private Integer rdIndex;//项目编号

    private String pname;//项目名称

    private String masterENumber;//负责人编号

    private String rdNumber;//内部编号

    private Integer deptId;

    private String deptName;//部门

    private String workshop;//车间

    private String productLine;//产线

    private String processSection;//工艺段

    private Integer rdDeptId;//研发部门id

    private List<Integer> childIds;//要合并的子项目ids

    private Integer parentId;//已有父项目的id

    private Integer sign;//0:将所选项目加入已有父项目  1:合并项目

    private String involvedProduct;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getInvolvedProduct() {
        return involvedProduct;
    }

    public void setInvolvedProduct(String involvedProduct) {
        this.involvedProduct = involvedProduct;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public List<Integer> getChildIds() {
        return childIds;
    }

    public void setChildIds(List<Integer> childIds) {
        this.childIds = childIds;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getMasterENumber() {
        return masterENumber;
    }

    public void setMasterENumber(String masterENumber) {
        this.masterENumber = masterENumber;
    }

    public String getRdNumber() {
        return rdNumber;
    }

    public void setRdNumber(String rdNumber) {
        this.rdNumber = rdNumber;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProcessSection() {
        return processSection;
    }

    public void setProcessSection(String processSection) {
        this.processSection = processSection;
    }

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
    }
}
