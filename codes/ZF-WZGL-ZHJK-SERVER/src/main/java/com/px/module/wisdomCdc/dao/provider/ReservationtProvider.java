package com.px.module.wisdomCdc.dao.provider;

import com.px.module.common.base.BaseProvider;
import com.px.module.common.utils.StringUtil;
import com.px.module.wisdomCdc.dto.ReservationtDto;
import com.px.module.wisdomCdc.entity.ReservationtModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * @Package com.px.module.wisdomCdc.dao.provider
 * @ClassName ReservationtProvider
 * @Description ReservationtProvider
 * @author 品讯科技
 * @time   2022-07-04 10:45:18
 * @version V 1.0
 */
public class ReservationtProvider extends BaseProvider {

    //sql，selectOrInsert部分
    private String selectOrInsertSql="t.reservation_id as reservationId,"+
									 "t.check_type_id as checkTypeId,"+
									 "t.check_type_name as checkTypeName,"+
									 "t.reservation_num as reservationNum,"+
									 "t.personnel_id as personnelId,"+
									 "DATE_FORMAT(t.reservation_time,'%Y-%m-%d %H:%i') AS reservationTime,"+
									 "t.check_result_status as checkResultStatus,"+
                                     "CASE t.check_result_status " +
                                     "WHEN '1' THEN '已检测' " +
                                     "WHEN '2' THEN '未检测' " +
                                     "WHEN '3' THEN '已过期' " +
                                     "ELSE '' END AS checkResultStatusCn, " +
									 "t.pushed as pushed,"+
									 "t.check_result_id as checkResultId,"+
									 "t.community_id as communityId,"+
									 "t.is_removed as isRemoved,"+
									 "DATE_FORMAT(t.create_time,'%Y-%m-%d %H:%i:%S') AS createTime,"+
									 "t.create_user_code as createUserCode,"+
									 "t.create_user_name as createUserName,"+
                                     "DATE_FORMAT(t.update_time,'%Y-%m-%d %H:%i:%S') AS updateTime,"+
									 "t.update_user_code as updateUserCode,"+
									 "t.update_user_name as updateUserName";
    /**
     * @name save
     * @author 品讯科技
     * @param reservationtModel
     * @Description 增加
     * @time 2022-07-04 10:45:18
     * @throws
     * @return sql
     */
    public String save(final ReservationtModel reservationtModel) {
        String sql = new SQL () {
            {
                INSERT_INTO ("c_t_reservationt_info ");
                VALUES("reservation_id", "#{reservationId}");
                if(!StringUtil.isBlank (reservationtModel.getCheckTypeId())){
                    VALUES("check_type_id", "#{checkTypeId}");
                }
                if(!StringUtil.isBlank (reservationtModel.getCheckTypeName())){
                    VALUES("check_type_name", "#{checkTypeName}");
                }
                if(!StringUtil.isBlank (reservationtModel.getReservationNum())){
                    VALUES("reservation_num", "#{reservationNum}");
                }
                if(!StringUtil.isBlank (reservationtModel.getPersonnelId())){
                    VALUES("personnel_id", "#{personnelId}");
                }
                if(!StringUtil.isBlank (reservationtModel.getReservationTime())){
                    VALUES("reservation_time", "#{reservationTime}");
                }
                if(!StringUtil.isBlank (reservationtModel.getCheckResultStatus())){
                    VALUES("check_result_status", "#{checkResultStatus}");
                }
                if(!StringUtil.isBlank (reservationtModel.getPushed())){
                    VALUES("pushed", "#{pushed}");
                }
                if(!StringUtil.isBlank (reservationtModel.getCheckResultId())){
                    VALUES("check_result_id", "#{checkResultId}");
                }
                if(!StringUtil.isBlank (reservationtModel.getCommunityId())){
                    VALUES("community_id", "#{communityId}");
                }
                if(!StringUtil.isBlank (reservationtModel.getIsRemoved())){
                    VALUES("is_removed", "#{isRemoved}");
                }
                if(!StringUtil.isBlank (reservationtModel.getCreateTime())){
                    VALUES("create_time", "#{createTime}");
                }
                if(!StringUtil.isBlank (reservationtModel.getCreateUserCode())){
                    VALUES("create_user_code", "#{createUserCode}");
                }
                if(!StringUtil.isBlank (reservationtModel.getCreateUserName())){
                    VALUES("create_user_name", "#{createUserName}");
                }
                if(!StringUtil.isBlank (reservationtModel.getUpdateTime())){
                    VALUES("update_time", "#{updateTime}");
                }
                if(!StringUtil.isBlank (reservationtModel.getUpdateUserCode())){
                    VALUES("update_user_code", "#{updateUserCode}");
                }
                if(!StringUtil.isBlank (reservationtModel.getUpdateUserName())){
                    VALUES("update_user_name", "#{updateUserName}");
                }
            }
        }.toString ();
        return sql;
    }

