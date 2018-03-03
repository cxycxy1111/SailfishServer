package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.SQLHelper;

public class MemberCardDAO {
	
	private SQLHelper helper = new SQLHelper();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private CardDAO cardService = new CardDAO();
	
	public MemberCardDAO() {
		
	}
	
	/**
	 * pass
	 * 新增会员卡
	 * @param member_id 会员ID
	 * @param shop_id 机构ID
	 * @param card_id 卡ID
	 * @param shopmember_id 教师ID
	 * @param balance 余额
	 * @param start_time 生效时间
	 * @param expired_time 失效时间
	 * @return
	 */
	public boolean addMemberCard(long member_id,long shop_id,
			long card_id,long shopmember_id,int balance,
			String start_time,String expired_time) {
		int type = cardService.queryTypeById(card_id);
		boolean isAdded = false;
		String sql = "INSERT INTO member_card "
				+ "(member_id,shop_id,card_id,creator,last_modify_user,del,type,balance,"
				+ "start_time,expired_time,create_time,last_modify_time) VALUES (" 
				+ member_id + ","
				+ shop_id + ","
				+ card_id +","
				+ shopmember_id + ","
				+ shopmember_id + ",0,"
				+ type + ","
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
	
	/**
	 * pass
	 * 判断会员是否已经购买了某张会员卡
	 * @param member_id 会员ID
	 * @param card_id 卡ID
	 * @return
	 */
	public boolean isMemberCardHasExist(long member_id,long card_id) {
		boolean isExist = false;
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT * FROM member_card WHERE del = 0 AND member_id = " + member_id + " AND card_id = " + card_id ;
		list = helper.query(sql);
		if(list.size() != 0) {
			isExist = true;
		} 
		return isExist;
	}
	
	/**
	 * pass
	 * 检查会员卡余额是否充足，适用于余额卡、次卡
	 * @param membercard_id
	 * @param num
	 * @return
	 */
	public boolean isBalanceEnough(long membercard_id,int num){
		if (this.queryCurrentBalanceByMemberCardId(membercard_id) - num < 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * pass
	 * 检查会员卡是否被删，true为已被删
	 * @param membercard_id 会员卡ID
	 * @return
	 */
	public boolean isDel(long membercard_id) {
		String sql = "SELECT * FROM member_card WHERE del = 0 AND id = " + membercard_id;
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list = helper.query(sql);
		if (list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * pass
	 * 查询是否过期
	 * @return
	 */
	public boolean isExpired(long mc_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map = new HashMap<String,Object>();
		boolean isExpired = false;
		String sql = "SELECT expired_time FROM member_card WHERE id = " + mc_id;
		list = helper.query(sql);
		if (list.size() != 0) {
			map = list.get(0);
			Object o = map.get("expired_time");
			String s = String.valueOf(o);
			Date d1 = new Date();
			Date current_date = new Date();
			try {
				d1 = sdf.parse(s);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			isExpired = d1.before(current_date);
		}
		return isExpired;
	}

	/**
	 * pass
	 * 查询过期时间
	 * @param membercard_id
	 * @return
	 */
	public String queryExpiredTime(long membercard_id) {
		String sql = "SELECT expired_time FROM member_card WHERE id = " + membercard_id;
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list = helper.query(sql);
		return MethodTool.toString(list, "expired_time");
	}
	
	/**
	 * pass
	 * 延长过期时间，适用于余额卡、次卡、有效期卡
	 * @param id
	 * @param last_modify_user
	 * @param expired_time
	 * @return
	 */
	public boolean updateExpiredTime(long id,long last_modify_user,String expired_time) {
		boolean isUpdated = false;
		String sql = "UPDATE member_card SET expired_time = '" + expired_time 
				+ "',last_modify_user=" + last_modify_user + " WHERE id = " +id;
		try {
			isUpdated = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUpdated;
	}
	
	/**
	 * pass
	 * 会员卡充值，适用于次卡、余额卡
	 * @param id
	 * @param increasement
	 * @param last_modify_user
	 * @return
	 */
	public boolean updateBalancePlus(long id,int increasement,String new_invalid_date,long last_modify_user) {
		boolean isCharged = false;
		int currentBalance = this.queryCurrentBalanceByMemberCardId(id);
		int i = currentBalance + increasement;
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE member_card SET balance = " + i );
		if (new_invalid_date.length() != 0) {
			builder.append(",expired_time= '" + new_invalid_date + "'");
		}
		builder.append(",last_modify_user = " + last_modify_user
				+ ",last_modify_time = '" + sdf.format(new Date()) + "' WHERE id = " + id);
		String sql = builder.toString();
		try {
			if (helper.update(sql) == true) {
				isCharged = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isCharged;
	}
	
	/**
	 * pass
	 * 扣费，适用于余额卡、次卡
	 * @param membercard_id 会员卡ID
	 * @param price 扣费金额/次数
	 * @return
	 */
	public boolean updateBalanceReduce(long membercard_id,long lmu_id,int price,String invalid_date) {
		boolean isOk = false;
		int current_balance = this.queryCurrentBalanceByMemberCardId(membercard_id) - price;

		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE member_card SET balance = " + current_balance + ",last_modify_user = " + lmu_id);
		if (invalid_date.length() != 0) {
			builder.append(",expired_time='").append(invalid_date).append("'");
		}
		builder.append(" WHERE id = " + membercard_id);

		String sql = builder.toString();
		try {
			isOk = helper.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isOk;
	}
	
	/**
	 * pass
	 * 通过会员卡ID获取会员卡余额，适用于余额卡、次卡
	 * @param id 会员卡ID
	 * @return
	 */
	public int queryCurrentBalanceByMemberCardId(long id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT balance FROM member_card WHERE id = " + id;
		list = helper.query(sql);
		if (list.size() != 0) {
			int currentBalance = Integer.valueOf(String.valueOf(list.get(0).get("balance")));
			return currentBalance;
		}else{ 
			return 0;
		}
	}
	
	/**
	 * 通过会员ID和卡ID获取余额，适用于余额卡、次卡
	 * @param m_id
	 * @param c_id
	 * @return
	 */
	public int queryCurrentBalanceByMIdAndCId(long m_id,long c_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT balance FROM member_card WHERE member_id = " + m_id + " AND card_id = " + c_id;
		list = helper.query(sql);
		if (list.size() != 0) {
			int currentBalance = Integer.valueOf(String.valueOf(list.get(0).get("balance")));
			return currentBalance;
		}else{ 
			return 0;
		}
	}
	
	/**
	 * pass
	 * 通过会员ID获取会员卡列表
	 * @param member_id
	 * @return 会员卡ID，会员卡名称，会员卡余额
	 */
	public ArrayList<HashMap<String,Object>> queryListByMemberId(long member_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT mc.id,c.name,mc.balance,c.type,mc.card_id FROM member_card mc "
				+ "LEFT JOIN card c ON mc.card_id = c.id "
				+ "WHERE mc.member_id = " + member_id + " AND mc.del = 0";
		list = helper.query(sql);
		return list;
	}
	
	/**
	 * pass
	 * 通过会员ID获取简单的，会员旗下所有的会员卡
	 * @param member_id
	 * @return 会员卡ID，卡名
	 */
	public ArrayList<HashMap<String,Object>> querySimpleListByMemberId(long member_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT mc.id,c.name FROM member_card mc "
				+ "LEFT JOIN card c ON mc.card_id = c.id "
				+ "WHERE mc.del = 0 AND mc.member_id = " + member_id;
		list = helper.query(sql);
		return list;
	}
	
	/**
	 * pass
	 * 通过会员卡ID获取会员卡详情
	 * @param member_card_id
	 * @return 会员卡ID，会员ID，卡ID，类型，余额，生效时间，过期时间
	 */
	public ArrayList<HashMap<String,Object>> queryDetailById(long member_card_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT mc.id,mc.member_id,mc.card_id,trim(m.name) member_name,trim(c.name) card_name,mc.type,mc.balance,mc.start_time,mc.expired_time FROM member_card mc "
				+ "LEFT JOIN card c ON mc.card_id = c.id "
				+ "LEFT JOIN member m ON mc.member_id = m.id "
				+ "WHERE mc.id = " + member_card_id;
		list = helper.query(sql);
		return list;
	}
	
	/**
	 * 通过卡ID和会员ID获取会员卡详情
	 * @param m_id
	 * @param c_id
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryDetailByMIdAndCId(long m_id,long c_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "SELECT id,member_id,card_id,type,balance,start_time,expired_time FROM member_card "
				+ "WHERE member_id = " + m_id + " AND card_id = " + c_id;
		list = helper.query(sql);
		return list;
	}
	
	/**
	 * 删除会员卡，成功返回true
	 * @param id 会员卡ID
	 * @return
	 */
	public boolean deleteMemberCardById(long id,long lmu_id) {
		boolean isExecuted = false;
		String t = sdf.format(new Date());
		String sql = "UPDATE member_card SET del = 1 WHERE id = " + id + 
				",last_modify_user = " + lmu_id + 
				",last_modify_time = '" + t + "'";
		try {
			if (helper.update(sql) == true) {
				isExecuted = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isExecuted;
	}
	
}
