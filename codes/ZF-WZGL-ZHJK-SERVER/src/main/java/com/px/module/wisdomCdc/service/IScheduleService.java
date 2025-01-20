package com.px.module.wisdomCdc.service;

import com.px.module.common.base.IBaseService;
import com.px.module.common.base.Result;
import com.px.module.wisdomCdc.dto.ScheduleDto;
import com.px.module.wisdomCdc.dto.ScheduleListDto;
import com.px.module.wisdomCdc.entity.ScheduleModel;

public interface IScheduleService<T> extends IBaseService<T>{
     /**
     * @name findAll
     * @author 品讯科技
     * @param scheduleDto
     * @Description 根据条件查询所有数据并分页
     * @time 2022-04-25 23:00:18
     * @throws
     * @return Result
     */
    Result findAll(ScheduleDto scheduleDto);

	Result updateone(ScheduleModel scheduleModel);

	Result findlist(ScheduleListDto scheduleListDto);

	
}
