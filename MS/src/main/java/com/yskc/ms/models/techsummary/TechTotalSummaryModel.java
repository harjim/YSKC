package com.yskc.ms.models.techsummary;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.techsummary
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-15 10:07
 * @Description: 技改总计model
 */
public class TechTotalSummaryModel {
    private Integer stageTotal = 0;
    private Integer uploadTotal = 0;
    private Integer uploadedTotal = 0;

    public Integer getStageTotal() {
        return stageTotal;
    }

    public void setStageTotal(Integer stageTotal) {
        this.stageTotal = stageTotal;
    }

    public Integer getUploadTotal() {
        return uploadTotal;
    }

    public void setUploadTotal(Integer uploadTotal) {
        this.uploadTotal = uploadTotal;
    }

    public Integer getUploadedTotal() {
        return uploadedTotal;
    }

    public void setUploadedTotal(Integer uploadedTotal) {
        this.uploadedTotal = uploadedTotal;
    }
}
