package com.yskc.ms.enums;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: zhangdingfu
 * @CreateTime: 2022-08-31 18:32
 * @Description: 序列号
 */
public enum SnEnum {
    /**
     * 服务申请
     */
    SERVICE_APPLY("SA",1,"服务申请号"),
    CONTRACT("YS",2,"合同编号");
    private String prefix;

    private Integer type;

    private String label;

    SnEnum(String prefix, Integer type,String label) {
        this.prefix = prefix;
        this.type = type;
        this.label = label;
    }

    public String getPrefix() {
        return prefix;
    }

    public Integer getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }
}
