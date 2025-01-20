package com.px.module.wisdomCdc.service;

import com.px.module.common.base.IBaseService;
import com.px.module.common.base.Result;
import com.px.module.wisdomCdc.dto.SMessageDto;
public interface ISMessageService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param sMessageDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-05 17:06:38
     * @throws
     * @return Result
     */
    Result findAll(SMessageDto sMessageDto);
}
