package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/2/25 14:27
 * @Description:标准化实施情况
 */
@TableName("p_standard_implement")
public class StandardImplementEntity implements Serializable {
    @TableId
    private Integer id;

    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer companyId;
    private Integer year;
    private String rdCenterBuild;//研发中心建设标准化
    private String rdProjectManage;//研发项目管理标准化
    private String rdBudget;//研发费用核算标准化
    private String intellectualManage;//知识产权管理标准化
    private String highTech;//高新技术产品标准化
    private String fileManage;//研发档案管理标准化
    private String thoughtManage;//研发意识管理标准化
    private String rdResultManage;//研发成果管理标准化

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

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRdCenterBuild() {
        return rdCenterBuild;
    }

    public void setRdCenterBuild(String rdCenterBuild) {
        this.rdCenterBuild = rdCenterBuild;
    }

    public String getRdProjectManage() {
        return rdProjectManage;
    }

    public void setRdProjectManage(String rdProjectManage) {
        this.rdProjectManage = rdProjectManage;
    }

    public String getRdBudget() {
        return rdBudget;
    }

    public void setRdBudget(String rdBudget) {
        this.rdBudget = rdBudget;
    }

    public String getIntellectualManage() {
        return intellectualManage;
    }

    public void setIntellectualManage(String intellectualManage) {
        this.intellectualManage = intellectualManage;
    }

    public String getHighTech() {
        return highTech;
    }

    public void setHighTech(String highTech) {
        this.highTech = highTech;
    }

    public String getFileManage() {
        return fileManage;
    }

    public void setFileManage(String fileManage) {
        this.fileManage = fileManage;
    }

    public String getThoughtManage() {
        return thoughtManage;
    }

    public void setThoughtManage(String thoughtManage) {
        this.thoughtManage = thoughtManage;
    }

    public String getRdResultManage() {
        return rdResultManage;
    }

    public void setRdResultManage(String rdResultManage) {
        this.rdResultManage = rdResultManage;
    }
}
