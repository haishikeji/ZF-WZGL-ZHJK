package com.px.module.wisdomCdc.controller;

import com.alibaba.fastjson.JSONObject;
import com.px.module.common.base.Result;
import com.px.module.common.utils.TokenUtil;
import com.px.module.wisdomCdc.dto.GoodTypeDto;
import com.px.module.wisdomCdc.entity.GoodTypeModel;
import com.px.module.wisdomCdc.service.IGoodTypeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @Package com.px.module.wisdomCdc.controller
 * @ClassName: GoodTypeController
 * @Description: 对GoodType的业务处理controller
 * @author 品讯科技
 * @time   2022-06-27 14:37:40
 * @version V 1.0
 */
@RestController
@Api(tags = "GoodTypeController", description = "对GoodType的业务处理controller")
@RequestMapping("/goodType")
public class GoodTypeController {
    @Autowired
    private IGoodTypeService goodTypeService;//service

    /**
     * @name save
     * @author 品讯科技
     * @param goodTypeModel
     * @Description 保存
     * @time 2022-06-27 14:37:40
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "goodTypeModel", value = "实体", required = true, dataType = "GoodTypeModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody GoodTypeModel goodTypeModel,BindingResult result) {
        goodTypeModel.setToken (token);
        goodTypeModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        goodTypeModel.setCreateUserCode(TokenUtil.getUserId(token));
        goodTypeModel.setCreateUserName(TokenUtil.getUser(token).get("userName").toString());
        return goodTypeService.save(goodTypeModel);
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--物理删除
     * @time 2022-06-27 14:37:40
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
        return goodTypeService.deleteByIds(ids);
    }
    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2022-06-27 14:37:40
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
        return goodTypeService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param goodTypeModel
     * @Description 根据id 修改
     * @time 2022-06-27 14:37:40
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "goodTypeModel", value = "实体", required = true, dataType = "GoodTypeModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody GoodTypeModel goodTypeModel,BindingResult result) {
        goodTypeModel.setToken (token);
        goodTypeModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        goodTypeModel.setUpdateUserCode(TokenUtil.getUserId(token));
        goodTypeModel.setUpdateUserName(TokenUtil.getUser(token).get("userName").toString());
        return goodTypeService.update(goodTypeModel);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据主键id查看信息
    * @time 2022-06-27 14:37:40
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
        return goodTypeService.findById(id);
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param goodTypeDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2022-06-27 14:37:40
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "goodTypeDto", value = "实体", required = true, dataType = "GoodTypeDto")
    })
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody GoodTypeDto goodTypeDto,@RequestHeader(value="Authorization") String token) {
        //return goodTypeService.findAll(goodTypeDto);
        JSONObject jsonObject=new JSONObject ();
        jsonObject.put ("id","1");
        jsonObject.put("token",token);
        jsonObject.put ("typeName",goodTypeDto.getTypeName());
        jsonObject.put ("status",goodTypeDto.getStatus());
        return goodTypeService.findGoodTypeTreeAll (jsonObject);
    }
}
