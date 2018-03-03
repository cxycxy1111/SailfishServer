package com.alfred.Sailfish.app.ShopmemberService;

import java.util.ArrayList;
import java.util.HashMap;
import com.alfred.Sailfish.app.DAO.MemberDAO;
import com.alfred.Sailfish.app.DAO.MemberLoginLogDAO;
import com.alfred.Sailfish.app.DAO.ShopConfigDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class MemberService {
	
	private MemberDAO memberDAO = new MemberDAO();
	private ShopConfigDAO shopConfigDAO = new ShopConfigDAO();
	private MemberLoginLogDAO memberLoginLogDAO = new MemberLoginLogDAO();
	
	public MemberService(){
	}
	
	/**
	 * 新增会员
	 * @param login_name
	 * @param shop_id
	 * @param shopmember_id
	 * @param name
	 * @param password
	 * @param birthday
	 * @param phone
	 * @param im
	 * @return
	 */
	public String addMember(String sm_type,String login_name,long shop_id,long shopmember_id,
			String name,String password,String birthday,String phone,String im) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_MEMBER,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		if (!memberDAO.isLoginNameExist(login_name)) {
			boolean isAdded = memberDAO.addNewMmember(shop_id, shopmember_id, name, login_name,
					password, birthday, phone, im);
			if(isAdded) {
				return MethodTool.qr(Reference.EXE_SUC);
			}else {
				return MethodTool.qr(Reference.EXE_FAIL);
			}
		}else {
			return MethodTool.qr(Reference.DUPLICATE);
		}
	}

	/**
	 * 重设密码
	 * @param shop_id
	 * @param sm_type
	 * @param sm_id
	 * @param m_id
	 * @param password
	 * @return
	 */
	public String resetPassword(long shop_id,String sm_type,long sm_id,long m_id,String password) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_MEMBER,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		boolean b = false;
		if(memberDAO.isDel(m_id)) {
			return MethodTool.qr(Reference.NSR);
		}
		b = memberDAO.resetPassword(m_id, sm_id, password);
		if (b) {
			return MethodTool.qr(Reference.EXE_SUC);
		}
		return MethodTool.qr(Reference.EXE_FAIL);
	}
	
	/**
	 * 查询会员列表
	 * @param shop_id
	 * @return
	 */
	public String queryMemberList(long shop_id,String sm_type) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_VIEW_MEMBER,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list = memberDAO.queryList(shop_id);
		if (list.size() == 0) {
			return MethodTool.qr(Reference.EMPTY_RESULT);
		}
		return MethodTool.tfc(list);
	}
	
	/**
	 * 查询会员详情
	 * @param shop_id
	 * @param member_id
	 * @return
	 */
	public String queryMemberDetail(long shop_id,String sm_type,long member_id) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_VIEW_MEMBER,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		boolean isDel = memberDAO.isDel(member_id);
		if (!isDel) {
			list = memberDAO.queryDetail(member_id, shop_id);
			if (list.size() == 0) {
				return MethodTool.qr(Reference.NSR);
			} else {
				return MethodTool.tfc(list);
			}
		} else {
			return MethodTool.qr(Reference.NSR);
		}
	}

	/**
	 * 修改会员
	 * @param member_id
	 * @param shopmember_id
	 * @param name
	 * @param birthday
	 * @param phone
	 * @param im
	 * @return
	 */
	public String modifyMember(long shop_id,String sm_type,long member_id,long shopmember_id,String name,String birthday,String phone,String im) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_MEMBER,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		if (memberDAO.isDel(member_id)) {
			return MethodTool.qr(Reference.NSR);
		}
		boolean isModifyOk = memberDAO.modifyMmember(member_id, shopmember_id, name, birthday, phone, im);
		if (isModifyOk) {
			return MethodTool.qr(Reference.EXE_SUC);
		} else { 
			return MethodTool.qr(Reference.EXE_FAIL);
		}	
	}
	
	/**
	 * 删除会员
	 * @param m_id
	 * @param sm_id
	 * @return
	 */
	public String removeMember(long shop_id,String sm_type,long m_id,long sm_id) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_MEMBER,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		boolean isDel = memberDAO.isDel(m_id);
		if (isDel) {
			return MethodTool.qr(Reference.NSR);
		} else {
			boolean isDelete = memberDAO.deleteMember(m_id, sm_id);
			if (isDelete) {
				return MethodTool.qr(Reference.EXE_SUC);
			}else{
				return MethodTool.qr(Reference.EXE_FAIL);
			}
		}
	}

	/**
	 * 登录检查
	 * @param login_name
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
							 String login_name,
							 String password) {
		if (memberDAO.isLoginNameExist(login_name)) {
			if (memberDAO.isLoginNameAndPasswordMatch(login_name,password)) {
				memberLoginLogDAO.insert(request_time,1,ip_addredss,login_name,password,system_version,system_model,device_brand,imei,app_version);
				String s = MethodTool.tfc(memberDAO.queryMemberIdByLoginName(login_name));
				return s;
			}
			memberLoginLogDAO.insert(request_time,0,ip_addredss,login_name,password,system_version,system_model,device_brand,imei,app_version);
			return Reference.NOT_MATCH;
		}
		memberLoginLogDAO.insert(request_time,0,ip_addredss,login_name,password,system_version,system_model,device_brand,imei,app_version);
		return Reference.NOT_MATCH;
	}

	/**
	 * 通过登录名查询会员资料
	 * @param login_name
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> queryMemberInfoByLoginName(String login_name) {
		ArrayList<HashMap<String,Object>> hashMap = new ArrayList<>();
		hashMap = memberDAO.queryMemberIdByLoginName(login_name);
		return hashMap;
	}
	
}
