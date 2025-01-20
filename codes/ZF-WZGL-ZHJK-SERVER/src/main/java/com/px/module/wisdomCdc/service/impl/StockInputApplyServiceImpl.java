package com.px.module.wisdomCdc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.px.module.common.base.BaseService;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import com.px.module.common.exception.RequestException;
import com.px.module.common.utils.IDNumberUtil;
import com.px.module.common.utils.JsonUtil;
import com.px.module.common.utils.TokenUtil;
import com.px.module.common.utils.UUIDUtil;
import com.px.module.wisdomCdc.dao.IStockInputApplyMapper;
import com.px.module.wisdomCdc.dao.IStockInputMapper;
import com.px.module.wisdomCdc.dao.IStockMapper;
import com.px.module.wisdomCdc.dto.StockInputApplyDto;
import com.px.module.wisdomCdc.dto.StockInputDto;
import com.px.module.wisdomCdc.entity.StockInputApplyModel;
import com.px.module.wisdomCdc.entity.StockInputModel;
import com.px.module.wisdomCdc.entity.StockModel;
import com.px.module.wisdomCdc.service.IStockInputApplyService;
import com.px.module.wisdomCdc.service.IStockInputService;
import com.px.module.wisdomCdc.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Package com.px.module.wisdomCdc.service.impl
 * @ClassName StockInputApplyServiceImpl
 * @Description StockInputApplyServiceImpl
 * @author 品讯科技
 * @time   2022-07-02 23:07:46
 * @version V 1.0
 */
@Service
public class StockInputApplyServiceImpl extends BaseService  implements IStockInputApplyService<StockInputApplyModel> {
    @Autowired
    public IStockInputApplyMapper stockInputApplyMapper;//入库申请表

    @Autowired
    public IStockInputService stockInputService;//入库存信息表

    @Autowired
    public IStockService stockService;  //库存信息表

    @Autowired
    public IStockMapper stockMapper;  //库存信息表

    @Autowired
    public IStockInputMapper stockInputMapper;

