package com.yskc.rs.models.init.member;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.init.member
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-24 16:03
 * @Description: 批量操作项目人员
 */
public class BatchInitMemberModel implements Serializable {
    private Integer projectId;
    private List<String> enumbers;

    private Integer year;

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

    public List<String> getEnumbers() {
        return enumbers;
    }

    public void setEnumbers(List<String> enumbers) {
        this.enumbers = enumbers;
    }
}
