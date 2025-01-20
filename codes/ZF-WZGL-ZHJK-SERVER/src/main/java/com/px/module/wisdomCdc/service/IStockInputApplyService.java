package com.px.module.wisdomCdc.service;

import com.px.module.common.base.IBaseService;
import com.px.module.common.base.Result;
import com.px.module.wisdomCdc.dto.StockInputApplyDto;
import com.px.module.wisdomCdc.entity.StockInputApplyModel;

public interface IStockInputApplyService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param stockInputApplyDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    Result findAll(StockInputApplyDto stockInputApplyDto);


    /**
     * 保存物资申请
     * @param stockInputApplyModel
     * @return
     */
    Result stockInputApplySave(StockInputApplyModel stockInputApplyModel);


    /**
     * 更新物资申请
     * @param stockInputApplyModel
     * @return
     */
    Result stockInputApplyUpdate(StockInputApplyModel stockInputApplyModel);

    /**
     * 提交物资申请
     * @param stockInputApplyModel
     * @return
     */
    Result stockInputApplySubmit(StockInputApplyModel stockInputApplyModel);

    /**
     * 审批通过物资申请
     * @param stockInputApplyModel
     * @return
     */
    Result stockInputApplyApprove(StockInputApplyModel stockInputApplyModel);

    /**
     * 根据入库单号查询入库单信息和详情
     * @param inputNo
     * @return
     */
    Result findStockInputApplyByInputNo(String inputNo);

    /**
     * 物资入库申请删除inputNo，级联删除明细
     * @param inputNo
     * @return
     */
    Result logicDeleteByInputNo(String inputNo);

    Result report(StockInputApplyDto stockInputApplyDto);
}
