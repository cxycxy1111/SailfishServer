package com.alfred.Sailfish.app.ShopmemberService;

import com.alfred.Sailfish.app.DAO.ShopConfigDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopService {
	
	private ShopDAO shopDAO = new ShopDAO();
	private ShopConfigDAO shopConfigDAO = new ShopConfigDAO();
	
	public ShopService(){
		
	}

	/**
	 * 注册舞馆
	 * @param name
	 * @param intro
	 * @return
	 */
	public String register(String name,String intro) {
		boolean isExist = shopDAO.isShopNameRepeat(name);
		if (!isExist) {
			boolean created = shopDAO.createShop(name, intro);
			if (created) {
				long id = shopDAO.queryShopByName(name);
				shopConfigDAO.initParamaterAfterShopCreated(id);
				return Reference.dataprefix + String.valueOf(id) + Reference.datasuffix;
			}else {
				return MethodTool.qr(Reference.EXE_FAIL);
			}
		}else {
			return MethodTool.qr(Reference.DUPLICATE);
		}
	}

	/**
	 * 通过教师ID获取机构ID
	 * @param sm_id
	 * @return
	 */
	public String queryShopIdByShopmemberId(long sm_id) {
		long s_id = shopDAO.queryShopByShopmemberId(sm_id);
		return MethodTool.tfs(Reference.dataprefix + String.valueOf(s_id) + Reference.datasuffix);
	}

	/**
	 * 通过机构ID获取机构详情
	 * @param id
	 * @return
	 */
	public String queryShopDetail(long id) {
		ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<HashMap<String,Object>>();
		mapArrayList = shopDAO.queryShopDetailByShopId(id);
		if (mapArrayList.size() == 0) {
			return Reference.EMPTY_RESULT;
		}else {
			return MethodTool.tfc(mapArrayList);
		}
	}

	public String modifyShopInfo(long id,String sm_type,String name,String remark) {
		if (!sm_type.equals("1")) {
			return Reference.AUTHORIZE_FAIL;
		}
		if (shopDAO.modifyShopInfo(id,name,remark)) {
			return Reference.EXE_SUC;
		}
		return Reference.EXE_FAIL;
	}
	
}
