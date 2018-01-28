package com.alfred.Sailfish.app.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.DAO.ShopmemberDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class ShopmemberService {
	
	private ShopmemberDAO shopmemberDAO = new ShopmemberDAO();
	private ShopDAO shopDao = new ShopDAO();
	
	public ShopmemberService() {
	}
	
	/**
	 * 添加教师
	 * @param shop_id
	 * @param shopmember_id
	 * @param name
	 * @param user_name
	 * @param type
	 * @param password
	 * @return
	 */
	public String addShopmember(long shop_id,long shopmember_id,String name,String user_name,int type,String password) {
		boolean isAdded = false;
		boolean isLoginNameExist = false;
		isLoginNameExist = shopmemberDAO.isLoginNameExist(user_name);
		if (!isLoginNameExist) {
			isAdded = shopmemberDAO.addNewShopmember(shop_id, shopmember_id, type, name, user_name, password);
			if (isAdded) {
				return MethodTool.qr(Reference.dataprefix + shopmemberDAO.queryIdByUserName(user_name) + Reference.datasuffix);
			} else {
				return MethodTool.qr(Reference.EXE_FAIL);
			}
		} else {
			return MethodTool.qr(Reference.DUPLICATE);
		}
	}

	/**
	 * 删除教师
	 * @param id
	 * @param lmu_id
	 * @return
	 */
	public String deleteShopmember(long id,long lmu_id) {
		if (shopDao.queryShopByShopmemberId(id) != shopDao.queryShopByShopmemberId(lmu_id)) {
			return MethodTool.qr(Reference.INST_NOT_MATCH);
		}
		boolean is_exe = shopmemberDAO.deleteMember(lmu_id,id);
		if (!is_exe) {
			return MethodTool.qr(Reference.EXE_FAIL);
		}
		return MethodTool.qr(Reference.EXE_SUC);
	}

	/**
	 * 修改密码
	 * @param sm_id
	 * @param password
	 * @return
	 */
	public String modifyPassword(long sm_id,String password) {
		if(shopmemberDAO.isDel(sm_id)) {
			return MethodTool.qr(Reference.NSR);
		}
		boolean isModified = false;
		try {
			isModified = shopmemberDAO.modifyPassword(sm_id, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isModified) {
			return MethodTool.qr(Reference.EXE_SUC);
		}else{
			return MethodTool.qr(Reference.EXE_FAIL);
		}
	}

	public String modifyInfo(long sm_id,String name,long lmu_id,int new_type) throws SQLException{
		if (shopDao.queryShopByShopmemberId(sm_id) != shopDao.queryShopByShopmemberId(lmu_id)) {
			return MethodTool.qr(Reference.INST_NOT_MATCH);
		}
		if (!shopmemberDAO.modifyInfo(sm_id, lmu_id, name,new_type)) {
			return MethodTool.qr(Reference.EXE_FAIL);
		}
		return MethodTool.qr(Reference.EXE_SUC);
	}

	/**
	 * 查询教师列表
	 * @param shop_id
	 * @return
	 */
	public String queryShopmemberList(long shop_id,int type){
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>> ();
		list = shopmemberDAO.queryShopmemberList(shop_id,type);
		if (list.size()==0) {
			return MethodTool.qr(Reference.NSR);
		}
		return MethodTool.tfc(list);
	}

	/**
	 * 查询教师详情
	 * @param sm_id
	 * @param s_id
	 * @return
	 */
	public String queryShopmemberDetail(long sm_id,long s_id){
		if (shopmemberDAO.isDel(sm_id)) {
			return MethodTool.qr(Reference.NSR);
		}
		if (shopDao.queryShopByShopmemberId(sm_id) != s_id) {
			return MethodTool.qr(Reference.INST_NOT_MATCH);
		}
		ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
		arrayList = shopmemberDAO.queryDetail(sm_id);
		if (arrayList.size()==0) {
			return MethodTool.tfs(Reference.NSR);
		}else {
			return MethodTool.tfc(arrayList);
		}
	}

	/**
	 * 教师登录检查
	 * @param user_name
	 * @param password
	 * @return
	 */
	public String loginCheck(String user_name,String password) {
		long id = shopmemberDAO.queryIdByUserName(user_name);
		if (!shopmemberDAO.isLoginNameExist(user_name)) {
			return MethodTool.qr(Reference.NSR);
		}
		if (shopmemberDAO.isDel(id)) {
			return MethodTool.qr(Reference.NSR);
		}
		if (shopmemberDAO.isLoginNameAndPasswordMatch(user_name, password)) {
			return MethodTool.qr(Reference.dataprefix + String.valueOf(id) + Reference.datasuffix);
		}
		return MethodTool.qr(Reference.NOT_MATCH);
	}
	
	/**
	 * 注册过程
	 * @param shop_id
	 * @param login_name
	 * @param name
	 * @param password
	 * @return
	 */
	public String register(long shop_id,String login_name,String name,String password) {
		boolean is_Exist = false;
		is_Exist = shopmemberDAO.isLoginNameExist(login_name);
		if (!is_Exist) {
			boolean isRegistered = shopmemberDAO.addNewShopmember(shop_id, 1, name, login_name, password);
			if (isRegistered) {
				return MethodTool.qr(Reference.EXE_SUC);
			}else {
				return MethodTool.qr(Reference.EXE_FAIL);
			}
		}else {
			return MethodTool.qr(Reference.DUPLICATE);
		}
	}
	
}
