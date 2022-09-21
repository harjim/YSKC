package com.yskc.ms.enums;

import java.util.Arrays;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.enums
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-06 14:58
 * @Description: 工作报表枚举
 */
public enum DailyReportEnum {
    /**
     * 研发组织架构
     */
    RD_ORG(1, 1, "rdOrg", "研发组织架构"),
    /**
     * 研发花名册
     */
    EMPLOYEE(1, 2, "employee", "研发花名册"),
    /**
     * 研发设备
     */
    EQUIPMENT(1, 3, "equipment", "研发设备"),
    /**
     * 项目名称
     */
    RD_NAME(1, 4, "rdName", "项目名称"),
    /**
     * 立项报告
     */
    RD_REPORT(1, 5, "rdReport", "立项报告"),
    /**
     * 过程文件
     */
    RD_FILE(1, 6, "rdFile", "过程文件"),
    /**
     * 查新报告
     */
    NEW_REPORT(1, 8, "newReport", "查新报告"),
    /**
     * 创新体系
     */
    BUILD(1, 9, "build", "创新体系"),
    /**
     * 专利管理
     */
    PATENT(1, 7, "patent", "专利管理"),
    /**
     *提案管理
     */
    PROPOSAL(1, 11, "proposal", "提案管理"),
    /**
     * 成果管理
     */
    RESULT(1, 12, "result", "成果管理");

    DailyReportEnum(Integer type, Integer moduleId, String col, String title) {
        this.type = type;
        this.moduleId = moduleId;
        this.col = col;
        this.alias = "m" + moduleId;
        this.title = title;
    }

    private final static List<DailyReportEnum> LIST;

    static {
        LIST = Arrays.asList(values());
    }

    public static List<DailyReportEnum> getList() {
        return LIST;
    }

    private Integer type;
    private Integer moduleId;
    private String col;
    private String alias;
    private String title;

    public Integer getType() {
        return type;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public String getCol() {
        return col;
    }

    public String getAlias() {
        return alias;
    }

    public String getTitle() {
        return title;
    }
}
