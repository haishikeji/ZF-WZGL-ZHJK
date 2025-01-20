package ${package}.service;

import com.px.module.common.base.IBaseService;
import com.px.module.common.base.Result;
import ${package}.dto.${entity}Dto;
public interface I${entity}Service${r'<T>'} extends IBaseService${r'<T>'}{
     /**
     * @name findAll
     * @author ${authorName}
     * @param ${controllerNameSpace}Dto
     * @Description 根据条件查询所有数据并分页
     * @time ${systemDate}
     * @throws
     * @return Result
     */
    Result findAll(${entity}Dto ${controllerNameSpace}Dto);
}
