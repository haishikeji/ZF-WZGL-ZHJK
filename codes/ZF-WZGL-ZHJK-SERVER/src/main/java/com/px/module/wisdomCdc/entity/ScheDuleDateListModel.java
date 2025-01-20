package com.px.module.wisdomCdc.entity;

import com.px.module.common.customannotation.AttributeAnnotation;

import io.swagger.annotations.ApiModelProperty;

public class ScheDuleDateListModel  {

	
	 @ApiModelProperty(value = "日期", dataType = "String", example = "1001")
		@AttributeAnnotation(value="schedule_date")
		private String scheduleDate;        //'日期',
		 

		 
		 @ApiModelProperty(value = "开始时间", dataType = "String", example = "1001")
		@AttributeAnnotation(value="start_time")
		private String startTime ;          //'开始时间',
		 
		 @ApiModelProperty(value = "结束时间", dataType = "String", example = "1001")
		@AttributeAnnotation(value="end_time")
		private String endTime ;            //'结束时间',
		 

		 
		 @ApiModelProperty(value = "值班状态", dataType = "String", example = "1001")
		@AttributeAnnotation(value="schedule_work_status")
		 private String scheduleWorkStatus;

	
		 
		 
		 
		 
		 
	

		public String getScheduleDate() {
			return scheduleDate;
		}



		public void setScheduleDate(String scheduleDate) {
			this.scheduleDate = scheduleDate;
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
		 
		 
		 
		 
		 
		 

		
		
		
		
}
