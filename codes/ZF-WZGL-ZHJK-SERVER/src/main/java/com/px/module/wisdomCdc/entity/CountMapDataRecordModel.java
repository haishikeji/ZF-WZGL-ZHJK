package com.px.module.wisdomCdc.entity;

import com.px.module.common.base.BaseModel;
import com.px.module.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

public class CountMapDataRecordModel extends BaseModel {

    @ApiModelProperty(value = "", dataType = "String", example = "")
    @AttributeAnnotation(value="record_id")
    private String recordId;

    @ApiModelProperty(value = "检测类型id", dataType = "String", example = "")
    @AttributeAnnotation(value="check_type_id")
    private String checkTypeId;

    @ApiModelProperty(value = "检测类型名称", dataType = "String", example = "")
    @AttributeAnnotation(value="check_type_name")
    private String checkTypeName;

    @ApiModelProperty(value = "检测时间", dataType = "String", example = "")
    @AttributeAnnotation(value="statistical_time")
    private String statisticalTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getCheckTypeId() {
        return checkTypeId;
    }

    public void setCheckTypeId(String checkTypeId) {
        this.checkTypeId = checkTypeId;
    }

    public String getCheckTypeName() {
        return checkTypeName;
    }

    public void setCheckTypeName(String checkTypeName) {
        this.checkTypeName = checkTypeName;
    }

    public String getStatisticalTime() {
        return statisticalTime;
    }

    public void setStatisticalTime(String statisticalTime) {
        this.statisticalTime = statisticalTime;
    }
}
