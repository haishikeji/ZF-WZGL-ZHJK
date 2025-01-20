package com.px.module.wisdomCdc.entity;

import com.px.module.common.base.BaseModel;
import com.px.module.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

public class CountMapDataModel extends BaseModel {

    @ApiModelProperty(value = "主键id", dataType = "String", example = "")
    @AttributeAnnotation(value="id")
    private String id;

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

    @ApiModelProperty(value = "区域code", dataType = "String", example = "")
    @AttributeAnnotation(value="code")
    private String code;

    @ApiModelProperty(value = "区域name", dataType = "String", example = "")
    @AttributeAnnotation(value="name")
    private String name;

    @ApiModelProperty(value = "区域新增", dataType = "String", example = "")
    @AttributeAnnotation(value="new_value")
    private String newValue;

    @ApiModelProperty(value = "区域现有", dataType = "String", example = "")
    @AttributeAnnotation(value="old_value")
    private String oldValue;

    @ApiModelProperty(value = "区域类型（1吉林，2内蒙）", dataType = "String", example = "")
    @AttributeAnnotation(value="type")
    private String type;

    @ApiModelProperty(value = "上级区域id", dataType = "String", example = "")
    @AttributeAnnotation(value="parent_id")
    private String parentId;

    @ApiModelProperty(value = "区域id", dataType = "String", example = "")
    @AttributeAnnotation(value="community_id")
    private String communityId;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
