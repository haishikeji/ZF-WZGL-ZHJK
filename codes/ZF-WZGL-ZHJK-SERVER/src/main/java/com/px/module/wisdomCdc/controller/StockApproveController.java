package com.px.module.wisdomCdc.controller;

import com.alibaba.fastjson.JSONObject;
import com.px.module.common.base.Result;
import com.px.module.common.utils.ExcelUtil;
import com.px.module.wisdomCdc.dto.StockApproveDayDtoExcel;
import com.px.module.wisdomCdc.dto.StockApproveDto;
import com.px.module.wisdomCdc.dto.StockApproveDtoExcel;
import com.px.module.wisdomCdc.entity.StockApproveModel;
import com.px.module.wisdomCdc.entity.StockInputApplyModel;
import com.px.module.wisdomCdc.entity.StockOutputApplyModel;
import com.px.module.wisdomCdc.service.IStockApproveService;
import com.px.module.wisdomCdc.service.IStockInputApplyService;
import com.px.module.wisdomCdc.service.IStockOutputApplyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @Package com.px.module.wisdomCdc.controller
 * @ClassName: StockApproveController
 * @Description: 对StockApprove的业务处理controller
 * @author 品讯科技
 * @time   2022-07-18 13:57:19
 * @version V 1.0
 */
@RestController
@Api(tags = "StockApproveController", description = "对StockApprove的业务处理controller")
@RequestMapping("/stockApprove")
public class StockApproveController {
    @Autowired
    private IStockApproveService stockApproveService;//service

    @Autowired
    private IStockOutputApplyService stockOutputApplyService;//service 出库申请

