package com.px.module.wisdomCdc.entity;

import java.util.List;

import com.px.module.common.base.BaseModel;
import com.px.module.common.customannotation.AttributeAnnotation;

import io.swagger.annotations.ApiModelProperty;

public class ScheduleListModel extends BaseModel {

	 
	 @ApiModelProperty(value = "排班id", dataType = "String", example = "1001")
	@AttributeAnnotation(value="schedule_id")
	private String scheduleId ;         //'排班id',
	 
	 @ApiModelProperty(value = "排版类别", dataType = "String", example = "1001")
	 @AttributeAnnotation(value="schedule_type")
	private String  scheduleType   ;  
	 @ApiModelProperty(value = "排班id", dataType = "String", example = "1001")
	@AttributeAnnotation(value="schedule_name")
	private String scheduleName ;         //'排班id',
	 
	 

	 @ApiModelProperty(value = "人员id", dataType = "String", example = "1001")
	@AttributeAnnotation(value="usercode")
	private String usercode ;             //'司机id',
	 
	 @ApiModelProperty(value = "人员电话", dataType = "String", example = "1001")
	@AttributeAnnotation(value="phone")
	private String phone;         //'司机电话',
	 
	 @ApiModelProperty(value = "司机名称", dataType = "String", example = "1001")
	@AttributeAnnotation(value="username")
	private String username;          //'司机名称',
	 
	 @ApiModelProperty(value = "所属机构id", dataType = "String", example = "1001")
	@AttributeAnnotation(value="org_id")
	private String orgId;     //'车辆所属公司id',
	 
	 
	 @ApiModelProperty(value = "所属机构id", dataType = "String", example = "1001")
		@AttributeAnnotation(value="depart_id")
		private String departId;      //所属部门id',

	 
	//排班管理新增中list时间集合 
	 private  List<ScheDuleDateListModel> dateListModel;
	 
	 
	 


		 
		 @ApiModelProperty(value = "开始时间", dataType = "String", example = "1001")
		@AttributeAnnotation(value="start_time")
		private String startTime ;          //'开始时间',
		 
		 @ApiModelProperty(value = "结束时间", dataType = "String", example = "1001")
		@AttributeAnnotation(value="end_time")
		private String endTime ;            //'结束时间',
		 

		 
		 @ApiModelProperty(value = "值班状态", dataType = "String", example = "1001")
		@AttributeAnnotation(value="schedule_work_status")
		 private String scheduleWorkStatus;
		 
		 
		 @ApiModelProperty(value = "值班状态", dataType = "String", example = "1001")
			@AttributeAnnotation(value="schedle_s_status")
		 private String   schedleSStatus;
		 
		 

		 public String getSchedleSStatus() {
				return schedleSStatus;
			}

			public void setSchedleSStatus(String schedleSStatus) {
				this.schedleSStatus = schedleSStatus;
			}
	 
	 
	 
	 
	 //统计人员数量需要用到的
	 


	@ApiModelProperty(value = "日期", dataType = "String", example = "1001")
	@AttributeAnnotation(value="schedule_date")
	private String scheduleDate;        //'日期',

	 @ApiModelProperty(value = "当班人员数量", dataType = "String", example = "1001")
	@AttributeAnnotation(value="people_total")
	 private String peopleTotal;
	 	 
	 @ApiModelProperty(value = "请假人员数量", dataType = "String", example = "1001")
		@AttributeAnnotation(value="people_total_leave")
		 private String peopleTotalLeave;

	 
 
	 
	 
	 
 	 
	 @ApiModelProperty(value = "所属部门名字", dataType = "String", example = "1001")
		@AttributeAnnotation(value="departName")
		 private String departName;
	 
	 
	 
	 //详情需要用到的
	 private List<SchdulePeoplelistModel> listpeople;
	 
	 
	 
	 
	 

	public List<SchdulePeoplelistModel> getListpeople() {
		return listpeople;
	}

	public void setListpeople(List<SchdulePeoplelistModel> listpeople) {
		this.listpeople = listpeople;
	}



	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<ScheDuleDateListModel> getDateListModel() {
		return dateListModel;
	}

	public void setDateListModel(List<ScheDuleDateListModel> dateListModel) {
		this.dateListModel = dateListModel;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getPeopleTotal() {
		return peopleTotal;
	}

	public void setPeopleTotal(String peopleTotal) {
		this.peopleTotal = peopleTotal;
	}

	public String getPeopleTotalLeave() {
		return peopleTotalLeave;
	}

	public void setPeopleTotalLeave(String peopleTotalLeave) {
		this.peopleTotalLeave = peopleTotalLeave;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getScheduleWorkStatus() {
		return scheduleWorkStatus;
	}

	public void setScheduleWorkStatus(String scheduleWorkStatus) {
		this.scheduleWorkStatus = scheduleWorkStatus;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}


	

	
	



	 

	 
	

	
	

}
