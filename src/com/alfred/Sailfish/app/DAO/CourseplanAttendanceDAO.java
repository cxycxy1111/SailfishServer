package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CourseplanAttendanceDAO extends DAO {

    public CourseplanAttendanceDAO() {
        super();
    }

    /**
     * 查看签到记录
     * @param m_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryList(long m_id) {
        ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
        String sql = "SELECT cpa.courseplan_id FROM courseplan_attendance cpa " +
                "WHERE cpa.del=0 AND member_id=" + m_id;
        mapArrayList = sqlHelper.query(sql);
        return mapArrayList;
    }

    /**
     * 签到
     * @param m_id
     * @param cp_id
     * @return
     */
    public boolean attend(long m_id,long cp_id) {
        String sql = "INSERT INTO courseplan_attendance (courseplan_id,member_id,del,last_modify_time) " +
                "VALUES (" + cp_id + "," + m_id + ",0,'" + simpleDateFormat.format(new Date()) + "'";
        try {
            return sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unAttend(long m_id,long cp_id) {
        String sql = "UPDATE courseplan_attendance SET del=1 WHERE m_id=" + m_id + " AND courseplan_id=" + cp_id;
        try {
            return sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否已经签到
     * @param m_id
     * @param cp_id
     * @return
     */
    public boolean isAttended(long m_id,long cp_id) {
        String sql = "SELECT * FROM courseplan_attendance cpa " +
                "WHERE cpa.member_id=" + m_id + " AND cpa.courseplan_id=" + cp_id + " AND cpa.del=0";
        if (sqlHelper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否曾经有签到过
     * @param m_id
     * @param cp_id
     * @return
     */
    public boolean wasAttended(long m_id,long cp_id) {
        String sql = "SELECT * FROM courseplan_attendance WHERE courseplan_id=" + cp_id + " AND member_id=" + m_id + " AND del = 1";
        if (sqlHelper.query(sql).size() != 0) {
            return true;
        }
        return false;
    }

    /**
     * 如果重新签到，进行的操作
     * @param m_id
     * @param cp_id
     * @return
     */
    public boolean updateAttendanceState(long m_id,long cp_id) {
        String sql = "UPDATE courseplan_attendance SET del=0 WHERE m_id=" + m_id + " AND courseplan_id=" + cp_id;
        try {
            return sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
