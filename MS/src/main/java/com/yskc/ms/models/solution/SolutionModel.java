package com.yskc.ms.models.solution;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.solution
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-09 10:28
 * @Description: 解决方案model
 */
public class SolutionModel {
    @TableId
    private Integer id;
    /**
     * 标题
     */
    @NotEmpty(message = "标题不能为空")
    @NotNull(message = "标题不能为空")
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
    @NotEmpty(message = "解决方案不能为空")
    @NotNull(message = "解决方案不能为空")
    private String content;
    /**
     * 创建人
     */
    private String creatorName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer type;

    private Integer rate;

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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
