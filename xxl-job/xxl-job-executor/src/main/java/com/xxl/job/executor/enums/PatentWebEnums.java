package com.xxl.job.executor.enums;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.enums
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-19 08:26
 * @Description: 专利类型
 */
public enum PatentWebEnums {
    PATENT_STAR(1, "专利之星"),
    SIPOP(2, "sipop"),
    BAI_TEN(3, "佰腾");

    private int type;
    private String title;

    PatentWebEnums(Integer type, String title) {
        this.type = type;
        this.title = title;
    }

    public static PatentWebEnums getByType(Integer type) {
        if (type == PATENT_STAR.type) {
            return PATENT_STAR;
        }
        if (type == SIPOP.type) {
            return SIPOP;
        }
        return BAI_TEN;
    }

}
