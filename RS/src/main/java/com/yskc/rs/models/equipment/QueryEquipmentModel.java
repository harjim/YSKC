package com.yskc.rs.models.equipment;

import com.yskc.common.utils.DateUtil;
import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.equipment
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-31 10:57
 * @Description: 设备查询model
 */
public class QueryEquipmentModel extends PageParams implements Serializable {
    private String ecode;
    private String ename;
    private String emodal;
    private Integer etype;
    private Date purchaseDate;
    private Date beginPurchaseDate;
    private Date endPurchaseDate;
    private Date depreciationDate;
    private Date beginDepreciationDate;
    private Date endDepreciationDate;
    private String kinds;
    @Deprecated
    private String deptPath;
    @Deprecated
    private String rdDeptPath;
    private Integer year;
    @Deprecated
    private String workshopPath;
    private String deptName;
    private String fullname;
    private String workshop;
    private String remark;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEmodal() {
        return emodal;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getDepreciationDate() {
        return depreciationDate;
    }

    public void setDepreciationDate(Date depreciationDate) {
        this.depreciationDate = depreciationDate;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public Date getBeginPurchaseDate() {
        if (purchaseDate != null) {
            return DateUtil.getMonthFirstDay(purchaseDate);
        }
        return beginPurchaseDate;
    }

    public void setBeginPurchaseDate(Date beginPurchaseDate) {
        this.beginPurchaseDate = beginPurchaseDate;
    }

    public Date getEndPurchaseDate() {
        if (purchaseDate != null) {
            return DateUtil.getMonthLastDay(purchaseDate);
        }
        return endPurchaseDate;
    }

    public void setEndPurchaseDate(Date endPurchaseDate) {
        this.endPurchaseDate = endPurchaseDate;
    }

    public Date getBeginDepreciationDate() {
        if (depreciationDate != null) {
            return DateUtil.getMonthFirstDay(depreciationDate);
        }
        return beginDepreciationDate;
    }

    public void setBeginDepreciationDate(Date beginDepreciationDate) {
        this.beginDepreciationDate = beginDepreciationDate;
    }

    public Date getEndDepreciationDate() {
        if (depreciationDate != null) {
            return DateUtil.getMonthLastDay(depreciationDate);
        }
        return endDepreciationDate;
    }

    public void setEndDepreciationDate(Date endDepreciationDate) {
        this.endDepreciationDate = endDepreciationDate;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public String getDeptPath() {
        return deptPath;
    }

    public void setDeptPath(String deptPath) {
        this.deptPath = deptPath;
    }

    public String getRdDeptPath() {
        return rdDeptPath;
    }

    public void setRdDeptPath(String rdDeptPath) {
        this.rdDeptPath = rdDeptPath;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getWorkshopPath() {
        return workshopPath;
    }

    public void setWorkshopPath(String workshopPath) {
        this.workshopPath = workshopPath;
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
}
