package com.px.module.wisdomCdc.controller;

import com.px.module.common.base.Result;
import com.px.module.wisdomCdc.dto.SConsultationDto;
import com.px.module.wisdomCdc.entity.SConsultationModel;
import com.px.module.wisdomCdc.service.ISConsultationService;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Package com.px.module.wisdomCdc.controller
 * @ClassName: SConsultationController
 * @Description: 对SConsultation的业务处理controller
 * @author 品讯科技
 * @time   2022-07-06 14:50:01
 * @version V 1.0
 */
@RestController
@Api(tags = "SConsultationController", description = "对SConsultation的业务处理controller")
@RequestMapping("/SConsultation")
public class SConsultationController {
    @Autowired
    private ISConsultationService sConsultationService;//service

    /**
     * @name save
     * @author 品讯科技
     * @param sConsultationModel
     * @Description 保存
     * @time 2022-07-06 14:50:01
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "sConsultationModel", value = "实体", required = true, dataType = "SConsultationModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody SConsultationModel sConsultationModel,BindingResult result) {
        sConsultationModel.setToken (token);
        return sConsultationService.save(sConsultationModel);
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--物理删除
     * @time 2022-07-06 14:50:01
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据ids，删除数据-物理删除", notes = "根据ids，删除数据")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "ids", value = "id的数据集", required = true, dataType = "String")
    })
    @RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
    public Result deleteByIds(@RequestBody String ids) {
        return sConsultationService.deleteByIds(ids);
    }
    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2022-07-06 14:50:01
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
        return sConsultationService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param sConsultationModel
     * @Description 根据id 修改
     * @time 2022-07-06 14:50:01
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "sConsultationModel", value = "实体", required = true, dataType = "SConsultationModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated  @RequestBody SConsultationModel sConsultationModel,BindingResult result) {
        sConsultationModel.setToken (token);
        return sConsultationService.update(sConsultationModel);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据主键id查看信息
    * @time 2022-07-06 14:50:01
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
        return sConsultationService.findById(id);
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param sConsultationDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-06 14:50:01
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "sConsultationDto", value = "实体", required = true, dataType = "SConsultationDto")
    })
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody SConsultationDto sConsultationDto) {
        return sConsultationService.findAll(sConsultationDto);
    }
}
