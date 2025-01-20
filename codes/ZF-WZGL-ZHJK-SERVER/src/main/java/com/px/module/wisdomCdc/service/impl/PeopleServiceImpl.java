package com.px.module.wisdomCdc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.px.module.common.base.BaseService;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import com.px.module.common.config.ApplicationHttpUrl;
import com.px.module.common.utils.*;
import com.px.module.wisdomCdc.dao.IPeopleMapper;
import com.px.module.wisdomCdc.dto.PeopleDto;
import com.px.module.wisdomCdc.entity.PeopleModel;
import com.px.module.wisdomCdc.service.IPeopleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Package com.px.module.wisdomCdc.service.impl
 * @ClassName PeopleServiceImpl
 * @Description PeopleServiceImpl
 * @author 品讯科技
 * @time   2022-07-19 17:02:39
 * @version V 1.0
 */
@Service
public class PeopleServiceImpl extends BaseService  implements IPeopleService<PeopleModel> {
    @Autowired
    public IPeopleMapper peopleMapper;
    @Autowired
    ApplicationHttpUrl applicationHttpUrl;
    private static Logger logger = LoggerFactory.getLogger(PeopleServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param peopleModel
     * @Description 保存
     * @time 2022-07-19 17:02:39
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(PeopleModel peopleModel) {
        try {

            String idnum = peopleModel.getIdnum();
            PeopleDto peopleDto = new PeopleDto();
            peopleDto.setIdnum(idnum);
            List<PeopleDto> peopleModels = peopleMapper.findAll(peopleDto);
            if (!peopleModels.isEmpty()){
                return getResult(ReturnMsgEnum.people_already_exists);
            }

            JSONObject user = TokenUtil.getUser(peopleModel.getToken());
            if (user != null) {
                String userName = user.getString("userName");
                String userCode = user.getString("userCode");
                peopleModel.setCreateUserName(userName);
                peopleModel.setCreateUserCode(userCode);
            }

            String nowTime = DateUtil.getNowTime();

            peopleModel.setCreateTime(nowTime);

            String id="GL"+new IDNumberUtil(1, 12).nextId();
            peopleModel.setPeopleId (id);
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(peopleModel));
            if(peopleMapper.save(peopleModel) < 1) {
                return getResult(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入PeopleModel成功-------------");
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
     * @time 2022-07-19 17:02:39
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(peopleMapper.deleteByIds(ids) < 1) {
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
     * @time 2022-07-19 17:02:39
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(peopleMapper.logicDeleteByIds(ids) < 1) {
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
     * @param peopleModel
     * @Description 修改
     * @time 2022-07-19 17:02:39
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(PeopleModel peopleModel) {
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(peopleModel));
        try {
            if(peopleMapper.update(peopleModel) < 1) {
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
    * @time 2022-07-19 17:02:39
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, peopleMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param peopleDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-19 17:02:39
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(PeopleDto peopleDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(peopleDto));
        try {
            page(peopleDto);//分页判断
            return getResult(ReturnMsgEnum.OK, peopleMapper.findAll(peopleDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    @Override
    public Result addPeopleUrl(PeopleDto peopleDto) {
        String addPeopleUrl = applicationHttpUrl.getAddPeopleUrl();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("addPeopleUrl",addPeopleUrl);
        jsonObject.put("urlBase64",QrCodeUtil.getQRCodeImage(addPeopleUrl));
        return getResult(ReturnMsgEnum.OK,jsonObject);
    }
}
