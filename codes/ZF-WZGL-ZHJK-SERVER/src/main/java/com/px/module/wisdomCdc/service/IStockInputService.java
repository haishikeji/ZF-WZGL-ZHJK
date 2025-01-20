package com.px.module.wisdomCdc.service;

import com.px.module.common.base.IBaseService;
import com.px.module.common.base.Result;
import com.px.module.wisdomCdc.dto.StockInputDto;
import com.px.module.wisdomCdc.entity.StockInputApplyModel;
import com.px.module.wisdomCdc.entity.StockInputModel;

import java.util.List;

public interface IStockInputService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param stockInputDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-02 23:29:27
     * @throws
     * @return Result
     */
    Result findAll(StockInputDto stockInputDto);

    /**
     * 批量插入入库信息明细
     * @param stockInputModels
     * @return
     */
    boolean batchSaveStockInput(StockInputApplyModel stockInputApplyModel,List<StockInputModel> stockInputModels);

    /**
     * 根据入库单号删除信息
     * @param inputNo
     * @return
     */
    boolean deleteByInputNo(String inputNo);
}
