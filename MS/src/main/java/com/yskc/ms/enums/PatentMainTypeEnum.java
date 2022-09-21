package com.yskc.ms.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.enums
 * @Author: blueToWhite
 * @CreateTime: 2021-09-10 09:24
 * @Description: 专利类型枚举
 */
public enum PatentMainTypeEnum {
    /**
     * 发明专利
     */
    INVENTOR(1, "发明专利"),
    MODEL_UTILITY(2, "实用新型"),
    APPEARANCE_DESIGN(3, "外观设计");

    /**
     * 专利类型map
     */
    private final static Map<Integer,String> MAIN_TYPE_MAP = new HashMap<>();
    static {
        for(PatentMainTypeEnum cur : values()){
            MAIN_TYPE_MAP.put(cur.mainType,cur.mainName);
        }
    }

    public static String getMainName(Integer mainType){
        return MAIN_TYPE_MAP.get(mainType);
    }

    private Integer mainType;

    private String mainName;

    public int getMainType() {
        return mainType;
    }

    public String getMainName() {
        return mainName;
    }

    PatentMainTypeEnum(Integer mainType, String mainName) {
        this.mainName = mainName;
        this.mainType = mainType;
    }
}
