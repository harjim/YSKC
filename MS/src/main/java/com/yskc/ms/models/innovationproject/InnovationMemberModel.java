package com.yskc.ms.models.innovationproject;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.innovationproject
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-26 09:56
 * @Description: 创新项目成员
 */
public class InnovationMemberModel {

    private int id;
    private Integer innovationId;
    /**
     * 1:技术人员,2:财务人员
     */
    private Integer mType;
    private Integer memberId;
    private String realName;
    private Integer customerId;
    private Boolean isMaster;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getInnovationId() {
        return innovationId;
    }

    public void setInnovationId(Integer innovationId) {
        this.innovationId = innovationId;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Boolean getMaster() {
        return isMaster;
    }

    public void setMaster(Boolean master) {
        isMaster = master;
    }
}
