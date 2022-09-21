package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

import java.io.Serializable;

/**
 * @DateTime: 2022/5/19 9:59
 * @Description:
 * @author: hsx
 */
@TableName
public class ProjectAuditOpt extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4314789895443586171L;

    @TableId
    private Integer id;

    private Integer companyId;

    private Integer projectId;

    private Integer year;

    /**
     * 工厂技术
     */
    private String ftyTech;

    /**
     * 工厂财务
     */
    private String ftyFina;

    /**
     * 运行总监
     */
    private String areaTech;

    /**
     * 财务经理
     */
    private String areaFina;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFtyTech() {
        return ftyTech;
    }

    public void setFtyTech(String ftyTech) {
        this.ftyTech = ftyTech;
    }

    public String getFtyFina() {
        return ftyFina;
    }

    public void setFtyFina(String ftyFina) {
        this.ftyFina = ftyFina;
    }

    public String getAreaTech() {
        return areaTech;
    }

    public void setAreaTech(String areaTech) {
        this.areaTech = areaTech;
    }

    public String getAreaFina() {
        return areaFina;
    }

    public void setAreaFina(String areaFina) {
        this.areaFina = areaFina;
    }
}
