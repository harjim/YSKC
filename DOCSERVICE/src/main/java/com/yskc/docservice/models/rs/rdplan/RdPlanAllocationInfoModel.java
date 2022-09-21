package com.yskc.docservice.models.rs.rdplan;

import com.yskc.docservice.entity.rs.project.ProjectRdAggConfig;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: doc-service
 * @BelongsPackage: com.yskc.docservice.models.rs.rdplan
 * @Author: zhangdingfu
 * @CreateTime: 2022-04-13 10:17
 * @Description: 分配保存model
 */
public class RdPlanAllocationInfoModel {
    private List<ProjectRdAggConfig> configs;
    private Set<String> enumbers;
    private Set<Date> months;
    private Integer companyId;

    public static RdPlanAllocationInfoModel build(List<ProjectRdAggConfig> configs, Set<String> enumbers,
                                                  Set<Date> months, Integer companyId) {
        RdPlanAllocationInfoModel item = new RdPlanAllocationInfoModel();
        item.configs = configs;
        item.enumbers =enumbers;
        item.months = months;
        item.companyId = companyId;
        return item;
    }

    public List<ProjectRdAggConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ProjectRdAggConfig> configs) {
        this.configs = configs;
    }


    public Set<String> getEnumbers() {
        return enumbers;
    }

    public void setEnumbers(Set<String> enumbers) {
        this.enumbers = enumbers;
    }

    public Set<Date> getMonths() {
        return months;
    }

    public void setMonths(Set<Date> months) {
        this.months = months;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
