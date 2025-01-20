package com.px.module.wisdomCdc.service;

import com.px.module.common.base.IBaseService;
import com.px.module.common.base.Result;
import com.px.module.wisdomCdc.dto.GoodDto;
public interface IGoodService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param goodDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-06-27 22:48:21
     * @throws
     * @return Result
     */
    Result findAll(GoodDto goodDto);

    /**
     * @name findGoodStockAll
     * @author 品讯科技
     * @param goodDto
     * @Description 根据条件查询所有数据并分页  物资和库存关联 入库时候选择物资用
     * @time 2022-06-27 22:48:21
     * @throws
     * @return Result
     */
    Result findGoodStockAll(GoodDto goodDto);
}
