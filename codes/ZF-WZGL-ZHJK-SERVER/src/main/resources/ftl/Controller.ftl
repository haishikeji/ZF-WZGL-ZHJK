package ${package}.controller;

import com.px.module.common.base.Result;
import ${package}.dto.${entity}Dto;
import ${package}.entity.${entity}Model;
import ${package}.service.I${entity}Service;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Package ${package}.controller
 * @ClassName: ${entity}Controller
 * @Description: 对${entity}的业务处理controller
 * @author ${authorName}
 * @time   ${systemDate}
 * @version V 1.0
 */
@RestController
@Api(tags = "${entity}Controller", description = "对${entity}的业务处理controller")
@RequestMapping("/${controllerNameSpace}")
public class ${entity}Controller {
    @Autowired
    private I${entity}Service ${controllerNameSpace}Service;//service

    /**
     * @name save
     * @author ${authorName}
     * @param ${controllerNameSpace}Model
     * @Description 保存
     * @time ${systemDate}
     * @throws
     * @return Result
     */
    @ApiOperation(value = "保存", notes = "保存")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "${controllerNameSpace}Model", value = "实体", required = true, dataType = "${entity}Model")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestHeader(value="Authorization") String token,@Validated @RequestBody ${entity}Model ${controllerNameSpace}Model,BindingResult result) {
        ${controllerNameSpace}Model.setToken (token);
        return ${controllerNameSpace}Service.save(${controllerNameSpace}Model);
    }

    /**
     * @name deleteByIds
     * @author ${authorName}
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--物理删除
     * @time ${systemDate}
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
        return ${controllerNameSpace}Service.deleteByIds(ids);
    }
    /**
     * @name logicDeleteByIds
     * @author ${authorName}
     * @param ids 例：ids=1,2,3
     * @Description 根据id删除  支持批量删除--逻辑删除
     * @time ${systemDate}
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
        return ${controllerNameSpace}Service.logicDeleteByIds(ids);
    }

    /**
     * @name update
     * @author ${authorName}
     * @param ${controllerNameSpace}Model
     * @Description 根据id 修改
     * @time ${systemDate}
     * @throws
     * @return Result
     */
    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "${controllerNameSpace}Model", value = "实体", required = true, dataType = "${entity}Model")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestHeader(value="Authorization") String token,@Validated @RequestBody ${entity}Model ${controllerNameSpace}Model,BindingResult result) {
        ${controllerNameSpace}Model.setToken (token);
        return ${controllerNameSpace}Service.update(${controllerNameSpace}Model);
    }

    /**
    * @name findById
    * @author ${authorName}
    * @param id
    * @Description 根据主键id查看信息
    * @time ${systemDate}
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
        return ${controllerNameSpace}Service.findById(id);
    }

    /**
     * @name findAll
     * @author ${authorName}
     * @param ${controllerNameSpace}Dto
     *      {
     *          "pageNum":"",页码
     *          "pageSize":"",每页长度
     *          "condition":""条件
     *      }
     * @Description 根据条件查询所有数据并分页
     * @time ${systemDate}
     * @throws
     * @return Result
     */
    @ApiOperation(value = "根据条件查询", notes = "根据条件查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "header",name = "Authorization",value = "身份认证Token"),
        @ApiImplicitParam(name = "${controllerNameSpace}Dto", value = "实体", required = true, dataType = "${entity}Dto")
    })
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody ${entity}Dto ${controllerNameSpace}Dto) {
        return ${controllerNameSpace}Service.findAll(${controllerNameSpace}Dto);
    }
}
