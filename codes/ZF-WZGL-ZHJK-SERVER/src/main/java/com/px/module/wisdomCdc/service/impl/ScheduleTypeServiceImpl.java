package com.px.module.wisdomCdc.service.impl;

import com.px.module.common.base.BaseService;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import com.px.module.common.utils.JsonUtil;
import com.px.module.common.utils.TokenUtil;
import com.px.module.common.utils.UUIDUtil;
import com.px.module.wisdomCdc.dao.IScheduleTypeMapper;
import com.px.module.wisdomCdc.dto.ScheduleTypeDto;
import com.px.module.wisdomCdc.entity.ScheduleTypeModel;
import com.px.module.wisdomCdc.service.IScheduleTypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Package com.px.module.wisdomCdc.service.impl
 * @ClassName ScheduleTypeServiceImpl
 * @Description ScheduleTypeServiceImpl
 * @author 品讯科技
 * @time   2021-11-05 18:11:45
 * @version V 1.0
 */
@Service
public class ScheduleTypeServiceImpl extends BaseService  implements IScheduleTypeService<ScheduleTypeModel> {
    @Autowired
    public IScheduleTypeMapper scheduleTypeMapper;
    private static Logger logger = LoggerFactory.getLogger(ScheduleTypeServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param scheduleTypeModel
     * @Description 保存
     * @time 2021-11-05 18:11:45
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(ScheduleTypeModel scheduleTypeModel) {
        //更新操作人员
        scheduleTypeModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        scheduleTypeModel.setCreateUserCode(TokenUtil.getUserId(scheduleTypeModel.getToken()));

        try {
            String id=UUIDUtil.get32UUID ();
            scheduleTypeModel.setId (id);
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(scheduleTypeModel));
            if(scheduleTypeMapper.save(scheduleTypeModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入ScheduleTypeModel成功-------------");
            return getResult(ReturnMsgEnum.Save_Success, id);
        } catch (Exception e) {
            logger.info ("插入失败");
            logger.info (e.toString ());
            return getResult(ReturnMsgEnum.Internal_Servers_Error);
        }
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2021-11-05 18:11:45
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(scheduleTypeMapper.deleteByIds(ids) < 1) {
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
     * @time 2021-11-05 18:11:45
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(scheduleTypeMapper.logicDeleteByIds(ids) < 1) {
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
     * @param scheduleTypeModel
     * @Description 修改
     * @time 2021-11-05 18:11:45
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(ScheduleTypeModel scheduleTypeModel) {
        //更新操作人员
        scheduleTypeModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        scheduleTypeModel.setUpdateUserCode(TokenUtil.getUserId(scheduleTypeModel.getToken()));
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(scheduleTypeModel));
        try {
            if(scheduleTypeMapper.update(scheduleTypeModel) < 1) {
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
    * @time 2021-11-05 18:11:45
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, scheduleTypeMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param scheduleTypeDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-11-05 18:11:45
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(ScheduleTypeDto scheduleTypeDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(scheduleTypeDto));
        try {
            page(scheduleTypeDto);//分页判断
            return getResult(ReturnMsgEnum.OK, scheduleTypeMapper.findAll(scheduleTypeDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
    /**
     * @name updateone
     * @author 品讯科技
     * @param scheduleTypeModel
     * @Description 修改
     * @time 2021-11-05 18:11:45
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result updateone(ScheduleTypeModel scheduleTypeModel) {
        //更新操作人员
        scheduleTypeModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        scheduleTypeModel.setCreateUserCode(TokenUtil.getUserId(scheduleTypeModel.getToken()));
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(scheduleTypeModel));
        try {
            if(scheduleTypeMapper.updateone(scheduleTypeModel) < 1) {
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
	
}
