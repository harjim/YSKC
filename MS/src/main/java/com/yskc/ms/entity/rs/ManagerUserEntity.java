package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs
 * @Author: wangxing
 * @CreateTime: 2019-09-12 10:18
 * @Description: 服务人员表
 */
@TableName("app_manager_user")
public class ManagerUserEntity implements Serializable{

    private static final long serialVersionUID = 1L;

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
    private Integer msUserId;
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
    /**
     *
     */
    private String remark;

    private  String realName;
    private String userName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(Integer id){
        this.id=id;
    }
    public Integer getId(){
        return id;
    }
    public void setCompanyId(Integer companyId){
        this.companyId=companyId;
    }
    public Integer getCompanyId(){
        return companyId;
    }
    public void setMsUserId(Integer msUserId){
        this.msUserId=msUserId;
    }
    public Integer getMsUserId(){
        return msUserId;
    }
    public void setCreatorId(Integer creatorId){
        this.creatorId=creatorId;
    }
    public Integer getCreatorId(){
        return creatorId;
    }
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
    public Date getCreateTime(){
        return createTime;
    }
    public void setLastUpdatorId(Integer lastUpdatorId){
        this.lastUpdatorId=lastUpdatorId;
    }
    public Integer getLastUpdatorId(){
        return lastUpdatorId;
    }
    public void setLastUpdateTime(Date lastUpdateTime){
        this.lastUpdateTime=lastUpdateTime;
    }
    public Date getLastUpdateTime(){
        return lastUpdateTime;
    }
    public void setRemark(String remark){
        this.remark=remark;
    }
    public String getRemark(){
        return remark;
    }

}
