package com.px.module.wisdomCdc.entity;

import com.px.module.common.base.BaseModel;
import com.px.module.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

/**
 * @Package com.px.module.wisdomCdc.entity
 * @ClassName: StockModel
 * @Description: 库存总表
 * @author 品讯科技
 * @time   2022年7月2日
 * @version V 1.0
 */
public class StockModel extends BaseModel {

    @ApiModelProperty(value = "库存编号", dataType = "String", example = "STOCK001")
    @AttributeAnnotation(value="stock_no")
    @Size(min=1,max=32,message = "库存编号")
    private String stockNo;//库存编号

    @ApiModelProperty(value = "仓库ID", dataType = "String", example = "storeNo001")
    @AttributeAnnotation(value="store_no")
    private String storeNo;//仓库ID

    @ApiModelProperty(value = "物资编码", dataType = "String", example = "GOODID0001")
    @AttributeAnnotation(value="good_code")
    private String goodCode;//物资编码

    @ApiModelProperty(value = "供应商编号", dataType = "String", example = "GYS0001")
    @AttributeAnnotation(value="supplier_no")
    private String supplierNo;//供应商编号

    @ApiModelProperty(value = "库存数量", dataType = "String", example = "888")
    @AttributeAnnotation(value="stock_num")
    private String stockNum;//库存数量

    private String stockLower;//库存下限
    private String stockUpper;//库存上限
    private String goodTypeName;//物资类型名称
    private String goodTypeCode;//物资类型编号


    public String getStockNo() {
        return stockNo;
    }

    public void setStockNo(String stockNo) {
        this.stockNo = stockNo;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
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

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }


    @ApiModelProperty(value = "仓库名称", dataType = "String", example = "GYS2022030101")
    private String storeName;
    @ApiModelProperty(value = "物资名称", dataType = "String", example = "GYS2022030101")
    private String goodName;
    @ApiModelProperty(value = "供应商名称", dataType = "String", example = "GYS2022030101")
    private String supplierName;

    private String measureUnit;

    private String specsModelName;
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getSpecsModelName() {
        return specsModelName;
    }

    public void setSpecsModelName(String specsModelName) {
        this.specsModelName = specsModelName;
    }

    public String getStockLower() {
        return stockLower;
    }

    public void setStockLower(String stockLower) {
        this.stockLower = stockLower;
    }

    public String getStockUpper() {
        return stockUpper;
    }

    public void setStockUpper(String stockUpper) {
        this.stockUpper = stockUpper;
    }

    public String getGoodTypeName() {
        return goodTypeName;
    }

    public void setGoodTypeName(String goodTypeName) {
        this.goodTypeName = goodTypeName;
    }

    public String getGoodTypeCode() {
        return goodTypeCode;
    }

    public void setGoodTypeCode(String goodTypeCode) {
        this.goodTypeCode = goodTypeCode;
    }
}
