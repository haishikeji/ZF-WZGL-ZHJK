package com.px.module.wisdomCdc.controller;

import com.px.module.common.base.Result;
import com.px.module.common.utils.TokenUtil;
import com.px.module.wisdomCdc.dto.SupplierDto;
import com.px.module.wisdomCdc.entity.SupplierModel;
import com.px.module.wisdomCdc.service.ISupplierService;
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
 * @ClassName: SupplierController
 * @Description: 对Supplier的业务处理controller
 * @author 品讯科技
 * @time   2022-06-26 14:45:44
 * @version V 1.0
 */
@RestController
@Api(tags = "SupplierController", description = "对Supplier的业务处理controller")
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private ISupplierService supplierService;//service

    /**
     * @name save
     * @author 品讯科技
     * @param supplierModel
     * @Description 保存
     * @time 2022-06-26 14:45:44
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "supplierModel", value = "实体", required = true, dataType = "SupplierModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody SupplierModel supplierModel,BindingResult result) {
        supplierModel.setToken (token);
        supplierModel.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        supplierModel.setCreateUserCode(TokenUtil.getUserId(token));
        supplierModel.setCreateUserName(TokenUtil.getUser(token).get("userName").toString());
        return supplierService.save(supplierModel);
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--物理删除
     * @time 2022-06-26 14:45:44
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
        return supplierService.deleteByIds(ids);
    }
    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2022-06-26 14:45:44
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
        return supplierService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param supplierModel
     * @Description 根据id 修改
     * @time 2022-06-26 14:45:44
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "supplierModel", value = "实体", required = true, dataType = "SupplierModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody SupplierModel supplierModel,BindingResult result) {
        supplierModel.setToken (token);
        supplierModel.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        supplierModel.setUpdateUserCode(TokenUtil.getUserId(token));
        supplierModel.setUpdateUserName(TokenUtil.getUser(token).get("userName").toString());
        return supplierService.update(supplierModel);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据主键id查看信息
    * @time 2022-06-26 14:45:44
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
        return supplierService.findById(id);
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param supplierDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2022-06-26 14:45:44
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "supplierDto", value = "实体", required = true, dataType = "SupplierDto")
    })
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody SupplierDto supplierDto) {
        return supplierService.findAll(supplierDto);
    }






    /**
     * @name supplierList
     * @author 品讯科技
     * @param supplierDto
     *      {
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2022-06-26 14:45:44
     * @throws
     * @return Result
     */
    @ApiOperation(value = "供应商下拉", notes = "供应商下拉")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "supplierDto", value = "实体", required = true, dataType = "SupplierDto")
    })
    @RequestMapping(value = "/supplierList", method = RequestMethod.POST)
    public Result supplierList(@RequestBody SupplierDto supplierDto) {
        return supplierService.supplierList(supplierDto);
    }


    /**
     * @name supplierStatusList
     * @author 品讯科技
     * @param supplierDto
     *      {
     *      }
     * @Description 供应商状态下拉
     * @time 2022-06-26 14:45:44
     * @throws
     * @return Result
     */
    @ApiOperation(value = "供应商下拉", notes = "供应商下拉")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "supplierDto", value = "实体", required = true, dataType = "SupplierDto")
    })
    @RequestMapping(value = "/supplierStatusList", method = RequestMethod.POST)
    public Result supplierStatusList(@RequestBody SupplierDto supplierDto) {
        return supplierService.supplierStatusList(supplierDto);
    }

}
