package com.alfred.Sailfish.app.Service;

import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class ShopService {
	
	private ShopDAO shopDAO = new ShopDAO();
	
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
		if (isExist == false) {
			boolean created = shopDAO.createShop(name, intro);
			if (created == true) {
				long id = shopDAO.queryShopByName(name);
				return Reference.dataprefix + String.valueOf(id) + Reference.datasuffix;
			}else {
				return MethodTool.qr(Reference.EXE_FAIL);
			}
		}else {
			return MethodTool.qr(Reference.DUPLICATE);
		}
	}
	
	public String queryShopIdByShopmemberId(long sm_id) {
		long s_id = shopDAO.queryShopByShopmemberId(sm_id);
		System.out.println(s_id);
		return MethodTool.tfs(Reference.dataprefix + String.valueOf(s_id) + Reference.datasuffix);
	}
	
}
