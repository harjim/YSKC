package com.yskc.rs.models.inspection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.entity.InspectionEntity;
import com.yskc.rs.models.UsedModel;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-11 15:59
 * @Description: 检测修理Modal
 */
public class InspectionModel implements Serializable {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date accDate;
    /**
     *
     */
    private String accNumber;
    /**
     *
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal expense;
    /**
     *
     */
    private String summary;
    /**
     *
     */
    private Integer type;

    /**
     *
     */
    @Size(max=200,message="备注不能超过200个字")
    private String remark;

    private String typeName;
    private String deptName;

    private String ename;

    private List<InspectionEntity> entityList;

    private  String enumber;

    private Integer accountTitleId;
    private  String fullAccountName;

    public String getTypeName() {
        return typeName;
    }

    public String getFullAccountName() {
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }

    public Integer getAccountTitleId() {
        return accountTitleId;
    }

    public void setAccountTitleId(Integer accountTitleId) {
        this.accountTitleId = accountTitleId;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public List<InspectionEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<InspectionEntity> entityList) {
        this.entityList = entityList;
    }


    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setAccDate(Date accDate) {
        this.accDate = accDate;
    }

    public Date getAccDate() {
        return accDate;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
