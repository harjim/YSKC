package com.yskc.rs.models.aggregation;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.aggregation
 * @Author: zhangdingfu
 * @CreateTime: 2020-08-04 09:13
 * @Description: 导出条件model
 */
public class ExportTermModel {
    private List<Integer> projectIds;
    private List<Date> months;
    private List<Integer> types;
    /**
     * d表类型
     */
    private Integer type;
    private String name;
    private List<DateRangeModel> range;
    /**
     * 0 单sheet导出
     * 1 按RD分Sheet
     * 2 按月分sheet
     */
    private Integer exportType;

    public List<DateRangeModel> getRange() {
        return DateRangeModel.getDateRange(months);
    }

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    public List<Date> getMonths() {
        return months;
    }

    public void setMonths(List<Date> months) {
        this.months = months;
    }

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }
}
