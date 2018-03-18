package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CourseplanBookAndAttendDAO extends DAO{

    public CourseplanBookAndAttendDAO() {
        super();
    }



    /**
     * 通过ID查询是否有预订或签到过
     * @param id
     * @return
     */
    public boolean wasBookOrAttend(long id,int status) {
        String sql = "SELECT * FROM courseplan_book WHERE id = " + id + " AND status=" + status;
        if (sqlHelper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 通过ID查询是否有预订或签到过
     * @param id
     * @return
     */
    public boolean wasBookOrAttend(long id) {
        String sql = "SELECT * FROM courseplan_book WHERE id = " + id;
        if (sqlHelper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 通过ID查询是否有预订或签到过
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean wasBookOrAttend(long cp_id,long m_id,int status) {
        String sql = "SELECT * FROM courseplan_book WHERE courseplan_id = " + cp_id + " AND member_id=" + m_id + " AND status=" + status;
        if (sqlHelper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 通过ID查询是否有预订或签到过
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean wasBookOrAttend(long cp_id,long m_id) {
        String sql = "SELECT * FROM courseplan_book WHERE courseplan_id = " + cp_id + " AND member_id=" + m_id;
        if (sqlHelper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }


    public int queryBookStatus(long cp_id,long m_id) {
        String sql = "SELECT status FROM courseplan_book WHERE courseplan_id = " + cp_id + " AND member_id=" + m_id;
        ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
        mapArrayList = sqlHelper.query(sql);
        if (mapArrayList.size() == 0) {
            return 0;
        }
        return Integer.parseInt(String.valueOf(mapArrayList.get(0).get("status")));
    }

    /**
     * 查询出所有预订
     * @param m_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryBookListByMemberId(long m_id) {

        String sql = "SELECT cpb.id,cpb.courseplan_id,trim(c.name) c_name,cp.start_time,cp.end_time,c.type,trim(cr.name) cr_name FROM courseplan_book cpb " +
                "LEFT JOIN courseplan cp ON cpb.courseplan_id = cp.id " +
                "LEFT JOIN course c ON cp.course_id=c.id " +
                "LEFT JOIN classroom cr ON cp.classroom_id=cr.id " +
                "WHERE cp.start_time > now() " +
                    "AND c.del = 0 " +
                    "AND cp.del = 0 " +
                    "AND cpb.status = 1 " +
                    "AND cpb.member_id = " + m_id;
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
        String sql = "SELECT cpb.id,cpb.courseplan_id,c.name,cp.start_time,cp.end_time,cr.name FROM courseplan_book cpb " +
                "LEFT JOIN courseplan cp ON cpb.courseplan_id=cp.id " +
                "LEFT JOIN course c ON cp.course_id=c.id " +
                "LEFT JOIN classroom cr ON cp.classroom_id = cr.id " +
                "WHERE cpb.course_plan_id=" + cp_id;
        mapArrayList = sqlHelper.query(sql);
        return mapArrayList;
    }

    /**
     * 通过排课ID、会员ID预订排课
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean book(long cp_id,long m_id) {
        String sql = "INSERT INTO courseplan_book (courseplan_id,member_id,del,last_modify_time,status) " +
                "VALUES (" + cp_id + "," + m_id + ",0,'" + simpleDateFormat.format(new Date()) + "',1)";
        boolean isSuccessed = false;
        try {
            isSuccessed = sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccessed;
    }

    /**
     * 通过ID预订排课
     * @param id
     * @param status
     * @return
     */
    public boolean updateStatus(long id,int status) {
        String sql = "UPDATE courseplan_book SET status=" + status + " WHERE id=" + id;
        boolean isSuccessed = false;
        try {
            isSuccessed = sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccessed;
    }

    /**
     * 通过ID预订排课
     * @param cp_id
     * @param m_id
     * @param status
     * @return
     */
    public boolean updateStatus(long cp_id,long m_id,int status) {
        String sql = "UPDATE courseplan_book SET status=" + status + " WHERE courseplan_id=" + cp_id + " AND member_id=" + m_id;
        boolean isSuccessed = false;
        try {
            isSuccessed = sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccessed;
    }

    /**
     * 通过ID判断是否有预订
     * @param id
     * @return
     */
    public boolean isBooked(long id) {
        String sql = "SELECT * FROM courseplan_book WHERE id = " + id + " AND status=1";
        if (sqlHelper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 通过排课ID及会员ID判断是否有预订
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean isBooked(long cp_id,long m_id) {
        String sql = "SELECT * FROM courseplan_book WHERE courseplan_id = " + cp_id + " AND member_id=" + m_id + " AND status=1";
        if (sqlHelper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 通过ID取消预订
     * @param id
     * @return
     */
    public boolean unBook(long id) {
        String sql = "UPDATE courseplan_book SET status=0,last_modify_time='" + simpleDateFormat.format(new Date()) + "' " +
                " WHERE id=" + id;
        try {
            return sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过排课ID、会员ID取消预订
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean unBook(long cp_id,long m_id) {
        String sql = "UPDATE courseplan_book SET status=0,last_modify_time='" + simpleDateFormat.format(new Date()) + "' " +
                "WHERE courseplan_id=" + cp_id + " AND member_id=" + m_id;
        try {
            return sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据会员ID查看签到记录
     * @param m_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryAttendanceListByMemberId(long m_id) {
        ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
        String sql = "SELECT cpb.courseplan_id FROM courseplan_book cpb " +
                "WHERE cpb.status=1 AND cpbmember_id=" + m_id;
        mapArrayList = sqlHelper.query(sql);
        return mapArrayList;
    }

    /**
     * 根据排课ID查询签到明细
     * @param cp_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryAttendanceListByCoursePlanId(long cp_id) {
        ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
        String sql = "SELECT m.id,m.name FROM courseplan_book cpb " +
                "LEFT JOIN member m ON cpb.member_id=m.id " +
                "WHERE cpb.status=2 AND cpb.courseplan_id = " + cp_id;
        mapArrayList = sqlHelper.query(sql);
        return mapArrayList;
    }

    /**
     * 通过ID签到
     * @param id
     * @return
     */
    public boolean attend(long id) {
        String sql = "UPDATE courseplan_book SET status = 2,last_modify_time='" + simpleDateFormat.format(new Date()) + "' " +
                "WHERE id=" + id;
        try {
            return sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过排课ID及会员ID签到
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean attend(long cp_id,long m_id) {
        String sql = "UPDATE courseplan_book SET status = 2,last_modify_time='" + simpleDateFormat.format(new Date()) + "' " +
                "WHERE courseplan_id=" + cp_id + " AND member_id=" + m_id;
        try {
            return sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 通过ID取消预订
     * @param id
     * @return
     */
    public boolean unAttend(long id) {
        String sql = "UPDATE courseplan_book SET status=1 WHERE id=" + id ;
        try {
            return sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过排课ID及会员ID取消预订
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean unAttend(long cp_id,long m_id) {
        String sql = "UPDATE courseplan_book SET status=1 WHERE courseplan_id=" + cp_id + " AND member_id=" + m_id ;
        try {
            return sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过ID判断是否已经签到
     * @param id
     * @return
     */
    public boolean isAttended(long id) {
        String sql = "SELECT * FROM courseplan_book cpb WHERE cpb.member_id=" + id + " AND cpb.status=2";
        if (sqlHelper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 通过排课ID及会员ID判断是否已经签到
     * @param cp_id
     * @param m_id
     * @return
     */
    public boolean isAttended(long cp_id,long m_id) {
        String sql = "SELECT * FROM courseplan_book cpb WHERE cpb.member_id=" + m_id + " AND courseplan_id=" + cp_id + " AND cpb.status=2";
        if (sqlHelper.query(sql).size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 查询普通课程排课ID
     * @param m_id
     * @return
     */
    public ArrayList<HashMap<String, Object>> queryTeacherListByMemberId(long m_id) {
        ArrayList<HashMap<String, Object>> mapArrayList = new ArrayList<>();
        String sql = "SELECT cpb.courseplan_id,truncate(sm.id,0) teacher_id,trim(sm.name) sm_name FROM courseplan_book cpb " +
                "LEFT JOIN courseplan_teacher cpt ON cpb.courseplan_id = cpt.courseplan_id " +
                "LEFT JOIN courseplan cp ON cpb.courseplan_id=cp.id " +
                "LEFT JOIN shopmember sm ON cpt.teacher_id=sm.id " +
                "WHERE cp.del=0 AND sm.del=0 AND cpt.del=0 AND cpb.status=1 AND cpb.member_id=" + m_id +
                " ORDER BY cpt.courseplan_id";
        mapArrayList = super.sqlHelper.query(sql);
        return mapArrayList;
    }

    /**
     * 查询私教课程
     * @param m_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryCoursePrivateTeacherList(long m_id) {
        String sql = "SELECT cpb.courseplan_id,c.teacher_id,trim(sm.name) sm_name FROM courseplan_book cpb " +
                "LEFT JOIN courseplan cp ON cpb.courseplan_id=cp.id " +
                "LEFT JOIN course c ON cp.course_id=c.id " +
                "LEFT JOIN shopmember sm ON c.teacher_id=sm.id " +
                "WHERE cp.del=0 AND sm.del=0 AND cpb.status=1 AND cpb.member_id=" + m_id + " AND c.type IN (4) " +
                "ORDER BY cpb.courseplan_id";
        ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
        mapArrayList = sqlHelper.query(sql);
        return mapArrayList;
    }

}
