package com.px.module.wisdomCdc.dao.provider;

import com.px.module.common.base.BaseProvider;
import com.px.module.common.utils.StringUtil;
import com.px.module.wisdomCdc.dto.SConsultationTypeDto;
import com.px.module.wisdomCdc.entity.SConsultationTypeModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.module.wisdomCdc.dao.provider
 * @ClassName SConsultationTypeProvider
 * @Description SConsultationTypeProvider
 * @author 品讯科技
 * @time   2021-11-05 09:39:09
 * @version V 1.0
 */
public class SConsultationTypeProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
									 "t.consultation_id as consultationId,"+
									 "t.consultation_name as consultationName,"+
									 "t.consultation_id_type as consultationIdType,"+
									 "t.create_time as createTime,"+
									 "t.create_user_code as createUserCode,"+
									 "t.create_user_name as createUserName,"+
									 "t.update_time as updateTime,"+
									 "t.update_user_code as updateUserCode,"+
									 "t.update_user_code as updateUserCode,"+
									 "t.is_removed as isRemoved,"+
									 "t.update_user_name as updateUserName";
    /**
     * @name save
     * @author 品讯科技
     * @param sConsultationTypeModel
     * @Description 增加
     * @time 2021-11-05 09:39:09
     * @throws
     * @return sql
     */
    public String save(final SConsultationTypeModel sConsultationTypeModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("s_consultation_type ");
                VALUES("id", "#{id}");
                if(!StringUtil.isBlank (sConsultationTypeModel.getConsultationId())){
                    VALUES("consultation_id", "#{consultationId}");
                }
                if(!StringUtil.isBlank (sConsultationTypeModel.getConsultationName())){
                    VALUES("consultation_name", "#{consultationName}");
                }
                if(!StringUtil.isBlank (sConsultationTypeModel.getConsultationIdType())){
                    VALUES("consultation_id_type", "#{consultationIdType}");
                }
                if(!StringUtil.isBlank (sConsultationTypeModel.getCreateTime())){
                    VALUES("create_time", "#{createTime}");
                }
                if(!StringUtil.isBlank (sConsultationTypeModel.getCreateUserCode())){
                    VALUES("create_user_code", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (sConsultationTypeModel.getCreateUserName())){
                    VALUES("create_user_name", "#{createUserName}");
                }
                if(!StringUtil.isBlank (sConsultationTypeModel.getUpdateTime())){
                    VALUES("update_time", "#{updateTime}");
                }
                if(!StringUtil.isBlank (sConsultationTypeModel.getUpdateUserCode())){
                    VALUES("update_user_code", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (sConsultationTypeModel.getUpdateUserName())){
                    VALUES("update_user_name", "#{updateUserName}");
                }
                if(!StringUtil.isBlank (sConsultationTypeModel.getIsRemoved())){
                    VALUES("is_removed", "#{isRemoved}");
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
     * @time 2021-11-05 09:39:09
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("s_consultation_type");
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
     * @time 2021-11-05 09:39:09
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("s_consultation_type");
                SET("is_removed = '0'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param sConsultationTypeModel
     * @Description 修改
     * @time 2021-11-05 09:39:09
     * @throws
     * @return sql
     */
    public String update(final SConsultationTypeModel sConsultationTypeModel) {
        String sql = new SQL () {
            {
                UPDATE("s_consultation_type ");
//                    if(sConsultationTypeModel.getConsultationId() != null){
//                        SET("consultation_id = #{consultationId}");
//                    }
//                    if(sConsultationTypeModel.getConsultationName() != null){
//                        SET("consultation_name = #{consultationName}");
//                    }
                    if(sConsultationTypeModel.getConsultationIdType() != null){
                        SET("consultation_id_type = #{consultationIdType}");
                    }
//                    if(sConsultationTypeModel.getCreateTime() != null){
//                        SET("createTime = #{createTime}");
//                    }
//                    if(sConsultationTypeModel.getCreateUserCode() != null){
//                        SET("createUserCode = #{createUserCode}");
//                    }
//                    if(sConsultationTypeModel.getCreateUserName() != null){
//                        SET("createUserName = #{createUserName}");
//                    }
//                    if(sConsultationTypeModel.getUpdateTime() != null){
//                        SET("updateTime = #{updateTime}");
//                    }
//                    if(sConsultationTypeModel.getUpdateUserCode() != null){
//                        SET("updateUserCode = #{updateUserCode}");
//                    }
//                    if(sConsultationTypeModel.getUpdateUserName() != null){
//                        SET("updateUserName = #{updateUserName}");
//                    }
                WHERE("id = #{id}");
            }
        }.toString();
        return sql;
    }
    
    
    
    
    public String updateOne(final SConsultationTypeModel sConsultationTypeModel) {
        String sql = new SQL () {
            {
                UPDATE("s_consultation_type ");
                    if(sConsultationTypeModel.getConsultationId() != null){
                        SET("consultation_id = #{consultationId}");
                    }
                    if(sConsultationTypeModel.getConsultationName() != null){
                       SET("consultation_name = #{consultationName}");
                    }
//                    if(sConsultationTypeModel.getConsultationIdType() != null){
//                        SET("consultation_id_type = #{consultationIdType}");
//                    }
//                    if(sConsultationTypeModel.getCreateTime() != null){
//                        SET("createTime = #{createTime}");
//                    }
//                    if(sConsultationTypeModel.getCreateUserCode() != null){
//                        SET("createUserCode = #{createUserCode}");
//                    }
//                    if(sConsultationTypeModel.getCreateUserName() != null){
//                        SET("createUserName = #{createUserName}");
//                    }
//                    if(sConsultationTypeModel.getUpdateTime() != null){
//                        SET("updateTime = #{updateTime}");
//                    }
//                    if(sConsultationTypeModel.getUpdateUserCode() != null){
//                        SET("updateUserCode = #{updateUserCode}");
//                    }
//                    if(sConsultationTypeModel.getUpdateUserName() != null){
//                        SET("updateUserName = #{updateUserName}");
//                    }
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
     * @time 2021-11-05 09:39:09
     * @throws
     * @return sql
     */
    public String findById(@Param ("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("s_consultation_type t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param sConsultationTypeDto
     * @Description 根据条件查询数据并分页
     * @time 2021-11-05 09:39:09
     * @throws
     * @return sql
     */
    public String findAll(final SConsultationTypeDto sConsultationTypeDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("s_consultation_type t  ");
//                if(!StringUtil.isBlank (sConsultationTypeDto.getId())){
//                    WHERE("t.id = #{id}");
//                }
//                if(!StringUtil.isBlank (sConsultationTypeDto.getConsultationId())){
//                    WHERE("t.consultation_id = #{consultationId}");
//                }
                if(!StringUtil.isBlank (sConsultationTypeDto.getConsultationName())){
	                String  ss = null;
	                ss ="%"+sConsultationTypeDto.getConsultationName()+"%";
	                sConsultationTypeDto.setConsultationName(ss);
                    WHERE("t.consultation_name like #{consultationName}");
                }
                
                
                
//                if(!StringUtil.isBlank (sConsultationTypeDto.getConsultationIdType())){
//                    WHERE("t.consultation_id_type = #{consultationIdType}");
//                }
//                if(!StringUtil.isBlank (sConsultationTypeDto.getCreateTime())){
//                    WHERE("t.createTime = #{createTime}");
//                }
//                if(!StringUtil.isBlank (sConsultationTypeDto.getCreateUserCode())){
//                    WHERE("t.createUserCode = #{createUserCode}");
//                }
//                if(!StringUtil.isBlank (sConsultationTypeDto.getCreateUserName())){
//                    WHERE("t.createUserName = #{createUserName}");
//                }
//                if(!StringUtil.isBlank (sConsultationTypeDto.getUpdateTime())){
//                    WHERE("t.updateTime = #{updateTime}");
//                }
//                if(!StringUtil.isBlank (sConsultationTypeDto.getUpdateUserCode())){
//                    WHERE("t.updateUserCode = #{updateUserCode}");
//                }
               if(!StringUtil.isBlank (sConsultationTypeDto.getIsRemoved())){
                    WHERE("t.is_removed = #{isRemoved}");
                }
            }
        }.toString();
        
     
        
        return sql;
    }

}
