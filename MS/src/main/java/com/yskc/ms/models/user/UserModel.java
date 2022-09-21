package com.yskc.ms.models.user;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel("操作用户model")
public class UserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    private String userName;
	
	@NotNull(message = "姓名不能为空")
    @NotBlank(message = "姓名不能为空")
    private String realName;
    private int gender;
    
    @NotNull(message = "联系电话不能为空")
    @NotBlank(message = "联系电话不能为空")
    private String tel;
	private String depId;
	@NotNull(message = "职位不能为空")
    @NotBlank(message = "职位不能为空")
    private String postion;
	private String remark;

	private String password;

	private  Integer companyId;

	private  String email;

	private Integer type;

	private Integer deptId;
	private String deptName;
	private String[] deptIdArr;
	private String[] oldDeptId;
	private List<UserModel> userList;
	private String dingUserId;
	private String unionid;
	private String fullPath;
	private Integer status;
	private String mtypes;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
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

	public List<UserModel> getUserList() {
		return userList;
	}

	public void setUserList(List<UserModel> userList) {
		this.userList = userList;
	}

	public String[] getOldDeptId() {
		return oldDeptId;
	}

	public void setOldDeptId(String[] oldDeptId) {
		this.oldDeptId = oldDeptId;
	}

	public String[] getDeptIdArr() {
		return deptIdArr;
	}

	public void setDeptIdArr(String[] deptIdArr) {
		this.deptIdArr = deptIdArr;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getMtypes() {
		return mtypes;
	}

	public void setMtypes(String mtypes) {
		this.mtypes = mtypes;
	}
}
