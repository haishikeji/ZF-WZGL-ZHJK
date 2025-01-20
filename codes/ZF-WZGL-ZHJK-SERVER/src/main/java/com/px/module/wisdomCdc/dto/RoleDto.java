package com.px.module.wisdomCdc.dto;

import com.alibaba.fastjson.JSONObject;
import com.px.module.wisdomCdc.entity.RoleModel;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(value = "RoleDto", description = "Role数据传输对象")
public class RoleDto extends RoleModel {
    List<JSONObject> menuData;

    String type;

    public List<JSONObject> getMenuData() {
        return menuData;
    }

    public void setMenuData(List<JSONObject> menuData) {
        this.menuData = menuData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
