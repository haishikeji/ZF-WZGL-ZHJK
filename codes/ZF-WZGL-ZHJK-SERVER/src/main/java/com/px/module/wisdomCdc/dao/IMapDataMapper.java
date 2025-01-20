package com.px.module.wisdomCdc.dao;

import com.px.module.wisdomCdc.dao.provider.MapDataProvider;
import com.px.module.wisdomCdc.dto.MapDataDto;
import com.px.module.wisdomCdc.entity.MapDataModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface IMapDataMapper {

    @InsertProvider(type = MapDataProvider.class, method = "save")
    int save(MapDataModel mapDataModel);

    @DeleteProvider(type = MapDataProvider.class, method = "deleteByIds")
    int deleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = MapDataProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = MapDataProvider.class, method = "update")
    int update(MapDataModel mapDataModel);

    @SelectProvider(type = MapDataProvider.class, method = "findById")
    MapDataModel findById(@Param ("id") String id);

    @SelectProvider(type = MapDataProvider.class, method = "findAll")
    List<MapDataModel> findAll(MapDataDto mapDataDto);
    @SelectProvider(type = MapDataProvider.class, method = "findCountAll")
    List<MapDataModel> findCountAll(MapDataDto mapDataDto);
    
    @SelectProvider(type = MapDataProvider.class, method = "findMapList")
    List<MapDataModel> findMapList(MapDataDto mapDataDto);
}
