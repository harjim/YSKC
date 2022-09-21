package com.yskc.ms.enums;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.enums
 * @Author: blueToWhite
 * @CreateTime: 2022-05-13 15:17
 * @Description: 过程文档数据枚举
 */
public enum DocFileDataEnum {

    /**
     * 默认
     */
    DEFAULT(0),
    MATERIAL(1),
    DESIGN_REPORT(2),
    ATTENDANCE_AGG(3),
    EQUIPMENT_AGG(4);
    private Integer type;

    DocFileDataEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static DocFileDataEnum getByType(Integer type) {
        if (null == type) {
            return DEFAULT;
        }
        for (DocFileDataEnum cur : values()) {
            if (type.equals(cur.type)) {
                return cur;
            }
        }
        return DEFAULT;
    }

}
