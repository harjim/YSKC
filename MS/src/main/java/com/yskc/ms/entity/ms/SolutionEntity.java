package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.ms.models
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-09 10:11
 * @Description: 解决方案
 */
@TableName("solution")
public class SolutionEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 客户名称
     */
    private String customer;
    /**
     * 服务项目
     */
    private String serveProject;
    /**
     * 行业
     */
    private String industry;
    /**
     * 内容，解决方案
     */
    private String content;

    private Integer type;

    private Integer rate;
    private Integer rateUserId;
    private Date rateTime;

    private String filePath;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getServeProject() {
        return serveProject;
    }

    public void setServeProject(String serveProject) {
        this.serveProject = serveProject;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getRateUserId() {
        return rateUserId;
    }

    public void setRateUserId(Integer rateUserId) {
        this.rateUserId = rateUserId;
    }

    public Date getRateTime() {
        return rateTime;
    }

    public void setRateTime(Date rateTime) {
        this.rateTime = rateTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
