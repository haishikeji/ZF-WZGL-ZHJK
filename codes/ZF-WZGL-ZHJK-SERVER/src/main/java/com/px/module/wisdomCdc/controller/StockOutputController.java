package com.px.module.wisdomCdc.controller;

import com.px.module.common.base.Result;
import com.px.module.common.utils.TokenUtil;
import com.px.module.wisdomCdc.dto.StockOutputDto;
import com.px.module.wisdomCdc.entity.StockOutputModel;
import com.px.module.wisdomCdc.service.IStockOutputService;
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
 * @ClassName: StockOutputController
 * @Description: 对StockOutput的业务处理controller
 * @author 品讯科技
 * @time   2022-07-02 23:30:12
 * @version V 1.0
 */
@RestController
@Api(tags = "StockOutputController", description = "对StockOutput的业务处理controller")
@RequestMapping("/stockOutput")
public class StockOutputController {
    @Autowired
    private IStockOutputService stockOutputService;//service

    /**
     * @name save
     * @author 品讯科技
     * @param stockOutputModel
     * @Description 保存
     * @time 2022-07-02 23:30:12
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "stockOutputModel", value = "实体", required = true, dataType = "StockOutputModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody StockOutputModel stockOutputModel,BindingResult result) {
        stockOutputModel.setToken (token);
        stockOutputModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        stockOutputModel.setCreateUserCode( TokenUtil.getUserId(token));
        stockOutputModel.setCreateUserName(TokenUtil.getUser(token).get("userName").toString());
        return stockOutputService.save(stockOutputModel);
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--物理删除
     * @time 2022-07-02 23:30:12
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
        return stockOutputService.deleteByIds(ids);
    }
    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2022-07-02 23:30:12
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
        return stockOutputService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param stockOutputModel
     * @Description 根据id 修改
     * @time 2022-07-02 23:30:12
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "stockOutputModel", value = "实体", required = true, dataType = "StockOutputModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody StockOutputModel stockOutputModel,BindingResult result) {
        stockOutputModel.setToken (token);
        stockOutputModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        stockOutputModel.setUpdateUserCode(TokenUtil.getUserId(token));
        stockOutputModel.setUpdateUserName(TokenUtil.getUser(token).get("userName").toString());
        return stockOutputService.update(stockOutputModel);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据主键id查看信息
    * @time 2022-07-02 23:30:12
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
        return stockOutputService.findById(id);
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param stockOutputDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-02 23:30:12
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "stockOutputDto", value = "实体", required = true, dataType = "StockOutputDto")
    })
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody StockOutputDto stockOutputDto) {
        return stockOutputService.findAll(stockOutputDto);
    }
}
