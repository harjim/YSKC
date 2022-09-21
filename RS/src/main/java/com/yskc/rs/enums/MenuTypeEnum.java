package com.yskc.rs.enums;

/**
 * 功能类型枚举
 * 对应app_menu表中的type
 *
 * @author huronghua
 */
public enum MenuTypeEnum {
    /**
     * 目录
     */
    CATALOGUE(0),
    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2),
    /**
     * 模块
     */
    MODULE(3);
    private int value;

    MenuTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
