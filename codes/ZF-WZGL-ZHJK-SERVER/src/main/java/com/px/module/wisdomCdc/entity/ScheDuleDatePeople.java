package com.px.module.wisdomCdc.entity;

import com.px.module.common.customannotation.AttributeAnnotation;

import io.swagger.annotations.ApiModelProperty;

public class ScheDuleDatePeople {
	
	 @ApiModelProperty(value = "人员id", dataType = "String", example = "1001")
	@AttributeAnnotation(value="usercode")
	private String usercode ;             //'司机id',
	 
	 @ApiModelProperty(value = "人员电话", dataType = "String", example = "1001")
	@AttributeAnnotation(value="phone")
	private String phone;         //'司机电话',
	 
	 @ApiModelProperty(value = "司机名称", dataType = "String", example = "1001")
	@AttributeAnnotation(value="username")
	private String username;

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}    
	 
	 
	 
	 
	 

}