    /**
     * @name deleteByIds
     * @author 品讯科技
     * @param reservationIds
     * @Description 物理删除
     * @time 2022-07-04 10:45:18
     * @throws
     * @return sql
     */
    public String deleteByIds(@Param ("reservationIds") String reservationIds) {
        String sql = new SQL () {
            {
                DELETE_FROM("c_t_reservationt_info");
            }
        }.toString();
        sql += batchJoinField("reservation_id",reservationIds);
        return sql;
    }


    /**
     * @name logicDeleteByIds
     * @author 品讯科技
     * @param reservationIds
     * @Description 逻辑删除
     * @time 2022-07-04 10:45:18
     * @throws
     * @return sql
     */
    public String logicDeleteByIds(@Param ("reservationIds") String reservationIds) {
        String sql = new SQL () {
            {
                UPDATE("c_t_reservationt_info");
                SET("is_removed = '0'");
            }
        }.toString();
        sql += batchJoinField("reservation_id",reservationIds);
        return sql;
    }

    /**
     * @name update
     * @author 品讯科技
     * @param reservationtModel
     * @Description 修改
     * @time 2022-07-04 10:45:18
     * @throws
     * @return sql
     */
    public String update(final ReservationtModel reservationtModel) {
        String sql = new SQL () {
            {
                UPDATE("c_t_reservationt_info ");
                    if(reservationtModel.getCheckTypeId() != null){
                        SET("check_type_id = #{checkTypeId}");
                    }
                    if(reservationtModel.getCheckTypeName() != null){
                        SET("check_type_name = #{checkTypeName}");
                    }
                    if(reservationtModel.getReservationNum() != null){
                        SET("reservation_num = #{reservationNum}");
                    }
                    if(reservationtModel.getPersonnelId() != null){
                        SET("personnel_id = #{personnelId}");
                    }
                    if(reservationtModel.getReservationTime() != null){
                        SET("reservation_time = #{reservationTime}");
                    }
                    if(reservationtModel.getCheckResultStatus() != null){
                        SET("check_result_status = #{checkResultStatus}");
                    }
                    if(reservationtModel.getPushed() != null){
                        SET("pushed = #{pushed}");
                    }
                    if(reservationtModel.getCheckResultId() != null){
                        SET("check_result_id = #{checkResultId}");
                    }
                    if(reservationtModel.getCommunityId() != null){
                        SET("community_id = #{communityId}");
                    }
                    if(reservationtModel.getIsRemoved() != null){
                        SET("is_removed = #{isRemoved}");
                    }
                    if(reservationtModel.getCreateTime() != null){
                        SET("create_time = #{createTime}");
                    }
                    if(reservationtModel.getCreateUserCode() != null){
                        SET("create_user_code = #{createUserCode}");
                    }
                    if(reservationtModel.getCreateUserName() != null){
                        SET("create_user_name = #{createUserName}");
                    }
                    if(reservationtModel.getUpdateTime() != null){
                        SET("update_time = #{updateTime}");
                    }
                    if(reservationtModel.getUpdateUserCode() != null){
                        SET("update_user_code = #{updateUserCode}");
                    }
                    if(reservationtModel.getUpdateUserName() != null){
                        SET("update_user_name = #{updateUserName}");
                    }
                WHERE("reservation_id = #{reservationId}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findById
     * @author 品讯科技
     * @param reservationId
     * @Description 根据Id查询数据
     * @time 2022-07-04 10:45:18
     * @throws
     * @return sql
     */
    public String findById(@Param ("reservationId") String reservationId) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql +","+
                        "a.check_result_num as checkResultNum, " +
                        "a.check_time as checkTime, " +
                        "a.check_principal as checkPrincipal, " +
                        "a.check_content as checkContent, " +
                        "a.check_result_display as checkResultDisplay, " +
                        "a.check_result as checkResult, " +
                        "a.check_result_url as checkResultUrl, " +
                        "a.check_result_file_name as checkResultFileName, " +
                        "a.vaccine_category as vaccineCategory, " +
                        "b.personnel_name as personnelName, " +
                        "b.idnum as idnum, " +
                        "b.phone as phone, " +
                        "b.sex as sex, " +
                        "b.personnel_add as personnelAdd ");
                FROM("c_t_reservationt_info t");
                LEFT_OUTER_JOIN("c_t_check_result a on a.check_result_id = t.check_result_id");
                LEFT_OUTER_JOIN("c_t_personnel_info b on b.personnel_id = t.personnel_id");
                WHERE("t.reservation_id = #{reservationId}");
            }
        }.toString();
        return sql;
    }

