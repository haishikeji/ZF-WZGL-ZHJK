package com.px.module.wisdomCdc.dto;

import com.px.module.common.utils.Excel;
import com.px.module.wisdomCdc.entity.StockApproveModel;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "StockApproveDto", description = "StockApprove数据传输对象")
public class StockApproveDayDtoExcel extends StockApproveModel {

    /**
     * 报表查询起始时间
     */
    private String dateStart;
    private String dateEnd;

    @Excel(name = "日期")
    private String date;
    @Excel(name = "物资编号")
    private String goodCode;//申请单号  对应出入库单号
    @Excel(name = "物资名称")
    private String goodName;//申请部门名称
    @Excel(name = "出库数量")
    private String outtotal;//申请人NAME
    @Excel(name = "入库数量")
    private String intotal;//申请人电话

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getOuttotal() {
        return outtotal;
    }

    public void setOuttotal(String outtotal) {
        this.outtotal = outtotal;
    }

    public String getIntotal() {
        return intotal;
    }

    public void setIntotal(String intotal) {
        this.intotal = intotal;
    }
}
