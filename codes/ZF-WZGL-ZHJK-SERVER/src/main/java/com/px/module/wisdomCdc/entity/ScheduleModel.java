package com.px.module.wisdomCdc.entity;

import java.util.List;

import com.px.module.common.base.BaseModel;
import com.px.module.common.customannotation.AttributeAnnotation;

import io.swagger.annotations.ApiModelProperty;




public class ScheduleModel   extends BaseModel {
	//排班管理model
	
	 @ApiModelProperty(value = "排班名称", dataType = "String", example = "1001")
	 @AttributeAnnotation(value="schedule_name")
	private String  scheduleName;     
	
	@ApiModelProperty(value = "排版类别", dataType = "String", example = "1001")
	 @AttributeAnnotation(value="schedule_type")
	private String  scheduleType   ;  
	
	@ApiModelProperty(value = "人员数量", dataType = "String", example = "1001")
	 @AttributeAnnotation(value="schedule_nums")
	private String  scheduleNums   ;  
	
	@ApiModelProperty(value = "所属公司id", dataType = "String", example = "1001")
	 @AttributeAnnotation(value="org_id")
	private String  orgId  ;
	
	@ApiModelProperty(value = "排班状态", dataType = "String", example = "1001")
	 @AttributeAnnotation(value="schedule_status")
	private String  scheduleStatus   ;
	

	@ApiModelProperty(value = "排班周期", dataType = "String", example = "1001")
	 @AttributeAnnotation(value="schedule_cycle_date")
	private String scheduleCycleDate;
	
	
	@ApiModelProperty(value = "排班工作日合集", dataType = "String", example = "1001")
   @AttributeAnnotation(value="schedule_work_day")
	private String  scheduleWorkDay;
	
	 @ApiModelProperty(value = "排版类别名称", dataType = "String", example = "1001")
		@AttributeAnnotation(value="schedule_type_name")
	 private String scheduleTypeName; 
	
	
	@ApiModelProperty(value = "备注信息", dataType = "String", example = "1001")
	 @AttributeAnnotation(value="remark")
	private String  remark;    
	
	
	@ApiModelProperty(value = "开始时间", dataType = "String", example = "1001")
	 @AttributeAnnotation(value="remark")
	private String  startTime;  
	
	@ApiModelProperty(value = "结束时间", dataType = "String", example = "1001")
	 @AttributeAnnotation(value="remark")
	private String  endTime;  
	
	

	private List<ScheduleListModel> listmodel;





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



	public String getScheduleTypeName() {
		return scheduleTypeName;
	}



	public void setScheduleTypeName(String scheduleTypeName) {
		this.scheduleTypeName = scheduleTypeName;
	}



	public String getScheduleWorkDay() {
		return scheduleWorkDay;
	}



	public void setScheduleWorkDay(String scheduleWorkDay) {
		this.scheduleWorkDay = scheduleWorkDay;
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



	public String getScheduleNums() {
		return scheduleNums;
	}



	public void setScheduleNums(String scheduleNums) {
		this.scheduleNums = scheduleNums;
	}



	





	public String getOrgId() {
		return orgId;
	}



	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}



	public String getScheduleStatus() {
		return scheduleStatus;
	}



	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}




	public List<ScheduleListModel> getListmodel() {
		return listmodel;
	}



	public void setListmodel(List<ScheduleListModel> listmodel) {
		this.listmodel = listmodel;
	}



	public String getScheduleCycleDate() {
		return scheduleCycleDate;
	}



	public void setScheduleCycleDate(String scheduleCycleDate) {
		this.scheduleCycleDate = scheduleCycleDate;
	}
	
	
	
	

	

}
