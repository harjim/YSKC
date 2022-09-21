package com.yskc.ms.enums;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.enums
 * @Description: 费用枚举
 */
public enum CostEnum {
    /**
     * 默认
     */
    DEFAULT(0, "", 0, "default"),
    // 已确定
    /* 人员费用 employee */
    SALARY(10000, "工资", 100, "salary"),
    BONUS(10001, "奖金", 100, "bonus"),
    // 已确定
    INSURANCE(10100, "五险一金", 101, "insurance"),
    ENDOWMENT(10101, "养老", 101, "endowment"),
    MEDICAL(10102, "医疗", 101, "medical"),
    INJURY(10103, "工伤", 101, "injury"),
    UNEMPLOYMENT(10104, "失业", 101, "unemployment"),
    MATERNITY(10105, "生育", 101, "maternity"),
    HOUSE(10106, "公积金", 101, "house"),

    /* 研发材料 material */
    MATERIAL(20000, "材料", 200, "material"),
    PAPER_MATERIAL(20001, "造纸材料", 200, "paper_material"),
    IRON_MATERIAL(20002, "流程材料", 200, "iron_material"),

    /* 动力损耗 energy */
    STIMULUS(20100, "研发动力", 201, "stimulus"),
    STIMULUS_PROD(20101, "设备动力", 201, "stimulusProd"),
    IRON_STIMULUS(20102, "流程动力", 201, "ironStimulus"),

    /* 燃料损耗 energy */
    FUEL(20200, "研发燃料", 202, "fuel"),
    IRON_FUEL(20201, "流程燃料", 202, "ironFuel"),

    /* 中间试制 */
    TRIAL_PROD(20300, "试制", 203, "trialProd"),
    TRIAL_MATERIAL(20301, "试制材料", 203, "trialMaterial"),
    TRIAL_STIMULUS(20302, "试制动力", 203, "trialStimulus"),
    PAPER_TRIAL(20303, "造纸试制", 203, "paper_trial"),
    IRON_TRIAL(20304, "流程试制", 203, "iron_trial"),

    TRIAL_TEST(20400, "试检", 204, "trialTest"),

    /* 检测费用 */
    INSPECTION(20500, "检测", 205, "inspection"),

    /* 修理费用 */
    REPAIR(20600, "修理", 206, "repair"),
    REPAIR_MATERIAL(20601, "修理材料", 206, "repairMaterial"),

    /* 样机费用 */
    SAMPLE_MACHINE(20700, "样机", 207, "sampleMachine"),

    /* 设备折旧 */
    PROD(30000, "设备折旧", 300, "prod"),
    LAB(30100, "仪器折旧", 301, "lab"),
    ARCHITECTURE(30200, "房屋建筑", 302, "architecture"),

    /* 摊销费用 */
    SOFT_AMORTIZATION(40000, "软件摊销", 400, "softAmortization"),
    ASSETS_AMORTIZATION(40001, "资产摊销", 400, "softAmortization"),
    PATENT_AMORTIZATION(40100, "专利摊销", 401, "patentAmortization"),
    OTHER_AMORTIZATION(40200, "其他摊销", 402, "otherAmortization"),

    /* 设计费用 */
    DESIGN(50000, "设计", 500, "design"),

    TECH_PROCEDURE(50100, "规程制定", 501, "techProcedure"),
    CLINICAL_TRIALS(50200, "临床实验", 502, "clinicalTrials"),
    EXPLORE(50300, "勘探", 503, "explore"),

    /* 其他费用 */
    BOOK(60000, "资料", 600, "book"),
    RD_PRODUCTION(60100, "研发成果", 601, "rdProduction"),
    COPYRIGHT(60200, "知识产权", 602, "copyright"),
    BENEFITS(60300, "福利", 603, "benefits"),
    OTHER(69900, "其他", 699, "other"),

    /* 差旅费用 */
    TRAVEL(60400, "差旅费", 604, "travel");

    private static Map<String, CostEnum> filedMap = new HashMap<>();

    static {
        for (CostEnum cost : values()) {
            filedMap.put(cost.field, cost);
        }
    }

    public static Map<String, CostEnum> getFiledMap() {
        return filedMap;
    }

//    private static Integer[] auditCostList = {10000, 20000, 20100, 20200, 20300, 20500, 20600, 20700, 30000, 40000, 50000, 60400, 69900};

    private int type;

    private String title;

    private int parent;

    private String field;

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

/*    public static List getAuditCostList() {
        List<String> list = new ArrayList<>();
        for (Integer cost : auditCostList) {
            String title = "c" + cost;
            list.add(title);
        }
        return list;
    }*/

    CostEnum(int type, String title, int parent, String field) {
        this.type = type;
        this.title = title;
        this.parent = parent;
        this.field = field;
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
    public static CostEnum getCostEnum(String title, Integer remainder) {
        if (StringUtils.isEmpty(title)) {
            return DEFAULT;
        }
        for (CostEnum costEnum : values()) {
            if (costEnum.title.equals(title) && costEnum.getType() / 10000 == remainder) {
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

    public static CostEnum getEquipmentType(String title) {
        if (StringUtils.isEmpty(title)) {
            return PROD;
        }
        if (title.equals(LAB.title)) {
            return LAB;
        }
        return PROD;
    }

    public static CostEnum getByField(String field) {
        return filedMap.getOrDefault(field, DEFAULT);
    }

/*    public static List<CostEnum> getAuditCostEnum() {
        List<CostEnum> costEnumList = new ArrayList<>();
        for(CostEnum costEnum: values()) {
            if (Arrays.binarySearch(auditCostList, costEnum.getType()) >= 0) {
                costEnumList.add(costEnum);
            }
        }
        return costEnumList;
    }*/
}
