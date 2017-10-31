package com.alfred.Sailfish.app.Service;

import java.util.ArrayList;
import java.util.HashMap;
import com.alfred.Sailfish.app.DAO.MemberDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class MemberService {
	
	private MemberDAO memberDAO = new MemberDAO();
	
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
	public String addMember(String login_name,long shop_id,long shopmember_id,
			String name,String password,String birthday,String phone,String im) {
		if (!memberDAO.isLoginNameExist(login_name, shop_id)) {
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
	
	public String resetPassword(long sm_id,long m_id,String password) {
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
	public String queryMemberList(long shop_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list = memberDAO.queryList(shop_id);
		return MethodTool.tfc(list);
	}
	
	/**
	 * 查询会员详情
	 * @param shop_id
	 * @param member_id
	 * @return
	 */
	public String queryMemberDetail(long shop_id,long member_id) {
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
	public String modifyMember(long member_id,long shopmember_id,String name,String birthday,String phone,String im) {
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
	public String removeMember(long m_id,long sm_id) {
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
	
}
