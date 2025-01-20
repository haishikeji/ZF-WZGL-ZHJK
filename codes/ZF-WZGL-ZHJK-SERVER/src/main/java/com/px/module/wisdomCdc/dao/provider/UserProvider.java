package com.px.module.wisdomCdc.dao.provider;

import com.px.module.wisdomCdc.dto.UserDto;
import com.px.module.wisdomCdc.entity.UserExpandModel;
import com.px.module.wisdomCdc.entity.UserModel;
import com.px.module.common.base.BaseProvider;
import com.px.module.common.utils.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @Package com.px.module.wisdomCdc.dao.provider
 * @ClassName UserProvider
 * @Description UserProvider
 * @author 品讯科技
 * @time   2021-02-22 16:18:34
 * @version V 1.0
 */
public class UserProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.id as id,"+
            "t.loginname as loginName,"+
            "t.usercode as userCode,"+
            "t.username as userName,"+
            "t.realname as realName,"+
            "t.nickname as nickname,"+
            "t.sex as sex,"+
            "t.birthday as birthday,"+
            "t.image as image,"+
            "t.status as status,"+
            "t.passWord as passWord,"+
            "t.mark as mark,"+
            "t.phone as phone,"+
            "t.createTime as createTime,"+
            "t.createUserCode as createUserCode,"+
            "t.createUserName as createUserName,"+
            "t.updateTime as updateTime,"+
            "t.updateUserCode as updateUserCode,"+
            "t.updateUserName as updateUserName,"+
            "t.isremoved as isRemoved";

    private String findListSql="u.id as id,"+
            "u.loginname as loginName,"+
            "u.usercode as userCode,"+
            "u.username as userName,"+
            "u.realname as realName,"+
            "u.nickname as nickName,"+
            "u.sex as sex,"+
            "u.birthday as birthday,"+
            "u.image as image,"+
            "u.status as status,"+
            "u.passWord as passWord,"+
            "u.mark as mark,"+
            "u.phone as phone,"+
            "u.createTime as createTime,"+
            "u.createUserCode as createUserCode,"+
            "u.createUserName as createUserName,"+
            "u.updateTime as updateTime,"+
            "u.updateUserCode as updateUserCode,"+
            "u.updateUserName as updateUserName,"+
            "u.isremoved as isRemoved";
    /**
     * @name save
     * @author 品讯科技
     * @param userModel
     * @Description 增加
     * @time 2021-02-22 16:18:34
     * @throws
     * @return sql
     */
    public String save(final UserModel userModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("s_t_user ");
                VALUES("id", "#{id}");
                VALUES("createTime", "now()");
                VALUES("isremoved", "'1'");
                if(!StringUtil.isBlank (userModel.getLoginName())){
                    VALUES("loginname", "#{loginName}");
                }
                if(!StringUtil.isBlank (userModel.getUserCode())){
                    VALUES("usercode", "#{userCode}");
                }
                if(!StringUtil.isBlank (userModel.getUserName())){
                    VALUES("username", "#{userName}");
                }
                if(!StringUtil.isBlank (userModel.getRealName())){
                    VALUES("realname", "#{realName}");
                }
                if(!StringUtil.isBlank (userModel.getNickname())){
                    VALUES("nickname", "#{nickname}");
                }
                if(!StringUtil.isBlank (userModel.getSex())){
                    VALUES("sex", "#{sex}");
                }
                if(!StringUtil.isBlank (userModel.getBirthday())){
                    VALUES("birthday", "#{birthday}");
                }
                if(!StringUtil.isBlank (userModel.getImage())){
                    VALUES("image", "#{image}");
                }
                if(!StringUtil.isBlank (userModel.getStatus())){
                    VALUES("status", "#{status}");
                }
                if(!StringUtil.isBlank (userModel.getPassWord())){
                    VALUES("passWord", "#{passWord}");
                }
                if(!StringUtil.isBlank (userModel.getMark())){
                    VALUES("mark", "#{mark}");
                }
                if(!StringUtil.isBlank (userModel.getPhone())){
                    VALUES("phone", "#{phone}");
                }
                if(!StringUtil.isBlank (userModel.getCreateUserCode())){
                    VALUES("createUserCode", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (userModel.getCreateUserName())){
                    VALUES("createUserName", "#{createUserName}");
                }
                if(!StringUtil.isBlank (userModel.getUpdateTime())){
                    VALUES("updateTime", "#{updateTime}");
                }
                if(!StringUtil.isBlank (userModel.getUpdateUserCode())){
                    VALUES("updateUserCode", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (userModel.getUpdateUserName())){
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
     * @time 2021-02-22 16:18:34
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param("ids") String ids) {
        String sql = new SQL () {
            {
                DELETE_FROM("s_t_user");
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
     * @time 2021-02-22 16:18:34
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param("ids") String ids) {
        String sql = new SQL () {
            {
                UPDATE("s_t_user");
                SET("isremoved = '0'");
            }
        }.toString();
        sql += batchJoinField("id",ids);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param userModel
     * @Description 修改
     * @time 2021-02-22 16:18:34
     * @throws
     * @return sql
     */
    public String update(final UserModel userModel) {
        String sql = new SQL () {
            {
                UPDATE("s_t_user ");
                SET("updateTime = now()");
                if(userModel.getLoginName() != null){
                    SET("loginname = #{loginName}");
                }
                if(userModel.getUserCode() != null){
                    SET("usercode = #{userCode}");
                }
                if(userModel.getUserName() != null){
                    SET("username = #{userName}");
                }
                if(userModel.getRealName() != null){
                    SET("realname = #{realName}");
                }
                if(userModel.getNickname() != null){
                    SET("nickname = #{nickname}");
                }
                if(userModel.getSex() != null){
                    SET("sex = #{sex}");
                }
                if(userModel.getBirthday() != null){
                    SET("birthday = #{birthday}");
                }
                if(userModel.getImage() != null){
                    SET("image = #{image}");
                }
                if(userModel.getStatus() != null){
                    SET("status = #{status}");
                }
               /*if(userModel.getPassWord() != null){
                    SET("passWord = #{passWord}");
                }*/
                if(userModel.getMark() != null){
                    SET("mark = #{mark}");
                }
                if(userModel.getPhone() != null){
                    SET("phone = #{phone}");
                }
                if(userModel.getCreateTime() != null){
                    SET("createTime = #{createTime}");
                }
                if(userModel.getCreateUserCode() != null){
                    SET("createUserCode = #{createUserCode}");
                }
                if(userModel.getCreateUserName() != null){
                    SET("createUserName = #{createUserName}");
                }
                if(userModel.getUpdateUserCode() != null){
                    SET("updateUserCode = #{updateUserCode}");
                }
                if(userModel.getUpdateUserName() != null){
                    SET("updateUserName = #{updateUserName}");
                }
                if(userModel.getIsRemoved() != null){
                    SET("isremoved = #{isRemoved}");
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
     * @time 2021-02-22 16:18:34
     * @throws
     * @return sql
     */
    public String findById(@Param("id") String id) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("s_t_user t");
                WHERE("t.id = #{id}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findById
     * @author 品讯科技
     * @param id
     * @Description 根据Id查询数据
     * @time 2021-02-22 16:18:34
     * @throws
     * @return sql
     */
    public String findByUserId(@Param("id") String id) {
        String sql="SELECT " +
                " u.id, " +
                " u.username as userName, " +
                " u.loginname as loginName, " +
                " u.realname as realName, " +
                " u.phone, " +
                " p.postname as postName, " +
                " p.id as postId, " +
                " u.id as roleId, " +
                " r.roleName, " +
                " c.company_name as companyName, " +
                " c.company_code as companyCode, " +
                " u.status,  " +
                " u.createTime, " +
                " u.createUserName, " +
                " u.updateTime, " +
                " u.updateUserName " +
                "FROM " +
                " s_t_user u, " +
                " s_t_roleuser ru, " +
                " s_t_role r, " +
                " s_t_postuser pu, " +
                " s_t_post p, " +
                " s_t_company c, " +
                " s_t_companyuser cu " +
                "WHERE " +
                " u.id = ru.userid " +
                "AND ru.roleid = r.id " +
                "AND pu.userid = u.id " +
                "AND pu.postid = p.id " +
                "and cu.userid=u.id " +
                "and cu.companyid=c.id " +
                "and u.id = #{id}";
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param userDto
     * @Description 根据条件查询数据并分页
     * @time 2021-02-22 16:18:34
     * @throws
     * @return sql
     */
    public String findAll(final UserDto userDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql);
                FROM("s_t_user t");
                if(!StringUtil.isBlank (userDto.getId())){
                    WHERE("t.id = #{id}");
                }
                if(!StringUtil.isBlank (userDto.getLoginName())){
                    WHERE("t.loginname = #{loginName}");
                }
                if(!StringUtil.isBlank (userDto.getUserCode())){
                    WHERE("t.usercode = #{userCode}");
                }
                if(!StringUtil.isBlank (userDto.getUserName())){
                    WHERE("t.username = #{userName}");
                }
                if(!StringUtil.isBlank (userDto.getRealName())){
                    WHERE("t.realname = #{realName}");
                }
                if(!StringUtil.isBlank (userDto.getNickname())){
                    WHERE("t.nickname = #{nickname}");
                }
                if(!StringUtil.isBlank (userDto.getSex())){
                    WHERE("t.sex = #{sex}");
                }
                if(!StringUtil.isBlank (userDto.getBirthday())){
                    WHERE("t.birthday = #{birthday}");
                }
                if(!StringUtil.isBlank (userDto.getImage())){
                    WHERE("t.image = #{image}");
                }
                if(!StringUtil.isBlank (userDto.getStatus())){
                    WHERE("t.status = #{status}");
                }
                if(!StringUtil.isBlank (userDto.getPassWord())){
                    WHERE("t.passWord = #{passWord}");
                }
                if(!StringUtil.isBlank (userDto.getMark())){
                    WHERE("t.mark = #{mark}");
                }
                if(!StringUtil.isBlank (userDto.getPhone())){
                    WHERE("t.phone = #{phone}");
                }
                if(!StringUtil.isBlank (userDto.getCreateTime())){
                    WHERE("t.createTime = #{createTime}");
                }
                if(!StringUtil.isBlank (userDto.getCreateUserCode())){
                    WHERE("t.createUserCode = #{createUserCode}");
                }
                if(!StringUtil.isBlank (userDto.getCreateUserName())){
                    WHERE("t.createUserName = #{createUserName}");
                }
                if(!StringUtil.isBlank (userDto.getUpdateTime())){
                    WHERE("t.updateTime = #{updateTime}");
                }
                if(!StringUtil.isBlank (userDto.getUpdateUserCode())){
                    WHERE("t.updateUserCode = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (userDto.getUpdateUserName())){
                    WHERE("t.updateUserName = #{updateUserName}");
                }
                if(!StringUtil.isBlank (userDto.getIsRemoved())){
                    WHERE("t.isremoved = #{isRemoved}");
                }
            }
        }.toString();
        return sql;
    }

    public String findByLoginName(@Param("loginName") String loginName) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql+" ,passWord as passWord");
                FROM("s_t_user t");
                if(!StringUtil.isBlank (loginName)){
                    WHERE("(t.loginname=#{loginName} or t.phone=#{loginName})");
                    WHERE("t.status='1'");
                    WHERE("t.isremoved='1'");
                }else {
                    WHERE("1!=1");
                }
            }
        }.toString();
        return sql;
    }


    public String resetPassword(@Param("userId") String userId, @Param("passWord") String passWord) {
        String sql = new SQL () {
            {
                UPDATE("s_t_user");
                SET ("passWord=#{passWord}");
                WHERE ("id=#{userId}");
            }
        }.toString();
        return sql;
    }

    public String findList(@Param("userName") String userName, @Param("phone") String phone, @Param("deptId") String deptId, @Param("orgId") String orgId) {
//        String sql="SELECT " +
//                " u.id, " +
//                " u.username as userName, " +
//                " u.loginname as loginName, " +
//                " u.realname as realName, " +
//                " u.phone, " +
//                " p.postname as postName, " +
//                " p.id as postId, " +
//                " r.id as roleId, " +
//                " r.roleName, " +
//                " c.company_name as companyName, " +
//                " c.company_code as companyCode, " +
//                " u.status,  " +
//                " u.createTime, " +
//                " u.createUserName, " +
//                " u.updateTime, " +
//                " u.updateUserName " +
//                "FROM " +
//                " s_t_user u, " +
//                " s_t_roleuser ru, " +
//                " s_t_role r, " +
//                " s_t_postuser pu, " +
//                " s_t_post p, " +
//                " s_t_company c, " +
//                " s_t_companyuser cu " +
//                "WHERE " +
//                " u.id = ru.userid " +
//                "AND ru.roleid = r.id " +
//                "AND pu.userid = u.id " +
//                "AND pu.postid = p.id " +
//                "AND u.isremoved = '1' " +
//                "and cu.userid=u.id " +
//                "and cu.companyid=c.id " +
//                "and c.company_code like '"+companyCode+"%' " +
//                "and u.phone like '%"+phone+"%' " +
//                "and u.username like '%"+userName+"%' ";
    	 String sql="SELECT" + 
         		"	u.id," + 
         		"	u.username AS userName," + 
         		"	u.loginname AS loginName," + 
         		"	u.realname AS realName," + 
         		"	u.phone," + 
         		"	u.birthday," +
         		"	group_concat(p.community_name) AS communityNames," +
         		"	group_concat(p.community_id) AS communityIds," +
         		"	r.id AS roleId," +
         		"	r.roleName AS roleName," +
         		"	c.depart_name AS deptName," +
         		"	c.depart_id AS deptId," +
         		"	u. status," + 
         		"	u.createTime," + 
         		"	u.createUserName," + 
         		"	u.updateTime," + 
         		"	o.org_name as orgName," +
         		"	o.org_id as orgId," +
         		"	u.updateUserName" +
         		"   FROM" + 
         		"	s_t_user u left join s_t_roleuser ru on u.id = ru.userid" + 
         		"	left join s_t_role r on ru.roleid = r.id" + 
         		"	left join s_t_communityuser pu on pu.user_id = u.id" +
         		"	left join m_t_community p on pu.community_id = p.community_id" +
         		"	left join s_t_deptuser cu on cu.user_id = u.id" +
         		"	left join m_t_department c on cu.dept_id = c.depart_id" +
         		"	left join s_t_orguser ou on ou.user_id = u.id" +
         		"	left join m_t_org o on ou.org_id = o.org_id" +
         		"   WHERE 1=1 and u.isremoved='1'" ;
    	 StringBuffer sqlBuffer = new StringBuffer(sql);
    	 if(!StringUtil.isBlank (userName)){
    		 sqlBuffer.append( " and u.username like '%"+userName+"%' ");
         }
    	 if(!StringUtil.isBlank (phone)){
    		 sqlBuffer.append(" and u.phone like '%"+phone+"%' ");
         }
    	 if(!StringUtil.isBlank (orgId)){
    		 sqlBuffer.append(" and ou.org_Id = '"+orgId+"' ");
         }
    	 if(!StringUtil.isBlank (deptId)){
    		 sqlBuffer.append(" and c.depart_id = '"+deptId+"' ");
         }
         sqlBuffer.append (" GROUP BY u.id");
        return sqlBuffer.toString();
    }

    public String updateStatus(@Param("id") String id, @Param("status") String status) {
        String sql = new SQL () {
            {
                UPDATE("s_t_user");
                SET ("status=#{status}");
                WHERE ("id=#{id}");
            }
        }.toString();
        return sql;
    }

    public String saveUserRole(@Param("id") String id,@Param("userId") String userId, @Param("roleId") String roleId) {
        String sql = "INSERT s_t_roleuser  (id,roleid,userid) VALUES(#{id},#{roleId},#{userId}) ";
        return sql;
    }

    public String saveUserOrg(@Param("id") String id,@Param("userId") String userId,@Param("orgId") String orgId) {
        String sql = "INSERT s_t_orguser  (id,org_id,user_id) VALUES(#{id},#{orgId},#{userId}) ";
        return sql;
    }

    public String saveUserDept(@Param("id") String id,@Param("userId") String userId, @Param("deptId") String deptId) {
        String sql = "INSERT s_t_deptuser  (id,dept_id,user_id) VALUES(#{id},#{deptId},#{userId}) ";
        return sql;
    }

    public String saveUserCommunity(@Param("id") String id,@Param("userId") String userId, @Param("communityId") String communityId) {
        String sql = "INSERT s_t_communityuser  (id,community_id,user_id) VALUES(#{id},#{communityId},#{userId}) ";
        return sql;
    }

    public String deleteUserRole(@Param("userId") String userId) {
        String sql = "delete from s_t_roleuser  where userid=#{userId}";
        return sql;
    }
    public String deleteUserOrg(@Param("userId") String userId) {
        String sql = "delete from s_t_orguser  where user_id=#{userId}";
        return sql;
    }
    public String deleteUserDept(@Param("userId") String userId) {
        String sql = "delete from s_t_deptuser  where user_id=#{userId}";
        return sql;
    }
    public String deleteUserCommunity(@Param("userId") String userId) {
        String sql = "delete from s_t_communityuser  where user_id=#{userId}";
        return sql;
    }


    /**
     * @name findInfoByUserId
     * @author 品讯科技
     * @param id
     * @Description 根据Id查询数据
     * @time 2021-02-22 16:18:34
     * @throws
     * @return sql
     */
    public String findByUserInfoId(@Param("id") String id) {
        String sql="SELECT" +
                "	u.id," +
                "	u.username AS userName," +
                "	u.loginname AS loginName," +
                "	u.realname AS realName," +
                "	u.phone," +
                "	u.birthday," +
                "	group_concat(p.community_name) AS communityNames," +
                "	group_concat(p.community_id) AS communityIds," +
                "	r.id AS roleId," +
                "	r.roleName AS roleName," +
                "	c.depart_name AS deptName," +
                "	c.depart_id AS deptId," +
                "	u. status," +
                "	u.createTime," +
                "	u.createUserName," +
                "	u.updateTime," +
                "	o.org_name as orgName," +
                "	o.org_id as orgId," +
                "	o.is_approval as isApproval," +
                "	u.updateUserName" +
                "   FROM" +
                "	s_t_user u left join s_t_roleuser ru on u.id = ru.userid" +
                "	left join s_t_role r on ru.roleid = r.id" +
                "	left join s_t_communityuser pu on pu.user_id = u.id" +
                "	left join m_t_community p on pu.community_id = p.community_id" +
                "	left join s_t_deptuser cu on cu.user_id = u.id" +
                "	left join m_t_department c on cu.dept_id = c.depart_id" +
                "	left join s_t_orguser ou on ou.user_id = u.id" +
                "	left join m_t_org o on ou.org_id = o.org_id" +
        		"   WHERE" +
                "   u.id = #{id}";
        return sql;
    }
    public String findList2(@Param("userName") String userName, @Param("phone") String phone, @Param("companyCode") String companyCode) {
        String sql="SELECT " +
                " u.id, " +
                " u.username as userName, " +
                " u.loginname as loginName, " +
                " u.realname as realName, " +
                " u.phone, " +
                " p.postname as postName, " +
                " p.id as postId, " +
                " u.id as roleId, " +
                " r.roleName, " +
                " c.company_name as companyName, " +
                " u.status,  " +
                " u.createTime, " +
                " u.createUserName, " +
                " u.updateTime, " +
                " u.updateUserName " +
                "FROM " +
                "	s_t_user u left join s_t_roleuser ru on u.id = ru.userid" + 
        		"	left join s_t_role r on ru.roleid = r.id" + 
        		"	left join s_t_postuser pu on pu.userid = u.id" + 
        		"	left join s_t_post p on pu.postid = p.id" + 
        		"	left join s_t_companyuser cu on cu.userid = u.id" + 
        		"	left join s_t_company c on cu.companyid = c.company_code"  ;
               
        return sql;
    }
    public static void main(String[] args) {
    	String sql="SELECT " +
                " u.id, " +
                " u.username as userName, " +
                " u.loginname as loginName, " +
                " u.realname as realName, " +
                " u.phone, " +
                " p.postname as postName, " +
                " p.id as postId, " +
                " r.id as roleId, " +
                " r.roleName, " +
                " c.company_name as companyName, " +
                " c.company_code as companyCode, " +
                " u.status,  " +
                " u.createTime, " +
                " u.createUserName, " +
                " u.updateTime, " +
                " u.updateUserName " +
                "FROM " +
                " s_t_user u, " +
                " s_t_roleuser ru, " +
                " s_t_role r, " +
                " s_t_postuser pu, " +
                " s_t_post p, " +
                " s_t_company c, " +
                " s_t_companyuser cu " +
                "WHERE " +
                " u.id = ru.userid " +
                "AND ru.roleid = r.id " +
                "AND pu.userid = u.id " +
                "AND pu.postid = p.id " +
                "AND u.isremoved = '1' " +
                "and cu.userid=u.id " +
                "and cu.companyid=c.id " +
                "and c.company_code like '"+1000+"%' " +
                "and u.phone like '%"+0000+"%' " +
                "and u.username like '%"+0000+"%' ";
    	System.err.println(sql);
	}
    
    
    
    
    
    
    
    
    
    
    
    
    String sqlss = "t.id as id,"+
            "t.loginname as loginName,"+
            "t.usercode as userCode,"+
            "t.username as userName,"+
            "t.realname as realName,"+
            "t.nickname as nickname,"+
            "t.sex as sex,"+
            "t.birthday as birthday,"+
            "t.image as image,"+
            "t.status as status,"+
            "t.passWord as passWord,"+
            "t.mark as mark,"+
            "t.phone as phone,"+
            "t.createTime as createTime,"+
            "t.createUserCode as createUserCode,"+
            "t.createUserName as createUserName,"+
            "t.updateTime as updateTime,"+
            "t.updateUserCode as updateUserCode,"+
            "t.updateUserName as updateUserName,"+
            "s.dept_id as departId,"+
            "o.org_id as orgId,"+
            "t.isremoved as isRemoved";
    
    
    
    
    /**
     * @name findByuserIds
     * @author 品讯科技
     * @param userDto
     * @Description 根据条件查询数据并分页
     * @time 2021-02-22 16:18:34
     * @throws
     * @return sql
     */
    public String findByuserIds(final String userId) {
        String sql = new SQL () {
            {
                SELECT(sqlss);
                FROM("s_t_user t, s_t_deptuser s ,s_t_orguser o");
               
                
                    WHERE("t.isremoved = '1'   ");
                
                    WHERE("t.id  = s.user_id and t.id =o.user_id  ");
                
                if(!StringUtil.isBlank (userId)){
                    WHERE("t.id = #{userId}");
                }
                
            }
        }.toString();
        return sql;
    }

    
    
    
    
    private String selectOrInusersertSql="t.id as id,"+
            "t.loginname as loginName,"+
            "t.usercode as userCode,"+
            "t.username as userName,"+
            "t.realname as realName,"+
            "t.nickname as nickname,"+
            "t.sex as sex,"+
            "t.birthday as birthday,"+
            "t.image as image,"+
            "t.status as status,"+
            "t.passWord as passWord,"+
            "t.mark as mark,"+
            "t.phone as phone,"+
            "t.createTime as createTime,"+
            "t.createUserCode as createUserCode,"+
            "t.createUserName as createUserName,"+
            "t.updateTime as updateTime,"+
            "t.updateUserCode as updateUserCode,"+
            "t.updateUserName as updateUserName,"+
           " s.dept_id as departId,"+
            "o.org_id as orgId,"+
            
            "t.isremoved as isRemoved";
    
    
    
    
    /**
     * @name finduser
     * @author 品讯科技
     * @param userDto
     * @Description 根据条件查询数据并分页
     * @time 2021-02-22 16:18:34
     * @throws
     * @return sql
     */
    public String finduser(final UserExpandModel findById) {
        String sql = new SQL () {
            {
                SELECT(selectOrInusersertSql);
                FROM("s_t_user t, s_t_deptuser s ,s_t_orguser o");
               
                
                    WHERE("t.isremoved = '1'   ");
                
                    WHERE("t.id  = s.user_id and t.id =o.user_id  ");
                
                if(!StringUtil.isBlank (findById.getOrgId())){
                    WHERE("o.org_id = #{orgId}");
                }
                
            }
        }.toString();
        return sql;
    }

    public String findLoginNameByUserId(@Param ("userId") String userId) {
        String sql = new SQL () {
            {
                SELECT("t.loginname");
                FROM("s_t_user t");
                WHERE ("t.id=#{userId}");
            }
        }.toString();
        return sql;
    }
    
    
    
}
