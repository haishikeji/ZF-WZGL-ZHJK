package com.px.module.wisdomCdc.dao;

import com.px.module.wisdomCdc.dao.provider.RoleProvider;
import com.px.module.wisdomCdc.dto.RoleDto;
import com.px.module.wisdomCdc.entity.RoleModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface IRoleMapper {

    @InsertProvider(type = RoleProvider.class, method = "save")
    int save(RoleModel roleModel);

    @DeleteProvider(type = RoleProvider.class, method = "deleteByIds")
    int deleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = RoleProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param ("ids") String ids);

    @UpdateProvider(type = RoleProvider.class, method = "update")
    int update(RoleModel roleModel);

    @SelectProvider(type = RoleProvider.class, method = "findById")
    RoleModel findById(@Param ("id") String id);

    @SelectProvider(type = RoleProvider.class, method = "findAll")
    List<RoleModel> findAll(RoleDto roleDto);

    @UpdateProvider(type = RoleProvider.class, method = "deleteByRoleId")
    int deleteByRoleId(@Param ("roleId") String roleId);

    @UpdateProvider(type = RoleProvider.class, method = "batchSaveRoleMenu")
    int batchSaveRoleMenu(@Param ("roleId") String roleId,@Param ("menuIds") String menuIds,@Param ("type") String type);
}
