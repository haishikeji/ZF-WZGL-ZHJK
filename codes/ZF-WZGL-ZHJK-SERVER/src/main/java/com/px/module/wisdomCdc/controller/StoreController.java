package com.px.module.wisdomCdc.controller;

import com.px.module.common.base.Result;
import com.px.module.common.utils.TokenUtil;
import com.px.module.wisdomCdc.dto.StoreDto;
import com.px.module.wisdomCdc.entity.StoreModel;
import com.px.module.wisdomCdc.service.IStoreService;
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
 * @ClassName: StoreController
 * @Description: 对Store的业务处理controller
 * @author 品讯科技
 * @time   2022-07-15 10:04:59
 * @version V 1.0
 */
@RestController
@Api(tags = "StoreController", description = "对Store的业务处理controller")
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private IStoreService storeService;//service

    /**
     * @name save
     * @author 品讯科技
     * @param storeModel
     * @Description 保存
     * @time 2022-07-15 10:04:59
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "storeModel", value = "实体", required = true, dataType = "StoreModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody StoreModel storeModel,BindingResult result) {
        storeModel.setToken (token);
        storeModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        storeModel.setCreateUserCode(TokenUtil.getUserId(token));
        storeModel.setCreateUserName(TokenUtil.getUser(token).get("userName").toString());
        return storeService.save(storeModel);
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--物理删除
     * @time 2022-07-15 10:04:59
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
        return storeService.deleteByIds(ids);
    }
    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2022-07-15 10:04:59
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
        return storeService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param storeModel
     * @Description 根据id 修改
     * @time 2022-07-15 10:04:59
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "storeModel", value = "实体", required = true, dataType = "StoreModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody StoreModel storeModel,BindingResult result) {
        storeModel.setToken (token);
        storeModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        storeModel.setUpdateUserCode( TokenUtil.getUserId(token));
        storeModel.setUpdateUserName(TokenUtil.getUser(token).get("userName").toString());
        return storeService.update(storeModel);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据主键id查看信息
    * @time 2022-07-15 10:04:59
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
        return storeService.findById(id);
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param storeDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-15 10:04:59
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "storeDto", value = "实体", required = true, dataType = "StoreDto")
    })
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody StoreDto storeDto) {
        return storeService.findAll(storeDto);
    }



    /**
     * @name storeList
     * @author 品讯科技
     * @param storeDto
     *      {
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-15 10:04:59
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "storeDto", value = "实体", required = true, dataType = "StoreDto")
    })
    @RequestMapping(value = "/storeList", method = RequestMethod.POST)
    public Result storeList(@RequestBody StoreDto storeDto) {
        return storeService.storeList(storeDto);
    }
}
