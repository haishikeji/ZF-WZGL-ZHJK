package com.px.module.wisdomCdc.service;

import com.alibaba.fastjson.JSONObject;
import com.px.module.wisdomCdc.dto.MenuDto;
import com.px.module.common.base.IBaseService;
import com.px.module.common.base.Result;

import java.util.List;

public interface IMenuService<T>  extends IBaseService<T> {

    List<JSONObject> findMenu();

    List<JSONObject> findUserMenuByUserId(JSONObject jsonObject);

    /**
     * 获取不带按钮的全部菜单
     * @param jsonObject
     * @return
     */
    Result menuRecurrence(JSONObject jsonObject);

    /**
     * 获取该用户不带按钮的全部菜单
     * @param jsonObject
     * @return
     */
    Result menuRecurrenceUser(JSONObject jsonObject);
    Result findMenuByUserId(JSONObject jsonObject);
    

    Result findAll(MenuDto menuDto);

    /**
     * 根据角色查询菜单id
     * @param roleId
     * @return
     */
    Result findMenuByRoleId(String roleId);

}