    /**
     * @name findAll
     * @author 品讯科技
     * @param reservationtDto
     * @Description 根据条件查询数据并分页
     * @time 2022-07-04 10:45:18
     * @throws
     * @return sql
     */
    public String findAll(final ReservationtDto reservationtDto) {
        String sql = new SQL () {
            {
                SELECT(selectOrInsertSql+","+
                        "a.personnel_name as personnelName," +
                        "a.idnum as idnum," +
                        "a.phone," +
                        "a.personnel_add as personnelAdd," +
                        "b.check_principal as checkPrincipal," +
                        "b.check_time as checkTime," +
                        "b.check_result_display as checkResultDisplay," +
                        "CASE b.check_result_display " +
                        "WHEN '1' THEN '绿色无症状' " +
                        "WHEN '2' THEN '黄色预警' " +
                        "WHEN '3' THEN '红色预警' " +
                        "ELSE '' END AS checkResultDisplayCn " );
                FROM("c_t_reservationt_info t");
                LEFT_OUTER_JOIN("c_t_personnel_info a on t.personnel_id = a.personnel_id");
                LEFT_OUTER_JOIN("c_t_check_result b on b.check_result_id = t.check_result_id");
                ORDER_BY("t.create_time DESC ");
                if(!StringUtil.isBlank (reservationtDto.getReservationId())){
                    WHERE("t.reservation_id = #{reservationId}");
                }
                if(!StringUtil.isBlank (reservationtDto.getCheckTypeId())){
                    WHERE("t.check_type_id = #{checkTypeId}");
                }
                if(!StringUtil.isBlank (reservationtDto.getCheckTypeName())){
                    WHERE("t.check_type_name = #{checkTypeName}");
                }
                if(!StringUtil.isBlank (reservationtDto.getReservationNum())){
                    WHERE("t.reservation_num = #{reservationNum}");
                }
                if(!StringUtil.isBlank (reservationtDto.getPersonnelId())){
                    WHERE("t.personnel_id = #{personnelId}");
                }
                if(!StringUtil.isBlank (reservationtDto.getReservationTime())){
                    WHERE("t.reservation_time = #{reservationTime}");
                }
                if(!StringUtil.isBlank (reservationtDto.getCheckResultStatus())){
                    WHERE("t.check_result_status = #{checkResultStatus}");
                }

                if(!StringUtil.isBlank (reservationtDto.getCheckResultStatuss())){
//                    WHERE("tt.personnelType in ${personnelTypes}");

                    WHERE( batchJoinField("t.check_result_status",reservationtDto.getCheckResultStatuss()).replace("where",""));
                }

                if(!StringUtil.isBlank (reservationtDto.getPushed())){
                    WHERE("t.pushed = #{pushed}");
                }
                if(!StringUtil.isBlank (reservationtDto.getIsRemoved())){
                    WHERE("t.is_removed = #{isRemoved}");
                }
                if(!StringUtil.isBlank (reservationtDto.getCreateTime())){
                    WHERE("t.create_time = #{createTime}");
                }
                if(!StringUtil.isBlank (reservationtDto.getCreateUserCode())){
                    WHERE("t.create_user_code = #{createUserCode}");
                }
                if(!StringUtil.isBlank (reservationtDto.getCreateUserName())){
                    WHERE("t.create_user_name = #{createUserName}");
                }
                if(!StringUtil.isBlank (reservationtDto.getUpdateTime())){
                    WHERE("t.update_time = #{updateTime}");
                }
                if(!StringUtil.isBlank (reservationtDto.getUpdateUserCode())){
                    WHERE("t.update_user_code = #{updateUserCode}");
                }
                if(!StringUtil.isBlank (reservationtDto.getUpdateUserName())){
                    WHERE("t.update_user_name = #{updateUserName}");
                }

                if(!StringUtil.isBlank (reservationtDto.getPersonnelName())){
                    WHERE("a.personnel_name like CONCAT('%', #{personnelName},'%')");
                }
                if(!StringUtil.isBlank (reservationtDto.getIdnum())){
                    WHERE("a.idnum like CONCAT('%', #{idnum},'%')");
                }
                if(!StringUtil.isBlank (reservationtDto.getPhone())){
                    WHERE("a.phone = #{phone}");
                }

                if(!StringUtil.isBlank (reservationtDto.getCheckResultDisplay())){
                    WHERE("b.check_result_display = #{checkResultDisplay}");
                }
                if(!StringUtil.isBlank (reservationtDto.getDateStart())&&!StringUtil.isBlank (reservationtDto.getDateEnd())){
                    WHERE("b.check_time BETWEEN #{dateStart} AND #{dateEnd}");
                }
            }
        }.toString();
        return sql;
    }

