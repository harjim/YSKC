package com.yskc.docservice.enums;

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
    DEFAULT(0, "", 0, 0, "default"),
    // 已确定
    SALARY(10000, "工资", 1, 100, "salary"),
    BONUS(10001, "奖金", 1, 100, "bonus"),
    // 已确定
    INSURANCE(10100, "五险一金", 1, 101, "insurance"),
    ENDOWMENT(10101, "养老", 1, 101, "endowment"),
    MEDICAL(10102, "医疗", 1, 101, "medical"),
    INJURY(10103, "工伤", 1, 101, "injury"),
    UNEMPLOYMENT(10104, "失业", 1, 101, "unemployment"),
    MATERNITY(10105, "生育", 1, 101, "maternity"),
    HOUSE(10106, "公积金", 1, 101, "house"),
    MATERIAL(20000, "材料", 2, 200, "material"),
    PAPER_MATERIAL(20001, "造纸材料", 2, 200, "paper_material"),
    IRON_MATERIAL(20002, "钢铁材料", 2, 200, "iron_material"),
    STIMULUS(20100, "动力", 2, 201, "stimulus"),
    STIMULUS_PROD(20101, "设备动力", 2, 201, "stimulusProd"),
    FUEL(20200, "燃料", 2, 202, "fuel"),
    IRON_STIMULUS(20102, "钢铁动力", 2, 201, "ironStimulus"),
    IRON_FUEL(20201, "钢铁燃料", 2, 202, "ironFuel"),
    TRIAL_PROD(20300, "试制", 2, 203, "trialProd"),
    TRIAL_MATERIAL(20301, "试制材料", 2, 203, "trialMaterial"),
    TRIAL_STIMULUS(20302, "试制动力", 2, 203, "trialStimulus"),
    PAPER_TRIAL(20303, "造纸试制", 2, 203, "paper_trial"),
    IRON_TRIAL(20304, "钢铁试制", 2, 203, "iron_trial"),
    TRIAL_TEST(20400, "试检", 2, 204, "trialTest"),
    INSPECTION(20500, "检测", 2, 205, "inspection"),
    REPAIR(20600, "修理", 2, 206, "repair"),
    REPAIR_MATERIAL(20601, "修理材料", 2, 206, "repairMaterial"),
    SAMPLE_MACHINE(20700, "样机", 2, 207, "sampleMachine"),
    PROD(30000, "设备折旧", 3, 300, "prod"),
    LAB(30100, "仪器折旧", 3, 301, "lab"),
    ARCHITECTURE(30200, "房屋建筑", 3, 302, "architecture"),
    SOFT_AMORTIZATION(40000, "软件摊销", 4, 400, "softAmortization"),
    ASSETS_AMORTIZATION(40001, "资产摊销", 4, 400, "softAmortization"),
    PATENT_AMORTIZATION(40100, "专利摊销", 4, 401, "patentAmortization"),
    OTHER_AMORTIZATION(40200, "其他摊销", 4, 402, "otherAmortization"),
    DESIGN(50000, "设计", 5, 500, "design"),
    TECH_PROCEDURE(50100, "规程制定", 5, 501, "techProcedure"),
    CLINICAL_TRIALS(50200, "临床实验", 5, 502, "clinicalTrials"),
    EXPLORE(50300, "勘探", 5, 503, "explore"),
    BOOK(60000, "资料", 6, 600, "book"),
    RD_PRODUCTION(60100, "研发成果", 6, 601, "rdProduction"),
    COPYRIGHT(60200, "知识产权", 6, 602, "copyright"),
    BENEFITS(60300, "福利", 6, 603, "benefits"),
    TRAVEL(60400, "差旅费", 6, 604, "travel"),
    OTHER(69900, "其他", 6, 699, "other");


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

    CostEnum(int type, String title, int root, int parent, String field) {
        this.type = type;
        this.title = title;
        this.parent = parent;
        this.field = field;
        this.root = root;
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
