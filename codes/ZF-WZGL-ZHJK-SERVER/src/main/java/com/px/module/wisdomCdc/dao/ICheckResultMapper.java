package com.px.module.wisdomCdc.dao;

import com.px.module.wisdomCdc.dao.provider.CheckResultProvider;
import com.px.module.wisdomCdc.dto.CheckResultDto;
import com.px.module.wisdomCdc.dto.CheckResultDtoExcel;
import com.px.module.wisdomCdc.entity.CheckResultModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ICheckResultMapper {

    @InsertProvider(type = CheckResultProvider.class, method = "save")
    int save(CheckResultModel checkResultModel);

    @DeleteProvider(type = CheckResultProvider.class, method = "deleteByIds")
    int deleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = CheckResultProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = CheckResultProvider.class, method = "update")
    int update(CheckResultModel checkResultModel);

    @SelectProvider(type = CheckResultProvider.class, method = "findById")
    CheckResultModel findById(@Param ("id") String id);

    @SelectProvider(type = CheckResultProvider.class, method = "findAll")
    List<CheckResultModel> findAll(CheckResultDto checkResultDto);

    @SelectProvider(type = CheckResultProvider.class, method = "findTotal")
    List<CheckResultDto> findTotal(CheckResultDto checkResultDto);

    @SelectProvider(type = CheckResultProvider.class, method = "resultWarningTotal")
    List<CheckResultDto> resultWarningTotal(CheckResultDto checkResultDto);

    @SelectProvider(type = CheckResultProvider.class, method = "checkInfoTotal")
    List<CheckResultDto> checkInfoTotal(CheckResultDto checkResultDto);

    @SelectProvider(type = CheckResultProvider.class, method = "checkInfoTotal")
    List<CheckResultDtoExcel> checkInfoTotalExcel(CheckResultDto checkResultDto);
}
