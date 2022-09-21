package com.yskc.ms.models.finaDaily;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/models/finaDaily
 * @Author: hm
 * @CreateTime: 2022/9/6
 * @Description: 查询财务日志model
 */
public class QueryDailyModel extends PageParams {
    /**
     * 客户名称
     */
    private String companyName;
    /**
     * 事项
     */
    private Integer itemType;
    /**
     * 创建人
     */
    private Integer creatorId;

    /**
     * 当前处理人
     */
    private String auditUsers;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 业务员
     */
    private Integer ownerId;

    /**
     * 所属部门
     */
    private Integer deptId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getAuditUsers() {
        return auditUsers;
    }

    public void setAuditUsers(String auditUsers) {
        this.auditUsers = auditUsers;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
