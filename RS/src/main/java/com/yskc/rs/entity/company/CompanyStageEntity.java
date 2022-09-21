package com.yskc.rs.entity.company;

import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;
import io.swagger.models.auth.In;

/**
 * @program: rs
 * @description: c_stage
 * @author: cyj
 * @create: 2022-05-11 11:03
 **/
@TableName("c_stage")
public class CompanyStageEntity extends BaseEntity {
    private Integer companyId;
    private String stageKey;
    private String stageName;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
}
