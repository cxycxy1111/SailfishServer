package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.SQLHelper;

public class CardDAO {
	
	private SQLHelper helper = new SQLHelper();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public CardDAO() {
	}
	
	/**
	 * pass
	 * 新增卡
	 * @param shop_id 机构ID
	 * @param shopmember_id 教师ID
	 * @param name 卡名
	 * @param type 类型，1是余额卡，2是次卡，3是有限期卡
	 * @param price 售价
	 * @param balance 余额
	 * @param start_time 生效时间
	 * @param expired_time 失效时间
	 * @return
	 */
	public boolean addNewCard(long shop_id,long shopmember_id,String name,int type,int price,int balance,String start_time,String expired_time) {
		if(type == 3) {
			boolean isAdded = this.addPeriodCard(shop_id, shopmember_id, name, type, price, start_time, expired_time);
			return isAdded;
		} else {
			boolean isAdded = false;
			String sql = "INSERT INTO card (shop_id,creator_id,last_modify_user,name,type,price,del,balance,start_time,expired_time,create_time,last_modify_time) "
					+ "VALUES ("
					+ shop_id + "," 
					+ shopmember_id + "," 
					+ shopmember_id + ",'" 
					+ name + "'," 
					+ type + "," 
					+ price + ",0," 
					+ balance + ",'" 
					+ start_time + "','" 
					+ expired_time + "','" 
					+ sdf.format(new Date()) + "','" 
					+ sdf.format(new Date()) + "')";
			try {
				isAdded = helper.update(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return isAdded;
		
		}
		}
	
	/**
	 * pass
	 * 添加有效期卡
	 * @param shop_id
	 * @param shopmember_id
	 * @param name
	 * @param type
	 * @param price
	 * @param start_time
	 * @param expired_time
	 * @return
	 */
	public boolean addPeriodCard(long shop_id,long shopmember_id,String name,int type,int price,String start_time,String expired_time) {
		boolean isAdded = false;
		String sql = "INSERT INTO card (shop_id,creator_id,last_modify_user,name,type,price,del,start_time,expired_time,create_time,last_modify_time) "
				+ "VALUES ("
				+ shop_id + "," 
				+ shopmember_id + "," 
				+ shopmember_id + ",'" 
				+ name + "'," 
				+ type + "," 
				+ price + ",0,'" 
				+ start_time + "','" 
				+ expired_time + "','" 
				+ sdf.format(new Date()) + "','" 
				+ sdf.format(new Date()) + "')";
		try {
			isAdded = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdded;
	}
	
	
	
	/**
	 * pass
	 * 删除卡
	 * @param card_id 卡ID
	 * @param last_modify_user 创建者ID
	 */
	public boolean removeCard(long card_id,long last_modify_user) {
		String sql = "UPDATE card SET del = 1 , last_modify_user = " + last_modify_user + ",last_modify_time = '" + sdf.format(new Date()) + "' WHERE id = " + card_id;
		boolean isDeleted = false;
		try {
			if (helper.update(sql) == true) {
				isDeleted = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isDeleted;
	}
	
	/**
	 * pass
	 * 更新卡
	 * @param shopmember_id 修改人ID
	 * @param name 卡名
	 * @param price 售价
	 * @param balance 卡余额
	 * @param start_time 生效时间
	 * @param expired_time 失效时间
	 * @return
	 */
	public boolean modifyPeriodCard(long id,long shopmember_id,String name,int price,int balance,String start_time,String expired_time) {
		boolean isModified = false;
		String sql = "UPDATE card SET last_modify_user = " + shopmember_id 
				+ ",name = '" + name
				+ "',price = " + price
				+ ",balance = " + balance
				+ ",start_time = '"+ start_time 
				+ "',expired_time = '"+ expired_time 
				+ "',last_modify_time = '"+ sdf.format(new Date()) + "' WHERE id = " + id;
		try {
			isModified = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isModified;
	}

	/**
	 * pass
	 * 更新有效期卡卡
	 * @param shopmember_id 修改人ID
	 * @param name 卡名
	 * @param price 售价
	 * @param balance 卡余额
	 * @param start_time 生效时间
	 * @param expired_time 失效时间
	 * @return
	 */
	public boolean modifyCard(long id,long shopmember_id,String name,int price,int balance,String start_time,String expired_time) {
		boolean isModified = false;
		String sql = "UPDATE card SET last_modify_user = " + shopmember_id 
				+ ",name = '" + name
				+ "',price = " + price 
				+ ",balance = " + balance
				+ ",start_time = '"+ start_time 
				+ "',expired_time = '"+ expired_time 
				+ "',last_modify_time = '"+ sdf.format(new Date()) + "' WHERE id = " + id;
		try {
			isModified = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isModified;
	}
	
	/**
	 * pass
	 * 通过卡名获取卡的ID
	 * @param name 卡名
	 * @return
	 */
	public long queryIdByName(String name,long shop_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT id FROM card WHERE name = '" + name + "' AND shop_id = " + shop_id;
		list = helper.query(sql);
		if (list.size() == 0) {
			return 0;
		}else {
			return Long.parseLong(String.valueOf(list.get(0).get("id")));
		}
	}
	
	/**
	 * pass
	 * 通过卡ID获取卡名
	 * @param id 卡ID
	 * @return
	 */
	public String queryNameById(long id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT name FROM card WHERE id = " + id + "";
		list = helper.query(sql);
		return String.valueOf(list.get(0).get("name"));
	}

	/**
	 * 根据会员卡ID查询卡ID
	 * @param mc_id
	 * @return
	 */
	public long queryCardIdByMemberCardId(long mc_id){
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT card_id FROM member_card WHERE id = " + mc_id;
		list = helper.query(sql);
		return Long.parseLong(String.valueOf(list.get(0).get("card_id")));
	}
	
	/**
	 * 通过卡ID获取卡的类型
	 * @param card_id
	 * @return
	 */
	public int queryTypeById(long card_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT type FROM card WHERE id = " + card_id;
		list = helper.query(sql);
		if (list.size() != 0) {
			return MethodTool.toInt(list, "type");
		}else {
			return 0;
		}
		
	}

	/**
	 * pass
	 * 通过卡ID获取卡详情
	 * @param id 卡ID
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryDetail(long id) {
		String sql = "SELECT name,type,price,balance,start_time,expired_time FROM card WHERE id = " + id;
		return helper.query(sql);
	}
	
	/**
	 * pass
	 * 通过卡名、机构ID获取卡详情
	 * @param name 卡名
	 * @param shop_id 机构ID
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryDetail(String name,long shop_id) {
		String sql = "SELECT name,type,price,balance,start_time,expired_time FROM card WHERE shop_id = " + shop_id + " AND name = '" + name + "'";
		return helper.query(sql);
	}
	
	/**
	 * pass
	 * 获取卡列表
	 * @param shop_id 机构ID
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryList(long shop_id,int type) {
		String field = "id,name,type";
		return basicListQuery(field,shop_id,type);
	}

	/**
	 * pass
	 * 获取卡列表
	 * @param shop_id 机构ID
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryCardSelectList(long shop_id) {
		String field = "id,name,type,balance,start_time,expired_time,price";
		return basicListQuery(field,shop_id,0);
	}

	private ArrayList<HashMap<String,Object>> basicListQuery(String field,long shop_id,int type) {
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ").append(field).append(" FROM card WHERE shop_id = " + shop_id + " ");
        if (type != 0) {
            builder.append("AND type=" + type + " ");
        }
        builder.append("AND del = 0 ORDER BY type");
        list = helper.query(builder.toString());
        return list;
    }
	
	/**
	 * pass
	 * 检查卡名是否重复
	 * @param shop_id 机构ID
	 * @param name 卡名
	 * @return
	 */
	public boolean isCardNameRepeat(long shop_id,String name){
		//默认未重复
		boolean isRepeat = false;
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT * FROM card WHERE shop_id = " + shop_id + " AND name = '" + name + "' AND del = 0";
		list = helper.query(sql);
		if(list.size() != 0) {
			isRepeat = true;
		}
		return isRepeat;
	}
	
	/**
	 * 检查卡ID是否存在
	 * @param cd_id
	 * @return
	 */
	public boolean isExist(long cd_id) {
		String sql = "SELECT * FROM card WHERE id = " + cd_id;
		if(helper.query(sql).size() != 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查卡的删除状态
	 * @param id 卡ID
	 * @return
	 */
	public boolean isDel(long id) {
		//默认未删除
		boolean isDel = false;
		String sql = "SELECT * FROM card WHERE del = 1 AND id = " + id;
		if (helper.query(sql).size() != 0) {
			isDel = true;
		}
		return isDel;
	}
}