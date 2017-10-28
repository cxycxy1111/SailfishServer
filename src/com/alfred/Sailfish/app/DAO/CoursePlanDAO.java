package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;

import com.alfred.Sailfish.app.Util.SQLHelper;

public class CoursePlanDAO {
	
	private ShopDAO shopDAO = new ShopDAO();
	private SQLHelper helper = new SQLHelper();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public CoursePlanDAO(){
		
	}
	
	/**
	 * pass
	 * 新增排课
	 * @param c_id 课程ID
	 * @param cr_id 教室ID
	 * @param lmu_id 创建者ID/上次修改人ID
	 * @param s_time 课程开始时间
	 * @param e_time 课程结束时间
	 * @param remark 标记
	 * @return boolean true为成功
	 */
	public boolean addCoursePlan(long c_id,long cr_id,long lmu_id,String s_time,String e_time,String remark) {
		boolean isAdded = false;
		String t = sdf.format(new Date());
		long s_id = shopDAO.queryShopIdByCourseId(c_id);
		String sql = "INSERT INTO courseplan (shop_id,course_id,classroom_id,creator,last_modify_user,start_time,end_time,remark,last_modify_time,create_time,del) "
				+ "VALUES (" + s_id + "," + c_id + "," + cr_id + "," + lmu_id + "," + lmu_id + ",'" + s_time + "','" + e_time + "','" + remark + "','" + t + "','" + t + "',0)";
		try {
			isAdded = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdded;
	}
	
	/**
	 * pass
	 * 删除排课
	 * @param id 排课ID
	 * @return
	 */
	public boolean removeCoursePlan(long id,long lmu_id) {
		boolean isDel = false;
		String t = sdf.format(new Date());
		String sql = "UPDATE courseplan "
				+ "SET del = 1,last_modify_user = " + lmu_id + ",last_modify_time = '" + t + "' "
				+ "WHERE id = " + id;
		try {
			isDel = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isDel;
	}
	
	/**
	 * pass
	 * 修改排课
	 * @param id 排课ID
	 * @param cr_id 教室ID
	 * @param lmu_id 上次修改人ID
	 * @param s_time 开始时间
	 * @param e_time 结束时间
	 * @param remark 标记
	 * @return
	 */
	public boolean modifyCoursePlan(long id,long cr_id,long lmu_id,String s_time,String e_time,String remark) {
		boolean isAdded = false;
		String sql = "UPDATE courseplan SET classroom_id =" + cr_id + ","
				+ "last_modify_user = " + lmu_id + " ,"
				+ "start_time = '" + s_time + "',"
				+ "end_time = '" + e_time + "',"
				+ "remark = '" + remark
				+ "' WHERE id = " + id;
		try {
			isAdded = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdded;
	}
	
	/**
	 * 查询排课详情
	 * @param id 排课ID
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryById(long id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT trim(c.name) course_name,trim(cr.name) classroom_name,cp.start_time,c.last_time,cp.remark FROM courseplan cp "
				+ "LEFT JOIN classroom cr ON cp.classroom_id = cr.id "
				+ "LEFT JOIN course c ON cp.course_id = c.id "
				+ "WHERE cp.id = " + id;
		list = helper.query(sql);
		return list;
	}
	
	/**
	 * 查询排课是否已被删除
	 * @param id
	 * @return
	 */
	public boolean isDel(long id) {
		boolean isDel = false;
		String sql = "SELECT * FROM courseplan WHERE del = 0 AND id = " + id;
		if (helper.query(sql).size() == 0) {
			isDel = true;
		}
		return isDel;
	}
	
	/**
	 * 根据课程ID查询排课列表
	 * @param course_id
	 * @return
	 */
	public ArrayList<IdentityHashMap<String, Object>> queryByCourseId(long course_id) {
		ArrayList<IdentityHashMap<String, Object>> list = new ArrayList<IdentityHashMap<String, Object>>();
		String sql = "SELECT truncate(cp.id,0) courseplan_id,trim(c.name) course_name,truncate(c.last_time,0) last_time,cp.start_time FROM courseplan cp "
				+ "LEFT JOIN course c ON cp.course_id = c.id "
				+ "WHERE cp.del = 0 AND course_id = " + course_id + " ORDER BY cp.start_time DESC";
		list = helper.linkquery(sql);
		return list;
	}
	
	/**
	 * 通过教师ID查询排课列表
	 * @param sm_id
	 * @return
	 */
	public ArrayList<IdentityHashMap<String, Object>> queryByShopMemberId(long sm_id) {
		ArrayList<IdentityHashMap<String, Object>> list = new ArrayList<IdentityHashMap<String, Object>>();
		String sql = "SELECT truncate(cp.id,0) courseplan_id,trim(c.name) course_name,trim(cr.name) classroom_name,truncate(c.last_time,0) last_time FROM courseplan cp "
				+ "LEFT JOIN course c ON cp.course_id = c.id "
				+ "LEFT JOIN classroom cr ON cp.classroom_id = cr.id "
				+ "LEFT JOIN courseplan_teacher ct ON cp.id = ct.courseplan_id "
				+ "WHERE cp.del = 0 AND ct.teacher_id = " + sm_id + " AND cp.start_time > now() ORDER BY cp.start_time DESC";
		list = helper.linkquery(sql);
		System.out.println(list.size());
		return list;
	}
	
	/**
	 * 通过机构ID获取排课列表
	 * @param s_id
	 * @return
	 */
	public ArrayList<IdentityHashMap<String, Object>> queryByShopId(long s_id) {
		ArrayList<IdentityHashMap<String, Object>> list = new ArrayList<IdentityHashMap<String, Object>>();
		String sql = "SELECT truncate(cp.id,0) courseplan_id,trim(c.name) course_name,trim(cr.name) classroom_name,truncate(c.last_time,0) last_time,cp.start_time FROM courseplan cp "
				+ "LEFT JOIN course c ON cp.course_id = c.id "
				+ "LEFT JOIN classroom cr ON cp.classroom_id = cr.id "
				+ "LEFT JOIN courseplan_teacher ct ON cp.id = ct.courseplan_id "
				+ "WHERE cp.del = 0 AND cp.shop_id = " + s_id + " AND cp.start_time > now() ORDER BY cp.start_time DESC";
		list = helper.linkquery(sql);
		return list;
	}
	
	/**
	 * 判断排课是否存在
	 * @param id
	 * @return
	 */
	public boolean isExist(long id) {
		String sql = "SELECT * FROM courseplan WHERE id = " + id;
		if (helper.query(sql).size() == 0) {
			return false;
		}
		return true;
	}
}
