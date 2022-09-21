package com.yskc.rs.models.summary;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 研发费用model
 *
 * @author zhangdingfu
 */
public class DataReportModel {

    private Integer id;

    private String rdNumber;

    private String month;

    private String pname;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Map<String, BigDecimal> totalFunds = new LinkedHashMap<>();

    private List<DataReportModel> children = new ArrayList<>();

    public DataReportModel() {
    }

    public DataReportModel(String month) {
        this.month = month;
    }

    public DataReportModel(Integer id, String rdNumber, String month, String pname) {
        this.id = id;
        this.rdNumber = rdNumber;
        this.month = month;
        this.pname = pname;
    }

    public Map<String, BigDecimal> getTotalFunds() {
        return totalFunds;
    }

    public void setTotalFunds(Map<String, BigDecimal> totalFunds) {
        this.totalFunds = totalFunds;
    }

    public List<DataReportModel> getChildren() {
        return children;
    }

    public void setChildren(List<DataReportModel> children) {
        this.children = children;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getRdNumber() {
        return rdNumber;
    }

    public void setRdNumber(String rdNumber) {
        this.rdNumber = rdNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
