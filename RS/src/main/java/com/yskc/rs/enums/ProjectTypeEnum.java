package com.yskc.rs.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @DateTime: 2021/9/2 17:33
 * @Description:项目研发形式
 */
public enum ProjectTypeEnum {
    /**
     *
     */
    INDEPENDENT(10, "自主完成"),
    INSTITUTION(21, "与境内研究机构合作"),
    HIGHER_COLLEGE(22, "与境内高等学校合作"),
    OTHER_ENTERPRISE(23, "与境内其他企业或单位合作"),
    OVERSEAS(24, "与境外机构合作"),
    ENTRUST(30, "委托其他企业或单位"),
    OTHER(40, "其他形式");

    private int type;
    private String typeName;
    public static Map<Integer,String> valueType = new HashMap<>();

    static{
        for (ProjectTypeEnum projectTypeEnum : values()) {
            valueType.put(projectTypeEnum.type,projectTypeEnum.typeName);
        }
    }
    ProjectTypeEnum(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }


    public static ProjectTypeEnum getProjectTypeEnum(Integer formula) {
        if (formula == null) {
            return INDEPENDENT;
        }
        for (ProjectTypeEnum cur : values()) {
            if (formula == cur.getType()) {
                return cur;
            }
        }
        return INDEPENDENT;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public static String getEmployeeType(Integer value) {
        return valueType.get(value);
    }
}
