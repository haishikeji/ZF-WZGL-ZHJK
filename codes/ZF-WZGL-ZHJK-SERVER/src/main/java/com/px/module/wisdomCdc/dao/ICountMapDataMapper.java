package com.px.module.wisdomCdc.dao;

import com.px.module.wisdomCdc.dao.provider.CountMapDataProvider;
import com.px.module.wisdomCdc.dto.CountMapDataDto;
import com.px.module.wisdomCdc.entity.CountMapDataModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ICountMapDataMapper {

    @InsertProvider(type = CountMapDataProvider.class, method = "save")
    int save(CountMapDataModel countMapDataModel);

    @DeleteProvider(type = CountMapDataProvider.class, method = "deleteByIds")
    int deleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = CountMapDataProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = CountMapDataProvider.class, method = "update")
    int update(CountMapDataModel countMapDataModel);

    @SelectProvider(type = CountMapDataProvider.class, method = "findById")
    CountMapDataModel findById(@Param ("id") String id);

    @SelectProvider(type = CountMapDataProvider.class, method = "findAll")
    List<CountMapDataModel> findAll(CountMapDataDto countMapDataDto);

    @InsertProvider(type = CountMapDataProvider.class, method = "saveBatch")
    int saveBatch(CountMapDataDto countMapDataDto);

    @DeleteProvider(type = CountMapDataProvider.class, method = "deleteByRecordId")
    int deleteByRecordId(@Param ("id") String id);

    @Select("select " +
            "id as id,"+
            "record_id as recordId,"+
            "check_type_id as checkTypeId,"+
            "check_type_name as checkTypeName,"+
            "statistical_time as statisticalTime,"+
            "code as code,"+
            "name as name,"+
            "new_value as newValue,"+
            "old_value as oldValue,"+
            "type as type,"+
            "parent_id as parentId,"+
            "community_id as communityId,"+
            "create_time as createTime,"+
            "is_removed as isRemoved,"+
            "create_user_code as createUserCode,"+
            "create_user_name as createUserName,"+
            "update_time as updateTime,"+
            "update_user_code as updateUserCode,"+
            "update_user_name as updateUserName "+
            " from m_count_map_data " +
            "where record_id = #{recordId}" )
    List<CountMapDataModel> findAllByRecordId(@Param("recordId") String recordId);
}
