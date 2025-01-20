package com.px.module.wisdomCdc.dao.provider;

import com.px.module.common.base.BaseProvider;
import com.px.module.common.utils.StringUtil;
import com.px.module.wisdomCdc.dto.AppDto;
import com.px.module.wisdomCdc.entity.AppModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.module.wisdomCdc.dao.provider
 * @ClassName AppProvider
 * @Description AppProvider
 * @author 品讯科技
 * @time   2022-07-27 11:32:37
 * @version V 1.0
 */
public class AppProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.app_id as appId,"+
									 "t.app_code as appCode,"+
									 "t.app_type as appType,"+
									 "t.app_name as appName,"+
									 "t.app_url as appUrl,"+
									 "t.app_description as appDescription,"+
									 "t.app_status as appStatus,"+
									 "t.remark as remark,"+
									 "t.create_time as createTime,"+
									 "t.create_user_code as createUserCode,"+
									 "t.create_user_name as createUserName,"+
									 "t.update_time as updateTime,"+
									 "t.update_user_code as updateUserCode,"+
									 "t.update_user_name as updateUserName";
    /**
     * @name save
     * @author 品讯科技
     * @param appModel
     * @Description 增加
     * @time 2022-07-27 11:32:37
     * @throws
     * @return sql
     */
    public String save(final AppModel appModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("a_t_app ");
                VALUES("app_id", "#{appId}");
                if(!StringUtil.isBlank (appModel.getAppCode())){
                    VALUES("app_code", "#{appCode}");
                }
                if(!StringUtil.isBlank (appModel.getAppType())){
                    VALUES("app_type", "#{appType}");
                }
                if(!StringUtil.isBlank (appModel.getAppName())){
                    VALUES("app_name", "#{appName}");
                }
                if(!StringUtil.isBlank (appModel.getAppUrl())){
                    VALUES("app_url", "#{appUrl}");
                }
                if(!StringUtil.isBlank (appModel.getAppDescription())){
                    VALUES("app_description", "#{appDescription}");
                }
                if(!StringUtil.isBlank (appModel.getAppStatus())){
                    VALUES("app_status", "#{appStatus}");
                }
                if(!StringUtil.isBlank (appModel.getRemark())){
                    VALUES("remark", "#{remark}");
                }
                if(!StringUtil.isBlank (appModel.getCreateTime())){
                    VALUES("create_time", "#{createTime}");
                }
                if(!StringUtil.isBlank (appModel.getCreateUserCode())){
                    VALUES("create_user_code", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (appModel.getCreateUserName())){
                    VALUES("create_user_name", "#{createUserName}");
                }
                if(!StringUtil.isBlank (appModel.getUpdateTime())){
                    VALUES("update_time", "#{updateTime}");
                }
                if(!StringUtil.isBlank (appModel.getUpdateUserCode())){
                    VALUES("update_user_code", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (appModel.getUpdateUserName())){
                    VALUES("update_user_name", "#{updateUserName}");
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
     * @time 2022-07-27 11:32:37
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("a_t_app");
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
     * @time 2022-07-27 11:32:37
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("appIds") String ids) {
        String sql = new SQL () {
            {
                UPDATE("a_t_app");
                SET("is_removed = '0'");
            }
        }.toString();
        sql += batchJoinField("app_id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param appModel
     * @Description 修改
     * @time 2022-07-27 11:32:37
     * @throws
     * @return sql
     */
    public String update(final AppModel appModel) {
        String sql = new SQL () {
            {
                UPDATE("a_t_app ");
                    if(appModel.getAppCode() != null){
                        SET("app_code = #{appCode}");
                    }
                    if(appModel.getAppType() != null){
                        SET("app_type = #{appType}");
                    }
                    if(appModel.getAppName() != null){
                        SET("app_name = #{appName}");
                    }
                    if(appModel.getAppUrl() != null){
                        SET("app_url = #{appUrl}");
                    }
                    if(appModel.getAppDescription() != null){
                        SET("app_description = #{appDescription}");
                    }
                    if(appModel.getAppStatus() != null){
                        SET("app_status = #{appStatus}");
                    }
                    if(appModel.getRemark() != null){
                        SET("remark = #{remark}");
                    }
                    if(appModel.getCreateTime() != null){
                        SET("create_time = #{createTime}");
                    }
                    if(appModel.getCreateUserCode() != null){
                        SET("create_user_code = #{createUserCode}");
                    }
                    if(appModel.getCreateUserName() != null){
                        SET("create_user_name = #{createUserName}");
                    }
                    if(appModel.getUpdateTime() != null){
                        SET("update_time = #{updateTime}");
                    }
                    if(appModel.getUpdateUserCode() != null){
                        SET("update_user_code = #{updateUserCode}");
                    }
                    if(appModel.getUpdateUserName() != null){
                        SET("update_user_name = #{updateUserName}");
                    }
                WHERE("app_id = #{appId}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findById
     * @author 品讯科技
     * @param appId
     * @Description 根据Id查询数据
     * @time 2022-07-27 11:32:37
     * @throws
     * @return sql
     */
    public String findById(@Param ("appId") String appId) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("a_t_app t");
                WHERE("t.app_id = #{appId}");
            }
        }.toString();
        return sql;
    }

    
    
    /**
     * @name findBycode
     * @author 品讯科技
     * @param appId
     * @Description 根据Id查询数据
     * @time 2022-07-27 11:32:37
     * @throws
     * @return sql
     */
    public String findBycode(@Param ("appCode") String appId) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("a_t_app t");
                WHERE("t.app_code = #{appCode}");
            }
        }.toString();
        return sql;
    }
    
    
    /**
     * @name findAll
     * @author 品讯科技
     * @param appDto
     * @Description 根据条件查询数据并分页
     * @time 2022-07-27 11:32:37
     * @throws
     * @return sql
     */
    public String findAll(final AppDto appDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("a_t_app t");
                if(!StringUtil.isBlank (appDto.getAppId())){
                    WHERE("t.app_id = #{appId}");
                }
                if(!StringUtil.isBlank (appDto.getAppCode())){
                    WHERE("t.app_code = #{appCode}");
                }
                if(!StringUtil.isBlank (appDto.getAppType())){
                    WHERE("t.app_type = #{appType}");
                }
                if(!StringUtil.isBlank (appDto.getAppName())){
                    WHERE("t.app_name = #{appName}");
                }
                if(!StringUtil.isBlank (appDto.getAppUrl())){
                    WHERE("t.app_url = #{appUrl}");
                }
                if(!StringUtil.isBlank (appDto.getAppDescription())){
                    WHERE("t.app_description = #{appDescription}");
                }
                if(!StringUtil.isBlank (appDto.getAppStatus())){
                    WHERE("t.app_status = #{appStatus}");
                }
                if(!StringUtil.isBlank (appDto.getRemark())){
                    WHERE("t.remark = #{remark}");
                }
                if(!StringUtil.isBlank (appDto.getCreateTime())){
                    WHERE("t.create_time = #{createTime}");
                }
                if(!StringUtil.isBlank (appDto.getCreateUserCode())){
                    WHERE("t.create_user_code = #{createUserCode}");
                }
                if(!StringUtil.isBlank (appDto.getCreateUserName())){
                    WHERE("t.create_user_name = #{createUserName}");
                }
                if(!StringUtil.isBlank (appDto.getUpdateTime())){
                    WHERE("t.update_time = #{updateTime}");
                }
                if(!StringUtil.isBlank (appDto.getUpdateUserCode())){
                    WHERE("t.update_user_code = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (appDto.getUpdateUserName())){
                    WHERE("t.update_user_name = #{updateUserName}");
                }
                WHERE("t.is_removed = '1'");
            }
        }.toString();
        return sql;
    }


    public String checkAppNameUnique(final AppModel appModel) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("a_t_app t");
                WHERE("t.app_name = #{appName}");


            }
        }.toString()+" limit 1";
        return sql;
    }

}
