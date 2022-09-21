package com.yskc.rs.models.highscore;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.highscore
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-04 15:39
 * @Description: 机构建设事项文件类型及事项子类型model
 */
public class ChildBuildTypeModel {

    private Integer buildType;

    private Integer childType;

    public Integer getBuildType() {
        return buildType;
    }

    public void setBuildType(Integer buildType) {
        this.buildType = buildType;
    }

    public Integer getChildType() {
        return childType;
    }

    public void setChildType(Integer childType) {
        this.childType = childType;
    }
}
