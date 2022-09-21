package com.yskc.ms.enums;

/**
 * @program: ms
 * @description: 人员角色枚举
 * @author: wjy
 * @create: 2022-08-11 17:46
 **/
public enum UserRoleEnum {
    /**
     * 财务经理
     */
    FINA_MANAGER("财务经理"),
    /**
     * 财务总监
     */
    FINA_DIRECTOR("财务总监"),
    /**
     * 技术经理
     */
    TECH_MANAGER("技术经理"),
    /**
     * 技术总监
     */
    TECH_DIRECTOR("技术总监");

    private String roleName;

    UserRoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

}
