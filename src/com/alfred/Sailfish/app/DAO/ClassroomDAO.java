package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.SQLHelper;

public class ClassroomDAO {
	
	private SQLHelper helper = new SQLHelper();

	public ClassroomDAO() {
	}
	
	/**
	 * pass
	 * 新增教室
	 * @param shop_id
	 * @param name
	 * @return true为成功，false为失败
	 */
	public boolean add(long shop_id,String name) {
		boolean isAdded = false;
		String sql = "INSERT INTO classroom (shop_id,name,del) VALUES ("+ shop_id + ",'" + name + "',0)";
		try {
			isAdded = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdded;
	}
	
	/**
	 * pass
	 * pass
	 * 删除教室
	 * @param id
	 * @return
	 */
	public boolean remove(long id) {
		boolean isUpdated = false;
		String sql = "UPDATE classroom SET del = 1 WHERE id = " + id;
		try {
			isUpdated =  helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	/**
	 * pass
	 * 修改教室名称
	 * @param id
	 * @param name
	 */
	public boolean modify (long id,String name) {
		String sql = "UPDATE classroom SET name = '" + name +"' WHERE id = " + id;
		boolean isUpdated = false;
		try {
			isUpdated = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	/**
	 * pass
	 * 通过机构ID查询教室列表
	 * @param shop_id
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryByShopId(long shop_id) {
		String sql = "SELECT id,name FROM classroom WHERE del = 0 AND shop_id = " + shop_id + " ORDER BY name DESC";
		return helper.query(sql);
	}
	
	
	public ArrayList<HashMap<String,Object>> queryCRIdByCRName(long shop_id,String name) {
		String sql = "SELECT id FROM classroom WHERE shop_id = " + shop_id + " AND name = '" + name + "' AND del = 0";
		return helper.query(sql);
	}
	
	/**
	 * pass
	 * 教室是否已被删除
	 * @param cr_id 教室ID
	 * @return
	 */
	public boolean isDel(long cr_id) {
		String sql = "SELECT * FROM classroom WHERE del = 0 AND id = " + cr_id;
		if (helper.query(sql).size() == 0 ){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断课室是否存在
	 * @param cr_id
	 * @return
	 */
	public boolean isExist(long cr_id) {
		String sql = "SELECT * FROM classroom WHERE id = " + cr_id;
		if(helper.query(sql).size() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 查询教师详情
	 * @param cr_id
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryDetailById(long cr_id) {
		String sql = "SELECT id,name FROM classroom WHERE id = " + cr_id;
		return helper.query(sql);
		
	}
	
	/**
	 * 查询教室总数量
	 * @param shop_id
	 * @return
	 */
	public int countTotalNum(long shop_id) {
		String sql = "SELECT truncate(COUNT(1),0) c FROM classroom "
				+ "WHERE del =0 AND shop_id = " + shop_id;
		int i = MethodTool.toInt(helper.query(sql), "c");
		return i;
	}
	
	/**
	 * 检查名字是否重复
	 * @param name
	 * @param s_id
	 * @return
	 */
	public boolean isNameRepeated(String name,long s_id) {
		String sql = "SELECT * FROM classroom "
				+ "WHERE shop_id = " + s_id + " AND name = '" + name + "' AND del = 0";
		if (helper.query(sql).size() != 0) {
			return true;
		}
		return false;
	}
	
}
