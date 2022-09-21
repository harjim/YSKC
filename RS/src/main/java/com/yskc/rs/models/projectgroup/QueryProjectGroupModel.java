package com.yskc.rs.models.projectgroup;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectgroup
 * @Author: zhangdingfu
 * @CreateTime: 2020-07-09 14:35
 * @Description: 项目组查询model
 */
public class QueryProjectGroupModel {

    private Integer year;

    private String gname;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }
}
