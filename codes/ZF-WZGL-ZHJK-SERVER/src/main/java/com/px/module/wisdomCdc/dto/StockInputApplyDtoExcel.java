package com.px.module.wisdomCdc.dto;

import com.px.module.common.utils.Excel;
import com.px.module.wisdomCdc.entity.StockInputApplyModel;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "StockInputApplyDto", description = "StockInputApply数据传输对象")
public class StockInputApplyDtoExcel extends StockInputApplyModel {

    @Excel(name = "入库单号")
    private String inputNo;//入库单号

    @Excel(name = "仓库名称")
    private String storeName;//仓库名称

    @Excel(name = "供应商名称")
    private String supplierName;//供应商名称

    @Excel(name = "入库申请人")
    private String applyUserName;//入库申请人

    @Excel(name = "入库申请提交时间")
    private String applyTime;//入库申请提交时间

    @Excel(name = "审批状态")
    private String approveStatusName;//审批状态(0草稿1待审批2已入库3拒绝入库)

    @Override
    public String getInputNo() {
        return inputNo;
    }

    @Override
    public void setInputNo(String inputNo) {
        this.inputNo = inputNo;
    }

    @Override
    public String getStoreName() {
        return storeName;
    }

    @Override
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public String getSupplierName() {
        return supplierName;
    }

    @Override
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public String getApplyUserName() {
        return applyUserName;
    }

    @Override
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    @Override
    public String getApplyTime() {
        return applyTime;
    }

    @Override
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    @Override
    public String getApproveStatusName() {
        return approveStatusName;
    }

    @Override
    public void setApproveStatusName(String approveStatusName) {
        this.approveStatusName = approveStatusName;
    }
}
