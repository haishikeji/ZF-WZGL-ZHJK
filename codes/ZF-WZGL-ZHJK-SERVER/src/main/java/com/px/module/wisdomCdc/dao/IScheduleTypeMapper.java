package com.px.module.wisdomCdc.dao;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.px.module.wisdomCdc.dao.provider.ScheduleTypeProvider;
import com.px.module.wisdomCdc.dto.ScheduleTypeDto;
import com.px.module.wisdomCdc.entity.ScheduleTypeModel;

@Mapper
@Repository
public interface IScheduleTypeMapper {

    @InsertProvider(type = ScheduleTypeProvider.class, method = "save")
    int save(ScheduleTypeModel scheduleTypeModel);

    @DeleteProvider(type = ScheduleTypeProvider.class, method = "deleteByIds")
    int deleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = ScheduleTypeProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = ScheduleTypeProvider.class, method = "update")
    int update(ScheduleTypeModel scheduleTypeModel);

    @SelectProvider(type = ScheduleTypeProvider.class, method = "findById")
    ScheduleTypeModel findById(@Param ("id") String id);

    @SelectProvider(type = ScheduleTypeProvider.class, method = "findAll")
    List<ScheduleTypeModel> findAll(ScheduleTypeDto scheduleTypeDto);
    
    @UpdateProvider(type = ScheduleTypeProvider.class, method = "updateone")
	int updateone(ScheduleTypeModel scheduleTypeModel);
}
