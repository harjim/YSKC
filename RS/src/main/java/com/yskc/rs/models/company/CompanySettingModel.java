package com.yskc.rs.models.company;

import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.entity.company.CompanySettingEntity;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.company
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-03 13:38
 * @Description: 系统设置-客户设置model
 */
public class CompanySettingModel {

    private Map<String, Object> accountPeriod;

    private Map<String, Object> miniProgram;

    private Map<String, Object> rdRatio;

    private Map<String, Object> documentNo;

    /**
     * ：keys： equipment(设备)，employee（人员）
     */
    private Map<String, Object> hourBit;

    public Map<String, Object> getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(Map<String, Object> documentNo) {
        this.documentNo = documentNo;
    }

    public Map<String, Object> getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(Map<String, Object> accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public Map<String, Object> getMiniProgram() {
        return miniProgram;
    }

    public void setMiniProgram(Map<String, Object> miniProgram) {
        this.miniProgram = miniProgram;
    }

    public Map<String, Object> getRdRatio() {
        return rdRatio;
    }

    public void setRdRatio(Map<String, Object> rdRatio) {
        this.rdRatio = rdRatio;
    }

    public void loadDefault() {
        accountPeriod = new HashMap<>();
        miniProgram = new HashMap<>();
        rdRatio = new HashMap<>();
        documentNo = new HashMap<>();
        hourBit = new HashMap<>();
    }

    public void buildSetting(CompanySettingEntity companySetting) {
        loadDefault();
        if (!StringUtils.isEmpty(companySetting.getAccountPeriod())) {
            accountPeriod.putAll(JsonUtils.jsonToPojo(companySetting.getAccountPeriod(), HashMap.class));
        }
        if (!StringUtils.isEmpty(companySetting.getMiniProgram())) {
            miniProgram.putAll(JsonUtils.jsonToPojo(companySetting.getMiniProgram(), HashMap.class));
        }
        if (!StringUtils.isEmpty(companySetting.getRdRatio())) {
            rdRatio.putAll(JsonUtils.jsonToPojo(companySetting.getRdRatio(), HashMap.class));
        }
        if (!StringUtils.isEmpty(companySetting.getDocumentNo())) {
            documentNo.putAll(JsonUtils.jsonToPojo(companySetting.getDocumentNo(), HashMap.class));
        }
        if (!StringUtils.isEmpty(companySetting.getHourBit())) {
            documentNo.putAll(JsonUtils.jsonToPojo(companySetting.getHourBit(), HashMap.class));
        }
    }

    public Map<String, Object> getHourBit() {
        return hourBit;
    }

    public void setHourBit(Map<String, Object> hourBit) {
        this.hourBit = hourBit;
    }
}
