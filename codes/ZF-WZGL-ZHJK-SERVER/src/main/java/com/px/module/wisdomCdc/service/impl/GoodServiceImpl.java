package com.px.module.wisdomCdc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.px.module.common.base.BaseService;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import com.px.module.common.utils.IDNumberUtil;
import com.px.module.common.utils.JsonUtil;
import com.px.module.common.utils.UUIDUtil;
import com.px.module.wisdomCdc.dao.IGoodMapper;
import com.px.module.wisdomCdc.dao.IGoodTypeMapper;
import com.px.module.wisdomCdc.dto.GoodDto;
import com.px.module.wisdomCdc.entity.GoodModel;
import com.px.module.wisdomCdc.service.IGoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Package com.px.module.wisdomCdc.service.impl
 * @ClassName GoodServiceImpl
 * @Description GoodServiceImpl
 * @author 品讯科技
 * @time   2022-06-27 22:48:21
 * @version V 1.0
 */
@Service
public class GoodServiceImpl extends BaseService  implements IGoodService<GoodModel> {
    @Autowired
    public IGoodMapper goodMapper;

    @Autowired
    public IGoodTypeMapper goodTypeMapper;

    private static Logger logger = LoggerFactory.getLogger(GoodServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param goodModel
     * @Description 保存
     * @time 2022-06-27 22:48:21
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(GoodModel goodModel) {
        try {
            String id=UUIDUtil.get32UUID ();
            goodModel.setId (id);
            IDNumberUtil worker = new IDNumberUtil (1, 10);//第二个参数是  长度
            String good_code = "WZ"+worker.nextId ();
            goodModel.setGoodCode( good_code );
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(goodModel));
            if(goodMapper.save(goodModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入GoodModel成功-------------");
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
     * @time 2022-06-27 22:48:21
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(goodMapper.deleteByIds(ids) < 1) {
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
     * @time 2022-06-27 22:48:21
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(goodMapper.logicDeleteByIds(ids) < 1) {
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
     * @param goodModel
     * @Description 修改
     * @time 2022-06-27 22:48:21
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(GoodModel goodModel) {
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(goodModel));
        try {
            if(goodMapper.update(goodModel) < 1) {
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
    * @time 2022-06-27 22:48:21
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            JSONObject objResult = goodMapper.findByIdJson(id);
            List<JSONObject> objStockInputResult = goodTypeMapper.goodTypeCodeList();

            //objResult.put( "goodTypeCodeList",objStockInputResult );
            return getResult(ReturnMsgEnum.OK, objResult);
//            return getResult(ReturnMsgEnum.OK, goodMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param goodDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-06-27 22:48:21
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(GoodDto goodDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(goodDto));
        try {
            page(goodDto);//分页判断
            List<JSONObject> objResult = goodMapper.findAll(goodDto);
            return getResult(ReturnMsgEnum.OK, objResult);
            //return getResult(ReturnMsgEnum.OK, goodMapper.findAll(goodDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }


    /**
     * @name findGoodStockAll
     * @author 品讯科技
     * @param goodDto
     * @Description 根据条件查询所有数据并分页  物资信息连接库存  入库时候用
     * @time 2022-06-27 22:48:21
     * @throws
     * @return Result
     */
    @Override
    public Result findGoodStockAll(GoodDto goodDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(goodDto));
        try {
            page(goodDto);//分页判断
            return getResult(ReturnMsgEnum.OK, goodMapper.findGoodStockAll(goodDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
}
