package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.SQLHelper;

public class ShopmemberDAO {

	SQLHelper helper = new SQLHelper();
	private ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ShopmemberDAO() {
		
	}
	
	/**
	 * 通过教师ID修改密码
	 * @param sm_id
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public boolean modifyPassword(long sm_id,String password) throws SQLException {
		String sql = "UPDATE shopmember SET password = '" + MethodTool.MD5(password) + "' WHERE id = " + sm_id;
		return helper.update(sql);
	}

	/**
	 * 修改教师资料
	 * @param sm_id
	 * @param lmu_id
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public boolean modifyInfo(long sm_id,long lmu_id,String name) throws SQLException {
		String sql = "UPDATE shopmember SET name='" + name +
				"' , last_modify_user=" + lmu_id +
				" , last_modify_time='" +sdf.format(new Date()) + "' WHERE id=" + sm_id;
		return helper.update(sql);
	}
	
	/**
	 * pass
	 * 通过教师姓名获取教师ID
	 * @param name
	 * @return
	 */
	public long queryIdByName(String name) {
		String sql = "select id from shopmember where name = '" + name + "'";
		long shopmember_id = 0;
		if (helper.query(sql).size() == 0) {
			return 0;
		}else {
			shopmember_id = Integer.toUnsignedLong((int)helper.query(sql).get(0).get("id"));
			return shopmember_id;
		}
	}
	
	/**
	 * pass
	 * 根据舞馆ID获得教师列表
	 * @param shop_id 机构ID
	 */
	public ArrayList<HashMap<String,Object>> queryShopmemberList(long shop_id,int type) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>> ();
		String sql = "select id,name,type from shopmember where shop_id = " + shop_id + " AND del = 0 AND type = " + type;
		list = helper.query(sql);
		return list;
	}
	
	/**
	 * 根据ID获取教师信息
	 * @param sm_id
	 * @param s_id
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryDetail(long sm_id,long s_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<>();
		String sql = "SELECT id,type,name,user_name FROM shopmember WHERE id = " + sm_id + " AND shop_id = " + s_id;
		list = helper.query(sql);
		return list;
	}
	
	/**
	 * pass
	 * 通过登录名查询教师ID
	 * @param user_name
	 * @return
	 */
	public long queryIdByUserName(String user_name) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>> ();
		String sql = "SELECT id FROM shopmember WHERE user_name = '" + user_name + "'";
		list = helper.query(sql);
		if (list.size() == 0) {
			return 0;
		}else {
			return Long.parseLong(String.valueOf(list.get(0).get("id")));
		}
	}
	
	/**
	 * 管理员新增自己
	 * @param shop_id
	 * @param type
	 * @param name
	 * @param login_name
	 * @param password
	 * @return
	 */
	public boolean addNewShopmember(long shop_id,int type,String name,String login_name,String password) {
		boolean isSuccessed = false;
		String pwd = MethodTool.MD5(password);
		String sql = "INSERT INTO shopmember (shop_id,type,user_name,password,del,name,create_time) VALUES ("
		+ shop_id + "," + type + ",'" + login_name + "','" + pwd + "'," + 0 + ",'" + name + "','"
		+ sdf.format(new Date()) + "')";
		try {
			isSuccessed = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccessed;
	}
	/**
	 * 管理员新增普通教师
	 * @param shop_id
	 * @param shopmember_id
	 * @param type
	 * @param name
	 * @param user_name
	 * @param password
	 * @return
	 */
	public boolean addNewShopmember(long shop_id,long shopmember_id,int type,String name,String user_name,String password) {
		boolean isSuccessed = false;
		String sql = "INSERT INTO shopmember (shop_id,last_modify_user,creator,type,user_name,password,del,name,create_time,last_modify_time) VALUES (" +
				shop_id + "," +
				shopmember_id + "," +
				shopmember_id + "," +
				type + ",'" +
				user_name + "','" +
				MethodTool.MD5(password) + "'," +
				0 + ",'" +
				name + "','" +
				sdf.format(new Date()) + "','" +
				sdf.format(new Date()) + "')";
		System.out.println(sql);
		try {
			isSuccessed = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccessed;
	}
	
	/**
	 * 删除教师
	 * @param lmu_id 上次修改人
	 * @param id 要被删除的教师
	 */
	public boolean deleteMember(long lmu_id,long id) {
		boolean isUpdated = false;
		String sql = "UPDATE shopmember SET del = 1 ,"
				+ "last_modify_user = " + lmu_id + ","
				+ "last_modify_time = '" + sdf.format(new Date())
				+ "' WHERE id = " + id ;
		try {
			isUpdated = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	/**
	 * pass
	 * 检查教师是否被删
	 * @param member_id
	 * @return
	 */
	public boolean isDel(long member_id) {
		String sql = "select del from shopmember where id = "+ member_id;
		return MethodTool.toBool(helper.query(sql), "del");
	}
	
	
	/**
	 * pass
	 * 检查是否存在登录名
	 * @param login_name
	 *
	 */
	public boolean isLoginNameExist(String login_name) {
		boolean isExist = false;
		String sql = "SELECT * FROM shopmember WHERE user_name = '" + login_name + "' AND del = 0";
		list = helper.query(sql);
		if (list.size() != 0) {
			isExist = true;
		}
		System.gc();
		return isExist;
	}
	
	/**
	 * pass
	 * 检查教师登录名和密码是否匹配
	 * @param login_name
	 * @param password
	 */
	public boolean isLoginNameAndPasswordMatch(String login_name,String password) {
		boolean isMatch = false;
		String pwd = MethodTool.MD5(password);
		String sql = "select * from shopmember where user_name = '" + login_name + "' and password = '" + pwd + "'";
		list = helper.query(sql);
		if (list.size() != 0) {
			isMatch = true;
		}
		System.gc();
		return isMatch;
	}
	
	
}
