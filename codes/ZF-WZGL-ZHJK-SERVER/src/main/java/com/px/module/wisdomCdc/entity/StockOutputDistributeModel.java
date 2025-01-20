package com.px.module.wisdomCdc.entity;

import com.px.module.common.base.BaseModel;
import com.px.module.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

/**
 * @Package com.px.module.wisdomCdc.entity
 * @ClassName: StockOutputModel
 * @Description: 出库表明细表
 * @author 品讯科技
 * @time   2022年7月2日
 * @version V 1.0
 */
public class StockOutputDistributeModel extends BaseModel {

    @ApiModelProperty(value = "出库单号", dataType = "String", example = "OUT001")
    @AttributeAnnotation(value="output_no")
    @Size(min=1,max=32,message = "出库单号")
    private String outputNo;//出库单号

    @ApiModelProperty(value = "仓库ID", dataType = "String", example = "storeNo001")
    @AttributeAnnotation(value="store_no")
    private String storeNo;//仓库ID

    @ApiModelProperty(value = "库位ID", dataType = "String", example = "1")
    @AttributeAnnotation(value="grid_id")
    private String gridId;//库位ID

    @ApiModelProperty(value = "物资编码", dataType = "String", example = "GOODID0001")
    @AttributeAnnotation(value="good_code")
    private String goodCode;//物资编码

    @ApiModelProperty(value = "供应商编号", dataType = "String", example = "GYS0001")
    @AttributeAnnotation(value="supplier_no")
    private String supplierNo;//供应商编号

    @ApiModelProperty(value = "出库数量", dataType = "String", example = "1")
    @AttributeAnnotation(value="output_num")
    private String output_num;//出库数量

    @ApiModelProperty(value = "出库前库存总数", dataType = "String", example = "777")
    @AttributeAnnotation(value="before_output_num")
    private String before_output_num;//出库前库存总数


    @ApiModelProperty(value = "出库时间", dataType = "String", example = "2022-11-11 11:11:11")
    @AttributeAnnotation(value="output_time")
    private String output_time;//出库时间



    @ApiModelProperty(value = "领取时间", dataType = "String", example = "2022-11-11 11:11:11")
    @AttributeAnnotation(value="receiveTime")
    private String receiveTime;//领取时间


    @ApiModelProperty(value = "领取人code", dataType = "String", example = "22222")
    @AttributeAnnotation(value="receiveUserCode")
    private String receiveUserCode;//领取人code

    @ApiModelProperty(value = "领取人Name", dataType = "String", example = "领取人姓名")
    @AttributeAnnotation(value="receiveUserName")
    private String receiveUserName;//领取人Name

    @ApiModelProperty(value = "备注", dataType = "String", example = "领取人姓名")
    @AttributeAnnotation(value="receiveRemarks")
    private String receiveRemarks;//备注

    @ApiModelProperty(value = "领取状态", dataType = "String", example = "0未领取1已领取")
    @AttributeAnnotation(value="distribute_status")
    private String distributeStatus;//领取状态




    private String distributeStatusName;//物资领取状态名称
    private String outputNum;//物资出库数量
    private String goodName;
    private String applyDeptName;//部门名称


    private String measureUnitId;//计量单位id

    private String measureUnit;//计量单位

    private String specsModelId;//规格型号id

    private String specsModelName;//规格型号


    public String getOutputNo() {
        return outputNo;
    }

    public void setOutputNo(String outputNo) {
        this.outputNo = outputNo;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getGridId() {
        return gridId;
    }

    public void setGridId(String gridId) {
        this.gridId = gridId;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    public String getOutput_num() {
        return output_num;
    }

    public void setOutput_num(String output_num) {
        this.output_num = output_num;
    }

    public String getBefore_output_num() {
        return before_output_num;
    }

    public void setBefore_output_num(String before_output_num) {
        this.before_output_num = before_output_num;
    }

    public String getOutput_time() {
        return output_time;
    }

    public void setOutput_time(String output_time) {
        this.output_time = output_time;
    }


    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReceiveUserCode() {
        return receiveUserCode;
    }

    public void setReceiveUserCode(String receiveUserCode) {
        this.receiveUserCode = receiveUserCode;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }

    public String getReceiveRemarks() {
        return receiveRemarks;
    }

    public void setReceiveRemarks(String receiveRemarks) {
        this.receiveRemarks = receiveRemarks;
    }

    public String getDistributeStatus() {
        return distributeStatus;
    }

    public void setDistributeStatus(String distributeStatus) {
        this.distributeStatus = distributeStatus;
    }

    public String getDistributeStatusName() {
        return distributeStatusName;
    }

    public void setDistributeStatusName(String distributeStatusName) {
        this.distributeStatusName = distributeStatusName;
    }

    public String getOutputNum() {
        return outputNum;
    }

    public void setOutputNum(String outputNum) {
        this.outputNum = outputNum;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getApplyDeptName() {
        return applyDeptName;
    }

    public void setApplyDeptName(String applyDeptName) {
        this.applyDeptName = applyDeptName;
    }

    public String getMeasureUnitId() {
        return measureUnitId;
    }

    public void setMeasureUnitId(String measureUnitId) {
        this.measureUnitId = measureUnitId;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getSpecsModelId() {
        return specsModelId;
    }

    public void setSpecsModelId(String specsModelId) {
        this.specsModelId = specsModelId;
    }

    public String getSpecsModelName() {
        return specsModelName;
    }

    public void setSpecsModelName(String specsModelName) {
        this.specsModelName = specsModelName;
    }
}
