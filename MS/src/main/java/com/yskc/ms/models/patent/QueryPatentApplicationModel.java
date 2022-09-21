package com.yskc.ms.models.patent;

import com.yskc.ms.models.MemberModel;
import com.yskc.ms.models.params.PageParams;

/**
 * @program: ms
 * @description: 专利申请(新)查询model
 * @author: cyj
 * @create: 2022-02-09 09:13
 **/
public class QueryPatentApplicationModel extends PageParams {
    private Integer year;
    private String customerName;
    private Integer ownerId;
    private Integer[] techIds;
    private Integer type;
    private String groupName;
    private Integer groupId;
    private MemberModel memberInfo;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Integer[] getTechIds() {
        return techIds;
    }

    public void setTechIds(Integer[] techIds) {
        this.techIds = techIds;
    }

    public MemberModel getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberModel memberInfo) {
        this.memberInfo = memberInfo;
    }
}
