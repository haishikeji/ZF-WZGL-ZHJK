package com.px.module.wisdomCdc.dto;

import com.px.module.wisdomCdc.entity.CountMapDataModel;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(value = "CountMapDataDto", description = "CountMapData数据传输对象")
public class CountMapDataDto extends CountMapDataModel {

    private List<CountMapDataModel> countMapDataList ;
    private String countNewValue;
    private String countOldValue;

    public String getCountNewValue() {
        return countNewValue;
    }

    public void setCountNewValue(String countNewValue) {
        this.countNewValue = countNewValue;
    }

    public List<CountMapDataModel> getCountMapDataList() {
        return countMapDataList;
    }

    public void setCountMapDataList(List<CountMapDataModel> countMapDataList) {
        this.countMapDataList = countMapDataList;
    }

    public String getCountOldValue() {
        return countOldValue;
    }

    public void setCountOldValue(String countOldValue) {
        this.countOldValue = countOldValue;
    }
}
