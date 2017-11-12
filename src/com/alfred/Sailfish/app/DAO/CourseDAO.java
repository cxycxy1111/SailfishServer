package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;

import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.SQLHelper;

public class CourseDAO {
	
	private SQLHelper helper = new SQLHelper();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private CourseSupportedCardDAO courseSupportedCardDAO = new CourseSupportedCardDAO();
	private ShopDAO shopDAO = new ShopDAO();
	
	public CourseDAO() {
		
	}
	
	/**
	 * pass
	 * 新增会员课、教练班、集训
	 * @param s_id 机构ID
	 * @param lmu_id 上次修改人ID
	 * @param name 课程名称
	 * @param type 1:会员课2:教练班3:集训
	 * @param last_time 持续时间
	 * @param max_book_num 最大预定人数
	 * @param summary 简介
	 * @return
	 */
	public boolean add(long s_id,long lmu_id,String name,
			int type,int last_time,int max_book_num,String summary) {
		boolean isAdded = false;
		String t = sdf.format(new Date());
		String sql = "INSERT INTO course (shop_id,creator,last_modify_user,name,type,del,"
				+ "last_time,max_book_num,summary,create_time,last_modify_time) "
				+ "VALUES (" + s_id + "," + lmu_id + "," + lmu_id + ",'" + name + "'," + 
				type + ",0," + last_time + "," + max_book_num + ",'" + summary + "','" + t + "','" + t + "')";
		try {
			isAdded = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdded;
	}
	
	/**
	 * pass
	 * 新增私教课程
	 * @param s_id 机构ID
	 * @param lmu_id 上次修改人ID
	 * @param sm_id 教师ID
	 * @param m_id 学生ID
	 * @param name 课程名称
	 * @param total_times 总次数
	 * @param e_time 过期时间
	 * @param actual_cost 总花费
	 * @return boolean 是否增加成功
	 */
	public boolean addPrivate(long s_id,long lmu_id,long sm_id,long m_id,String name,
			int total_times,String e_time,int actual_cost) {
		if (e_time.equals("") || e_time.equals(null)) {
			e_time = sdf.format(new Date());
		}
		boolean isAdded = false;
		String t = sdf.format(new Date());
		String sql = "INSERT INTO course (shop_id,creator,last_modify_user,teacher_id,student_id,"
				+ "name,type,del,total_times,rest_times,expired_time,actual_cost,create_time,last_modify_time)"
				+ " VALUES (" + s_id + "," + lmu_id + "," + lmu_id + "," + sm_id + "," + m_id + ",'" + name + "',4,0," + 
				total_times + "," + total_times + ",'" + e_time + "'," + actual_cost + ",'" + t + "','" + t +"')";
		try {
			isAdded = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdded;
	}
	
	/**
	 * pass
	 * 删除课程
	 * @param id 课程ID
	 * @param lmu 上次修改人ID
	 * @return
	 */
	public boolean remove(long id,long lmu) {
		String t = sdf.format(new Date());
		boolean isUpdated = false;
		String sql = "UPDATE course SET del = 1,last_modify_user = " + lmu + ",last_modify_time = '" + t + "' "
				+ "WHERE id = " + id;
		try {
			isUpdated = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}

	/**
	 * pass
	 * 修改课程
	 * @param lmu_id 上次修改人ID
	 * @param name 课程名称
	 * @param last_time 持续时间
	 * @param max_book_num 最大预约人数
	 * @param summary 课程简介
	 * @return
	 */
	public boolean modify(long c_id,long lmu_id,String name,
			int last_time,int max_book_num,String summary) {
		boolean isUpdated = false;
		String t = sdf.format(new Date());
		String sql = "UPDATE course SET " + 
				"last_modify_user = " + lmu_id + "," + 
				"name = '" + name + "'," + 
				"last_time = " + last_time + "," + 
				"max_book_num = " + max_book_num + "," + 
				"summary = '" + summary + "'," + 
				"last_modify_time = '" + t + "' WHERE id = " + c_id;
		try {
			isUpdated = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
		
	/**
	 * pass
	 * 修改私教课程
	 * @param lmu_id 上次修改人ID
	 * @param name 课程名称
	 * @param expired_time 失效时间
	 * @param total_times 总次数
	 * @param total_cost 总花费
	 * @return
	 */
	public boolean modifyPrivate(long c_id,long lmu_id,String name,String expired_time,
			int total_times,int total_cost) {
		boolean isUpdated = false;
		String t = sdf.format(new Date());
		String sql = "UPDATE course SET " + 
				"last_modify_user = " + lmu_id + "," + 
				"name = '" + name + "'," + 
				"expired_time = '" + expired_time + "'," + 
				"total_times = " + total_times + "," + 
				"last_modify_time = '" + t + "' WHERE id = " + c_id;
		try {
			isUpdated = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	/**
	 * pass
	 * 根据机构ID查询课程列表
	 * @param s_id 机构ID
	 * @return ArrayList<HashMap<String,Object>>
	 */
	public ArrayList<HashMap<String,Object>> queryList(long s_id) {
		String sql = "SELECT id,name,last_time FROM course "
				+ "WHERE del = 0 AND shop_id = " + s_id + " ORDER BY create_time DESC";
		return helper.query(sql);
	}
	
	/**
	 * pass
	 * 根据课程ID查询课程详情
	 * @param c_id 课程ID
	 * @return ArrayList<HashMap<String,Object>>
	 */
	public ArrayList<HashMap<String,Object>> queryDetail(long c_id){
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map = new HashMap<String,Object>();
		String sql = "SELECT id,type,name,last_time,max_book_num FROM course WHERE id = " + c_id;
		list = helper.query(sql);
		long s_id = shopDAO.queryShopIdByCourseId(c_id);
		map.put("card", courseSupportedCardDAO.querySupportedCards(s_id,c_id));
		list.add(map);
		return list;
	}
	
	/**
	 * pass
	 * 根据课程ID查询私教课程详情
	 * @param c_id 课程ID
	 * @return
	 */
	public ArrayList<IdentityHashMap<String,Object>> queryPrivateDetail(long c_id){
		String sql = "SELECT c.id,c.type,trim(c.name) course_name,trim(sm.name) shopmember_name,trim(m.name) member_name,c.total_times,c.rest_times,c.expired_time FROM course c "
				+ "LEFT JOIN shopmember sm ON c.shop_id = sm.id "
				+ "LEFT JOIN member m ON c.student_id = m.id "
				+ "WHERE c.type IN (4) AND c.id = " + c_id;
		return helper.linkquery(sql);
	}
	
	/**
	 * 查询课程类型
	 * @param c_id
	 * @return
	 */
	public int queryType(long c_id) {
		String sql = "SELECT type FROM course WHERE id = " + c_id;
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list = helper.query(sql);
		if (list.size() == 0) {
			return 0;
		}else {
			return MethodTool.toInt(list, "type");
		}
	}

	public boolean isCourseNameExist(String course_name,long s_id) {
		String sql = "SELECT * FROM course WHERE name = '" + course_name + "' AND shop_id = " + s_id;
		if (helper.query(sql).size() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 通过排课ID查询课程ID
	 * @param cp_id
	 * @return
	 */
	public long querycourseIdByCoursePlanId(long cp_id) {
		String sql = "SELECT course_id FROM courseplan WHERE id = " + cp_id;
		return MethodTool.toLong(helper.query(sql), "course_id");
	}

	/**
	 * 通过课程名称查询课程ID
	 * @param name
	 * @return
	 */
	public long queryCourseIdByCourseName(String name,long s_id) {
		String sql = "SELECT id FROM course WHERE name = '" + name + "' AND shop_id = " + s_id;
		return MethodTool.toLong(helper.query(sql),"id");
	}
	
	/**
	 * pass
	 * 检查课程是否存在
	 * @param c_id
	 * @return
	 */
	public boolean isExist(long c_id) {
		boolean isExist = false;
		String sql = "SELECT * FROM course WHERE id = " + c_id;
		if (helper.query(sql).size() != 0) {
			isExist = true;
		}
		return isExist;
	}
	
	/**
	 * pass
	 * 检查课程是否已被删除
	 * @param c_id
	 * @return
	 */
	public boolean isDel(long c_id) {
		String sql = "SELECT del FROM course WHERE id = " + c_id;
		return MethodTool.toBool(helper.query(sql), "del");
	}
	
}
