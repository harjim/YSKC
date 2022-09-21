package com.yskc.rs.enums;

import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.enums
 * @Author: blueToWhite
 * @CreateTime: 2019-08-09 14:14
 * @Description: 费用枚举
 */
public enum CostEnum {
    /**
     * 默认
     */
    DEFAULT(0, "", 0, 0, "default", 0),
    // 已确定
    SALARY(10000, "工资", 1, 100, "salary", 10000),
    BONUS(10001, "奖金", 1, 100, "bonus", 0),
    // 已确定
    INSURANCE(10100, "五险一金", 1, 101, "insurance", 10000),
    ENDOWMENT(10101, "养老", 1, 101, "endowment", 0),
    MEDICAL(10102, "医疗", 1, 101, "medical", 0),
    INJURY(10103, "工伤", 1, 101, "injury", 0),
    UNEMPLOYMENT(10104, "失业", 1, 101, "unemployment", 0),
    MATERNITY(10105, "生育", 1, 101, "maternity", 0),
    HOUSE(10106, "公积金", 1, 101, "house", 0),
    MATERIAL(20000, "材料", 2, 200, "material", 20000),
    PAPER_MATERIAL(20001, "造纸材料", 2, 200, "paper_material", 20000),
    IRON_MATERIAL(20002, "流程材料", 2, 200, "iron_material", 20000),
    STIMULUS(20100, "研发动力", 2, 201, "stimulus", 20100),
    STIMULUS_PROD(20101, "设备动力", 2, 201, "stimulusProd", 20100),
    IRON_STIMULUS(20102, "流程动力", 2, 201, "ironStimulus", 20100),
    FUEL(20200, "研发燃料", 2, 202, "fuel", 20200),
    IRON_FUEL(20201, "流程燃料", 2, 202, "ironFuel", 20200),
    TRIAL_PROD(20300, "试制", 2, 203, "trialProd", 20300),
    TRIAL_MATERIAL(20301, "试制材料", 2, 203, "trialMaterial", 20300),
    TRIAL_STIMULUS(20302, "试制动力", 2, 203, "trialStimulus", 20300),
    PAPER_TRIAL(20303, "造纸试制", 2, 203, "paper_trial", 20300),
    IRON_TRIAL(20304, "流程试制", 2, 203, "iron_trial", 20300),
    TRIAL_TEST(20400, "试检", 2, 204, "trialTest", 0),
    INSPECTION(20500, "检测", 2, 205, "inspection", 20500),
    REPAIR(20600, "修理", 2, 206, "repair", 20600),
    REPAIR_MATERIAL(20601, "修理材料", 2, 206, "repairMaterial", 20600),
    SAMPLE_MACHINE(20700, "样机", 2, 207, "sampleMachine", 20700),
    PROD(30000, "设备折旧", 3, 300, "prod", 30000),
    LAB(30100, "仪器折旧", 3, 301, "lab", 30000),
    ARCHITECTURE(30200, "房屋建筑", 3, 302, "architecture", 30000),
    SOFT_AMORTIZATION(40000, "软件摊销", 4, 400, "softAmortization", 40000),
    ASSETS_AMORTIZATION(40001, "资产摊销", 4, 400, "softAmortization", 40000),
    PATENT_AMORTIZATION(40100, "专利摊销", 4, 401, "patentAmortization", 40000),
    OTHER_AMORTIZATION(40200, "其他摊销", 4, 402, "otherAmortization", 40000),
    DESIGN(50000, "设计", 5, 500, "design", 50000),
    TECH_PROCEDURE(50100, "规程制定", 5, 501, "techProcedure", 0),
    CLINICAL_TRIALS(50200, "临床实验", 5, 502, "clinicalTrials", 0),
    EXPLORE(50300, "勘探", 5, 503, "explore", 0),
    BOOK(60000, "资料", 6, 600, "book", 69900),
    RD_PRODUCTION(60100, "研发成果", 6, 601, "rdProduction", 69900),
    COPYRIGHT(60200, "知识产权", 6, 602, "copyright", 69900),
    BENEFITS(60300, "福利", 6, 603, "benefits", 69900),
    TRAVEL(60400, "差旅费", 6, 604, "travel", 60400),
    OTHER(69900, "其他", 6, 699, "other", 69900);


