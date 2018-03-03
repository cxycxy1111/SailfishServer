package com.alfred.Sailfish.app.DAO;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CourseplanBookDAO extends DAO{

    public CourseplanBookDAO() {
        super();
    }

    /**
     * 查询出所有订购
     * @param m_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryListByMemberId(long m_id) {

        String sql = "SELECT c.name,cp.start_time,c.end_time FROM courseplan_book cpb,courseplan_attendance cpa " +
                "LEFT JOIN courseplan cp ON cpb.courseplan_id = cp.id " +
                "LEFT JOIN course c ON cp.course_id=c.id " +
                "WHERE (cpb.course_plan_id != cpa.courseplan_id OR cpb.member_id != cpa.member_id) " +
                    "AND cp.start_time > now() " +
                    "AND c.del = 0 " +
                    "AND cp.del = 0 " +
                    "AND cpb.del = 0" +
                    "AND cpa.member_id = " + m_id;
        ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
        mapArrayList = sqlHelper.query(sql);
        return mapArrayList;

    }

    /**
     * 通过排课ID查询预订详情
     * @param cp_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryDetailByCoursePlanId(long cp_id) {
        ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
        String sql = "SELECT cp.id,c.name,cp.start_time,cp.end_time,cr.name FROM courseplan_book cpb " +
                "LEFT JOIN courseplan cp ON cpb.courseplan_id=cp.id " +
                "LEFT JOIN course c ON cp.course_id=c.id " +
                "LEFT JOIN classroom cr ON cp.classroom_id = cr.id " +
                "WHERE cpb.course_plan_id=" + cp_id;
        mapArrayList = sqlHelper.query(sql);
        return mapArrayList;
    }

    /**
     * 预订排课
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean book(long cp_id,long m_id) {
        String sql = "INSERT INTO courseplan_book (courseplan_id,member_id,del,last_modify_time) " +
                "VALUES (" + cp_id + "," + m_id + ",0,'" + simpleDateFormat.format(new Date()) + "')";
        boolean isSuccessed = false;
        try {
            isSuccessed = sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccessed;
    }

    /**
     * 判断是否有预订
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean isBooked(long cp_id,long m_id) {
        String sql = "SELECT * FROM courseplan_book WHERE courseplan_id = " + cp_id + " AND member_id=" + m_id + " AND del=0";
        if (sqlHelper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否曾经预订过
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean wasBooked(long cp_id,long m_id) {
        String sql = "SELECT * FROM courseplan_book WHERE courseplan_id = " + cp_id + " AND member_id=" + m_id + " AND del=0";
        if (sqlHelper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 用于如果曾经预订过，重新预订时，更新预订状态
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean updateBookState(long cp_id,long m_id) {
        String sql = "UPDATE courseplan_book SET del = 0,last_modify_time='" + simpleDateFormat.format(new Date()) + "' " +
                "WHERE courseplan_id=" + cp_id +
                    " AND member_id=" + m_id;
        try {
            return sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unBook(long cp_id,long m_id) {
        String sql = "UPDATE courseplan_book SET del = 1,last_modify_time='" + simpleDateFormat.format(new Date()) + "' " +
                "WHERE courseplan_id=" + cp_id +
                " AND member_id=" + m_id;
        try {
            return sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
