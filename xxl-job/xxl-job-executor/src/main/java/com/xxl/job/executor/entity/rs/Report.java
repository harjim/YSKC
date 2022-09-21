package com.xxl.job.executor.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-14 15:19
 * @Description: rs项目
 */
@TableName("p_report")
public class Report {
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer ryear;
    /**
     *
     */
    private String rname;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String remark;

    private Integer cnt;
    private Integer rdFee;
    private String techIntro;

    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private LocalDateTime createTime;


    private Integer lastUpdatorId;

    private LocalDateTime lastUpdateTime;

    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setRyear(Integer ryear) {
        this.ryear = ryear;
    }

    public Integer getRyear() {
        return ryear;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRname() {
        return rname;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public String getTechIntro() {
        return techIntro;
    }

    public void setTechIntro(String techIntro) {
        this.techIntro = techIntro;
    }

    public Integer getRdFee() {
        return rdFee;
    }

    public void setRdFee(Integer rdFee) {
        this.rdFee = rdFee;
    }
}
