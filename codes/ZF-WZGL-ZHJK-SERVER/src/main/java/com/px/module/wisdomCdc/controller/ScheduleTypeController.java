package com.px.module.wisdomCdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.px.module.common.base.Result;
import com.px.module.wisdomCdc.dto.ScheduleTypeDto;
import com.px.module.wisdomCdc.entity.ScheduleTypeModel;
import com.px.module.wisdomCdc.service.IScheduleTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 *
 * @Package com.px.module.wisdomCdc.controller
 * @ClassName: ScheduleTypeController
 * @Description: 对ScheduleType的业务处理controller
 * @author 品讯科技
 * @time   2021-11-05 18:11:45
 * @version V 1.0
 */
@RestController
@Api(tags = "ScheduleTypeController", description = "对ScheduleType的业务处理controller")
@RequestMapping("/scheduleType")
public class ScheduleTypeController {
    @Autowired
    private IScheduleTypeService scheduleTypeService;//service

    /**
     * @name save
     * @author 品讯科技
     * @param scheduleTypeModel
     * @Description 保存
     * @time 2021-11-05 18:11:45
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "scheduleTypeModel", value = "实体", required = true, dataType = "ScheduleTypeModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody ScheduleTypeModel scheduleTypeModel,BindingResult result) {
    	scheduleTypeModel.setGenreStatus("1");//默认新增的排班类型为启用
        scheduleTypeModel.setToken (token);
       
        scheduleTypeModel.setIsRemoved("1");//默认不删除
        return scheduleTypeService.save(scheduleTypeModel);
    }


//    /**
//     * @name deleteByIds
//     * @author 品讯科技
//     * @param ids 例：ids=1,2,3
//     * @Description 根据id删除  支持批量删除--物理删除
//     * @time 2021-11-05 18:11:45
//     * @throws
//     * @return Result
//     */
//    @ApiOperation(value = "根据ids，删除数据-物理删除", notes = "根据ids，删除数据")
//    @ApiImplicitParams({
//        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
//        @ApiImplicitParam(name = "ids", value = "id的数据集", required = true, dataType = "String")
//    })
//    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
//    public Result deleteByIds(@RequestBody String ids) {
//        return scheduleTypeService.deleteByIds(ids);
//    }
    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2021-11-05 18:11:45
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据ids，删除数据--逻辑删除", notes = "根据ids，删除数据")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "ids", value = "id的数据集", required = true, dataType = "String")
    })
    @RequestMapping(value = "/logicDeleteByIds", method = RequestMethod.POST)
    public Result logicDeleteByIds(@RequestBody String ids) {
        return scheduleTypeService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param scheduleTypeModel
     * @Description 根据id 修改
     * @time 2021-11-05 18:11:45
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "scheduleTypeModel", value = "实体", required = true, dataType = "ScheduleTypeModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody ScheduleTypeModel scheduleTypeModel,BindingResult result) {
        scheduleTypeModel.setToken (token);
        return scheduleTypeService.update(scheduleTypeModel);
    }
    
    
    
    
    /**
     * @name updateone
     * @author 品讯科技
     * @param scheduleTypeModel
     * @Description 根据id 修改
     * @time 2021-11-05 18:11:45
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改状态", notes = "修改状态")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "scheduleTypeModel", value = "实体", required = true, dataType = "ScheduleTypeModel")
    })
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public Result updateone(@RequestHeader(value="Authorization") String token,@Validated @RequestBody ScheduleTypeModel scheduleTypeModel,BindingResult result) {
    	scheduleTypeModel.setGenreStatus(scheduleTypeModel.getOperateType());
    	scheduleTypeModel.setToken (token);
        scheduleTypeModel.setOperateType(scheduleTypeModel.getOperateType());
        return scheduleTypeService.updateone(scheduleTypeModel);
    }
    
    
    

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据主键id查看信息
    * @time 2021-11-05 18:11:45
    * @throws
    * @return Result
    */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "String", paramType = "path")
    })
    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return scheduleTypeService.findById(id);
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param scheduleTypeDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2021-11-05 18:11:45
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "scheduleTypeDto", value = "实体", required = true, dataType = "ScheduleTypeDto")
    })
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody ScheduleTypeDto scheduleTypeDto) {
    	

    	
        return scheduleTypeService.findAll(scheduleTypeDto);
    }
}