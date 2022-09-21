package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 15:26
 * @Description: 设备统计
 */
public class RdCntEquipmentSummaryModel extends BaseSummaryModel {

    private Integer equipmentCount;
    private Integer rdEquipmentCount;


    public Integer getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Integer equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public Integer getRdEquipmentCount() {
        return rdEquipmentCount;
    }

    public void setRdEquipmentCount(Integer rdEquipmentCount) {
        this.rdEquipmentCount = rdEquipmentCount;
    }

    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setEquipmentCount(equipmentCount);
        ps.setRdEquipmentCount(rdEquipmentCount);
    }
}
