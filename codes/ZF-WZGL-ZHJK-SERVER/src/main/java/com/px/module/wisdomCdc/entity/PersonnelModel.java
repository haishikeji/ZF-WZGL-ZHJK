package com.px.module.wisdomCdc.entity;

import com.px.module.common.base.BaseModel;
import com.px.module.common.customannotation.AttributeAnnotation;
import com.px.module.common.utils.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description= "人员信息实体")
public class PersonnelModel extends BaseModel {

    @ApiModelProperty(value = "主键id", dataType = "String", example = "0799e3cc5dd64221bf4b9da62d04ddf1")
    @AttributeAnnotation(value="personnel_id")
    private String personnelId;//主键id

    @Excel(name = "就诊人姓名")
    @ApiModelProperty(value = "姓名", dataType = "String", example = "张三")
    @AttributeAnnotation(value="personnel_name")
    private String personnelName;//姓名

    @Excel(name = "就诊人身份证号")
    @ApiModelProperty(value = "身份证号", dataType = "String", example = "210283199111111111")
    @AttributeAnnotation(value="idnum")
    private String idnum;//身份证号

    @Excel(name = "就诊人电话")
    @ApiModelProperty(value = "电话", dataType = "String", example = "18888888888")
    @AttributeAnnotation(value="phone")
    private String phone;//电话

    @Excel(name = "就诊人地址")
    @ApiModelProperty(value = "地址", dataType = "String", example = "辽宁省沈阳市和平区")
    @AttributeAnnotation(value="personnel_add")
    private String personnelAdd;//地址

    @ApiModelProperty(value = "性别(1男，2女)", dataType = "String", example = "1")
    @AttributeAnnotation(value="sex")
    private String sex;//性别(1男，2女)

    @ApiModelProperty(value = "年龄", dataType = "String", example = "1")
    @AttributeAnnotation(value="age")
    private String age;//年龄

    @ApiModelProperty(value = "车牌号", dataType = "String", example = "京A88888")
    @AttributeAnnotation(value="carnum")
    private String carnum;//车牌号

    @ApiModelProperty(value = "民族", dataType = "String", example = "维吾尔族")
    @AttributeAnnotation(value="nation")
    private String nation;//民族

    @ApiModelProperty(value = "检测次数", dataType = "String", example = "1")
    @AttributeAnnotation(value="check_times")
    private String checkTimes;//检测次数

    @ApiModelProperty(value = "最新检测类型", dataType = "String", example = "狂犬病")
    @AttributeAnnotation(value="latest_check_type_name")
    private String latestCheckTypeName;//最新检测类型

    @ApiModelProperty(value = "最新检测时间", dataType = "String", example = "2022-2-2 20:22:22")
    @AttributeAnnotation(value="latest_check_time")
    private String latestCheckTime;//最新检测时间

    @ApiModelProperty(value = "最新检测结果显示", dataType = "String", example = "正常")
    @AttributeAnnotation(value="latest_check_result_display")
    private String latestCheckResultDisplay;//最新检测结果显示

    @Excel(name = "前端用户账号")
    @ApiModelProperty(value = "前端用户账号", dataType = "String", example = "admin")
    @AttributeAnnotation(value="login_name")
    private String loginName;//前端用户账号

    @ApiModelProperty(value = "人员类型（1预约检测人，2拜访人，0以上几种都是）", dataType = "String", example = "admin")
    @AttributeAnnotation(value="personnel_type")
    private String personnelType;//人员类型（1预约检测人，2拜访人，0以上几种都是）
    private String personnelTypes;

    public String getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(String personnelType) {
        this.personnelType = personnelType;
    }

    public String getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(String personnelId) {
        this.personnelId = personnelId;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public String getPersonnelAdd() {
        return personnelAdd;
    }

    public void setPersonnelAdd(String personnelAdd) {
        this.personnelAdd = personnelAdd;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCheckTimes() {
        return checkTimes;
    }

    public void setCheckTimes(String checkTimes) {
        this.checkTimes = checkTimes;
    }

    public String getLatestCheckTypeName() {
        return latestCheckTypeName;
    }

    public void setLatestCheckTypeName(String latestCheckTypeName) {
        this.latestCheckTypeName = latestCheckTypeName;
    }

    public String getLatestCheckTime() {
        return latestCheckTime;
    }

    public void setLatestCheckTime(String latestCheckTime) {
        this.latestCheckTime = latestCheckTime;
    }

    public String getLatestCheckResultDisplay() {
        return latestCheckResultDisplay;
    }

    public void setLatestCheckResultDisplay(String latestCheckResultDisplay) {
        this.latestCheckResultDisplay = latestCheckResultDisplay;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonnelTypes() {
        return personnelTypes;
    }

    public void setPersonnelTypes(String personnelTypes) {
        this.personnelTypes = personnelTypes;
    }
}
