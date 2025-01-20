package com.px.module.wisdomCdc.service;

import com.px.module.common.base.IBaseService;
import com.px.module.common.base.Result;
import com.px.module.wisdomCdc.dto.AppDto;
public interface IAppService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param appDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-27 11:32:37
     * @throws
     * @return Result
     */
    Result findAll(AppDto appDto);
}
