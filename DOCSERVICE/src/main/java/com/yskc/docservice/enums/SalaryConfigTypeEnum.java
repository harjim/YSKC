package com.yskc.docservice.enums;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: blueToWhite
 * @CreateTime: 2020-08-19 15:17
 * @Description: 薪资配置类型
 */
public enum SalaryConfigTypeEnum {
    /**
     * 薪资
     */
    SALARY(0, "salary"),
    /**
     * 五险一金
     */
    INSURANCE(1, "insurance");


    private int type;
    private String title;

    SalaryConfigTypeEnum(int type, String title) {
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
