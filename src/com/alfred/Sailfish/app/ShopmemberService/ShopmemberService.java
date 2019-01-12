package com.alfred.Sailfish.app.ShopmemberService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.alfred.Sailfish.app.DAO.ShopConfigDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.DAO.ShopmemberDAO;
import com.alfred.Sailfish.app.DAO.ShopmemberLoginLogDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class ShopmemberService {
	
	private ShopmemberDAO shopmemberDAO = new ShopmemberDAO();
	private ShopDAO shopDao = new ShopDAO();
	private ShopConfigDAO shopConfigDAO = new ShopConfigDAO();
	private ShopmemberLoginLogDAO shopmemberLoginLogDAO = new ShopmemberLoginLogDAO();
	
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
	public String addShopmember(long shop_id,String sm_type,long shopmember_id,String name,String user_name,int type,String password) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_TEACHER,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		boolean isAdded = false;
		boolean isLoginNameExist = false;
		isLoginNameExist = shopmemberDAO.isLoginNameExist(user_name);
		if (!isLoginNameExist) {
			isAdded = shopmemberDAO.addNewShopmember(shop_id, shopmember_id, type, name, user_name, password);
			if (isAdded) {
				ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
				HashMap<String,String> hashMap = new HashMap<>();
				hashMap.put("id",String.valueOf(shopmemberDAO.queryIdByUserName(user_name)));
				arrayList.add(hashMap);
				return MethodTool.tfc(arrayList);
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
	public String deleteShopmember(long shop_id,String sm_type,long id,long lmu_id) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_TEACHER,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
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
	public String modifyPassword(long shop_id,String sm_type,long sm_id,String password) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_TEACHER,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
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

	public String modifyInfo(long shop_id,String sm_type,long sm_id,String name,long lmu_id,int new_type) throws SQLException{
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_TEACHER,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
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
	public String queryShopmemberList(long shop_id,String sm_type,int type){
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_VIEW_TEACHER,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
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

	public String queryShopmemberDetail(long sm_id,long s_id,String sm_type){
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_VIEW_TEACHER,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
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
	public String loginCheck(String request_time,
							 String ip_addredss,
							 String system_version,
							 String system_model,
							 String device_brand,
							 String imei,
							 String app_version,
							 String user_name,
							 String password) {
		long id = shopmemberDAO.queryIdByUserName(user_name);
		if (!shopmemberDAO.isLoginNameExist(user_name)) {
			shopmemberLoginLogDAO.insert(request_time,0,ip_addredss,user_name,password,system_version,system_model,device_brand,imei,app_version);
			return MethodTool.qr(Reference.NSR);
		}
		if (shopmemberDAO.isDel(id)) {
			shopmemberLoginLogDAO.insert(request_time,0,ip_addredss,user_name,password,system_version,system_model,device_brand,imei,app_version);
			return MethodTool.qr(Reference.NSR);
		}
		boolean isMatch = false;
		isMatch = shopmemberDAO.isLoginNameAndPasswordMatch(user_name, password);
		if (isMatch) {
			long sm_id = shopmemberDAO.queryIdByUserName(user_name);
			shopmemberLoginLogDAO.insert(request_time,1,ip_addredss,user_name,password,system_version,system_model,device_brand,imei,app_version);
			return MethodTool.tfc(shopmemberDAO.queryEssentiailDataAfterLogin(sm_id));
		}
		shopmemberLoginLogDAO.insert(request_time,0,ip_addredss,user_name,password,system_version,system_model,device_brand,imei,app_version);
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
