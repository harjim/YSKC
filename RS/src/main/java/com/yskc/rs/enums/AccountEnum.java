package com.yskc.rs.enums;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: blueToWhite
 * @CreateTime: 2019-09-20 15:33
 * @Description: 科目类型
 */
public enum AccountEnum {
    /**
     *
     */
    ASSET(0, "资产类"),
    LIABILITY(1, "负债类"),
    RIGHT(2, "权益类"),
    COST(3, "成本类"),
    LOSS(3, "损益类");

    private Integer type;

    private String typeName;


    AccountEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static int getType(String typeName) {
        typeName = typeName.trim();
        for (AccountEnum accountEnum : values()) {
            if (accountEnum.typeName.equals(typeName)) {
                return accountEnum.type;
            }
        }
        return ASSET.type;
    }
}
