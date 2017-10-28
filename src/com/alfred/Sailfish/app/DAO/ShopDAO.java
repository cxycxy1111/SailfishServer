package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.SQLHelper;

public class ShopDAO {
	
	private SQLHelper helper = new SQLHelper();
	
	public ShopDAO () {
	}
	
	/**
	 * 根据机构名查询机构id
	 */
	public long queryShopByName(String name) {
		String sql = "select id from shop where name = '" + name + "'";
		return this.parseId(sql);
	}
	
	/**
	 * 通过教师ID查询机构ID
	 * @param shopmember_id
	 * @return
	 */
	public long queryShopByShopmemberId(long shopmember_id) {
		String sql = "select shop_id from shopmember where id = " + shopmember_id;
		return this.parseShopId(sql);
	}
	
	/**
	 * 通过卡ID查询机构ID
	 * @param card_id
	 * @return
	 */
	public long queryShopIdByCardId(long card_id) {
		String sql = "SELECT shop_id FROM card WHERE id = " + card_id;
		return this.parseShopId(sql);
	}
	
	/**
	 * 通过会员ID查找机构ID
	 * @param member_id
	 * @return
	 */
	public long queryShopIdByMemberId(long member_id) {
		String sql = "SELECT shop_id FROM member WHERE id = " + member_id;
		return this.parseShopId(sql);
	}
	
	/**
	 * 通过会员卡ID查找机构ID
	 * @param member_card_id
	 * @return
	 */
	public long queryShopIdByMembercardId(long member_card_id) {
		String sql = "SELECT shop_id FROM member_card WHERE id = " + member_card_id;
		return this.parseShopId(sql);
	}
	
	/**
	 * 通过课程ID获取机构ID
	 * @param c_id
	 * @return
	 */
	public long queryShopIdByCourseId(long c_id) {
		String sql = "SELECT shop_id FROM course WHERE id = " + c_id;
		return this.parseShopId(sql);
	}
	
	/**
	 * 通过课室ID获取机构ID
	 * @param cr_id
	 * @return
	 */
	public long queryShopIdByClassroomId(long cr_id) {
		String sql = "SELECT shop_id FROM classroom WHERE id = " + cr_id;
		return this.parseShopId(sql);
	}
	
	/**
	 * 通过排课ID查询机构ID
	 * @param cp_id
	 * @return
	 */
	public long queryShopIdByCoursePlanId(long cp_id) {
		String sql = "SELECT shop_id FROM courseplan WHERE id = " + cp_id;
		return this.parseShopId(sql);
	}
	
	/**
	 * 判断机构名字是否重复
	 * @param name 机构名字
	 */
	public boolean isShopNameRepeat(String name) {
		boolean isShopNameRepeat = false;
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "select * from shop where name = '" + name + "'";
		list = helper.query(sql);
		if (list.size() != 0) {
			isShopNameRepeat = true;
		}
		return isShopNameRepeat;
	}
	
	
	/**
	 * 判断机构是否已被删除
	 * @param shop_id 机构的id
	 */
	public boolean isDeleted(long shop_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>> ();
		boolean isDel = false;
		String sql = "select * from shop where id = " + shop_id + " and del = 0";
		list = helper.query(sql);
		int del = list.size();
		if (del == 0) {
			isDel = true;
		}
		return isDel;
	}
	
	/**
	 * 创建机构
	 * @param name 机构名字
	 * @param introduction 机构简介
	 */
	public boolean createShop(String name,String introduction) {
		String sql = "insert into shop (name,del,introduction) values('" + name +"'," + 0 + ",'" + introduction + "')";
		boolean created = false;
		try {
			created = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (created == false) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 判断舞馆是否已经被删除
	 * @param login_name 登录名，会员或舞馆管理者均可
	 * @param type 类型，1为会员，2为舞馆管理者
	 */
	public boolean isDeleted(String login_name,int type) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		boolean isShopDeleted = false;
		switch (type) {
		case 1:
			String sql = "select * from shop where id = (select shop_id from member where login_name  = '"
					+ login_name + "') and del = 0";
			list = helper.query(sql);
		case 2:
			String sql_search_by_shopmember = "select * from shop where id = (select shop_id from shopmember where user_name  = '"
					+ login_name + "') and del = 0";
			list = helper.query(sql_search_by_shopmember);
		}
		if (list.size() == 0) {
			isShopDeleted = true;
		}
		return isShopDeleted;
	}
	
	/**
	 * 判断机构是否存在
	 * @param s_id
	 * @return
	 */
	public boolean isExist(long s_id) {
		String sql = "SELECT * FROM shop WHERE id = " + s_id;
		if (helper.query(sql).size() == 0) {
			return false;
		}
		return true;
	}
	
	private long parseId(String sql) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list = helper.query(sql);
		System.out.println(list.size());
		if (list.size() == 0) {
			return 0;
		}
		return MethodTool.toLong(list, "id");
	}
	
	private long parseShopId(String sql) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list = helper.query(sql);
		if (list.size() == 0) {
			return 0;
		}
		return MethodTool.toLong(list, "shop_id");
	}


}
