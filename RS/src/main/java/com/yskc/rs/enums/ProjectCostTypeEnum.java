package com.yskc.rs.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-25 14:12
 * @Description: 项目支出类型枚举
 */
public enum ProjectCostTypeEnum {

    /**
     *
     */
    EQUIPMENT(1, "设备"),
    CONSTRUCTION(2, "建设费"),
    INIT_WORK_CAPITAL(3, "铺底流动资金");

    private int type;

    private String typeName;

    ProjectCostTypeEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * 获取枚举map 《type,Str》
     * @return
     */
    public static Map<String, Integer> getTypeMap() {
        ProjectCostTypeEnum[] values = values();
        Map<String, Integer> result = new HashMap<>(values.length);
        for (ProjectCostTypeEnum typeEnum : values) {
            result.put(typeEnum.typeName, typeEnum.type);
        }
        return result;
    }
}
