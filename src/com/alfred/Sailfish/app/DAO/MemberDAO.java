package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.SQLHelper;

public class MemberDAO{
	
	SQLHelper helper = new SQLHelper();
	ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();

	public MemberDAO() {
		
	}
	
	/**
	 * 获得会员列表
	 * @param shop_id 机构ID
	 */
	public ArrayList<HashMap<String, Object>> queryList(long shop_id) {
		String sql = "SELECT id,name FROM member WHERE del = 0 AND shop_id = " + shop_id + " ORDER BY name";
		return helper.query(sql);
	}
	
	/**
	 * 查询会员详情
	 * @param member_id
	 * @param shop_id
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryDetail(long member_id,long shop_id) {
		String sql = "select id,name,login_name,birthday,phone,im from member where id = " + member_id +" and shop_id = " + shop_id;
		return helper.query(sql);
	}
	
	/**
	 * 新增会员
	 * @param shop_id 机构ID
	 */
	public boolean addNewMmember(long shop_id,long shopmember_id,String name,String login_name,String password,String birthday,String phone,String im) {
		boolean isSuccessed = false;
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );	
		String sql = "insert into member (shop_id, creator,last_modify_user,"
				+ "name,login_name,password,del,birthday,phone,im,create_time,last_modify_time)"
				+ "VALUES("+ shop_id + "," + shopmember_id + ","+ shopmember_id+ ",'"+ name+ "','"+ login_name +"','" + MethodTool.MD5(password) + "',"
				+ 0 + ",'" + birthday + "','" + phone + "','" + im + "','" + sdf.format(new Date()) + "','" + sdf.format(new Date()) + "')";
		try {
			isSuccessed = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccessed;
	}
	
	/**
	 * 删除会员
	 * @param sm_id 上次修改人
	 * @param m_id 要被删除的会员
	 */
	public boolean deleteMember(long m_id,long sm_id) {
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );	
		String sql = "UPDATE member SET del=1,"
				+ "last_modify_user=" + sm_id + "," 
				+ "last_modify_time='" + sdf.format(new Date())
				+ "' WHERE id=" + m_id ;
		boolean updated = false;
		try {
			updated = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updated;
	}
	
	
	/**
	 * 重设密码
	 * @param id
	 * @param shopmemebr_id
	 */
	public boolean resetPassword(long id,long shopmemebr_id,String newPassword) {
		String pwd = MethodTool.MD5(newPassword);
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String sql = "UPDATE member SET password = '" + pwd 
				+ "',last_modify_user = " 
				+ shopmemebr_id 
				+ ",last_modify_time = '" 
				+ sdf.format(new Date()) + "' " 
				+ "WHERE id = " + id;
		boolean b = false;
		try {
			b = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
		
	}
	
	/**
	 * 修改会员
	 * @param id
	 * @param shopmember_id
	 * @param name
	 * @param birthday
	 * @param phone
	 * @param im
	 * @return boolean
	 */
	public boolean modifyMmember(long id,long shopmember_id,String name,String birthday,String phone,String im) {
		boolean isSuccessed = false;
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );	
		String sql = "UPDATE member SET last_modify_user = " + shopmember_id 
				+ ",last_modify_time = '" + sdf.format(new Date()) 
				+ "',name = '" + name 
				+ "',birthday = '" +birthday 
				+ "',phone = '" + phone + "',im = '" + im + "' WHERE id = " + id;
		try {
			isSuccessed = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSuccessed;
	}
	
	/**
	 * 检查会员是否已被删除
	 */
	public boolean isDel(long member_id) {
		boolean isDel = false;
		String sql = "SELECT del FROM member WHERE id = " + member_id;
		list = helper.query(sql);
		isDel = MethodTool.toBool(list, "del");
		return isDel;
	}
	
	
	/**
	 * 检查是否存在登录名 
	 * @param login_name
	 * @param shop_id
	 * 
	 */
	public boolean isLoginNameExist(String login_name,long shop_id) {
		boolean isExist = false;
		String sql = "select * from member where del = 0 and login_name = '"+login_name + "' and shop_id = " + shop_id;
		list = helper.query(sql);
		if (list.size() != 0) {
			isExist = true;
		}
		System.gc();
		return isExist;
	}
	
	/**
	 * 检查会员登录名和密码是否匹配
	 * @param login_name
	 * @param password
	 * @return true 匹配;false 不匹配
	 */
	public boolean isLoginNameAndPasswordMatch(String login_name,String password) {
		boolean isMatch = false;
		String pwd = MethodTool.MD5(password);
		String sql = "select * from member where login_name = '" + login_name + "' and password = '" + pwd + "'";
		list = helper.query(sql);
		if (list.size() != 0) {
			isMatch = true;
		}
		System.gc();
		return isMatch;
	}
	
}
