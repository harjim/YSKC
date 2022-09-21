package com.yskc.rs.models.material;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.material
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-18 10:06
 * @Description: 设置领料人
 */
public class SetMaterialPickerModel {

    private List<Integer> ids;

    private String picker;

    private String biller;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getPicker() {
        return picker;
    }

    public void setPicker(String picker) {
        this.picker = picker;
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }
}
