package com.yskc.ms.models.rdfunds;

import com.yskc.ms.models.params.PageParams;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2022/1/20 8:32
 * @Description:
 * @author: hsx
 */
public class QueryFundsModel extends PageParams {

    private Integer year;

    private Date month;

    /**
     * 公司名
     */
    private String companyName;

    /**
     * 部门Id
     */
    private Integer deptId;

    /**
     * 技术人员Id
     */
    private List<Integer> tIds;

    /**
     * 财务人员Id
     */
    private List<Integer> fIds;

    /**
     * 业务员Id
     */
    private Integer ownerId;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public List<Integer> gettIds() {
        return tIds;
    }

    public void settIds(List<Integer> tIds) {
        this.tIds = tIds;
    }

    public List<Integer> getfIds() {
        return fIds;
    }

    public void setfIds(List<Integer> fIds) {
        this.fIds = fIds;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}
