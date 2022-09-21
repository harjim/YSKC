package com.yskc.ms.models.rs.achievement;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs.achievement
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-19 16:52
 * @Description: 查询成果model
 */
public class QueryAchievementModel extends PageParams {

    private Integer companyId;

    private Integer year;

    private String achievementName;

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
