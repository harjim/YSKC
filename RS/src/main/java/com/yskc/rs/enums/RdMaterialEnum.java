package com.yskc.rs.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: blueToWhite
 * @CreateTime: 2020-08-06 16:21
 * @Description: 研发材料枚举
 */
public enum RdMaterialEnum {
    /**
     * 材料
     */
    MATERIAL(20000, "研发材料"),
    TRIAL_MATERIAL(20301,"中间试制"),
    REPAIR_MATERIAL(20601,"修理材料");

    private static final Map<String,Integer> materialTitleMap = new HashMap<>();
    static {
        for(RdMaterialEnum rdMaterialEnum : values()){
            materialTitleMap.put(rdMaterialEnum.getTitle(),rdMaterialEnum.getType());
        }
    }
    private Integer type;
    private String title;

    RdMaterialEnum(Integer type, String title) {
        this.type = type;
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public static Map<String, Integer> getMaterialTitleMap() {
        return materialTitleMap;
    }
}
