package com.yskc.rs.models.hightech;

import java.io.Serializable;
import java.util.List;

/**
 * @DateTime: 2022/5/19 9:48
 * @Description:
 * @author: hsx
 */
public class HighTechAllotModel implements Serializable {

    private List<Integer> projectIds;

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

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
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
