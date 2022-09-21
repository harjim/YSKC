package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-30 16:22:26
 */
@TableName("app_user_role")
public class RsUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

		/**
	* 
	*/
		@TableId
		private Integer id;
		/**
	* 
	*/
		private Integer userId;
		/**
	* 
	*/
		private Integer roleId;
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
		    public void setId(Integer id){
    	this.id=id;
	}
	public Integer getId(){
		return id;
	}
	    public void setUserId(Integer userId){
    	this.userId=userId;
	}
	public Integer getUserId(){
		return userId;
	}
	    public void setRoleId(Integer roleId){
    	this.roleId=roleId;
	}
	public Integer getRoleId(){
		return roleId;
	}
	    public void setCompanyId(Integer companyId){
    	this.companyId=companyId;
	}
	public Integer getCompanyId(){
		return companyId;
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
	
}
