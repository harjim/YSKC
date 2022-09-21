package com.yskc.rs.models.init.equipment;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.init.equipment
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-25 16:28
 * @Description: 项目设备初始化
 */
public class InitEquipmentMiniModel implements Serializable {

    private Integer projectId;

    private String ecode;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }
}
