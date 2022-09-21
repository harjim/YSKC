package com.xxl.job.executor.enums;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.enums
 * @Author: blueToWhite
 * @CreateTime: 2020-05-13 18:55
 * @Description: rs研发阶段表枚举
 */
public enum RsStageTableEnum {
    /**
     * 默认
     */
    DEFAULT("", 0, false, false, ""),
    // STAGE_ONE("", 10000, false, false,"基本信息"),
    EMPLOYEE("employee", 10001, false, false, "花名册"),
    EQUIPMENT("equipment", 10002, false, false, "设备列表"),
    EMPLOYEE_OPENID("employeeOpenid",10003,false,false,"小程序绑定"),
    // STAGE_TWO("p_report", 20000, true, false,"规划项目"),
    P_REPORT("p_report", 20000, true, false, "规划项目"),
    // STAGE_THREE("", 30000, true, false, "立项准备"),
    RD_DEPT("rdDept", 30001, true, true, "研发架构"),
    RD_EQUIPMENT("rdEquipment", 30002, true, true, "研发设备"),
    RD_EMPLOYEE("rdEmployee", 30003, true, true, "研发人员"),
    // STAGE_FOUR("", 40000, true, true, "技术立项"),
    P_PROJECT("p_project", 40001, true, true, "项目管理"),
    I_MEMBER("i_member", 40002, true, true, "项目成员"),
    I_EQUIPMENT("i_equipment", 40003, true, true, "项目设备"),
    // STAGE_FIVE("p_budget", 50000, true, false, "预算"); 预算先查询项目表
    P_BUDGET("p_year_info", 50000, true, true, "预算"),
    P_SUMMARY("p_summary", 60000, true, false, "归集数"),
    P_DOC_FILE_DATA("p_docFile_data", 70000, true, false, "过程文档数"),
    C_YEAR_COST("c_year_cost",80000,true,true,"年度成本管理"),
    C_REVENUE("c_revenue",80001,true,true,"年度营收管理"),
    PROPOSAL_MANAGEMENT("proposal_management",80002,true,true,"提案管理"),
    p_achievement("p_achievement",80003,true,true,"成果管理"),
    PATENT_PLAN("patent_plan",90000,true,true,"专利申请");



    private String tableName;
    private Integer stage;
    private boolean hasYear;
    private boolean selfYear;
    private String name;
    private static final List<RsStageTableEnum> TABLES = new ArrayList<>(
            Arrays.stream(values()).filter(a -> !StringUtils.isEmpty(a.tableName)).collect(Collectors.toList())
    );

    RsStageTableEnum(String tableName, Integer stage, boolean hasYear, boolean selfYear, String name) {
        this.tableName = tableName;
        this.stage = stage;
        this.hasYear = hasYear;
        this.selfYear = selfYear;
        this.name = name;
    }

    public static List<RsStageTableEnum> getTables() {
        return TABLES;
    }

    public String getTableName() {
        return tableName;
    }

    public String getName() {
        return name;
    }

    public Integer getStage() {
        return stage;
    }

    public boolean isHasYear() {
        return hasYear;
    }

    public boolean isSelfYear() {
        return selfYear;
    }
}
