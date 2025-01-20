package com.px.module.wisdomCdc.controller;

import com.alibaba.fastjson.JSONObject;
import com.px.module.common.base.Result;
import com.px.module.common.base.ReturnMsgEnum;
import com.px.module.common.utils.ExcelUtil;
import com.px.module.wisdomCdc.dto.CheckResultDto;
import com.px.module.wisdomCdc.dto.CheckResultDtoExcel;
import com.px.module.wisdomCdc.entity.CheckResultModel;
import com.px.module.wisdomCdc.service.ICheckResultService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @Package com.px.module.wisdomCdc.controller
 * @ClassName: CheckResultController
 * @Description: 对CheckResult的业务处理controller
 * @author 品讯科技
 * @time   2022-07-05 15:41:37
 * @version V 1.0
 */
@RestController
@Api(tags = "CheckResultController", description = "对CheckResult的业务处理controller")
@RequestMapping("/checkResult")
public class CheckResultController {
    @Autowired
    private ICheckResultService checkResultService;//service
    private static Logger logger = LoggerFactory.getLogger(CheckResultController.class);
    /**
     * @name save
     * @author 品讯科技
     * @param checkResultModel
     * @Description 保存
     * @time 2022-07-05 15:41:37
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "checkResultModel", value = "实体", required = true, dataType = "CheckResultModel")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody CheckResultModel checkResultModel,BindingResult result) {
        checkResultModel.setToken (token);

        try {
            return checkResultService.save(checkResultModel);
        } catch (Exception e) {
            logger.info("保存检测结果失败"+e);
            return Result.buildResult(ReturnMsgEnum.Save_Failed);
        }

    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--物理删除
     * @time 2022-07-05 15:41:37
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
        return checkResultService.deleteByIds(ids);
    }
    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time 2022-07-05 15:41:37
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
        return checkResultService.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author 品讯科技
     * @param checkResultModel
     * @Description 根据id 修改
     * @time 2022-07-05 15:41:37
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "checkResultModel", value = "实体", required = true, dataType = "CheckResultModel")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody CheckResultModel checkResultModel,BindingResult result) {
        checkResultModel.setToken (token);
        return checkResultService.update(checkResultModel);
    }

    /**
    * @name findById
    * @author 品讯科技
    * @param id
    * @Description 根据主键id查看信息
    * @time 2022-07-05 15:41:37
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
        return checkResultService.findById(id);
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param checkResultDto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-05 15:41:37
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "checkResultDto", value = "实体", required = true, dataType = "CheckResultDto")
    })
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody CheckResultDto checkResultDto) {
        return checkResultService.findAll(checkResultDto);
    }

    //检测统计 naiwang
    @ApiOperation(value = "检测统计", notes = "检测统计")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "checkResultDto", value = "实体", required = true, dataType = "CheckResultDto")
    })
    @RequestMapping(value = "/findTotal", method = RequestMethod.POST)
    public Result findTotal(@RequestBody CheckResultDto checkResultDto) {
        return checkResultService.findTotal(checkResultDto);
    }

    //结果预警统计 naiwang
    @ApiOperation(value = "结果预警统计", notes = "结果预警统计")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "checkResultDto", value = "实体", required = true, dataType = "CheckResultDto")
    })
    @RequestMapping(value = "/resultWarningTotal", method = RequestMethod.POST)
    public Result resultWarningTotal(@RequestBody CheckResultDto checkResultDto) {
        return checkResultService.resultWarningTotal(checkResultDto);
    }

    //检测信息报表统计 naiwang
    @ApiOperation(value = "结果预警统计", notes = "结果预警统计")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "checkResultDto", value = "实体", required = true, dataType = "CheckResultDto")
    })
    @RequestMapping(value = "/checkInfoTotal", method = RequestMethod.POST)
    public Result checkInfoTotal(@RequestBody CheckResultDto checkResultDto) {
        return checkResultService.checkInfoTotal(checkResultDto);
    }

    //检测信息报表统计导出
    @RequestMapping(value = "/exportCheckInfoTotal", method = RequestMethod.GET)
    public void exportCheckInfoTotal(HttpServletResponse response, CheckResultDto checkResultDto) {
        Result result = checkResultService.checkInfoTotalExcel(checkResultDto);
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition,exportStatus");
        JSONObject data = (JSONObject)result.getData();
        String code = result.getCode();
        if (!"success".equals(code)){
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("exportStatus", "F");
            return ;
        }
        List<CheckResultDtoExcel> list = data.getJSONArray("list").toJavaList(CheckResultDtoExcel.class);
        ExcelUtil<CheckResultDtoExcel> util = new ExcelUtil<>(CheckResultDtoExcel.class);
        response.setHeader("exportStatus", "S");
        util.exportExcelNow(response,list, "检测信息报表统计"+System.currentTimeMillis());
    }

}
