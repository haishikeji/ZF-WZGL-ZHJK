package com.px.module.wisdomCdc.service.impl;

import com.px.module.common.base.BaseService;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import com.px.module.common.utils.JsonUtil;
import com.px.module.common.utils.UUIDUtil;
import com.px.module.wisdomCdc.dao.ICheckDetailMapper;
import com.px.module.wisdomCdc.dto.CheckDetailDto;
import com.px.module.wisdomCdc.entity.CheckDetailModel;
import com.px.module.wisdomCdc.entity.CheckModel;
import com.px.module.wisdomCdc.service.ICheckDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Package com.px.module.wisdomCdc.service.impl
 * @ClassName CheckDetailServiceImpl
 * @Description CheckDetailServiceImpl
 * @author 品讯科技
 * @time   2022-07-18 18:39:30
 * @version V 1.0
 */
@Service
public class CheckDetailServiceImpl extends BaseService  implements ICheckDetailService<CheckDetailModel> {
    @Autowired
    public ICheckDetailMapper checkDetailMapper;
    private static Logger logger = LoggerFactory.getLogger(CheckDetailServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param checkDetailModel
     * @Description 保存
     * @time 2022-07-18 18:39:30
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(CheckDetailModel checkDetailModel) {
        try {
            String id=UUIDUtil.get32UUID ();
            checkDetailModel.setId (id);
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(checkDetailModel));
            if(checkDetailMapper.save(checkDetailModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入CheckDetailModel成功-------------");
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
     * @time 2022-07-18 18:39:30
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(checkDetailMapper.deleteByIds(ids) < 1) {
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
     * @time 2022-07-18 18:39:30
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(checkDetailMapper.logicDeleteByIds(ids) < 1) {
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
     * @param checkDetailModel
     * @Description 修改
     * @time 2022-07-18 18:39:30
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(CheckDetailModel checkDetailModel) {
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(checkDetailModel));
        try {
            if(checkDetailMapper.update(checkDetailModel) < 1) {
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
    * @time 2022-07-18 18:39:30
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, checkDetailMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param checkDetailDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-18 18:39:30
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(CheckDetailDto checkDetailDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(checkDetailDto));
        try {
            page(checkDetailDto);//分页判断
            return getResult(ReturnMsgEnum.OK, checkDetailMapper.findAll(checkDetailDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }


    /**
     * 批量插入盘点信息明细
     * @param checkDetailModels
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean batchSaveCheckDetail(CheckModel checkModel,List<CheckDetailModel> checkDetailModels) {
        if (checkDetailMapper.batchSaveCheckDetail(checkModel,checkDetailModels) < 1) {
            return false;
        }
        return true;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteByCheckNo(String checkNo) {
        if (checkDetailMapper.deleteByCheckNo(checkNo) < 1) {
            return false;
        }
        return true;
    }

}
