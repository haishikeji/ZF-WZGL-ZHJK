package com.px.module.wisdomCdc.service;

import com.px.module.common.base.IBaseService;
import com.px.module.common.base.Result;
import com.px.module.wisdomCdc.dto.StockOutputApplyDto;
import com.px.module.wisdomCdc.entity.StockOutputApplyModel;

public interface IStockOutputApplyService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param stockOutputApplyDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-02 22:59:07
     * @throws
     * @return Result
     */
    Result findAll(StockOutputApplyDto stockOutputApplyDto);



    /**
     * 保存物资申请
     * @param stockOutputApplyModel
     * @return
     */
    Result stockOutputApplySave(StockOutputApplyModel stockOutputApplyModel);


    /**
     * 更新物资申请
     * @param stockOutputApplyModel
     * @return
     */
    Result stockOutputApplyUpdate(StockOutputApplyModel stockOutputApplyModel);

    /**
     * 提交物资申请
     * @param stockOutputApplyModel
     * @return
     */
    Result stockOutputApplySubmit(StockOutputApplyModel stockOutputApplyModel);

    /**
     * 审批通过物资申请
     * @param stockOutputApplyModel
     * @return
     */
    Result stockOutputApplyApprove(StockOutputApplyModel stockOutputApplyModel);

    /**
     * 根据出库单号调用出库明细信息
     * @param outputNo
     * @return
     */
    Result findStockOutputApplyByOutputNo(String outputNo);

    /**
     * 删除出库信息
     * @param outputNo
     * @return
     */
    Result logicDeleteByOutputNo(String outputNo);


    /**
     * 根据手机号查询出库所有申请信息
     * @param stockOutputApplyDto
     * @return
     */
    Result findAllByPhone(StockOutputApplyDto stockOutputApplyDto);

    /**
     * 手机查询未领取信息
     * @param stockOutputApplyDto
     * @return
     */
    Result findAllByPhoneNoDistribute(StockOutputApplyDto stockOutputApplyDto);

    /**
     * 手机查询领取过信息
     * @param stockOutputApplyDto
     * @return
     */
    Result findAllByPhoneDistribute(StockOutputApplyDto stockOutputApplyDto);

    Result report(StockOutputApplyDto stockOutputApplyDto);

    Result reportDistribute(StockOutputApplyDto stockOutputApplyDto);


//    /**
//     * 手机端直接提交申请
//     * @param stockOutputApplyModel
//     * @return
//     */
//    Result stockOutputApplyAppSubmit(StockOutputApplyModel stockOutputApplyModel);
}
