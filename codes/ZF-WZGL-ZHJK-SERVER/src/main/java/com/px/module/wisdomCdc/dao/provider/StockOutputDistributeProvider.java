package com.px.module.wisdomCdc.dao.provider;

import com.px.module.common.base.BaseProvider;
import com.px.module.common.utils.StringUtil;
import com.px.module.wisdomCdc.dto.StockOutputDistributeDto;
import com.px.module.wisdomCdc.entity.StockOutputDistributeModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.module.wisdomCdc.dao.provider
 * @ClassName StockOutputDistributeProvider
 * @Description StockOutputDistributeProvider
 * @author 品讯科技
 * @time   2022-07-27 13:46:11
 * @version V 1.0
 */
public class StockOutputDistributeProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.output_no as outputNo,"+
									 "t.store_no as storeNo,"+
									 "t.grid_id as gridId,"+
									 "t.good_code as goodCode,"+
									 "t.supplier_no as supplierNo,"+
									 "t.output_num as output_num,"+
									 "t.before_output_num as before_output_num,"+
									 "t.output_time as output_time,"+
									 "t.createTime as createTime,"+
									 "t.createUserCode as createUserCode,"+
									 "t.createUserName as createUserName,"+
									 "t.updateTime as updateTime,"+
									 "t.updateUserCode as updateUserCode,"+
									 "t.updateUserName as updateUserName,"+

                                     "t.distribute_status as distributeStatus,"+
                                     "t.receiveUserCode as receiveUserCode,"+
                                     "t.receiveUserName as receiveUserName,"+
                                     "t.receiveTime as receiveTime,"+
                                     "t.receiveRemarks as receiveRemarks"
            ;
    /**
     * @name save
     * @author 品讯科技
     * @param stockOutputDistributeModel
     * @Description 增加
     * @time 2022-07-27 13:46:11
     * @throws
     * @return sql
     */
    public String save(final StockOutputDistributeModel stockOutputDistributeModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("b_t_stock_output_distribute ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (stockOutputDistributeModel.getOutputNo())){
                    VALUES("output_no", "#{outputNo}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getStoreNo())){
                    VALUES("store_no", "#{storeNo}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getGridId())){
                    VALUES("grid_id", "#{gridId}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getGoodCode())){
                    VALUES("good_code", "#{goodCode}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getSupplierNo())){
                    VALUES("supplier_no", "#{supplierNo}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getOutput_num())){
                    VALUES("output_num", "#{output_num}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getBefore_output_num())){
                    VALUES("before_output_num", "#{before_output_num}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getOutput_time())){
                    VALUES("output_time", "#{output_time}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
                }

                if(!StringUtil.isBlank (stockOutputDistributeModel.getReceiveUserCode())){
                    VALUES("receiveUserCode", "#{receiveUserCode}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getReceiveUserName())){
                    VALUES("receiveUserName", "#{receiveUserName}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getReceiveTime())){
                    VALUES("receiveTime", "#{receiveTime}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getReceiveRemarks())){
                    VALUES("receiveRemarks", "#{receiveRemarks}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeModel.getDistributeStatus())){
                    VALUES("distribute_status", "#{distributeStatus}");
                }
            }
        }.toString ();
        return sql;
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 物理删除
     * @time 2022-07-27 13:46:11
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("b_t_stock_output_distribute");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }


    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param ids
     * @Description 逻辑删除
     * @time 2022-07-27 13:46:11
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("b_t_stock_output_distribute");
                SET("isRemoved = '0'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param stockOutputDistributeModel
     * @Description 修改
     * @time 2022-07-27 13:46:11
     * @throws
     * @return sql
     */
    public String update(final StockOutputDistributeModel stockOutputDistributeModel) {
        String sql = new SQL () {
            {
                UPDATE("b_t_stock_output_distribute ");
                    if(stockOutputDistributeModel.getOutputNo() != null){
                        SET("output_no = #{outputNo}");
                    }
                    if(stockOutputDistributeModel.getStoreNo() != null){
                        SET("store_no = #{storeNo}");
                    }
                    if(stockOutputDistributeModel.getGridId() != null){
                        SET("grid_id = #{gridId}");
                    }
                    if(stockOutputDistributeModel.getGoodCode() != null){
                        SET("good_code = #{goodCode}");
                    }
                    if(stockOutputDistributeModel.getSupplierNo() != null){
                        SET("supplier_no = #{supplierNo}");
                    }
                    if(stockOutputDistributeModel.getOutput_num() != null){
                        SET("output_num = #{output_num}");
                    }
                    if(stockOutputDistributeModel.getBefore_output_num() != null){
                        SET("before_output_num = #{before_output_num}");
                    }
                    if(stockOutputDistributeModel.getOutput_time() != null){
                        SET("output_time = #{output_time}");
                    }
                    if(stockOutputDistributeModel.getCreateTime() != null){
                        SET("createTime = #{createTime}");
                    }
                    if(stockOutputDistributeModel.getCreateUserCode() != null){
                        SET("createUserCode = #{createUserCode}");
                    }
                    if(stockOutputDistributeModel.getCreateUserName() != null){
                        SET("createUserName = #{createUserName}");
                    }
                    if(stockOutputDistributeModel.getUpdateTime() != null){
                        SET("updateTime = #{updateTime}");
                    }
                    if(stockOutputDistributeModel.getUpdateUserCode() != null){
                        SET("updateUserCode = #{updateUserCode}");
                    }
                    if(stockOutputDistributeModel.getUpdateUserName() != null){
                        SET("updateUserName = #{updateUserName}");
                    }
                WHERE("id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findById
     * @author 品讯科技
     * @param id
     * @Description 根据Id查询数据
     * @time 2022-07-27 13:46:11
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_stock_output_distribute t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param stockOutputDistributeDto
     * @Description 根据条件查询数据并分页
     * @time 2022-07-27 13:46:11
     * @throws
     * @return sql
     */
    public String findAll(final StockOutputDistributeDto stockOutputDistributeDto) {
        String selectUnion="t.id as id,"+
                "t.output_no as outputNo,"+
                "t.store_no as storeNo,"+
                "t.grid_id as gridId,"+
                "t.good_code as goodCode,"+
                "g.good_name as goodName,"+
                "g.measure_unit_id as measureUnitId,"+
                "d.dic_name as measureUnit,"+
                "g.specs_model_name as specsModelName,"+
                "t.supplier_no as supplierNo,"+
                "s.supplier_name as supplierName,"+
                "t.output_num as output_num,"+
                "t.before_output_num as before_output_num,"+
                "t.output_time as output_time,"+
                "t.createTime as createTime,"+
                "t.createUserCode as createUserCode,"+
                "t.createUserName as createUserName,"+
                "t.updateTime as updateTime,"+
                "t.updateUserCode as updateUserCode,"+
                "t.updateUserName as updateUserName";
        String sql = new SQL () {
            {
                SELECT(selectUnion);
                FROM("b_t_stock_output_distribute t " +
                        "left join b_t_good g on t.good_code = g.good_code " +
                        "left join b_t_supplier s on t.supplier_no = s.supplier_no " +
                        "left join b_t_dic d on d.dic_code = g.measure_unit_id  and d.dic_type = 'measure_unit' ");
                if(!StringUtil.isBlank (stockOutputDistributeDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getOutputNo())){
                    WHERE("t.output_no = #{outputNo}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getStoreNo())){
                    WHERE("t.store_no = #{storeNo}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getGridId())){
                    WHERE("t.grid_id = #{gridId}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getGoodCode())){
                    WHERE("t.good_code = #{goodCode}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getSupplierNo())){
                    WHERE("t.supplier_no = #{supplierNo}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getOutput_num())){
                    WHERE("t.output_num = #{output_num}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getBefore_output_num())){
                    WHERE("t.before_output_num = #{before_output_num}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getOutput_time())){
                    WHERE("t.output_time = #{output_time}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }

                if(!StringUtil.isBlank (stockOutputDistributeDto.getReceiveUserCode())){
                    WHERE("t.receiveUserCode = #{receiveUserCode}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getReceiveUserName())){
                    WHERE("t.receiveUserName = #{receiveUserName}");
                }
                if(!StringUtil.isBlank (stockOutputDistributeDto.getReceiveRemarks())){
                    WHERE("t.receiveTime = #{receiveTime}");
                }
            }
        }.toString();
        return sql;
    }


    /**
     * @name updateByOutputNo
     * @author 品讯科技
     * @param stockOutputDistributeModel
     * @Description 修改
     * @time 2022-07-27 13:46:11
     * @throws
     * @return sql
     */
    public String updateByOutputNo(final StockOutputDistributeModel stockOutputDistributeModel) {
        String sql = new SQL () {
            {
                UPDATE("b_t_stock_output_distribute ");
                if(stockOutputDistributeModel.getOutputNo() != null){
                    SET("distribute_status = #{distributeStatus}");
                }
                if(stockOutputDistributeModel.getCreateTime() != null){
                    SET("createTime = #{createTime}");
                }
                if(stockOutputDistributeModel.getCreateUserCode() != null){
                    SET("createUserCode = #{createUserCode}");
                }
                if(stockOutputDistributeModel.getCreateUserName() != null){
                    SET("createUserName = #{createUserName}");
                }
                if(stockOutputDistributeModel.getUpdateTime() != null){
                    SET("updateTime = #{updateTime}");
                }
                if(stockOutputDistributeModel.getUpdateUserCode() != null){
                    SET("updateUserCode = #{updateUserCode}");
                }
                if(stockOutputDistributeModel.getUpdateUserName() != null){
                    SET("updateUserName = #{updateUserName}");
                }

                if(stockOutputDistributeModel.getReceiveUserCode() != null){
                    SET("receiveUserCode = #{receiveUserCode}");
                }
                if(stockOutputDistributeModel.getReceiveUserName() != null){
                    SET("receiveUserName = #{receiveUserName}");
                }
                if(stockOutputDistributeModel.getReceiveTime() != null){
                    SET("receiveTime = #{receiveTime}");
                }
                if(stockOutputDistributeModel.getReceiveRemarks() != null){
                    SET("receiveRemarks = #{receiveRemarks}");
                }

                WHERE("output_no = #{outputNo}");
            }
        }.toString();
        return sql;
    }
}
