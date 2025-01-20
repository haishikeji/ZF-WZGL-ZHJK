package com.px.module.wisdomCdc.entity;

import com.px.module.common.base.BaseModel;
import com.px.module.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description= "部门实体")
public class DepartmentModel extends BaseModel {

    @ApiModelProperty(value = "主键id", dataType = "String", example = "")
    @AttributeAnnotation(value="depart_id")
    private String departId;

    @ApiModelProperty(value = "部门名称", dataType = "String", example = "")
    @AttributeAnnotation(value="depart_name")
    private String departName;

    @ApiModelProperty(value = "所属机构id", dataType = "String", example = "")
    @AttributeAnnotation(value="org_id")
    private String orgId;

    @ApiModelProperty(value = "所属机构名称", dataType = "String", example = "")
    @AttributeAnnotation(value="org_name")
    private String orgName;

    @ApiModelProperty(value = "职能描述", dataType = "String", example = "")
    @AttributeAnnotation(value="depart_function")
    private String departFunction;

    @ApiModelProperty(value = "部门状态（1启用，2禁用）", dataType = "String", example = "")
    @AttributeAnnotation(value="depart_status")
    private String departStatus;

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDepartFunction() {
        return departFunction;
    }

    public void setDepartFunction(String departFunction) {
        this.departFunction = departFunction;
    }

    public String getDepartStatus() {
        return departStatus;
    }

    public void setDepartStatus(String departStatus) {
        this.departStatus = departStatus;
    }
}
