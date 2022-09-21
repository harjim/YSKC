package com.yskc.docservice.models.rdequipment;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rdequipment
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-02 10:41
 * @Description: 添加研发设备
 */
public class AddRdEquipmentModel implements Serializable {

    private List<String> ecodes;

    private Integer year;

    public List<String> getEcodes() {
        return ecodes;
    }

    public void setEcodes(List<String> ecodes) {
        this.ecodes = ecodes;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
