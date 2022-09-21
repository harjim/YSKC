package com.yskc.rs.models.proposal;

import com.yskc.rs.models.params.PageParams;

/**
 * @Author: hck
 * @DateTime: 2021/7/14 14:13
 * @Description:
 */
public class QueryProposalModel extends PageParams {

    private Integer year;

    private String title;

    private String remark;

    private Integer type;

    private String pname;

    // 立项部门
    private String deptName;

    //项目提出人
    private String initiator;

    private Integer formula;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public Integer getFormula() {
        return formula;
    }

    public void setFormula(Integer formula) {
        this.formula = formula;
    }
}
