package com.yskc.rs.models.project;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/8/18 10:15
 * description:
 */
public class DelProjectModel implements Serializable {

    private List<Integer> projectIds;

    private Integer currentYear;

    public List<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }
}
