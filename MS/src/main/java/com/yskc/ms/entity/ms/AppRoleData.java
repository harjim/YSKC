package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.ms
 * @Author: wangxing
 * @CreateTime: 2019-12-17 10:46
 * @Description: 数据权限表
 */
@TableName("app_role_data")
public class AppRoleData implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer id;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer functionId;
    private Integer dataType;
    private Integer roleId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static AppRoleData build(Integer userId, Integer currentRoleId, Date now, Integer menuId, Integer dataType) {
        AppRoleData data = new AppRoleData();
        data.creatorId = userId;
        data.createTime = now;
        data.lastUpdatorId = userId;
        data.lastUpdateTime = now;
        data.roleId = currentRoleId;
        data.functionId = menuId;
        data.dataType = dataType;
        return data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