    public String updateReservationt(final ReservationtDto reservationtDto) {
        String sql = new SQL () {
            {
                UPDATE("c_t_reservationt_info t " +
                        "LEFT JOIN c_t_check_result a ON t.check_result_id = a.check_result_id " +
                        "LEFT JOIN c_t_personnel_info b ON b.personnel_id = t.personnel_id  ");
                if(reservationtDto.getCheckTypeId() != null){
                    SET("t.check_type_id = #{checkTypeId}");
                }
                if(reservationtDto.getCheckTypeName() != null){
                    SET("t.check_type_name = #{checkTypeName}");
                }
                if(reservationtDto.getReservationNum() != null){
                    SET("t.reservation_num = #{reservationNum}");
                }
                if(reservationtDto.getPersonnelId() != null){
                    SET("t.personnel_id = #{personnelId}");
                }
                if(reservationtDto.getReservationTime() != null){
                    SET("t.reservation_time = #{reservationTime}");
                }
                if(reservationtDto.getCheckResultStatus() != null){
                    SET("t.check_result_status = #{checkResultStatus}");
                }
                if(reservationtDto.getPushed() != null){
                    SET("t.pushed = #{pushed}");
                }
                if(reservationtDto.getCheckResultId() != null){
                    SET("t.check_result_id = #{checkResultId}");
                }
                if(reservationtDto.getCommunityId() != null){
                    SET("t.community_id = #{communityId}");
                }
                if(reservationtDto.getIsRemoved() != null){
                    SET("t.is_removed = #{isRemoved}");
                }
                if(reservationtDto.getCreateTime() != null){
                    SET("t.create_time = #{createTime}");
                }
                if(reservationtDto.getCreateUserCode() != null){
                    SET("t.create_user_code = #{createUserCode}");
                }
                if(reservationtDto.getCreateUserName() != null){
                    SET("t.create_user_name = #{createUserName}");
                }
                if(reservationtDto.getUpdateTime() != null){
                    SET("t.update_time = #{updateTime}");
                }
                if(reservationtDto.getUpdateUserCode() != null){
                    SET("t.update_user_code = #{updateUserCode}");
                }
                if(reservationtDto.getUpdateUserName() != null){
                    SET("t.update_user_name = #{updateUserName}");
                }


                if(reservationtDto.getPersonnelName() != null){
                    SET("b.personnel_name = #{personnelName}");
                }
                if(reservationtDto.getIdnum() != null){
                    SET("b.idnum = #{idnum}");
                }
                if(reservationtDto.getPhone() != null){
                    SET("b.phone = #{phone}");
                }
                if(reservationtDto.getPersonnelAdd() != null){
                    SET("b.personnel_add = #{personnelAdd}");
                }
                if(reservationtDto.getSex() != null){
                    SET("b.sex = #{sex}");
                }


                if(reservationtDto.getCheckTime() != null){
                    SET("a.check_time = #{checkTime}");
                }
                if(reservationtDto.getCheckPrincipal() != null){
                    SET("a.check_principal = #{checkPrincipal}");
                }
                if(reservationtDto.getCheckContent() != null){
                    SET("a.check_content = #{checkContent}");
                }
                if(reservationtDto.getCheckResultDisplay() != null){
                    SET("a.check_result_display = #{checkResultDisplay}");
                }
                if(reservationtDto.getCheckResult() != null){
                    SET("a.check_result = #{checkResult}");
                }
                if(reservationtDto.getCheckResultUrl() != null){
                    SET("a.check_result_url = #{checkResultUrl}");
                }
                if(reservationtDto.getVaccineCategory() != null){
                    SET("a.vaccine_category = #{vaccineCategory}");
                }

                WHERE("t.reservation_id = #{reservationId}");
            }
        }.toString();
        return sql;
    }

