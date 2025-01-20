package com.px.module.wisdomCdc.dto;

import com.px.module.common.utils.Excel;
import com.px.module.wisdomCdc.entity.ApprovalModel;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "ApprovalDto", description = "Approval数据传输对象")
public class ApprovalDto extends ApprovalModel {
    @Excel(name = "拜访人姓名")
    private String personnelName;

    @Excel(name = "身份证号")
    private String idnum;//身份证号

    @Excel(name = "手机号")
    private String phone;

    @Excel(name = "拜访人车牌号")
    private String carnum;
    private String dateStart;//开始时间
    private String dateEnd;//结束时间
    private String dat;//进入时间
    private String personnelNumTotal;//申请进入人数
    private String allowPersonnel;//允许进入人数
    private String refusePersonnel;//拒绝进入人数
    private String visitStatuss;//进入状态（1允许进入，2拒绝进入，3待审批），逗号分割
    private String isApproval;//是否需要审核（0不需要，1需要）
    private String sex;//1男，2女
    private String userId;

    @Excel(name = "进入状态")
    private String visitStatusCn;

    public String getVisitStatusCn() {
        return visitStatusCn;
    }

    public void setVisitStatusCn(String visitStatusCn) {
        this.visitStatusCn = visitStatusCn;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIsApproval() {
        return isApproval;
    }

    public void setIsApproval(String isApproval) {
        this.isApproval = isApproval;
    }

    public String getVisitStatuss() {
        return visitStatuss;
    }

    public void setVisitStatuss(String visitStatuss) {
        this.visitStatuss = visitStatuss;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getPersonnelNumTotal() {
        return personnelNumTotal;
    }

    public void setPersonnelNumTotal(String personnelNumTotal) {
        this.personnelNumTotal = personnelNumTotal;
    }

    public String getAllowPersonnel() {
        return allowPersonnel;
    }

    public void setAllowPersonnel(String allowPersonnel) {
        this.allowPersonnel = allowPersonnel;
    }

    public String getRefusePersonnel() {
        return refusePersonnel;
    }

    public void setRefusePersonnel(String refusePersonnel) {
        this.refusePersonnel = refusePersonnel;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
