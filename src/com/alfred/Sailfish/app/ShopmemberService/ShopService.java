package com.alfred.Sailfish.app.ShopmemberService;

import com.alfred.Sailfish.app.DAO.ShopConfigDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

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
	
	public String queryShopIdByShopmemberId(long sm_id) {
		long s_id = shopDAO.queryShopByShopmemberId(sm_id);
		return MethodTool.tfs(Reference.dataprefix + String.valueOf(s_id) + Reference.datasuffix);
	}
	
}
