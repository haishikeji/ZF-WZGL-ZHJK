package com.px.module.wisdomCdc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.px.module.common.base.BaseService;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import com.px.module.common.exception.RequestException;
import com.px.module.common.utils.*;
import com.px.module.wisdomCdc.dao.ICheckResultMapper;
import com.px.module.wisdomCdc.dao.IReservationtMapper;
import com.px.module.wisdomCdc.dao.ISMessageMapper;
import com.px.module.wisdomCdc.dto.CheckResultDto;
import com.px.module.wisdomCdc.dto.ReservationtDto;
import com.px.module.wisdomCdc.entity.CheckResultModel;
import com.px.module.wisdomCdc.entity.ReservationtModel;
import com.px.module.wisdomCdc.entity.SMessageModel;
import com.px.module.wisdomCdc.service.ICheckResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Package com.px.module.wisdomCdc.service.impl
 * @ClassName CheckResultServiceImpl
 * @Description CheckResultServiceImpl
 * @author 品讯科技
 * @time   2022-07-05 15:41:37
 * @version V 1.0
 */
@Service
public class CheckResultServiceImpl extends BaseService  implements ICheckResultService<CheckResultModel> {
    @Autowired
    public ICheckResultMapper checkResultMapper;

    @Autowired
    public IReservationtMapper reservationtMapper;
    @Autowired
    public ISMessageMapper sMessageMapper;
    private static Logger logger = LoggerFactory.getLogger(CheckResultServiceImpl.class);

