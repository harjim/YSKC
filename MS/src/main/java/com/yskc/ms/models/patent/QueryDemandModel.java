package com.yskc.ms.models.patent;

import com.yskc.ms.models.params.PageParams;

/**
 * @Author: hck
 * @DateTime: 2021/7/8 15:53
 * @Description: 需求查询model
 */
public class QueryDemandModel extends PageParams {

    private String companyName;//客户名称

    private Integer status;//状态

    private Integer ownerId;//业务员
    private String groupName;
    private Integer groupId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
}
