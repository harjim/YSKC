package com.yskc.docservice.enums;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.enums
 * @Author: blueToWhite
 * @CreateTime: 2022-03-16 13:56
 * @Description: 用户来源
 */
public enum UserSourceEnum {
    /**
     * rs
     */
    RS("rs"),
    MS("ms");

    UserSourceEnum(String userSource) {
        this.userSource = userSource;
    }

    private String userSource;

    public String getUserSource() {
        return userSource;
    }

}
