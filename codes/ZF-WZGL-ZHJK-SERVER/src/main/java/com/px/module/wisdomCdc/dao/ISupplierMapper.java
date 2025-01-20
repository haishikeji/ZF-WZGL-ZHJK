package com.px.module.wisdomCdc.dao;

import com.alibaba.fastjson.JSONObject;
import com.px.module.wisdomCdc.dao.provider.SupplierProvider;
import com.px.module.wisdomCdc.dto.SupplierDto;
import com.px.module.wisdomCdc.entity.SupplierModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface ISupplierMapper {

    @InsertProvider(type = SupplierProvider.class, method = "save")
    int save(SupplierModel supplierModel);

    @DeleteProvider(type = SupplierProvider.class, method = "deleteByIds")
    int deleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = SupplierProvider.class, method = "logicDeleteByIds")
    int logicDeleteByIds(@Param("ids") String ids);

    @UpdateProvider(type = SupplierProvider.class, method = "update")
    int update(SupplierModel supplierModel);

    @SelectProvider(type = SupplierProvider.class, method = "findById")
    SupplierModel findById(@Param("id") String id);

    @SelectProvider(type = SupplierProvider.class, method = "findAll")
    List<SupplierModel> findAll(SupplierDto supplierDto);

    @SelectProvider(type = SupplierProvider.class, method = "supplierList")
    List<JSONObject> supplierList(SupplierDto supplierDto);

    @SelectProvider(type = SupplierProvider.class, method = "supplierStatusList")
    List<JSONObject> supplierStatusList(SupplierDto supplierDto);
}
