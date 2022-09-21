package com.yskc.ms.models.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@ApiModel("重置密码")
public class ResetPasswordModel {
	
	@NotNull(message = "获取用户失败")
	private Integer id;

	@NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
	private String password;
	
	@NotNull(message = "获取用户名失败,请稍后再试！")
    @NotBlank(message = "获取用户名失败,请稍后再试！")
	private String userName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
