package com.px.module.wisdomCdc.service;

import com.px.module.common.base.IBaseService;
import com.px.module.common.base.Result;
import com.px.module.wisdomCdc.dto.SConsultationTypeDto;
import com.px.module.wisdomCdc.entity.SConsultationTypeModel;
public interface ISConsultationTypeService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param sConsultationTypeDto
     * @Description 根据条件查询所有数据并分页
     * @time 2021-11-05 09:39:09
     * @throws
     * @return Result
     */
    Result findAll(SConsultationTypeDto sConsultationTypeDto);

	Result updateOne(SConsultationTypeModel sConsultationTypeModel);
}