    /**
     * @name save
     * @author 品讯科技
     * @param checkResultModel
     * @Description 保存
     * @time 2022-07-05 15:41:37
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(CheckResultModel checkResultModel) {
        try {

            JSONObject user = TokenUtil.getUser(checkResultModel.getToken());
            String userName = user.getString("userName");
            String userCode = user.getString("userCode");
            String nowTime = DateUtil.getNowTime();
            checkResultModel.setCreateUserName(userName);
            checkResultModel.setCreateUserCode(userCode);
            checkResultModel.setCreateTime(nowTime);
            checkResultModel.setCheckResultNum(String.valueOf(new IDNumberUtil(1, 15).nextId()));

            String id=UUIDUtil.get32UUID ();
            checkResultModel.setCheckResultId (id);
            logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(checkResultModel));
            if(checkResultMapper.save(checkResultModel) < 1) {
                throw new RequestException(ReturnMsgEnum.Save_Failed);
            }
            logger.info ("-------------插入CheckResultModel成功-------------");

            //当检测结果异常，需要往信息表存数据
            String checkResultDisplay = checkResultModel.getCheckResultDisplay();//检测结果显示（1绿色无症状，2黄色预警，3红色预警）
            if ("3".equals(checkResultDisplay)){
                //获取预约检测人员信息
                ReservationtDto reservationtDto = reservationtMapper.findById(checkResultModel.getReservationId());
                SMessageModel sMessageModel = new SMessageModel();
                //  2022年03月11日，就诊人 杨某 ，手机号为 17623215412，新冠检测为阳性，请及时处理，并对其进行通知。
                StringBuffer stringBuffer = new StringBuffer();
                String checkTime = checkResultModel.getCheckTime();
                String personnelName = reservationtDto.getPersonnelName();
                String phone = reservationtDto.getPhone();
                String checkResult = checkResultModel.getCheckResult();
                stringBuffer.append(checkTime);
                stringBuffer.append("，就诊人 ");
                stringBuffer.append(personnelName);
                stringBuffer.append("，手机号 ");
                stringBuffer.append(phone);
                stringBuffer.append("，");
                stringBuffer.append(checkResult);
                stringBuffer.append("，请及时处理，并对其进行通知。");
                sMessageModel.setId(UUIDUtil.get32UUID());
                sMessageModel.setMessageNotification(stringBuffer.toString());
                sMessageModel.setSourceInformation("防疫检测");
                sMessageModel.setCreateTime(nowTime);
                sMessageModel.setCreateUserCode(userCode);
                sMessageModel.setCreateUserName(userName);
                sMessageModel.setIsRemoved("1");
                logger.info("保存实体对象数据："+JsonUtil.entityConvertToJson(sMessageModel));
                if(sMessageMapper.save(sMessageModel) < 1) {
                    throw new RequestException(ReturnMsgEnum.Save_Failed);
                }
            }
            //结果录入的时候要把预约表的检测状态改为已检测
            String reservationId = checkResultModel.getReservationId();
            ReservationtModel reservationtModel = new ReservationtModel();
            reservationtModel.setReservationId(reservationId);
            reservationtModel.setCheckResultStatus("1");//检测状态（1已检测，2未检测，3已过期）
            reservationtModel.setUpdateTime(nowTime);
            reservationtModel.setUpdateUserCode(userCode);
            reservationtModel.setUpdateUserName(userName);
            reservationtModel.setCheckResultId(id);
            if(reservationtMapper.update(reservationtModel) < 1) {
                throw new RequestException(ReturnMsgEnum.Update_Failure);
            }
            return getResult(ReturnMsgEnum.Save_Success, id);
        } catch (Exception e) {
            logger.info ("插入失败");
            logger.info (e.toString ());
            throw new RequestException(ReturnMsgEnum.Save_Failed);
        }
    }


    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2022-07-05 15:41:37
     * @throws
     * @return Result
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED)
    public Result deleteByIds(String ids) {
        logger.info ("--------------根据ids删除数据----------");
        logger.info("ids："+ids);
        try {
            if(checkResultMapper.deleteByIds(ids) < 1) {
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
     * @time 2022-07-05 15:41:37
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result logicDeleteByIds(String ids) {
        logger.info ("--------------根据ids逻辑删除数据----------");
        logger.info("ids："+ids);
        try {
            if(checkResultMapper.logicDeleteByIds(ids) < 1) {
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
     * @param checkResultModel
     * @Description 修改
     * @time 2022-07-05 15:41:37
     * @throws
     * @return Result
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Result update(CheckResultModel checkResultModel) {
        logger.info("修改实体对象数据："+JsonUtil.entityConvertToJson(checkResultModel));
        try {
            if(checkResultMapper.update(checkResultModel) < 1) {
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
    * @time 2022-07-05 15:41:37
    * @throws
    * @return Result
    */
    @Override
    public Result findById(String id) {
        logger.info ("id:"+id);
        try {
            return getResult(ReturnMsgEnum.OK, checkResultMapper.findById(id));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param checkResultDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-05 15:41:37
     * @throws
     * @return Result
     */
    @Override
    public Result findAll(CheckResultDto checkResultDto){
        logger.info ("入参:"+JsonUtil.entityConvertToJson(checkResultDto));
        try {
            page(checkResultDto);//分页判断
            return getResult(ReturnMsgEnum.OK, checkResultMapper.findAll(checkResultDto));
        } catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    @Override
    public Result findTotal(CheckResultDto checkResultDto) {
        logger.info ("findTotal入参:"+JsonUtil.entityConvertToJson(checkResultDto));
        try {
            return getResult(ReturnMsgEnum.OK, checkResultMapper.findTotal(checkResultDto));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    @Override
    public Result resultWarningTotal(CheckResultDto checkResultDto) {
        logger.info ("resultWarningTotal入参:"+JsonUtil.entityConvertToJson(checkResultDto));
        try {
            return getResult(ReturnMsgEnum.OK, checkResultMapper.resultWarningTotal(checkResultDto));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    @Override
    public Result checkInfoTotal(CheckResultDto checkResultDto) {
        logger.info ("checkInfoTotal入参:"+JsonUtil.entityConvertToJson(checkResultDto));
        try {
            return getResult(ReturnMsgEnum.OK, checkResultMapper.checkInfoTotal(checkResultDto));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }

    @Override
    public Result checkInfoTotalExcel(CheckResultDto checkResultDto) {
        try {
            return getResult(ReturnMsgEnum.OK, checkResultMapper.checkInfoTotalExcel(checkResultDto));
        }catch (Exception e) {
            logger.info ("查询失败");
            logger.info (e.getMessage ());
            return getResult(ReturnMsgEnum.Internal_Server_Error);
        }
    }
}
