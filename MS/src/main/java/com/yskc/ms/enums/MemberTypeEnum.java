package com.yskc.ms.enums;

public enum MemberTypeEnum {
    /**
     * 技术人员
     */
    Tech(1, "技术人员"),
    FINANCE(2, "财务人员"),
    Saler(3, "业务员"),
    Businesser(4, "谈单经理"),
    OTHER(5,"其他");

    MemberTypeEnum(Integer id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    private Integer id;
    private String typeName;

    public static boolean isTechOrFina(Integer curMtype) {
        return curMtype.equals(Tech.id) || curMtype.equals(FINANCE.id);
    }

    public Integer getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }
}
