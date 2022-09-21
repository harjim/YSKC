package com.yskc.rs.enums;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: blueToWhite
 * @CreateTime: 2020-03-19 15:16
 * @Description: 部门组织枚举
 */
public enum OrgEnum {
    /**
     * 部门
     */
    DEPT("dept"),
    RD_DEPT("rdDept"),
    WORKSHOP("workshop");

    private String typeName;

    OrgEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