    public String checkTypeTop(final ReservationtDto reservationtDto) {
        String sql = new SQL () {
            {
                SELECT("COUNT( t.check_type_id ) AS checkTypeCount, " +
                        "a.check_type_name AS checkTypeName");
                FROM("c_t_reservationt_info t");
                LEFT_OUTER_JOIN("c_t_check_type a ON a.check_type_id = t.check_type_id ");

                WHERE("t.is_removed = '1'");
                GROUP_BY("t.check_type_id");

                ORDER_BY("COUNT( t.check_type_id ) DESC LIMIT 5");
            }
        }.toString();
        return sql;
    }

    public String checkTotal(final ReservationtDto reservationtDto) {
        String sql = new SQL () {
            {
                SELECT("COUNT( IF(check_result_status ='1' ,check_result_status,NULL) ) AS alreadyCheck, " +
                        "COUNT( check_result_status) AS reservationt");
                FROM("c_t_reservationt_info t");
                WHERE("t.is_removed = '1'");
                if(!StringUtil.isBlank (reservationtDto.getDateStart())&&!StringUtil.isBlank (reservationtDto.getDateEnd())){
                    WHERE("t.reservation_time BETWEEN #{dateStart} AND #{dateEnd}");
                }
            }
        }.toString();
        return sql;
    }

    public String checkTotalCurveByDay(final ReservationtDto reservationtDto) {
        String sql = new SQL () {
            {
                SELECT("date_format(t.reservation_time, '%Y-%m-%d') as dat, " +
                        "COUNT( IF(check_result_status ='1' ,check_result_status,NULL) ) AS alreadyCheck, " +
                        "COUNT( check_result_status) AS reservationt");
                FROM("c_t_reservationt_info t");
                WHERE("t.is_removed = '1'");
                if(!StringUtil.isBlank (reservationtDto.getDateStart())&&!StringUtil.isBlank (reservationtDto.getDateEnd())){
                    WHERE("t.reservation_time BETWEEN #{dateStart} AND #{dateEnd}");
                }
                GROUP_BY("date_format(t.reservation_time, '%Y-%m-%d')");
            }
        }.toString();
        return sql;
    }

    public String monitorWarning(final ReservationtDto reservationtDto) {
        String sql = new SQL () {
            {
                SELECT("CASE  " +
                        "WHEN a.check_result_display = '3' " +
                        "THEN CONCAT(t.check_type_name,'红色预警') " +
                        "WHEN a.check_result_display = '2' " +
                        "THEN CONCAT(t.check_type_name,'黄色预警') " +
                        "ELSE NULL END AS checkResult," +
                        "DATE_FORMAT(a.check_time,'%Y-%m-%d %H:%i:%S') AS checkTime ");
                FROM("c_t_reservationt_info t");
                LEFT_OUTER_JOIN("c_t_check_result a ON a.check_result_id = t.check_result_id ");
                WHERE("t.is_removed = '1'");
                WHERE("a.check_result_display IN('2','3')");
                ORDER_BY("a.check_time DESC LIMIT 10");
            }
        }.toString();
        return sql;
    }


    public String findPushed(final ReservationtDto reservationtDto) {
        String sql = new SQL () {
            {
                SELECT("COUNT(t.reservation_id) AS count, " +
                        "GROUP_CONCAT(t.reservation_id) AS reservationIds");
                FROM("c_t_reservationt_info t");
                LEFT_OUTER_JOIN("c_t_personnel_info a on t.personnel_id = a.personnel_id");

                WHERE("t.pushed = '1'");

                if(!StringUtil.isBlank (reservationtDto.getPhone())){
                    WHERE("a.phone = #{phone}");
                }
            }
        }.toString();
        return sql;
    }


    public String updatePushedBatch(Map map) {
//        List<String> data = (List<String>) map.get("list");
        String data = (String) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("update  c_t_reservationt_info  set pushed = '3' WHERE ");
//        for (int i = 0; i < data.size(); i++) {
//            sb.append(data.get(i));
//            if (i < data.size() - 1)
//                sb.append(",");
//        }
        sb.append(batchJoinField("reservation_id",data).replace("where",""));
//        sb.append(")");
        return sb.toString();
    }


