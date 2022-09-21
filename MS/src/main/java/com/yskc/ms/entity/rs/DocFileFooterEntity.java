package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2021/2/19 9:13
 * description:过程文件页脚设置
 */
@TableName("p_docFile_footer")
public class DocFileFooterEntity implements Serializable {
    @TableId
    private Integer id;
    private Integer projectId;
    private Integer companyId;
    private String auditEnumber;//审核人员工号
    private String toCompileEnumber;//编制人员工号
    private String approvalEnumber;//批准人员工号

    private Integer lastUpdatorId;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer creatorId;

    private Date createTime;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAuditEnumber() {
        return auditEnumber;
    }

    public void setAuditEnumber(String auditEnumber) {
        this.auditEnumber = auditEnumber;
    }

    public String getToCompileEnumber() {
        return toCompileEnumber;
    }

    public void setToCompileEnumber(String toCompileEnumber) {
        this.toCompileEnumber = toCompileEnumber;
    }

    public String getApprovalEnumber() {
        return approvalEnumber;
    }

    public void setApprovalEnumber(String approvalEnumber) {
        this.approvalEnumber = approvalEnumber;
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

    public DocFileFooterEntity() {
    }

    public DocFileFooterEntity(Integer projectId, Integer companyId, String auditEnumber, String toCompileEnumber, String approvalEnumber, Integer msUserId, Integer userId, Date date) {
        this.projectId = projectId;
        this.companyId = companyId;
        this.auditEnumber = auditEnumber;
        this.toCompileEnumber = toCompileEnumber;
        this.approvalEnumber = approvalEnumber;
        this.msCreatorId = msUserId;
        this.creatorId = userId;
        this.createTime = date;
        this.msLastUpdatorId = msUserId;
        this.lastUpdateTime = date;
        this.lastUpdatorId = userId;
    }
}
