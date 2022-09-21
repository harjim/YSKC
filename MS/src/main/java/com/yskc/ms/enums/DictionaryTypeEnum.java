package com.yskc.ms.enums;

public enum DictionaryTypeEnum {

    AREA_CODE(1,"区域代码"),
    HIGE_NEW_TERRITORY(2,"高新领域"),
    BUDGET_SOURCE(3,"预算来源"),
    BUDGET_EXPENDITURE(4,"预算支出"),
    CURRENCY(5,"币种"),
    PROJECT_PHASE(6,"项目阶段"),
    DECLARE_PHASE(7,"申报阶段"),
    DECLARE_PROGRESS(8,"申报进度"),
    DECLARE_STATUS(9,"申报状态"),
    DECLARE_PROJECT_TYPE(10,"申报项目类型"),
    PROJECT_DECLARE_RANK(11,"项目申报级别"),
    PROFESSIONAL_TITLE(12,"职称"),
    SUBJECT(13,"学科"),
    UNIT_OF_FINANCIAL(14,"单位财务情况"),
    INDUSTRY(15,"行业"),
    FILING_PROPERTY_TYPE(16,"备案资产类型");

    private int code;

    private String name;

    DictionaryTypeEnum(int code,String name){
        this.code = code;
        this.name = name;
    }

    public static String getDictionaryTypeName(int code) {
        for (DictionaryTypeEnum m : DictionaryTypeEnum.values()) {
            if (m.getCode() == code) {
                return m.getName();
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
