package com.yskc.rs.models.aggregation;

import com.yskc.rs.enums.CostEnum;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.aggregation
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-15 16:04
 * @Description: 导出类型model
 */
public class ExportSheetModel {

    private Integer sheetIndex;

    private String sheetName;

    private Integer type;

    public final static Map<Integer, ExportSheetModel> SHEET_MAP = new LinkedHashMap<>();

    public ExportSheetModel() {
    }


    public ExportSheetModel(Integer sheetIndex, String sheetName) {
        this.sheetIndex = sheetIndex;
        this.sheetName = sheetName;
    }

    public ExportSheetModel(Integer sheetIndex, String sheetName, Integer type) {
        this.sheetIndex = sheetIndex;
        this.sheetName = sheetName;
        this.type = type;
    }

    public Integer getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public final static Set<Integer> getPages() {
        return SHEET_MAP.values().stream().map(a -> a.getSheetIndex()).collect(Collectors.toSet());
    }

    static {
        SHEET_MAP.put(CostEnum.SALARY.getType(), new ExportSheetModel(1, "人员费用"));
        SHEET_MAP.put(CostEnum.INSURANCE.getType(), new ExportSheetModel(1, "人员费用"));
        SHEET_MAP.put(CostEnum.PROD.getType(), new ExportSheetModel(2, "设备折旧"));
        SHEET_MAP.put(CostEnum.LAB.getType(), new ExportSheetModel(2, "设备折旧"));
        SHEET_MAP.put(CostEnum.MATERIAL.getType(), new ExportSheetModel(3, "研发材料-研发材料"));
        SHEET_MAP.put(CostEnum.PAPER_MATERIAL.getType(), new ExportSheetModel(4, "研发材料-造纸材料"));
        SHEET_MAP.put(CostEnum.IRON_MATERIAL.getType(), new ExportSheetModel(5, "研发材料-流程型"));
        SHEET_MAP.put(CostEnum.TRIAL_MATERIAL.getType(), new ExportSheetModel(6, "中间试制-试制材料"));
        SHEET_MAP.put(CostEnum.PAPER_TRIAL.getType(), new ExportSheetModel(7, "中间试制-造纸试制"));
        SHEET_MAP.put(CostEnum.IRON_TRIAL.getType(), new ExportSheetModel(8, "中间试制-流程型"));
        SHEET_MAP.put(CostEnum.TRIAL_STIMULUS.getType(), new ExportSheetModel(9, "中间试制-试制动力", 20100));
        SHEET_MAP.put(CostEnum.TRIAL_PROD.getType(), new ExportSheetModel(10, "中间试制-其他试制"));
        SHEET_MAP.put(CostEnum.REPAIR_MATERIAL.getType(), new ExportSheetModel(11, "修理费用-修理材料"));
        SHEET_MAP.put(CostEnum.REPAIR.getType(), new ExportSheetModel(12, "修理费用-凭证费用"));
        SHEET_MAP.put(CostEnum.SAMPLE_MACHINE.getType(), new ExportSheetModel(13, "样机费用"));
        SHEET_MAP.put(CostEnum.STIMULUS.getType(), new ExportSheetModel(14, "动力损耗-研发动力", 20100));
        SHEET_MAP.put(CostEnum.STIMULUS_PROD.getType(), new ExportSheetModel(15, "动力损耗-设备动力"));
        SHEET_MAP.put(CostEnum.IRON_STIMULUS.getType(), new ExportSheetModel(16, "动力损耗-流程型", 20100));
        SHEET_MAP.put(CostEnum.FUEL.getType(), new ExportSheetModel(17, "燃料损耗-研发燃料", 20200));
        SHEET_MAP.put(CostEnum.IRON_FUEL.getType(), new ExportSheetModel(18, "燃料损耗-流程型", 20200));
        SHEET_MAP.put(CostEnum.DESIGN.getType(), new ExportSheetModel(19, "设计费用"));
        SHEET_MAP.put(CostEnum.INSPECTION.getType(), new ExportSheetModel(20, "检测费用"));
        SHEET_MAP.put(CostEnum.TRAVEL.getType(), new ExportSheetModel(21, "差旅费用"));
        SHEET_MAP.put(CostEnum.SOFT_AMORTIZATION.getType(), new ExportSheetModel(22, "摊销费用-凭证费用"));
        SHEET_MAP.put(CostEnum.PATENT_AMORTIZATION.getType(), new ExportSheetModel(22, "摊销费用-凭证费用"));
        SHEET_MAP.put(CostEnum.OTHER_AMORTIZATION.getType(), new ExportSheetModel(22, "摊销费用-凭证费用"));
        SHEET_MAP.put(CostEnum.ASSETS_AMORTIZATION.getType(), new ExportSheetModel(23, "摊销费用-资产摊销"));
        SHEET_MAP.put(CostEnum.OTHER.getType(), new ExportSheetModel(24, "其他费用"));
        SHEET_MAP.put(CostEnum.BENEFITS.getType(), new ExportSheetModel(24, "其他费用"));
        SHEET_MAP.put(CostEnum.COPYRIGHT.getType(), new ExportSheetModel(24, "其他费用"));
        SHEET_MAP.put(CostEnum.RD_PRODUCTION.getType(), new ExportSheetModel(24, "其他费用"));
        SHEET_MAP.put(CostEnum.BOOK.getType(), new ExportSheetModel(24, "其他费用"));
    }
}
