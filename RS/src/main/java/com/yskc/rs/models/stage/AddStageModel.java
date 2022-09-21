package com.yskc.rs.models.stage;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/4/20 16:34
 * @Description:添加阶段model
 */
public class AddStageModel implements Serializable {


    @TableId
    private Integer id;

    private Integer projectId;

    @Size(max=50,message="阶段名称不能超过50个字")
    private String stageName;

    private String stageType;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    @Size(max=200,message="备注不能超过200个字")
    private String remark;

    private Integer companyId;
    @Size(max=200,message="工作内容不能超过200个字")
    private String workDesc;
    private String stageKey;
    private Boolean isAutoAdd;//是否自动添加默认文件 true 默认添加 false 反之
    /**
     * 生成文档类型 0:普通，1委托
     */
    private Integer type;

    public String getStageType() {
        return stageType;
    }

    public void setStageType(String stageType) {
        this.stageType = stageType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public Boolean getAutoAdd() {
        return isAutoAdd;
    }

    public void setAutoAdd(Boolean autoAdd) {
        isAutoAdd = autoAdd;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
