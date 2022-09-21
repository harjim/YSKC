package com.yskc.ms.enums;

/**
 * Created by hck
 * on 2020/11/2 11:23
 * description:项目时间线
 */
public enum TimelineEnum {

    BASIC_INFO(10,"基本信息"),
    RD_ARCHITECTURE(20,"研发架构"),
    RD_EMPLOYEE(30,"研发人员"),
    PROJECT_CNT(40,"项目立项"),
    PLAN_STAGE(100,"规划阶段"),
    RESEARCH_STAGE(200,"研究阶段"),
    DESIGN_DEVELOP(300,"设计开发"),
    DESIGN_VERIFICATION(400,"设计验证"),
    EXPERIMENTAL_STAGE(500,"实验阶段"),
    TEST_SAGE(600,"试验阶段"),
    TRIAL_PRODUCT_STAGE(700,"试制阶段"),
    ACCEPTANCE_STAGE(800,"验收阶段"),
    PROJECT_CONCLUSION(900,"项目收尾"),
    COMMON_STAGE(9999,"共用"),
    TOTAL_AMOUNT(50,"研发总额");

    private Integer itemType;
    private String description;

    public Integer getItemType() {
        return itemType;
    }

    public String getDescription() {
        return description;
    }
    TimelineEnum(Integer itemType,String description){
        this.itemType=itemType;
        this.description=description;
    }

    public static Integer getType(String description){
        description=description.trim();
        for (TimelineEnum timelineEnum:values()) {
            if(timelineEnum.description.equals(description)){
                return timelineEnum.itemType;
            }
        }
        return null;
    }
}
