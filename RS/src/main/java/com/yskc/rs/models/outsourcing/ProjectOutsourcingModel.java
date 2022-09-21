package com.yskc.rs.models.outsourcing;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.outsourcing
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-08 15:10
 * @Description: 项目委外model
 */
public class ProjectOutsourcingModel {

    private Integer projectId;

    private String rdTitle;

    private String pname;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    private Map<Integer, BaseOutsourcingModel> fundMap;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal total;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Map<Integer, BaseOutsourcingModel> getFundMap() {
        return fundMap;
    }

    public void setFundMap(Map<Integer, BaseOutsourcingModel> fundMap) {
        this.fundMap = fundMap;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
