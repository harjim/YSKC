package com.yskc.rs.models.projectyieldconfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectyieldconfig
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-13 10:08
 * @Description: 试制归集model
 */
public class TrialAggModel {

    private Date month;
    /**
     * 0：工时，1：产量
     */
    private Integer aggType;
    private List<Integer> rdTypes;
    private Boolean equipmentHour = false;
    private Boolean employeeHour = false;
    private Map<Integer,ProjectYieldConfigTotalModel> projectTotalMap;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getAggType() {
        return aggType;
    }

    public void setAggType(Integer aggType) {
        this.aggType = aggType;
    }

    public Boolean getEquipmentHour() {
        return equipmentHour;
    }

    public void setEquipmentHour(Boolean equipmentHour) {
        this.equipmentHour = equipmentHour;
    }

    public Boolean getEmployeeHour() {
        return employeeHour;
    }

    public void setEmployeeHour(Boolean employeeHour) {
        this.employeeHour = employeeHour;
    }

    public List<Integer> getRdTypes() {
        return rdTypes;
    }

    public void setRdTypes(List<Integer> rdTypes) {
        this.rdTypes = rdTypes;
    }

    public Map<Integer, ProjectYieldConfigTotalModel> getProjectTotalMap() {
        return projectTotalMap;
    }

    public void setProjectTotalMap(Map<Integer, ProjectYieldConfigTotalModel> projectTotalMap) {
        this.projectTotalMap = projectTotalMap;
    }
    public Boolean isHourAgg(){
        return  aggType == 0;
    }
}
