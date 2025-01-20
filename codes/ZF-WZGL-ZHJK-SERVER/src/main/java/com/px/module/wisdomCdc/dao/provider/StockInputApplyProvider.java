package com.px.module.wisdomCdc.dao.provider;

import com.px.module.common.base.BaseProvider;
import com.px.module.common.utils.StringUtil;
import com.px.module.wisdomCdc.dto.StockInputApplyDto;
import com.px.module.wisdomCdc.entity.StockInputApplyModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.module.wisdomCdc.dao.provider
 * @ClassName StockInputApplyProvider
 * @Description StockInputApplyProvider
 * @author 品讯科技
 * @time   2022-07-02 23:07:46
 * @version V 1.0
 */
public class StockInputApplyProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.input_no as inputNo,"+
									 "t.store_no as storeNo,"+
									 "t.supplier_no as supplierNo,"+
									 "t.applyDept as applyDept,"+
									 "t.applyUserCode as applyUserCode,"+
									 "t.applyUserName as applyUserName,"+
									 "t.applyPhone as applyPhone,"+
                                     "t.applyTime as applyTime,"+
									 "t.approveUserCode as approveUserCode,"+
									 "t.approveUserName as approveUserName,"+
									 "t.approveTime as approveTime,"+
									 "t.approveContent as approveContent,"+
									 "t.approveStatus as approveStatus,"+
									 "t.createTime as createTime,"+
									 "t.createUserCode as createUserCode,"+
									 "t.createUserName as createUserName,"+
									 "t.updateTime as updateTime,"+
									 "t.updateUserCode as updateUserCode,"+
									 "t.updateUserName as updateUserName";
    /**
     * @name save
     * @author 品讯科技
     * @param stockInputApplyModel
     * @Description 增加
     * @time 2022-07-02 23:07:46
     * @throws
     * @return sql
     */
    public String save(final StockInputApplyModel stockInputApplyModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("b_t_stock_input_apply ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (stockInputApplyModel.getInputNo())){
                    VALUES("input_no", "#{inputNo}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getStoreNo())){
                    VALUES("store_no", "#{storeNo}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getSupplierNo())){
                    VALUES("supplier_no", "#{supplierNo}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getApplyDept())){
                    VALUES("applyDept", "#{applyDept}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getApplyUserCode())){
                    VALUES("applyUserCode", "#{applyUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getApplyUserName())){
                    VALUES("applyUserName", "#{applyUserName}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getApplyPhone())){
                    VALUES("applyPhone", "#{applyPhone}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getApplyTime())){
                    VALUES("applyTime", "#{applyTime}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getApproveUserCode())){
                    VALUES("approveUserCode", "#{approveUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getApproveUserName())){
                    VALUES("approveUserName", "#{approveUserName}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getApproveTime())){
                    VALUES("approveTime", "#{approveTime}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getApproveContent())){
                    VALUES("approveContent", "#{approveContent}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getApproveStatus())){
                    VALUES("approveStatus", "#{approveStatus}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getCreateTime())){
                    VALUES("createTime", "#{createTime}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyModel.getUpdateUserName())){
                    VALUES("updateUserName", "#{updateUserName}");
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
     * @time 2022-07-02 23:07:46
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("b_t_stock_input_apply");
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
     * @time 2022-07-02 23:07:46
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("b_t_stock_input_apply");
                SET("is_Removed = '1'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param stockInputApplyModel
     * @Description 修改
     * @time 2022-07-02 23:07:46
     * @throws
     * @return sql
     */
    public String update(final StockInputApplyModel stockInputApplyModel) {
        String sql = new SQL () {
            {
                UPDATE("b_t_stock_input_apply ");
                    if(stockInputApplyModel.getInputNo() != null){
                        SET("input_no = #{inputNo}");
                    }
                    if(stockInputApplyModel.getStoreNo() != null){
                        SET("store_no = #{storeNo}");
                    }
                    if(stockInputApplyModel.getSupplierNo() != null){
                        SET("supplier_no = #{supplierNo}");
                    }
                    if(stockInputApplyModel.getApplyDept() != null){
                        SET("applyDept = #{applyDept}");
                    }
                    if(stockInputApplyModel.getApplyUserCode() != null){
                        SET("applyUserCode = #{applyUserCode}");
                    }
                    if(stockInputApplyModel.getApplyUserName() != null){
                        SET("applyUserName = #{applyUserName}");
                    }
                    if(stockInputApplyModel.getApplyPhone() != null){
                        SET("applyPhone = #{applyPhone}");
                    }
                    if(stockInputApplyModel.getApplyTime() != null){
                        SET("applyTime = #{applyTime}");
                    }
                    if(stockInputApplyModel.getApproveUserCode() != null){
                        SET("approveUserCode = #{approveUserCode}");
                    }
                    if(stockInputApplyModel.getApproveUserName() != null){
                        SET("approveUserName = #{approveUserName}");
                    }
                    if(stockInputApplyModel.getApproveTime() != null){
                        SET("approveTime = #{approveTime}");
                    }
                    if(stockInputApplyModel.getApproveContent() != null){
                        SET("approveContent = #{approveContent}");
                    }
                    if(stockInputApplyModel.getApproveStatus() != null){
                        SET("approveStatus = #{approveStatus}");
                    }
                    if(stockInputApplyModel.getCreateTime() != null){
                        SET("createTime = #{createTime}");
                    }
                    if(stockInputApplyModel.getCreateUserCode() != null){
                        SET("createUserCode = #{createUserCode}");
                    }
                    if(stockInputApplyModel.getCreateUserName() != null){
                        SET("createUserName = #{createUserName}");
                    }
                    if(stockInputApplyModel.getUpdateTime() != null){
                        SET("updateTime = #{updateTime}");
                    }
                    if(stockInputApplyModel.getUpdateUserCode() != null){
                        SET("updateUserCode = #{updateUserCode}");
                    }
                    if(stockInputApplyModel.getUpdateUserName() != null){
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
     * @time 2022-07-02 23:07:46
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_stock_input_apply t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param stockInputApplyDto
     * @Description 根据条件查询数据并分页
     * @time 2022-07-02 23:07:46
     * @throws
     * @return sql
     */
    public String findAll(final StockInputApplyDto stockInputApplyDto) {
        String selectOrInsertSql="t.id as id,"+
                "t.input_no as inputNo,"+
                "t.store_no as storeNo,"+
                "st.store_name as storeName,"+
                "t.supplier_no as supplierNo,"+
                "s.supplier_name as supplierName,"+
                "t.applyDept as applyDept,"+
                "td.depart_name as applyDeptName,"+
                "t.applyUserCode as applyUserCode,"+
                "t.applyUserName as applyUserName,"+
                "t.applyPhone as applyPhone,"+
                "DATE_FORMAT(t.applyTime,'%Y-%m-%d %H:%i:%S') as applyTime,"+
                "t.approveUserCode as approveUserCode,"+
                "t.approveUserName as approveUserName,"+
                "DATE_FORMAT(t.approveTime,'%Y-%m-%d %H:%i:%S')  as approveTime,"+
                "t.approveContent as approveContent,"+
                "t.approveStatus as approveStatus,"+
                "d.dic_name as approveStatusName,"+
                "DATE_FORMAT(t.createTime,'%Y-%m-%d %H:%i:%S') as createTime,"+
                "t.createUserCode as createUserCode,"+
                "t.createUserName as createUserName,"+
                "DATE_FORMAT(t.updateTime,'%Y-%m-%d %H:%i:%S') as updateTime,"+
                "t.updateUserCode as updateUserCode,"+
                "t.updateUserName as updateUserName";
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_stock_input_apply t " +
                        "left join b_t_supplier s on t.supplier_no = s.supplier_no "+
                        "left join b_t_store st on st.store_no = t.store_no "+
                        "left join m_t_department td on t.applyDept = td.depart_id "+
                        "left join b_t_dic d on t.approveStatus = d.dic_code and d.dic_type='input_status'  ");
                WHERE( " 1 = 1 AND t.is_removed = 0" );
                if(!StringUtil.isBlank (stockInputApplyDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getInputNo())){
                    WHERE("t.input_no like CONCAT('%', #{inputNo},'%')");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getStoreNo())){
                    WHERE("t.store_no = #{storeNo}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getSupplierNo())){
                    WHERE("t.supplier_no = #{supplierNo}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApplyDept())){
                    WHERE("t.applyDept = #{applyDept}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApplyUserCode())){
                    WHERE("t.applyUserCode = #{applyUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApplyUserName())){
                    WHERE("t.applyUserName like CONCAT('%', #{applyUserName},'%')");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApplyPhone())){
                    WHERE("t.applyPhone = #{applyPhone}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApproveUserCode())){
                    WHERE("t.approveUserCode = #{approveUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApproveUserName())){
                    WHERE("t.approveUserName = #{approveUserName}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApproveTime())){
                    WHERE("t.approveTime = #{approveTime}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApproveContent())){
                    WHERE("t.approveContent = #{approveContent}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApproveStatus())){
                    WHERE("t.approveStatus = #{approveStatus}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                ORDER_BY( " t.createTime desc " );
            }
        }.toString();
        return sql;
    }



    /**
     * @name findStockInputApplyByInputNo
     * @author 品讯科技
     * @param inputNo
     * @Description 根据Id查询数据
     * @time 2022-07-02 23:07:46
     * @throws
     * @return sql
     */
    public String findStockInputApplyByInputNo(@Param ("inputNo") String inputNo) {
        String selectOrInsertSql="t.id as id,"+
                "t.input_no as inputNo,"+
                "t.store_no as storeNo,"+
                "st.store_name as storeName,"+
                "t.supplier_no as supplierNo,"+
                "s.supplier_name as supplierName,"+
                "t.applyDept as applyDept,"+
                "td.depart_name as applyDeptName,"+
                "t.applyUserCode as applyUserCode,"+
                "t.applyUserName as applyUserName,"+
                "t.applyPhone as applyPhone,"+
                "t.approveUserCode as approveUserCode,"+
                "t.approveUserName as approveUserName,"+
                "t.approveTime as approveTime,"+
                "t.approveContent as approveContent,"+
                "t.approveStatus as approveStatus,"+
                "d.dic_name as approveStatusName,"+
                "t.createTime as createTime,"+
                "t.createUserCode as createUserCode,"+
                "t.createUserName as createUserName,"+
                "t.updateTime as updateTime,"+
                "t.updateUserCode as updateUserCode,"+
                "t.updateUserName as updateUserName";
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_stock_input_apply t " +
                        "left join b_t_supplier s on t.supplier_no = s.supplier_no "+
                        "left join b_t_store st on st.store_no = t.store_no "+
                        "left join m_t_department td on t.applyDept = td.depart_id "+
                        "left join b_t_dic d on t.approveStatus = d.dic_code and d.dic_type='input_status'  ");
                WHERE("t.input_no = #{inputNo}");
            }
        }.toString();
        return sql;
    }



    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param inputNo
     * @Description 逻辑删除
     * @time 2022-07-02 23:07:46
     * @throws
     * @return sql
     */
    public String logicDeleteByInputNo(@Param ("inputNo") String inputNo) {
        String sql = new SQL () {
            {
                UPDATE("b_t_stock_input_apply");
                SET("is_Removed = '1'");
            }
        }.toString();
        sql += batchJoinField("input_no",inputNo);
        return sql;
    }




    /**
     * @name deleteByInputNo
     * @author 品讯科技
     * @param inputNo
     * @Description 物理删除
     * @time 2022-07-02 23:07:46
     * @throws
     * @return sql
     */
    public String deleteByInputNo(@Param ("inputNo") String inputNo) {
        String sql = new SQL () {
            {
                DELETE_FROM("b_t_stock_input_apply");
            }
        }.toString();
        sql += batchJoinField("input_no",inputNo);
        return sql;
    }


    /**
     * @name report
     * @author 品讯科技
     * @param stockInputApplyDto
     * @Description 根据条件查询数据并分页
     * @time 2022-07-02 23:07:46
     * @throws
     * @return sql
     */
    public String report(final StockInputApplyDto stockInputApplyDto) {
        String selectOrInsertSql="t.id as id,"+
                "t.input_no as inputNo,"+
                "t.store_no as storeNo,"+
                "st.store_name as storeName,"+
                "t.supplier_no as supplierNo,"+
                "s.supplier_name as supplierName,"+
                "t.applyDept as applyDept,"+
                "td.depart_name as applyDeptName,"+
                "t.applyUserCode as applyUserCode,"+
                "t.applyUserName as applyUserName,"+
                "t.applyPhone as applyPhone,"+
                "DATE_FORMAT(t.applyTime,'%Y-%m-%d %H:%i:%S') as applyTime,"+
                "t.approveUserCode as approveUserCode,"+
                "t.approveUserName as approveUserName,"+
                "DATE_FORMAT(t.approveTime,'%Y-%m-%d %H:%i:%S')  as approveTime,"+
                "t.approveContent as approveContent,"+
                "t.approveStatus as approveStatus,"+
                "d.dic_name as approveStatusName,"+
                "DATE_FORMAT(t.createTime,'%Y-%m-%d %H:%i:%S') as createTime,"+
                "t.createUserCode as createUserCode,"+
                "t.createUserName as createUserName,"+
                "DATE_FORMAT(t.updateTime,'%Y-%m-%d %H:%i:%S') as updateTime,"+
                "t.updateUserCode as updateUserCode,"+
                "t.updateUserName as updateUserName";
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("b_t_stock_input_apply t " +
                        "left join b_t_supplier s on t.supplier_no = s.supplier_no "+
                        "left join b_t_store st on st.store_no = t.store_no "+
                        "left join m_t_department td on t.applyDept = td.depart_id "+
                        "left join b_t_dic d on t.approveStatus = d.dic_code and d.dic_type='input_status'  ");
                WHERE( " 1 = 1 AND t.is_removed = 0  and t.approveStatus = '2' " );
                if(!StringUtil.isBlank (stockInputApplyDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getInputNo())){
                    WHERE("t.input_no like CONCAT('%', #{inputNo},'%')");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getStoreNo())){
                    WHERE("t.store_no = #{storeNo}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getSupplierNo())){
                    WHERE("t.supplier_no = #{supplierNo}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApplyDept())){
                    WHERE("t.applyDept = #{applyDept}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApplyUserCode())){
                    WHERE("t.applyUserCode = #{applyUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApplyUserName())){
                    WHERE("t.applyUserName like CONCAT('%', #{applyUserName},'%')");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApplyPhone())){
                    WHERE("t.applyPhone = #{applyPhone}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApproveUserCode())){
                    WHERE("t.approveUserCode = #{approveUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApproveUserName())){
                    WHERE("t.approveUserName = #{approveUserName}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApproveTime())){
                    WHERE("t.approveTime = #{approveTime}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApproveContent())){
                    WHERE("t.approveContent = #{approveContent}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getApproveStatus())){
                    WHERE("t.approveStatus = #{approveStatus}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (stockInputApplyDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                ORDER_BY( " t.createTime desc " );
            }
        }.toString();
        return sql;
    }

}
