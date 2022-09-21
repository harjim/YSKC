package com.yskc.docservice.enums;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: blueToWhite
 * @CreateTime: 2020-07-15 11:25
 * @Description: 设备枚举
 */
public enum EquipmentEnum {
    /**
     * 设备
     */
    DEFAULT(0, ""),
    PROD(30000, "设备"),
    LAB(30100, "仪器"),
    ARCHITECTURE(30200, "房屋建筑"),
    ASSETS(40001, "软件摊销");

    EquipmentEnum(int type, String title) {
        this.type = type;
        this.title = title;
    }

    private int type;
    private String title;

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public static EquipmentEnum getOrDefault(String title) {
        if (LAB.title.equals(title)) {
            return LAB;
        }
        if(ASSETS.title.equals(title)){
            return  ASSETS;
        }
        if (ARCHITECTURE.title.equals(title)) {
            return ARCHITECTURE;
        }
        return PROD;
    }

    public static EquipmentEnum getByTitle(String title) {
        if (LAB.title.equals(title)) {
            return LAB;
        } else if (PROD.title.equals(title)) {
            return PROD;
        }else if(ASSETS.title.equals(title)){
            return  ASSETS;
        }else if(ARCHITECTURE.title.equals(title)) {
            return ARCHITECTURE;
        }
        return DEFAULT;
    }

    public static EquipmentEnum getByType(int type) {
        if (LAB.type == type) {
            return LAB;
        } else if (PROD.type == type) {
            return PROD;
        }else if(ASSETS.type == type){
            return ASSETS;
        }else if(ARCHITECTURE.type == type){
            return ARCHITECTURE;
        }
        return DEFAULT;
    }


}
