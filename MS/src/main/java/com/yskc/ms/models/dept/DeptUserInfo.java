package com.yskc.ms.models.dept;

import java.io.Serializable;

/**
 * 部门用户信息
 * @author zhangdingfu
 * @Date 2019年7月4日 下午5:52:06
 */
public class DeptUserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String userName;
    private String realName;
    private String tel;
    private String deptName;
    private String postion;
    private String remark;
    private Integer depId;
    private Integer gender;
    private String deptIdArrStr;
    private String[] deptIdArr;
	private String dingUserId;
	private String unionid;
	private Integer roleId;
	private Integer status;
	private String serviceDeptIds;
	private String serviceDeptNames;
	private String mtypes;


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * 关联权限
	 */
	private String relatedPermissions;

	public String getRelatedPermissions() {
		return relatedPermissions;
	}

	public void setRelatedPermissions(String relatedPermissions) {
		this.relatedPermissions = relatedPermissions;
	}

	public String getDingUserId() {
		return dingUserId;
	}

	public void setDingUserId(String dingUserId) {
		this.dingUserId = dingUserId;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getDeptIdArrStr() {
		return deptIdArrStr;
	}

	public void setDeptIdArrStr(String deptIdArrStr) {
		this.deptIdArrStr = deptIdArrStr;
	}

	public String[] getDeptIdArr() {
		return deptIdArr;
	}

	public void setDeptIdArr(String[] deptIdArr) {
		this.deptIdArr = deptIdArr;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getPostion() {
		return postion;
	}
	public void setPostion(String postion) {
		this.postion = postion;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDepId() {
		return depId;
	}
	public void setDepId(Integer depId) {
		this.depId = depId;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}


	public String getServiceDeptIds() {
		return serviceDeptIds;
	}

	public void setServiceDeptIds(String serviceDeptIds) {
		this.serviceDeptIds = serviceDeptIds;
	}

	public String getServiceDeptNames() {
		return serviceDeptNames;
	}

	public void setServiceDeptNames(String serviceDeptNames) {
		this.serviceDeptNames = serviceDeptNames;
	}

	public String getMtypes() {
		return mtypes;
	}

	public void setMtypes(String mtypes) {
		this.mtypes = mtypes;
	}
}
