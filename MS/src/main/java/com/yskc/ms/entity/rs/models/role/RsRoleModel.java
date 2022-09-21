package com.yskc.ms.entity.rs.models.role;

import com.baomidou.mybatisplus.annotations.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs.models.role
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-30 17:03
 * @Description: 角色model
 */
public class RsRoleModel implements Serializable {
    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String roleName;
    /**
     *
     */
    private String remark;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Date createTime;

    /**
     * 公司名称
     */
    private String companyName;

    private String userName;

    private Integer selectedRoleId;

    private Date lastUpdateTime;

    private String realName;

    private Integer msUserId;
    private boolean isvisible;

    private Integer mType;

    private Integer productType;

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public boolean isIsvisible() {
        return isvisible;
    }

    public void setIsvisible(boolean isvisible) {
        this.isvisible = isvisible;
    }

    public Integer getMsUserId() {
        return msUserId;
    }

    public void setMsUserId(Integer msUserId) {
        this.msUserId = msUserId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSelectedRoleId() {
        return selectedRoleId;
    }

    public void setSelectedRoleId(Integer selectedRoleId) {
        this.selectedRoleId = selectedRoleId;
    }
}
