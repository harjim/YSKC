package com.xxl.job.executor.enums;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.enums
 * @Author: blueToWhite
 * @CreateTime: 2020-12-26 09:39
 * @Description: 流程模块类型枚举
 */
public enum FlowModuleTypeEnum {
    /**
     *
     */
    RD_TECH(1, "研发加计扣除-技术-基础"),
    RD_DOC_FILE(2, "研发加计扣除-技术-文档");
    private int type;
    private String label;

    public int getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    FlowModuleTypeEnum(int type, String label) {
        this.type = type;
        this.label = label;
    }

    public static boolean isRdTech(Integer type) {
        return type == RD_TECH.type;
    }

    public static boolean isRdDocFile(Integer type) {
        return type == RD_DOC_FILE.type;
    }
}