    private final static Map<Integer, CostEnum> parentMap = new LinkedHashMap<>();
    private final static Map<Integer, CostEnum> rootMap = new LinkedHashMap<>();
    private final static Map<String, CostEnum> filedMap = new HashMap<>();
    private final static Map<String, CostEnum> titleMap = new HashMap<>();

    static {
        for (CostEnum cost : values()) {
            filedMap.put(cost.field, cost);
            titleMap.put(cost.title, cost);
            if (cost.type > 0) {
                if (cost.parent * 100 == cost.type) {
                    parentMap.put(cost.parent, cost);
                }
                if (cost.root * 10000 == cost.type) {
                    rootMap.put(cost.root, cost);
                }
            }

        }
    }

    public final static Map<String, CostEnum> getTitleMap() {
        return titleMap;
    }

    public final static Map<String, CostEnum> getFiledMap() {
        return filedMap;
    }

    public final static Map<Integer, CostEnum> getParentMap(Boolean all) {
        if (all) {
            Map<Integer, CostEnum> allMap = new LinkedHashMap<>();
            allMap.putAll(parentMap);
            allMap.putAll(rootMap);
            return allMap;
        }
        return parentMap;
    }

    private int type;

    private String title;

    private int parent;

    private String field;

    private int root;

    private int belong;

    /**
     * 获取所属的研发类型
     *
     * @param rdType
     * @return
     */
    public static List<Integer> getBelongRdTypes(Integer rdType) {
        List<Integer> result = new ArrayList<>();
        for (CostEnum cost : values()) {
            if (cost.belong == rdType) {
                result.add(cost.type);
            }
        }
        return result;
    }
    /**
     * 获取所属的研发类型(通过parentType)
     *
     * @param rdType
     * @return
     */
    public static List<Integer> getRdTypesByParent(Integer rdType) {
        // 获取实际 rdType 类型
        if (rdType >= 600 && rdType <= 603) {
            rdType = 699;
        }
        List<Integer> result = new ArrayList<>();
        for (CostEnum cost : values()) {
            if (cost.parent == rdType) {
                result.add(cost.type);
            } else if (rdType == 699) {
                // 当费用为其他时，获取 600-603、699 的费用
                if (cost.parent >= 600 && cost.parent <= 603) {
                    result.add(cost.type);
                }
            }
        }
        return result;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getParent() {
        return parent;
    }

    public String getField() {
        return field;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    CostEnum(int type, String title, int root, int parent, String field, int belong) {
        this.type = type;
        this.title = title;
        this.parent = parent;
        this.field = field;
        this.root = root;
        this.belong = belong;
    }

    /**
     * 通过type获取费用枚举
     *
     * @param type
     * @return
     */
    public static CostEnum getCostEnum(int type) {
        for (CostEnum costEnum : values()) {
            if (costEnum.type == type) {
                return costEnum;
            }
        }
        return DEFAULT;
    }

    /**
     * 通过类型名称获取费用枚举
     *
     * @param title
     * @return
     */
    public static CostEnum getCostEnum(String title) {
        if (title.equals("其他试制")) {
            return TRIAL_PROD;
        }
        for (CostEnum costEnum : values()) {
            if (costEnum.title.equals(title)) {
                return costEnum;
            }
        }
        return DEFAULT;
    }

    /**
     * 自定义费用类型集合
     *
     * @param costEnums
     * @return
     */
    public static List<Integer> getCostEnumList(CostEnum... costEnums) {
        List<Integer> rdTypeList = new ArrayList<>();
        for (CostEnum costEnum : costEnums) {
            rdTypeList.add(costEnum.getType());
        }
        return rdTypeList;
    }

    /**
     * 获取五险一金枚举
     *
     * @return
     */
    public static List<Integer> getInsuranceList() {
        List<Integer> rdTypeList = new ArrayList<>();
        rdTypeList.add(ENDOWMENT.getType());
        rdTypeList.add(HOUSE.getType());
        rdTypeList.add(INJURY.getType());
        rdTypeList.add(MATERNITY.getType());
        rdTypeList.add(MEDICAL.getType());
        rdTypeList.add(UNEMPLOYMENT.getType());
        return rdTypeList;
    }

    public static CostEnum getByField(String field) {
        return filedMap.getOrDefault(field, DEFAULT);
    }
}
