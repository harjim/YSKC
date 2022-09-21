package com.yskc.rs.models.stage;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.models.document.DocumentModel;
import com.yskc.rs.models.projectDocFile.ProjectDocFileModel;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class StageModel {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer projectId;
    /**
     *
     */
    private String stageName;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    /**
     *
     */
    private String remark;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Integer lastUpdatorId;
    /**
     *
     */
    private Date lastUpdateTime;

    private List<DocumentModel> docData;
    private String workDesc;

    private String stageKey;
    private String stageType;
    private Integer order;
    private Map<String, Boolean> typeList;
    private List<ProjectDocFileModel> projectDocList;
    private String docFileName;
    /**
     * 文件编号
     */
    private String documentNumber;
    /**
     * 序号
     */
    private Integer docSeq;
    private Integer status;
    /**
     * 默认为1 标识为只读(该值 = 立项报告status)
     */
    private Integer reportStatus = 1;

    private Integer seq;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getDocSeq() {
        return docSeq;
    }

    public void setDocSeq(Integer docSeq) {
        this.docSeq = docSeq;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    public List<ProjectDocFileModel> getProjectDocList() {
        return projectDocList;
    }

    public void setProjectDocList(List<ProjectDocFileModel> projectDocList) {
        this.projectDocList = projectDocList;
    }

    public Map<String, Boolean> getTypeList() {
        return typeList;
    }

    public void setTypeList(Map<String, Boolean> typeList) {
        this.typeList = typeList;
    }

    public String getStageType() {
        stageType = (stageType == null ? stageName : stageType);
        return stageType;
    }

    public void setStageType(String stageType) {
        this.stageType = stageType;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getWorkDesc() {
        return workDesc;
    }

    public void setWorkDesc(String workDesc) {
        this.workDesc = workDesc;
    }


    public List<DocumentModel> getDocData() {
        return docData;
    }

    public void setDocData(List<DocumentModel> docData) {
        this.docData = docData;
    }

    public String getId() {
        // // TODO: 22/02/25 zdf 阶段和文档同id问题。暂处理阶段id添加s前缀
        return StringUtils.isEmpty(id) ? null : "s" + id;
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

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Integer getStageId() {
        return id;
    }
}
