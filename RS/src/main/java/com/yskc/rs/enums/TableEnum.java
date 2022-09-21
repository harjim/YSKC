package com.yskc.rs.enums;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: blueToWhite
 * @CreateTime: 2019-10-12 10:01
 * @Description: 表名枚举
 */
public enum TableEnum {
    /**
     *
     */
    D_EQUIPMENT("d_equipment"),
    I_EQUIPMENT("i_equipment"),
    D_SALARY("d_salary"),
    D_BONUS("d_bonus"),
    I_MEMBER("i_member"),
    P_PROJECT("p_project"),
    RD_EMPLOYEE("rdEmployee"),
    RD_EQUIPMENT("rdEquipment"),
    C_ATTENDANCE("c_attendance");

    private String name;

    TableEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
