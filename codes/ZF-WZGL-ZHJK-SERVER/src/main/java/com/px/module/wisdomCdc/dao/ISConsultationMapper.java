package com.px.module.wisdomCdc.dao;

import com.px.module.wisdomCdc.dao.provider.SConsultationProvider;
import com.px.module.wisdomCdc.dto.SConsultationDto;
import com.px.module.wisdomCdc.entity.SConsultationModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ISConsultationMapper {

    @InsertProvider(type = SConsultationProvider.class, method = "save")
    int save(SConsultationModel sConsultationModel);

    @DeleteProvider(type = SConsultationProvider.class, method = "deleteByIds")
    int deleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = SConsultationProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = SConsultationProvider.class, method = "update")
    int update(SConsultationModel sConsultationModel);

    @SelectProvider(type = SConsultationProvider.class, method = "findById")
    SConsultationModel findById(@Param ("id") String id);

    @SelectProvider(type = SConsultationProvider.class, method = "findAll")
    List<SConsultationModel> findAll(SConsultationDto sConsultationDto);
}
