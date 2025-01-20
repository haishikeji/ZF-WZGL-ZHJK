package com.px.module.wisdomCdc.service.impl;

import com.px.module.common.base.BaseService;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import com.px.module.common.utils.JsonUtil;
import com.px.module.common.utils.UUIDUtil;
import com.px.module.wisdomCdc.dao.IpublicNetworkDiskMapper;
import com.px.module.wisdomCdc.dto.publicNetworkDiskDto;
import com.px.module.wisdomCdc.entity.publicNetworkDiskModel;
import com.px.module.wisdomCdc.service.IpublicNetworkDiskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Package com.px.module.wisdomCdc.service.impl
 * @ClassName publicNetworkDiskServiceImpl
 * @Description publicNetworkDiskServiceImpl
 * @author 品讯科技
 * @time   2022-07-13 09:58:32
 * @version V 1.0
 */
@Service
public class publicNetworkDiskServiceImpl extends BaseService  implements IpublicNetworkDiskService<publicNetworkDiskModel> {
    @Autowired
    public IpublicNetworkDiskMapper publicNetworkDiskMapper;
    private static Logger logger = LoggerFactory.getLogger(publicNetworkDiskServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param publicNetworkDiskModel
     * @Description 保存
     * @time 2022-07-13 09:58:32
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(publicNetworkDiskModel publicNetworkDiskModel) {
        try {
            String id=UUIDUtil.get32UUID ();
            publicNetworkDiskModel.setId (id);
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(publicNetworkDiskModel));
            if(publicNetworkDiskMapper.save(publicNetworkDiskModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入publicNetworkDiskModel成功-------------");
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
     * @time 2022-07-13 09:58:32
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(publicNetworkDiskMapper.deleteByIds(ids) < 1) {
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
     * @time 2022-07-13 09:58:32
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(publicNetworkDiskMapper.logicDeleteByIds(ids) < 1) {
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
     * @param publicNetworkDiskModel
     * @Description 修改
     * @time 2022-07-13 09:58:32
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(publicNetworkDiskModel publicNetworkDiskModel) {
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(publicNetworkDiskModel));
        try {
            if(publicNetworkDiskMapper.update(publicNetworkDiskModel) < 1) {
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
    * @time 2022-07-13 09:58:32
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, publicNetworkDiskMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param publicNetworkDiskDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-13 09:58:32
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(publicNetworkDiskDto publicNetworkDiskDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(publicNetworkDiskDto));
        try {
            page(publicNetworkDiskDto);//分页判断
            return getResult(ReturnMsgEnum.OK, publicNetworkDiskMapper.findAll(publicNetworkDiskDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
}
