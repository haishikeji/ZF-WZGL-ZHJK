package com.px.module.wisdomCdc.service.impl;

import com.px.module.common.base.BaseService;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import com.px.module.common.utils.JsonUtil;
import com.px.module.common.utils.UUIDUtil;
import com.px.module.wisdomCdc.dao.ISepidemicDataMapper;
import com.px.module.wisdomCdc.dto.SepidemicDataDto;
import com.px.module.wisdomCdc.entity.SepidemicDataModel;
import com.px.module.wisdomCdc.service.ISepidemicDataService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Package com.px.module.wisdomCdc.service.impl
 * @ClassName SepidemicDataServiceImpl
 * @Description SepidemicDataServiceImpl
 * @author 品讯科技
 * @time   2022-07-12 11:06:02
 * @version V 1.0
 */
@Service
public class SepidemicDataServiceImpl extends BaseService  implements ISepidemicDataService<SepidemicDataModel> {
    @Autowired
    public ISepidemicDataMapper sepidemicDataMapper;
    private static Logger logger = LoggerFactory.getLogger(SepidemicDataServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param sepidemicDataModel
     * @Description 保存
     * @time 2022-07-12 11:06:02
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(SepidemicDataModel sepidemicDataModel) {
        try {
            String id=UUIDUtil.get32UUID ();
            sepidemicDataModel.setId (id);
            sepidemicDataModel.setIsRemoved("1");
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(sepidemicDataModel));
            if(sepidemicDataMapper.save(sepidemicDataModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入SepidemicDataModel成功-------------");
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
     * @time 2022-07-12 11:06:02
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(sepidemicDataMapper.deleteByIds(ids) < 1) {
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
     * @time 2022-07-12 11:06:02
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(sepidemicDataMapper.logicDeleteByIds(ids) < 1) {
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
     * @param sepidemicDataModel
     * @Description 修改
     * @time 2022-07-12 11:06:02
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(SepidemicDataModel sepidemicDataModel) {
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(sepidemicDataModel));
        try {
            if(sepidemicDataMapper.update(sepidemicDataModel) < 1) {
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
    * @time 2022-07-12 11:06:02
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, sepidemicDataMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param sepidemicDataDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-12 11:06:02
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(SepidemicDataDto sepidemicDataDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(sepidemicDataDto));
        try {
            page(sepidemicDataDto);//分页判断
            sepidemicDataDto.setIsRemoved("1");
            return getResult(ReturnMsgEnum.OK, sepidemicDataMapper.findAll(sepidemicDataDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

	@Override
	public Result findCountAll(SepidemicDataDto sepidemicDataDto) {
        logger.info ("入参:"+JsonUtil.entityConvertToJson(sepidemicDataDto));
        try {
            page(sepidemicDataDto);//分页判断
            sepidemicDataDto.setIsRemoved("1");
            
            List<SepidemicDataModel> findCountAll = sepidemicDataMapper.findCountAll(sepidemicDataDto);
            if(findCountAll.size()<=0 || findCountAll == null || findCountAll.get(0) == null) {
            	List<SepidemicDataModel> findCountAlls = new ArrayList<SepidemicDataModel>();
            	
            	SepidemicDataModel ss =new SepidemicDataModel();
            	ss.setLocalNew("0");
            	ss.setNewSuspected("0");
            	ss.setDomesticNew("0");
            	ss.setLocalExistingDiagnosis("0");
            	ss.setCurrentDiagnosisInChina("0");
            	ss.setLocalNewCure("0");
            	ss.setLocalCumulativeCure("0");
            	ss.setDomesticNewCure("0");
            	ss.setCumulativeCureInChina("0");
            	
            	findCountAlls.add( ss);
            	  return getResult(ReturnMsgEnum.OK, findCountAlls);
            }else {
            	  return getResult(ReturnMsgEnum.OK, findCountAll);
            }
          
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
}
