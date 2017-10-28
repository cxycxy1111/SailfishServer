package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import com.alfred.Sailfish.app.Util.SQLHelper;

public class CoursePlanTeacherDAO {
	
	private SQLHelper helper = new SQLHelper();
	
	public CoursePlanTeacherDAO(){
	}
	
	/**
	 * pass
	 * 新增教师
	 * @param cp_id 排课ID
	 * @param sm_id 教师ID
	 * @return
	 */
	public boolean add(long cp_id,long sm_id) {
		boolean isAdded = false;
		String sql = "INSERT INTO courseplan_teacher (courseplan_id,teacher_id,del) "
				+ "VALUES ("+ cp_id + "," + sm_id + ",0)";
		try {
			isAdded = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdded;
	}
	
	/**
	 * pass
	 * 删除教师
	 * @param cp_id
	 * @param sm_id
	 * @return
	 */
	public boolean remove(long cp_id,long sm_id) {
		boolean isDel = false;
		String sql = "DELETE FROM courseplan_teacher WHERE courseplan_id = " + cp_id + " AND teacher_id = " + sm_id;
		try {
			isDel = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isDel;
	}
	
	/**
	 * pass
	 * 查询排课带课教师列表
	 * @param cp_id 排课ID
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryById(long cp_id,long shop_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT truncate(sm.id,0) sm_id,trim(name) t_name,truncate(ct.courseplan_id,0) cp_id "
				+ "FROM shopmember sm "
				+ "LEFT JOIN courseplan_teacher ct ON ct.teacher_id = sm.id "
				+ "WHERE sm.del = 0 AND shop_id = " + shop_id;
		list = helper.query(sql);
		return list;
	}
	
	/**
	 * pass
	 * 查询某位教师是否有带课
	 * @param cp_id
	 * @param sm_id
	 * @return
	 */
	public boolean isLead (long cp_id,long sm_id) {
		boolean isAdded = false;
		String sql = "SELECT * FROM courseplan_teacher WHERE courseplan_id = " + cp_id + " AND teacher_id = " + sm_id;
		if (helper.query(sql).size() != 0) {
			isAdded = true;
		}
		return isAdded;
	}
	
	/**
	 * 查询私教课程带课教师
	 * @param c_id 课程ID
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryPrivateCoursePlanTeacher(long c_id) {
		String sql = "SELECT sm.id,sm.name FROM course c "
				+ "LEFT JOIN shopmember sm ON c.teacher_id = sm.id "
				+ "WHERE id = " + c_id;
		return helper.query(sql);
	}

}
