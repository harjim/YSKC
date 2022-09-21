package com.yskc.rs.models.achievement;

import com.yskc.rs.models.params.PageParams;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.achievement
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-18 10:40
 * @Description: 查询成果model
 */
public class QueryAchievementModel extends PageParams {

    private Integer year;
    private Integer projectId;
    private String achievementName;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }
}
