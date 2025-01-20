package com.px.module.wisdomCdc.entity;

import com.px.module.common.base.BaseModel;
import com.px.module.common.customannotation.AttributeAnnotation;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;

/**
 * @Package com.px.module.wisdomCdc.entity
 * @ClassName: GoodModel
 * @Description: 物资
 * @author 品讯科技
 * @time   2019年2月18日
 * @version V 1.0
 */
public class GoodModel extends BaseModel {

    @ApiModelProperty(value = "物资编号", dataType = "String", example = "WZ0001")
    @AttributeAnnotation(value="good_code")
    @Size(min=1,max=32,message = "物资编号")
    private String goodCode;//物资编号

    @ApiModelProperty(value = "物资名称", dataType = "String", example = "口罩")
    @AttributeAnnotation(value="good_name")
    private String goodName;//物资名称

    @ApiModelProperty(value = "物资图片", dataType = "String", example = "http://XXXX.png")
    @AttributeAnnotation(value="good_pic")
    private String goodPic;//物资图片

    @ApiModelProperty(value = "计量单位id", dataType = "String", example = "1")
    @AttributeAnnotation(value="measure_unit_id")
    private String measureUnitId;//计量单位id

    @ApiModelProperty(value = "计量单位", dataType = "String", example = "个")
    @AttributeAnnotation(value="measure_unit")
    private String measureUnit;//计量单位

    @ApiModelProperty(value = "规格型号id", dataType = "String", example = "1")
    @AttributeAnnotation(value="specs_model_id")
    private String specsModelId;//规格型号id

    @ApiModelProperty(value = "规格型号", dataType = "String", example = "XX/SCG")
    @AttributeAnnotation(value="specs_model_name")
    private String specsModelName;//规格型号


    @ApiModelProperty(value = "类型代码", dataType = "String", example = "CODE0002")
    @AttributeAnnotation(value="good_type_code")
    @Size(min=1,max=32,message = "类型代码")
    private String goodTypeCode;//类型代码

    @ApiModelProperty(value = "类型名称", dataType = "String", example = "医学药剂")
    @AttributeAnnotation(value="good_type_name")
    private String goodTypeName;//类型名称

    @ApiModelProperty(value = "库存上限", dataType = "String", example = "1")
    @AttributeAnnotation(value="stock_upper")
    private String stockUpper;//库存上限

    @ApiModelProperty(value = "库存上限", dataType = "String", example = "100")
    @AttributeAnnotation(value="stock_lower")
    private String stockLower;//库存下限

    private String stockNum;

    @ApiModelProperty(value = "备注", dataType = "String", example = "物资备注")
    @AttributeAnnotation(value="remarks")
    private String remarks;//备注


    @ApiModelProperty(value = "状态, 0 已下架，1已上架", dataType = "String", example = "0")
    @AttributeAnnotation(value="status")
    private String status;//状态

    @ApiModelProperty(value = "是否删除, 0 未删除，1删除", dataType = "String", example = "1")
    @AttributeAnnotation(value="is_removed")
    private String isRemoved;//是否删除

    @AttributeAnnotation(value="goodTypeCodeList")
    private String goodTypeCodeList;//物资类型列表

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

    public String getGoodPic() {
        return goodPic;
    }

    public void setGoodPic(String goodPic) {
        this.goodPic = goodPic;
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

    public String getGoodTypeCode() {
        return goodTypeCode;
    }

    public void setGoodTypeCode(String goodTypeCode) {
        this.goodTypeCode = goodTypeCode;
    }

    public String getGoodTypeName() {
        return goodTypeName;
    }

    public void setGoodTypeName(String goodTypeName) {
        this.goodTypeName = goodTypeName;
    }

    public String getStockUpper() {
        return stockUpper;
    }

    public void setStockUpper(String stockUpper) {
        this.stockUpper = stockUpper;
    }

    public String getStockLower() {
        return stockLower;
    }

    public void setStockLower(String stockLower) {
        this.stockLower = stockLower;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getIsRemoved() {
        return isRemoved;
    }

    @Override
    public void setIsRemoved(String isRemoved) {
        this.isRemoved = isRemoved;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getGoodTypeCodeList() {
        return goodTypeCodeList;
    }

    public void setGoodTypeCodeList(String goodTypeCodeList) {
        this.goodTypeCodeList = goodTypeCodeList;
    }
}
