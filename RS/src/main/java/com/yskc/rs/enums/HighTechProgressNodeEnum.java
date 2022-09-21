package com.yskc.rs.enums;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: blueToWhite
 * @CreateTime: 2022-05-19 11:46
 * @Description: 高新进度枚举
 */
public enum HighTechProgressNodeEnum {

    /**
     * 默认
     */
    DEFAULT(0, "优赛准备中", 0, 1),
    FACTORY(10, "工厂审核中", 0, 0),
    AREA(20, "区域审核中", 1, 0);
    private int node;
    private String name;
    private int roleType;
    private int userSource;

    HighTechProgressNodeEnum(int node, String name, int roleType, int userSource) {
        this.node = node;
        this.name = name;
        this.roleType = roleType;
        this.userSource = userSource;
    }

    /**
     * 获取下一个节点
     *
     * @param node
     * @return
     */
    public static HighTechProgressNodeEnum getNext(Integer node) {
        if (null == node || node < 0) {
            return DEFAULT;
        } else if (node == DEFAULT.node) {
            return FACTORY;
        } else if (node == FACTORY.node) {
            return AREA;
        }
        return null;
    }

    /**
     * 获取当前节点
     * @param node
     * @return
     */
    public static HighTechProgressNodeEnum getById(Integer node){
        if (node == DEFAULT.node) {
            return DEFAULT;
        } else if (node == FACTORY.node) {
            return FACTORY;
        } else if (node == AREA.node) {
            return AREA;
        }
        return null;
    }

    public static boolean hasNode(Integer node, Integer roleType, Integer userSource) {
        for (HighTechProgressNodeEnum cur : values()) {
            if (cur.node == node && cur.roleType == roleType && cur.userSource == userSource) {
                return true;
            }
        }
        return false;
    }

    public int getNode() {
        return node;
    }

    public String getName() {
        return name;
    }

    public int getRoleType() {
        return roleType;
    }

    public int getUserSource() {
        return userSource;
    }
}
