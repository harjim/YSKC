package com.yskc.rs.enums;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: blueToWhite
 * @CreateTime: 2020-08-19 15:17
 * @Description: 薪资配置类型
 */
public enum FieldConfigTypeEnum {
    /**
     * 薪资
     */
    SALARY(0, "salary"),
    /**
     * 五险一金
     */
    INSURANCE(1, "insurance"),
    /**
     * 人员
     */
    EMPLOYEE(2, "employee"),
    /**
     * 设备
     */
    EQUIPMENT(3, "equipment");


    private int type;
    private String title;

    FieldConfigTypeEnum(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public static String getByType(int type) {
        if (SALARY.type == type) {
            return SALARY.title;
        }
        if (INSURANCE.type == type) {
            return INSURANCE.title;
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}
