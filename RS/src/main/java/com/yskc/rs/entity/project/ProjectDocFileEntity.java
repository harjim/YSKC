package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.yskc.rs.models.UserInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangdingfu
 */
@TableName("p_docFile")
public class ProjectDocFileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
    private Integer projectId;
    private String stage;
    private Integer docFileId;
    private String docFileName;

    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    private Integer docFileTemplateId;
    //排序
    private Integer seq;

    @TableField(strategy = FieldStrategy.IGNORED)
    private Date month;
    //是否删除
    private Boolean deleted;

    //是否需要数据必填后提交
    private Boolean finished;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getDocFileTemplateId() {
        return docFileTemplateId;
    }

    public void setDocFileTemplateId(Integer docFileTemplateId) {
        this.docFileTemplateId = docFileTemplateId;
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public ProjectDocFileEntity() {
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public ProjectDocFileEntity(Integer projectId, String stage, Integer docFileId,
                                String docFileName, Date date, UserInfo userInfo, Integer seq, Integer docFileTemplateId, Date month) {
        this.projectId = projectId;
        this.stage = stage;
        this.docFileId = docFileId;
        this.docFileName = docFileName;
        this.creatorId = userInfo.getUserId();
        this.lastUpdatorId = userInfo.getUserId();
        this.createTime = date;
        this.lastUpdateTime = date;
        this.msCreatorId = userInfo.getMsUserId();
        this.msLastUpdatorId = userInfo.getMsUserId();
        this.docFileTemplateId = docFileTemplateId;
        this.seq = seq;
        this.deleted = false;
        this.month=month;
    }
}
