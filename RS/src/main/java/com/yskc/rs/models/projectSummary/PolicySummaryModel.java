package com.yskc.rs.models.projectSummary;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/2/25 15:36
 * @Description:
 */
public class PolicySummaryModel implements Serializable {

    private Integer id;

    private String statute;//政策
    private String  filingMaterial;//备案资料
    private String additionalDeduction;//加计扣除流程

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatute() {
        return statute;
    }

    public void setStatute(String statute) {
        this.statute = statute;
    }

    public String getFilingMaterial() {
        return filingMaterial;
    }

    public void setFilingMaterial(String filingMaterial) {
        this.filingMaterial = filingMaterial;
    }

    public String getAdditionalDeduction() {
        return additionalDeduction;
    }

    public void setAdditionalDeduction(String additionalDeduction) {
        this.additionalDeduction = additionalDeduction;
    }
}
