package com.yskc.rs.models.projectenergy;

import com.yskc.rs.models.UsedModel;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectenergy
 * @Author: zhangdingfu
 * @CreateTime: 2020-08-25 09:09
 * @Description: 能源使用model
 */
public class EnergyUsedModel extends UsedModel {

    private Integer etype;

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }
}
