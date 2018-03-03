package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.SQLHelper;

public class CourseSupportedCardDAO {
	
	private SQLHelper helper = new SQLHelper();
	
	public CourseSupportedCardDAO() {
	}
	
	/**
	 * pass
	 * 新增受支持的会员卡
	 * @param course_id
	 * @param card_id
	 * @param price
	 * @return
	 */
	public boolean addSupportedCard(long course_id,long card_id,int price) {
		boolean isAdded = false;
		String sql = "INSERT INTO course_supportedcard (course_id,card_id,price,del) "
				+ "VALUES (" + course_id + "," + card_id + "," + price + "," + "0)";
		try {
			isAdded = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdded;
	}
	
	/**
	 * 新增受支持的有效期卡
	 * @param course_id
	 * @param card_id
	 * @return
	 */
	public boolean addSupportedTimeCard(long course_id,long card_id) {
		boolean isAdded = false;
		String sql = "INSERT INTO course_supportedcard (course_id,card_id,del) "
				+ "VALUES (" + course_id + "," + card_id + "," + "0)";
		try {
			isAdded = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdded;
	}
	
	/**
	 * pass
	 * 移除受支持的受支持的会员卡
	 * @param course_id
	 * @param card_id
	 * @return
	 */
	public boolean removeSupportedCard(long course_id,long card_id) {
		boolean isDel = false;
		String sql = "DELETE FROM course_supportedcard WHERE course_id = " + course_id + " AND card_id = " + card_id;
		try {
			isDel = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isDel;
	}
	
	/**
	 * pass
	 * 修改受支持的会员卡价格
	 * @param course_id
	 * @param card_id
	 * @param price
	 * @return
	 */
	public boolean modifySupportedCard(long course_id,long card_id,int price) {
		boolean isModified = false;
		String sql = "UPDATE course_supportedcard SET price = " + price + " WHERE card_id = " + card_id + " AND course_id = " + course_id;
		try {
			isModified = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isModified;
	}
	
	/**
	 * pass
	 * 查询受支持的会员卡列表
	 * @param c_id
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> querySupportedCards(long s_id,long c_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql;
		if (c_id == 0) {
			//查询全部课程
			sql = "SELECT truncate(c.id,0) c_id,trim(c.name) card_name,truncate(cs.price,0) cs_price,truncate(ce.id,0) course_id FROM course_supportedcard cs "
					+ "LEFT JOIN card c ON cs.card_id = c.id "
					+ "LEFT JOIN course ce ON cs.course_id=ce.id "
					+ "WHERE cs.del = 0 AND c.shop_id=" + s_id;
		}else {
			//查询具体课程所支持的会员卡
			sql = "SELECT truncate(c.id,0) c_id,trim(c.name) card_name,truncate(cs.price,0) cs_price,trim(ce.name) course_name,c.type FROM course_supportedcard cs "
					+ "LEFT JOIN card c ON cs.card_id = c.id "
					+ "LEFT JOIN course ce ON cs.course_id=ce.id "
					+ "WHERE cs.del = 0 AND cs.course_id = " + c_id + " AND c.shop_id = " + s_id;
		}
		list = helper.query(sql);
		return list;
	}
	
	/**
	 * pass
	 * 查询课程价格
	 * @param course_id
	 * @param card_id
	 * @return
	 */
	public int queryPrice (long course_id,long card_id) {
		String sql = "SELECT price FROM course_supportedcard "
				+ "WHERE course_id = " + course_id + " AND card_id = " + card_id;
		return MethodTool.toInt(helper.query(sql), "price");
	}
	
	/**
	 * 通过课程ID和卡ID查询是否存在
	 * @param ce_id
	 * @param cd_id
	 * @return
	 */
	public boolean isExist(long ce_id,long cd_id) {
		String sql = "SELECT * FROM course_supportedcard "
				+ "WHERE course_id = " + ce_id + " AND card_id = " + cd_id;
		if (helper.query(sql).size() == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 通过ID查询卡是否存在
	 * @param id
	 * @return
	 */
	public boolean isExist(long id) {
		String sql = "SELECT * FROM course_supportedcard "
				+ "WHERE id = " + id;
		if(helper.query(sql).size() == 0) {
			return false;
		}
		return true;
	}

}
