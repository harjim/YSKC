package com.yskc.ms.enums;

public enum ProjectOperationEnum {
    /**
     * 申请
     */
    APPLICATION("APPLICATION",0,"申请"),
    /**
     * 修改申请
     */
    EDIT("EDIT",1,"修改申请"),

    /**
     * 业务审核不通过
     */
    BUSINESS_FAIL("BUSINESS_FAIL",2,"业务审核不通过"),

    /**
     * 业务审核通过
     */
    BUSINESS_PASS("BUSINESS_PASS",3,"业务审核通过"),

    /**
     * 技术审核不通过
     */
    TECH_FAIL("TECH_FAIL",4,"技术审核不通过"),

    /**
     * 技术审核通过
     */
    TECH_PASS("TECH_PASS",5,"技术审核通过"),

    /**
     * 财务审核不通过
     */
    FINANCE_FAIL("FINANCE_FAIL",6,"财务审核不通过"),

    /**
     * 财务审核通过
     */
    FINANCE_PASS("FINANCE_PASS",7,"财务审核通过"),

    /**
     * 分配技术人员
     */
    ALLOCATION_TECH("ALLOCATION_TECH",8,"分配技术人员"),

    /**
     * 分配财务人员
     */
    ALLOCATION_FINANCE("ALLOCATION_FINANCE",9,"分配财务人员"),

    /**
     * 技术进行中
     */
    TECH_IN_PROGRESS("TECH_IN_PROGRESS",10,"技术进行中"),

    /**
     * 财务进行中
     */
    FINANCE_IN_PROGRESS("FINANCE_IN_PROGRESS",11,"财务进行中"),
    /**
     * 技术已完成
     */
    TECH_COMPLETED("TECH_COMPLETED",12,"技术已完成"),

    /**
     * 财务已完成
     */
    FINANCE_COMPLETED("FINANCE_COMPLETED",13,"财务已完成");

    private String type;

    private Integer value;

    private String content;

    ProjectOperationEnum(String type, Integer value, String content) {
        this.type = type;
        this.value = value;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }

    public String getContent() { return content; }
}
