package com.yskc.rs.models.init.equipment;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.init.member
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-24 16:03
 * @Description: 批量操作项目设备
 */
public class BatchInitEquipmentModel implements Serializable {

    private Integer projectId;
    private List<String> ecodes;
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

    public List<String> getEcodes() {
        return ecodes;
    }

    public void setEcodes(List<String> ecodes) {
        this.ecodes = ecodes;
    }
}
