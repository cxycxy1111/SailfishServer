package com.alfred.Sailfish.app.ShopmemberService;

import java.util.ArrayList;
import java.util.HashMap;
import com.alfred.Sailfish.app.DAO.CardDAO;
import com.alfred.Sailfish.app.DAO.ShopConfigDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class CardService {
	
	private CardDAO cardDAO = new CardDAO();
	private ShopDAO shopDAO = new ShopDAO();
	private ShopConfigDAO shopConfigDAO = new ShopConfigDAO();
	
	public CardService() {
	}
	
	/**
	 * 新增卡
	 * @param shop_id
	 * @param name
	 * @param shopmember_id
	 * @param type
	 * @param price
	 * @param balance
	 * @param start_time
	 * @param expired_time
	 * @return
	 */
	public String addCard(String sm_type,long shop_id,String name,long shopmember_id,int type,int price,int balance,String start_time,String expired_time){
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_CARD_TYPE,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		if (cardDAO.isCardNameRepeat(shop_id, name)) {
			return qr(Reference.DUPLICATE);
		}
		boolean isAdded = cardDAO.addNewCard(shop_id, shopmember_id, name, type, price, balance, start_time, expired_time);
		if (isAdded) {
			long id = cardDAO.queryIdByName(name, shop_id);
			return qr(Reference.id_prefix + id + Reference.datasuffix);
		}
		return qr(Reference.EXE_SUC);
	}
	
	/**
	 * 查询卡列表
	 * @param shop_id
	 * @return
	 */
	public String queryCardList(String sm_type,long shop_id,int type) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_VIEW_CARD_TYPE,shop_id).contains(String.valueOf(sm_type))) {
			return Reference.AUTHORIZE_FAIL;
		}
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list = cardDAO.queryList(shop_id,type);
		if (list.size() == 0) {
			return qr(Reference.NSR);
		}
		return MethodTool.tfc(list);
	}

	/**
	 * 查询卡明细
	 * @param c_id
	 * @return
	 */
	public String queryCardDetail(String sm_type,long s_id,long c_id) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_VIEW_CARD_TYPE,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		if (cardDAO.isDel(c_id)) {
			return MethodTool.qr(Reference.NSR);
		}
		list = cardDAO.queryDetail(c_id);
		if(list.size() == 0) {
			return MethodTool.qr(Reference.NSR);
		}
		return MethodTool.tfc(list);
	}
	
	/**
	 * 修改卡
	 * @param id
	 * @param name
	 * @param shopmember_id
	 * @param price
	 * @param balance
	 * @param start_time
	 * @param expired_time
	 * @return
	 */
	public String modifyCard(long s_id,String sm_type,long id,String name,long shopmember_id,int price,int balance,String start_time,String expired_time) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_CARD_TYPE,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		if (cardDAO.isDel(id)) {
			return qr(Reference.NSR);
		}
		boolean isModified = cardDAO.modifyCard(id,shopmember_id, name, price, balance, start_time, expired_time);
		if (isModified) {
			return qr(Reference.EXE_SUC);
		}
		return qr(Reference.EXE_FAIL);
	}
	
	/**
	 * 删除卡
	 * @param card_id
	 * @param shopmember_id
	 * @return
	 */
	public String removeCard(long s_id,String sm_type,long card_id,long shopmember_id) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_CARD_TYPE,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		if (cardDAO.removeCard(card_id, shopmember_id)) {
			return qr(Reference.EXE_SUC);
		}
		return qr(Reference.EXE_FAIL);
	}
	
	/**
	 * 快速转换String
	 * @param s
	 * @return
	 */
	private String qr(String s) {
		return MethodTool.tfs(s);
	}
	
}
