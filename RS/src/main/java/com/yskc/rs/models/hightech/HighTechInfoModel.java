package com.yskc.rs.models.hightech;

import com.yskc.rs.models.project.ProjectInfoModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 11:10
 * @Description:高品详情model
 */
public class HighTechInfoModel implements Serializable {

    private Integer id;//高品id

    private String hname;

    private String hcode;

    private String tecIndustry;

    private BigDecimal totalAmount;

    private HighTechDetailModel detailModel;

    private Map<Object, BigDecimal> incomeMap;

    private List<ProjectInfoModel> projects;

    private Map<Integer,List<HighTechFileModel>> fileMap;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public String getHcode() {
        return hcode;
    }

    public void setHcode(String hcode) {
        this.hcode = hcode;
    }

    public String getTecIndustry() {
        return tecIndustry;
    }

    public void setTecIndustry(String tecIndustry) {
        this.tecIndustry = tecIndustry;
    }

    public HighTechDetailModel getDetailModel() {
        return detailModel;
    }

    public void setDetailModel(HighTechDetailModel detailModel) {
        this.detailModel = detailModel;
    }

    public Map<Object, BigDecimal> getIncomeMap() {
        return incomeMap;
    }

    public void setIncomeMap(Map<Object, BigDecimal> incomeMap) {
        this.incomeMap = incomeMap;
    }

    public List<ProjectInfoModel> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectInfoModel> projects) {
        this.projects = projects;
    }

    public Map<Integer, List<HighTechFileModel>> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<Integer, List<HighTechFileModel>> fileMap) {
        this.fileMap = fileMap;
    }
}
