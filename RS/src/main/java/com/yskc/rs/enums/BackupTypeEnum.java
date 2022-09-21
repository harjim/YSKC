package com.yskc.rs.enums;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: blueToWhite
 * @CreateTime: 2021-03-22 16:27
 * @Description: 备份类型枚举
 */
public enum BackupTypeEnum {
    /**
     * 删除
     */
    DELETE(0),
    EDIT(1);
    private Integer type;

    BackupTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
