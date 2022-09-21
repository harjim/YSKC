package com.yskc.rs.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: blueToWhite
 * @CreateTime: 2022-05-19 11:52
 * @Description: 交付类型枚举
 */
public enum DeliverTypeEnum {
    /**
     * 交付类型：0：立项材料，1：月度轨迹材料，2：年度轨迹材料，3：验收材料，4：财务归集
     */
    TECH_PROJECT(0, 0, "立项材料"),
    TECH_MONTH(0, 1, "月度轨迹材料"),
    TECH_YEAR(0, 2, "年度轨迹材料"),
    TECH_CHECK(0, 3, "验收材料"),
    FINA_MONTH(1, 4, "月度财务归集"),
    FINA_YEAR(1, 5, "年度财务归集");
    private Integer type;
    private Integer deliverType;
    private String name;

    DeliverTypeEnum(Integer type, Integer deliverType, String name) {
        this.type = type;
        this.deliverType = deliverType;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public Integer getDeliverType() {
        return deliverType;
    }

    public String getName() {
        return name;
    }

    private final static Map<Integer, DeliverTypeEnum> DELIVER_MAP = new HashMap<>();

    static {
        for (DeliverTypeEnum deliverTypeEnum : values()) {
            DELIVER_MAP.putIfAbsent(deliverTypeEnum.deliverType, deliverTypeEnum);
        }
    }

    /**
     * 获取交付枚举
     *
     * @param deliverType
     * @return
     */
    public static DeliverTypeEnum getByDeliverType(Integer deliverType) {
        return DELIVER_MAP.get(deliverType);
    }
}
