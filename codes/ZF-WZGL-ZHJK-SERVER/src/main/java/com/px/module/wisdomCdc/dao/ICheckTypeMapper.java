package com.px.module.wisdomCdc.dao;

import com.px.module.wisdomCdc.dao.provider.CheckTypeProvider;
import com.px.module.wisdomCdc.dto.CheckTypeDto;
import com.px.module.wisdomCdc.entity.CheckTypeModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ICheckTypeMapper {

    @InsertProvider(type = CheckTypeProvider.class, method = "save")
    int save(CheckTypeModel checkTypeModel);

    @DeleteProvider(type = CheckTypeProvider.class, method = "deleteByIds")
    int deleteByIds(@Param ("checkTypeIds") String checkTypeIds);

    @UpdateProvider(type = CheckTypeProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param ("checkTypeIds") String ids);

    @UpdateProvider(type = CheckTypeProvider.class, method = "update")
    int update(CheckTypeModel checkTypeModel);

    @SelectProvider(type = CheckTypeProvider.class, method = "findById")
    CheckTypeModel findById(@Param ("checkTypeId") String id);

    @SelectProvider(type = CheckTypeProvider.class, method = "findAll")
    List<CheckTypeModel> findAll(CheckTypeDto checkTypeDto);

    @SelectProvider(type = CheckTypeProvider.class, method = "checkCheckTypeNameUnique")
    CheckTypeModel checkCheckTypeNameUnique(CheckTypeModel checkTypeModel);

    @SelectProvider(type = CheckTypeProvider.class, method = "checkCheckTypeCodeUnique")
    CheckTypeModel checkCheckTypeCodeUnique(CheckTypeModel checkTypeModel);
}
