package com.px.module.wisdomCdc.dao;

import com.alibaba.fastjson.JSONObject;
import com.px.module.wisdomCdc.dao.provider.GoodTypeProvider;
import com.px.module.wisdomCdc.dto.GoodTypeDto;
import com.px.module.wisdomCdc.entity.GoodTypeModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface IGoodTypeMapper {

    @InsertProvider(type = GoodTypeProvider.class, method = "save")
    int save(GoodTypeModel goodTypeModel);

    @DeleteProvider(type = GoodTypeProvider.class, method = "deleteByIds")
    int deleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = GoodTypeProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = GoodTypeProvider.class, method = "update")
    int update(GoodTypeModel goodTypeModel);

    @SelectProvider(type = GoodTypeProvider.class, method = "findById")
    GoodTypeModel findById(@Param ("id") String id);

    @SelectProvider(type = GoodTypeProvider.class, method = "findAll")
    List<GoodTypeModel> findAll(GoodTypeDto goodTypeDto);

    @SelectProvider(type = GoodTypeProvider.class, method = "findGoodTypeTreeAll")
    List<JSONObject> findGoodTypeTreeAll(@Param ("typeName") String typeName,@Param ("status") String status);


    @SelectProvider(type = GoodTypeProvider.class, method = "goodTypeCodeList")
    List<JSONObject> goodTypeCodeList();
}
