package com.px.module.wisdomCdc.service;

import com.px.module.common.base.IBaseService;
import com.px.module.common.base.Result;
import com.px.module.wisdomCdc.dto.DepartmentDto;
import com.px.module.wisdomCdc.entity.DepartmentModel;

public interface IDepartmentService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param departmentDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-07-07 14:26:27
     * @throws
     * @return Result
     */
    Result findAll(DepartmentDto departmentDto);

    boolean checkDepartNameUnique(DepartmentModel departmentModel);
}