    private static Logger logger = LoggerFactory.getLogger(StockInputApplyServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param stockInputApplyModel
     * @Description 保存
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(StockInputApplyModel stockInputApplyModel) {
        try {
            String id=UUIDUtil.get32UUID ();
            stockInputApplyModel.setId (id);
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(stockInputApplyModel));
            if(stockInputApplyMapper.save(stockInputApplyModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入StockInputApplyModel成功-------------");
            return getResult(ReturnMsgEnum.Save_Success, id);
        } catch (Exception e) {
            logger.info ("插入失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(stockInputApplyMapper.deleteByIds(ids) < 1) {
                return getResult(ReturnMsgEnum.Delete_Failure);
            }
        } catch (Exception e) {
            logger.info ("删除失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
        logger.info ("-------------删除成功-------------");
        return getResult(ReturnMsgEnum.Delete_Success);
    }

    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 逻辑删除
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(stockInputApplyMapper.logicDeleteByIds(ids) < 1) {
                return getResult(ReturnMsgEnum.Delete_Failure);
            }
        } catch (Exception e) {
            logger.info ("删除失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
        logger.info ("-------------逻辑删除失败-------------");
        return getResult(ReturnMsgEnum.Delete_Success);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param stockInputApplyModel
     * @Description 修改
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(StockInputApplyModel stockInputApplyModel) {
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(stockInputApplyModel));
        try {
            if(stockInputApplyMapper.update(stockInputApplyModel) < 1) {
                return getResult(ReturnMsgEnum.Update_Failure);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info ("修改失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
        return getResult(ReturnMsgEnum.Update_Success);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据条件查询所有数据
    * @time 2022-07-02 23:07:46
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, stockInputApplyMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param stockInputApplyDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(StockInputApplyDto stockInputApplyDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(stockInputApplyDto));
        try {
            page(stockInputApplyDto);//分页判断
            return getResult(ReturnMsgEnum.OK, stockInputApplyMapper.findAll(stockInputApplyDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

///////////////////////////////////////////////////////////////////////////自定义分割线
    /**
     * @name stockInputApplySave
     * @author 品讯科技
     * @param stockInputApplyModel
     * @Description 保存
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result stockInputApplySave(StockInputApplyModel stockInputApplyModel) {
        try {
            String id=UUIDUtil.get32UUID ();
            stockInputApplyModel.setId (id);
            IDNumberUtil worker = new IDNumberUtil (1, 10);//第二个参数是  长度
            String inputNo = "WZRK"+worker.nextId ();
            stockInputApplyModel.setInputNo(inputNo);
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(stockInputApplyModel));
            //批量插入入库信息   修改的时候需要先删除
            if (!stockInputService.batchSaveStockInput(stockInputApplyModel,stockInputApplyModel.getStockInputModels())) {
                throw new RequestException(ReturnMsgEnum.Save_Failed.getMsg(), ReturnMsgEnum.Save_Failed);
            }
            if(stockInputApplyMapper.save(stockInputApplyModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入StockInputApplyModel成功-------------");
            return getResult(ReturnMsgEnum.Save_Success, id);
        } catch (Exception e) {
            logger.info ("插入失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }



    /**
     * @name stockInputApplyUpdate
     * @author 品讯科技
     * @param stockInputApplyModel
     * @Description 更新
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result stockInputApplyUpdate(StockInputApplyModel stockInputApplyModel) {
        try {
            logger.info("更新实体对象数据："+JsonUtil.entityConvertToJson(stockInputApplyModel));
            //拒绝的时候  不需要更新子表
            //删除根据入库单号
            if (!stockInputService.deleteByInputNo(stockInputApplyModel.getInputNo())) {
                throw new RequestException(ReturnMsgEnum.Delete_Failure.getMsg(), ReturnMsgEnum.Delete_Failure);
            }
            //批量插入入库信息
            if (!stockInputService.batchSaveStockInput(stockInputApplyModel,stockInputApplyModel.getStockInputModels())) {
                throw new RequestException(ReturnMsgEnum.Save_Failure.getMsg(), ReturnMsgEnum.Save_Failure);
            }
            if(stockInputApplyMapper.update(stockInputApplyModel) < 1) {
                return getResult(ReturnMsgEnum.Update_Failure);
            }//主表最后操作
            logger.info ("-------------插入StockInputApplyModel成功-------------");
            return getResult(ReturnMsgEnum.Update_Success);
        } catch (Exception e) {
            logger.info ("插入失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name stockInputApplySubmit
     * @author 品讯科技
     * @param stockInputApplyModel
     * @Description 提交申请功能
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result stockInputApplySubmit(StockInputApplyModel stockInputApplyModel) {
        try {
            logger.info("更新实体对象数据："+JsonUtil.entityConvertToJson(stockInputApplyModel));
            //删除根据入库单号
            if (!stockInputService.deleteByInputNo(stockInputApplyModel.getInputNo())) {
                throw new RequestException(ReturnMsgEnum.Delete_Failure.getMsg(), ReturnMsgEnum.Delete_Failure);
            }
            //批量插入入库信息
            if (!stockInputService.batchSaveStockInput(stockInputApplyModel,stockInputApplyModel.getStockInputModels())) {
                throw new RequestException(ReturnMsgEnum.Update_Failure.getMsg(), ReturnMsgEnum.Save_Failure);
            }
            if(stockInputApplyMapper.update(stockInputApplyModel) < 1) {
                return getResult(ReturnMsgEnum.Update_Failure);
            }
            logger.info ("-------------插入StockInputApplyModel成功-------------");
            return getResult(ReturnMsgEnum.Update_Success);
        } catch (Exception e) {
            logger.info ("插入失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }


    /**
     * @name stockInputApplyApprove
     * @author 品讯科技
     * @param stockInputApplyModel
     * @Description 审批申请功能
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result stockInputApplyApprove(StockInputApplyModel stockInputApplyModel) {
        try {
            logger.info("更新实体对象数据："+JsonUtil.entityConvertToJson(stockInputApplyModel));
            //审批的时候只需要更新主表
//            //删除根据入库单号
//            if (!stockInputService.deleteByInputNo(stockInputApplyModel.getInputNo())) {
//                throw new RequestException(ReturnMsgEnum.Delete_Failure.getMsg(), ReturnMsgEnum.Delete_Failure);
//            }
//            //批量插入入库信息
//            if (!stockInputService.batchSaveStockInput(stockInputApplyModel,stockInputApplyModel.getStockInputModels())) {
//                throw new RequestException(ReturnMsgEnum.Save_Failure.getMsg(), ReturnMsgEnum.Save_Failure);
//            }
            StockInputDto query = new StockInputDto();
            query.setInputNo( stockInputApplyModel.getInputNo() );
            List<StockInputModel> stockOutputModelListCur = stockInputMapper.findAll(query);
            //此处需要更新库存功能
            if(stockOutputModelListCur.size()>0){
                List<StockInputModel> stockInputModelList = stockOutputModelListCur;// stockInputApplyModel.getStockInputModels();
                for(int i=0;i<stockInputModelList.size();i++){
                    String stockInputModelId= UUIDUtil.get32UUID();
                    stockInputModelList.get( i ).setId(stockInputModelId );
                    stockInputModelList.get( i ).setInputNo( stockInputApplyModel.getInputNo() );
                    stockInputModelList.get( i ).setStoreNo( stockInputApplyModel.getStoreNo() );
                    stockInputModelList.get( i ).setSupplierNo( stockInputApplyModel.getSupplierNo() );

                    StockModel stockModel = stockService.findByGoodCode(stockInputModelList.get(i).getGoodCode());//根据物资编号查询物资信息
                    if(stockModel == null){//没有查询到物资  新增
                        StockModel curStockModel = new StockModel();
                        curStockModel.setId( UUIDUtil.get32UUID() );
                        IDNumberUtil worker = new IDNumberUtil (1, 10);//第二个参数是  长度
                        String stockNo = "STOCKNO"+worker.nextId ();
                        curStockModel.setStockNo( stockNo );
                        curStockModel.setStoreNo( stockInputModelList.get( i ).getStoreNo());
                        curStockModel.setGoodCode(stockInputModelList.get( i ).getGoodCode());
                        curStockModel.setSupplierNo( stockInputModelList.get( i ).getSupplierNo() );
//                        curStockModel.setStockNo(stockInputModelList.get( i ).getSupplierNo()); 此编号目前没有
                        curStockModel.setStockNum(stockInputModelList.get( i ).getInput_num());
                        curStockModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        curStockModel.setCreateUserCode( TokenUtil.getUserId(stockInputApplyModel.getToken()));
                        curStockModel.setCreateUserName(TokenUtil.getUser(stockInputApplyModel.getToken()).get("userName").toString());
                        if(stockMapper.save( curStockModel) < 1) {  //库存原来没有   进行新增保存
                            return getResult(ReturnMsgEnum.Save_Failed);
                        }
                    }else{//查询到物资  修改
                        stockModel.setStockNum( Integer.parseInt(stockModel.getStockNum())+ Integer.parseInt(stockInputModelList.get( i ).getInput_num())+"");
                        stockModel.setSupplierNo( stockInputModelList.get( i ).getSupplierNo() );
                        stockModel.setUpdateTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
                        stockModel.setUpdateUserCode( TokenUtil.getUserId(stockInputApplyModel.getToken()));
                        stockModel.setUpdateUserName(TokenUtil.getUser(stockInputApplyModel.getToken()).get("userName").toString());
                        if(stockMapper.update( stockModel) < 1) {  //单挑保存
                            return getResult(ReturnMsgEnum.Update_Failure);
                        }
                    }
                }
            }
            if(stockInputApplyMapper.update(stockInputApplyModel) < 1) {
                return getResult(ReturnMsgEnum.Update_Failure);
            }
            logger.info ("-------------插入StockInputApplyModel成功-------------");
            return getResult(ReturnMsgEnum.Update_Success);
        } catch (Exception e) {
            logger.info ("插入失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    @Override
    public Result findStockInputApplyByInputNo(String inputNo) {
        logger.info ("inputNo:"+inputNo);
        try {
            JSONObject objResult = stockInputApplyMapper.findStockInputApplyByInputNo(inputNo);
            List<JSONObject> objStockInputResult = stockInputMapper.findStockInputApplyByInputNo(inputNo);
            objResult.put( "stockInputModels",objStockInputResult );
            return getResult(ReturnMsgEnum.OK, objResult);
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }


    /**
     * @name logicDeleteByInputNo
     * @author 品讯科技
     * @param inputNo
     * @Description 逻辑删除
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByInputNo(String inputNo) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("inputNo："+inputNo);
        try {
            if(stockInputMapper.deleteByInputNo(inputNo) < 1) {  //删除明细数据  物理删除
                return getResult(ReturnMsgEnum.Delete_Failure);
            }
            if(stockInputApplyMapper.deleteByInputNo(inputNo) < 1) {//删除总库数据  物理删除
                return getResult(ReturnMsgEnum.Delete_Failure);
            }
        } catch (Exception e) {
            logger.info ("删除失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
        logger.info ("-------------逻辑删除失败-------------");
        return getResult(ReturnMsgEnum.Delete_Success);
    }


    /**
     * @name report
     * @author 品讯科技
     * @param stockInputApplyDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-02 23:07:46
     * @throws
     * @return Result
     */
    @Override
    public Result report(StockInputApplyDto stockInputApplyDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(stockInputApplyDto));
        try {
            page(stockInputApplyDto);//分页判断
            return getResult(ReturnMsgEnum.OK, stockInputApplyMapper.report(stockInputApplyDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }


}