    @Autowired
    private IStockInputApplyService stockInputApplyService;//service  入库申请
    /**
     * @name save
     * @author 品讯科技
     * @param stockApproveModel
     * @Description 保存
     * @time 2022-07-18 13:57:19
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "stockApproveModel", value = "实体", required = true, dataType = "StockApproveModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody StockApproveModel stockApproveModel,BindingResult result) {
        stockApproveModel.setToken (token);
        return stockApproveService.save(stockApproveModel);
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--物理删除
     * @time 2022-07-18 13:57:19
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
        return stockApproveService.deleteByIds(ids);
    }
    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2022-07-18 13:57:19
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
        return stockApproveService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param stockApproveModel
     * @Description 根据id 修改
     * @time 2022-07-18 13:57:19
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "stockApproveModel", value = "实体", required = true, dataType = "StockApproveModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody StockApproveModel stockApproveModel,BindingResult result) {
        stockApproveModel.setToken (token);
        return stockApproveService.update(stockApproveModel);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据主键id查看信息
    * @time 2022-07-18 13:57:19
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
        return stockApproveService.findById(id);
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param stockApproveDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-18 13:57:19
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "stockApproveDto", value = "实体", required = true, dataType = "StockApproveDto")
    })
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody StockApproveDto stockApproveDto) {
        return stockApproveService.findAll(stockApproveDto);
    }


    /**
     * @name updateStatusAll
     * @author 品讯科技
     * @param stockApproveModel
     * @Description 根据id 修改
     * @time 2022-07-18 13:57:19
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改审批状态", notes = "修改审批状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "stockApproveModel", value = "实体", required = true, dataType = "StockApproveModel")
    })
    @Transactional( propagation = Propagation.REQUIRED)
    @RequestMapping(value = "/updateStatusAll", method = RequestMethod.POST)
    public Result updateStatusAll(@RequestHeader(value="Authorization") String token,@Validated @RequestBody StockApproveModel stockApproveModel,BindingResult result) {
        stockApproveModel.setToken (token);
        if(stockApproveModel.getApproveStatus().equals( "2" )){//审批通过
            //以下需要进行库存修改操作  修改总库存
            if(stockApproveModel.getStockType().equals( "1" )){ //出库

                StockOutputApplyModel stockOutputApplyModel = new StockOutputApplyModel();
                stockOutputApplyModel.setStockOutputModels(stockApproveModel.getStockOutputModels()  );
                stockOutputApplyModel.setId( stockApproveModel.getId() );
                stockOutputApplyModel.setApproveStatus( "2" );
                stockOutputApplyModel.setApproveContent( stockApproveModel.getApproveContent() );
                stockOutputApplyService.stockOutputApplyUpdate( stockOutputApplyModel );//更新状态为通过   并且需要插入库存主表  stock
            }
            if(stockApproveModel.getStockType().equals( "2" )){ //入库
                StockInputApplyModel stockInputApplyModel = new StockInputApplyModel();
                stockInputApplyModel.setStockInputModels( stockApproveModel.getStockInputModels() );
                stockInputApplyModel.setId( stockApproveModel.getId() );
                stockInputApplyModel.setApproveStatus( "2" );
                stockInputApplyModel.setApproveContent( stockApproveModel.getApproveContent() );
                stockInputApplyService.stockInputApplyUpdate( stockInputApplyModel );//更新状态为通过   并且插入库存主表
            }

        }else if(stockApproveModel.getApproveStatus().equals( "3" )){//审批被驳回  只修改主表状态，子表不懂
                if(stockApproveModel.getStockType().equals( "1" )){ //出库
                    StockOutputApplyModel stockOutputApplyModel = new StockOutputApplyModel();
                    stockOutputApplyModel.setId( stockApproveModel.getId() );
                    stockOutputApplyModel.setApproveStatus( "3" );
                    stockOutputApplyModel.setApproveContent( stockApproveModel.getApproveContent() );
                    stockOutputApplyService.update( stockOutputApplyModel );//更新状态为拒绝
                }
                if(stockApproveModel.getStockType().equals( "2" )){ //出库
                    StockInputApplyModel stockInputApplyModel = new StockInputApplyModel();
                    stockInputApplyModel.setId( stockApproveModel.getId() );
                    stockInputApplyModel.setApproveStatus( "3" );
                    stockInputApplyModel.setApproveContent( stockApproveModel.getApproveContent() );
                    stockInputApplyService.update( stockInputApplyModel );//更新状态为拒绝
                }
        }else{
            Result ret = new Result();
            ret.setStatus( false );
            ret.setMsg( "审批只有拒绝和通过，类型非法！" );
            return new Result();
        }
        return stockApproveService.update(stockApproveModel);
    }



    /**
     * @name InOutReportList
     * @author 品讯科技
     * @param stockApproveDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 出入库日期  物资 进出统计表
     * @time 2022-07-18 13:57:19
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
            @ApiImplicitParam(name = "stockApproveDto", value = "实体", required = true, dataType = "StockApproveDto")
    })
    @RequestMapping(value = "/InOutReportList", method = RequestMethod.POST)
    public Result InOutReportList(@RequestBody StockApproveDto stockApproveDto) {
        return stockApproveService.InOutReportDayList(stockApproveDto);
    }

    /**
     * @name InOutReport导出
     * @author 品讯科技
     * @param stockApproveDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 出入库日期  物资 进出统计表
     * @time 2022-07-18 13:57:19
     * @throws
     * @return Result
     */
//    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
//            @ApiImplicitParam(name = "stockApproveDto", value = "实体", required = true, dataType = "StockApproveDto")
//    })
//    @RequestMapping(value = "/InOutReport", method = RequestMethod.POST)
//    public Result InOutReport(@RequestBody StockApproveDto stockApproveDto) {
//        return stockApproveService.InOutReport(stockApproveDto);
//    }
    @RequestMapping(value = "/InOutReport", method = RequestMethod.GET)
    public void InOutReport(HttpServletResponse response, StockApproveDto stockApproveDto) {
        Result result = stockApproveService.InOutReport( stockApproveDto );
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition,exportStatus");
        JSONObject data = (JSONObject) result.getData();
        String code = result.getCode();
        if (!"success".equals(code)){
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("exportStatus", "F");
            return ;
        }
        List<StockApproveDayDtoExcel> list = data.getJSONArray( "list" ).toJavaList( StockApproveDayDtoExcel.class );
        ExcelUtil<StockApproveDayDtoExcel> util = new ExcelUtil<>( StockApproveDayDtoExcel.class );
        response.setHeader("exportStatus", "S");
        util.exportExcelNow( response, list, "入出日报表" + System.currentTimeMillis() );
    }


    /**
     * @name report
     * @author 品讯科技
     * @param stockApproveDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-18 13:57:19
     * @throws
     * @return Result
     */
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public void report(HttpServletResponse response, StockApproveDto stockApproveDto) {
        Result result = stockApproveService.report( stockApproveDto );
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition,exportStatus");
        JSONObject data = (JSONObject) result.getData();
        String code = result.getCode();
        if (!"success".equals(code)){
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("exportStatus", "F");
            return ;
        }
        List<StockApproveDtoExcel> list = data.getJSONArray( "list" ).toJavaList( StockApproveDtoExcel.class );
        ExcelUtil<StockApproveDtoExcel> util = new ExcelUtil<>( StockApproveDtoExcel.class );
        response.setHeader("exportStatus", "S");
        util.exportExcelNow( response, list, "入出库审批" + System.currentTimeMillis() );
    }
}
