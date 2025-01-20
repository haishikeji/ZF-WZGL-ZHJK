package com.px.module.wisdomCdc.entity;

import com.px.module.common.base.BaseModel;
import com.px.module.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Package com.px.module.wisdomCdc.entity
 * @ClassName: StockInputApplyModel
 * @Description: 入库表
 * @author 品讯科技
 * @time   2022年7月2日
 * @version V 1.0
 */
public class StockInputApplyModel extends BaseModel {

    @ApiModelProperty(value = "入库单号", dataType = "String", example = "IN001")
    @AttributeAnnotation(value="input_no")
    @Size(min=1,max=32,message = "入库单号")
    private String inputNo;//入库单号

    @ApiModelProperty(value = "仓库ID", dataType = "String", example = "storeNo001")
    @AttributeAnnotation(value="store_no")
    private String storeNo;//仓库ID

    @ApiModelProperty(value = "供应商编号", dataType = "String", example = "GYS0001")
    @AttributeAnnotation(value="supplier_no")
    private String supplierNo;//供应商ID



    @ApiModelProperty(value = "申请部门", dataType = "String", example = "101")
    @AttributeAnnotation(value="applyDept")
    private String applyDept;//申请部门

    @ApiModelProperty(value = "申请人CODE", dataType = "String", example = "999")
    @AttributeAnnotation(value="applyUserCode")
    private String applyUserCode;//申请人CODE

    @ApiModelProperty(value = "申请人NAME", dataType = "String", example = "999")
    @AttributeAnnotation(value="applyUserName")
    private String applyUserName;//申请人NAME

    @ApiModelProperty(value = "申请人电话", dataType = "String", example = "999")
    @AttributeAnnotation(value="applyPhone")
    private String applyPhone;//申请人电话

    @ApiModelProperty(value = "申请时间", dataType = "String", example = "2022-12-12 12:12:12")
    @AttributeAnnotation(value="applyTime")
    private String applyTime;//申请时间    提交的时间就是申请时间

    @ApiModelProperty(value = "审批人CODE", dataType = "String", example = "22211")
    @AttributeAnnotation(value="approveUserCode")
    private String approveUserCode;//审批人CODE

    @ApiModelProperty(value = "审批人NAME", dataType = "String", example = "22211")
    @AttributeAnnotation(value="approveUserName")
    private String approveUserName;//审批人NAME

    @ApiModelProperty(value = "审批时间", dataType = "String", example = "2022-12-12 12:12:12")
    @AttributeAnnotation(value="approveTime")
    private String approveTime;//审批时间    如果审批通过  此事件就是出库时间

    @ApiModelProperty(value = "审批内容", dataType = "String", example = "同意审批，不同意审批")
    @AttributeAnnotation(value="approveContent")
    private String approveContent;//审批内容

    @ApiModelProperty(value = "审批状态(0待审批1已出库)", dataType = "String", example = "0")
    @AttributeAnnotation(value="approveStatus")
    private String approveStatus;//审批状态(0待审批1已出库)


    @ApiModelProperty(value = "是否删除", dataType = "String", example = "1",hidden = true)
    @AttributeAnnotation(value="is_removed",isGenerate =false)
    private String isRemoved;//是否删除



    @ApiModelProperty(value = "入库明细", dataType = "java.util.List<StockInputModel>")
    @AttributeAnnotation(value = "入库明细")
    private List<StockInputModel> stockInputModels;


    private String approveStatusName;//审批状态(0草稿1待审批2已入库3拒绝入库)
    private String supplierName;//供应商名称
    private String storeName;//仓库名称
    private String applyDeptName;//部门名称

    public List<StockInputModel> getStockInputModels() {
        return stockInputModels;
    }

    public void setStockInputModels(List<StockInputModel> stockInputModels) {
        this.stockInputModels = stockInputModels;
    }

    public String getInputNo() {
        return inputNo;
    }

    public void setInputNo(String inputNo) {
        this.inputNo = inputNo;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getApplyDept() {
        return applyDept;
    }

    public void setApplyDept(String applyDept) {
        this.applyDept = applyDept;
    }

    public String getApplyUserCode() {
        return applyUserCode;
    }

    public void setApplyUserCode(String applyUserCode) {
        this.applyUserCode = applyUserCode;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getApplyPhone() {
        return applyPhone;
    }

    public void setApplyPhone(String applyPhone) {
        this.applyPhone = applyPhone;
    }

    public String getApproveUserCode() {
        return approveUserCode;
    }

    public void setApproveUserCode(String approveUserCode) {
        this.approveUserCode = approveUserCode;
    }

    public String getApproveUserName() {
        return approveUserName;
    }

    public void setApproveUserName(String approveUserName) {
        this.approveUserName = approveUserName;
    }

    public String getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(String approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveContent() {
        return approveContent;
    }

    public void setApproveContent(String approveContent) {
        this.approveContent = approveContent;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    @Override
    public String getIsRemoved() {
        return isRemoved;
    }

    @Override
    public void setIsRemoved(String isRemoved) {
        this.isRemoved = isRemoved;
    }

    public String getApproveStatusName() {
        return approveStatusName;
    }

    public void setApproveStatusName(String approveStatusName) {
        this.approveStatusName = approveStatusName;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getApplyDeptName() {
        return applyDeptName;
    }

    public void setApplyDeptName(String applyDeptName) {
        this.applyDeptName = applyDeptName;
    }
}
