package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.models.UserInfo;

import java.util.Date;

/**
 * @Author: yzh
 * @Description: 服务人员(项目子表)
 */
@TableName("project_member")
public class ProjectMember {
    private static final long serialVersionUID = 1L;
    @TableId
    private int id;
    private Integer projectId;
    /**
     * 1:技术人员,2:财务人员
     */
    private Integer mType;
    private Integer memberId;
    private String remark;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer customerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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


    public static ProjectMember build(UserInfo userInfo, Date now, int mType, Integer memberId, Integer projectId, Integer customerId) {
        ProjectMember member = new ProjectMember();
        member.memberId = memberId;
        member.projectId = projectId;
        member.lastUpdateTime = now;
        member.createTime = now;
        member.creatorId = userInfo.getId();
        member.lastUpdatorId = userInfo.getId();
        member.mType = mType;
        member.customerId = customerId;
        return member;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public ProjectMember copy(Integer pId) {
        ProjectMember member = new ProjectMember();
        member.memberId = this.memberId;
        member.projectId = pId;
        member.lastUpdateTime = this.lastUpdateTime;
        member.createTime = this.createTime;
        member.creatorId = this.creatorId;
        member.lastUpdatorId = this.lastUpdatorId;
        member.mType = this.mType;
        member.customerId = this.customerId;
        return member;
    }
}
