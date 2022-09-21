package com.yskc.rs.models.aggregation;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.aggregation
 * @Author: zhangdingfu
 * @CreateTime: 2021-05-21 14:01
 * @Description: 归集费用model
 */
public class AggFeeModel {

    private Date month;
    private List<String> keys;
    private Integer type;
    private Map<Integer,AggDeeConfigDetailModel> config;
    /**
     * 计划工时归集人员类型费用接口使用+用
     */
    private List<Integer> etypes;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Map<Integer, AggDeeConfigDetailModel> getConfig() {
        return config;
    }

    public void setConfig(Map<Integer, AggDeeConfigDetailModel> config) {
        this.config = config;
    }

    public List<Integer> getEtypes() {
        return etypes;
    }

    public void setEtypes(List<Integer> etypes) {
        this.etypes = etypes;
    }
}
