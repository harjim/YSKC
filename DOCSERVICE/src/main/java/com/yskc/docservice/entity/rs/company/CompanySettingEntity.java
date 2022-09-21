package com.yskc.docservice.entity.rs.company;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.company.CompanySettingModel;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-03 11:40
 * @Description: 系统设置-客户设置entity
 */
@TableName("c_setting")
public class CompanySettingEntity {

    @TableId
    private Integer id;
    private Integer creatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer lastUpdatorId;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer companyId;
    private String accountPeriod;
    private String miniProgram;
    private String rdRatio;
    private String documentNo;//文件编号
    private String hourBit;

    public static CompanySettingEntity build(RsUserInfo info, Date date, CompanySettingModel model) {
        CompanySettingEntity entity = new CompanySettingEntity();
        entity.creatorId = info.getUserId();
        entity.createTime = date;
        entity.lastUpdateTime = date;
        entity.lastUpdatorId = entity.creatorId;
        entity.msCreatorId = info.getMsUserId();
        entity.msLastUpdatorId = entity.msCreatorId;
        entity.companyId = info.getCompanyId();
        entity.accountPeriod = JsonUtils.objectToJson(model.getAccountPeriod());
        entity.miniProgram = JsonUtils.objectToJson(model.getMiniProgram());
        entity.rdRatio = JsonUtils.objectToJson(model.getRdRatio());
        entity.documentNo=JsonUtils.objectToJson(model.getDocumentNo());
        entity.hourBit = JsonUtils.objectToJson(model.getHourBit());
        return entity;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(String accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getMiniProgram() {
        return miniProgram;
    }

    public void setMiniProgram(String miniProgram) {
        this.miniProgram = miniProgram;
    }

    public String getRdRatio() {
        return rdRatio;
    }

    public void setRdRatio(String rdRatio) {
        this.rdRatio = rdRatio;
    }

    public String getHourBit() {
        return hourBit;
    }

    public void setHourBit(String hourBit) {
        this.hourBit = hourBit;
    }
}