    public String groupingHistogram(final ReservationtDto reservationtDto) {
        String sql = new SQL () {
            {
                SELECT("t.check_type_name as checkTypeName, " +
                        "COUNT(IF(a.check_result_display = '1',a.check_result_id,NULL)) AS green, " +
                        "COUNT(IF(a.check_result_display = '2',a.check_result_id,NULL)) AS yellowWarning, " +
                        "COUNT(IF(a.check_result_display = '3',a.check_result_id,NULL)) AS redWarning ");
                FROM("c_t_reservationt_info t");
                LEFT_OUTER_JOIN("c_t_check_result a ON t.check_result_id = a.check_result_id");

                WHERE("t.check_result_status = '1'");

                if(!StringUtil.isBlank (reservationtDto.getDateStart())&&!StringUtil.isBlank (reservationtDto.getDateEnd())){
                    WHERE("a.check_time BETWEEN #{dateStart} AND #{dateEnd}");
                }
                GROUP_BY("t.check_type_id ");
            }
        }.toString();
        return sql;
    }


    public String maleFemaleRatio(final ReservationtDto reservationtDto) {
        String sql = new SQL () {
            {
                SELECT(" b.sex, " +
                        "COUNT( t.personnel_id ) AS personnelNum, " +
                        "ifnull(abs(round(COUNT( t.personnel_id )/ (SELECT COUNT( t.personnel_id ) FROM `c_t_reservationt_info` t LEFT JOIN c_t_check_result a ON t.check_result_id = a.check_result_id WHERE t.check_result_status = '1'  AND a.check_result_display IN( '2','3') ) * 100, 2  )), 0  ) proportion ");
                FROM("c_t_reservationt_info t");
                LEFT_OUTER_JOIN("c_t_check_result a ON t.check_result_id = a.check_result_id ");
                LEFT_OUTER_JOIN("c_t_personnel_info b ON t.personnel_id = b.personnel_id ");

                WHERE("t.check_result_status = '1'");
                WHERE("a.check_result_display IN( '2','3')");

                if(!StringUtil.isBlank (reservationtDto.getDateStart())&&!StringUtil.isBlank (reservationtDto.getDateEnd())){
                    WHERE("a.check_time BETWEEN #{dateStart} AND #{dateEnd}");
                }
                GROUP_BY("b.sex ");
            }
        }.toString();
        return sql;
    }


    public String recentSixMonthsGrowth() {
        String sql = "SELECT " +
                "lastDays," +
                "IFNULL(yellowPersonnel,'0') AS yellowPersonnel," +
                "IFNULL(redPersonnel,'0') AS redPersonnel  " +
                "from " +
                "(SELECT " +
                " date_format(@lastDay := last_day( date_add(@lastDay,interval 1 month ) ) ,'%Y-%m'  ) lastDays " +
                "  from " +
                "(SELECT @lastDay := date_add(curdate(),interval -6 month) from mysql.help_topic limit 6) a ) date_table " +
                " LEFT JOIN( " +
                "SELECT date_format( a.check_time, '%Y-%m' ) AS datMonth, " +
                " COUNT( CASE WHEN a.check_result_display = '2' THEN t.personnel_id ELSE NULL END ) AS yellowPersonnel,  " +
                " COUNT( CASE WHEN a.check_result_display = '3' THEN t.personnel_id ELSE NULL END ) AS redPersonnel " +
                "FROM c_t_reservationt_info t  " +
                "RIGHT JOIN c_t_check_result a ON t.check_result_id = a.check_result_id " +
                "LEFT JOIN c_t_check_type b ON t.check_type_id = b.check_type_id " +
                "WHERE t.check_result_status = '1' " +
                "GROUP BY date_format( a.check_time, '%Y-%m' )) aa ON aa.datMonth = date_table.lastDays " +
                "ORDER BY lastDays DESC";
        return sql;
    }


    public String countCommunityCase() {
        String sql = "SELECT " +
                "b.community_name AS communityName, " +
                "COUNT(t.reservation_id) AS communityCaseCount " +
                "FROM " +
                "c_t_reservationt_info t " +
                "LEFT JOIN c_t_check_result a ON a.check_result_id = t.check_result_id " +
                "LEFT JOIN m_t_community b ON t.community_id = b.community_id " +
                "WHERE a.check_result_display = '3' " +
                "GROUP BY t.community_id LIMIT 10 ";
        return sql;
    }
}
