package com.yskc.rs.enums;

/**
 * 学历枚举类
 *
 * @author wangxing
 */
public enum EduLevelEnum {
    /**
     * 无
     */
    NOTHING(0, "无"),
    /**
     * 初中
     */
    SENIOR_MIDDLE_SCHOOL(7, "初中"),
    /**
     * 高中
     */
    SENIOR_HIGH_SCHOOL(1, "高中"),
    /**
     * 中专
     */
    TECHNICAL_SECONDARY_SCHOOL(2, "中专"),
    /**
     * 大专
     */
    COLLEGE(3, "大专"),
    /**
     * 本科
     */
    UNDERGRADUATE_COURSE(4, "本科"),
    /**
     * 硕士
     */
    MASTER(5, "硕士"),
    /**
     * 博士
     */
    DOCTOR(6, "博士");

    private int value;
    private String edu;

    EduLevelEnum(int value, String edu) {
        this.value = value;
        this.edu = edu;
    }

    public static EduLevelEnum getEduLevelEnum(int value) {
        EduLevelEnum[] eduLevelEnums = EduLevelEnum.values();
        for (EduLevelEnum eduLevelEnum : eduLevelEnums) {
            if (eduLevelEnum.getValue() == value) {
                return eduLevelEnum;
            }
        }
        return NOTHING;
    }

    public static EduLevelEnum getEduLevelEnum(String edu) {
        EduLevelEnum[] eduLevelEnums = EduLevelEnum.values();
        for (EduLevelEnum eduLevelEnum : eduLevelEnums) {
            if (eduLevelEnum.getEdu().equals(edu)) {
                return eduLevelEnum;
            }
        }
        return NOTHING;
    }

    public int getValue() {
        return value;
    }

    public String getEdu() {
        return edu;
    }
}
