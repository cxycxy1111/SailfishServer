package com.alfred.Sailfish.app.Service;

import java.util.ArrayList;
import java.util.HashMap;

import com.alfred.Sailfish.app.DAO.CardDAO;
import com.alfred.Sailfish.app.DAO.CourseDAO;
import com.alfred.Sailfish.app.DAO.CourseSupportedCardDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class CourseSupportedCardService {
	
	public CourseSupportedCardDAO courseSupportedCardDAO = new CourseSupportedCardDAO();
	public ShopDAO shopDAO = new ShopDAO();
	public CardDAO cardDAO = new CardDAO();
	public CourseDAO courseDAO = new CourseDAO();
	
	/**
	 * 查询受支持的会员卡列表
	 * @param c_id
	 * @return
	 */
	public String query(long c_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list = courseSupportedCardDAO.querySupportedCards(c_id);
		if (list.size() == 0) {
			return MethodTool.qr(Reference.EMPTY_RESULT);
		}
		return MethodTool.tfc(list);
	}
	
	/**
	 * 修改受支持的会员卡列表
	 * @param type
	 * @param course_id
	 * @param card_id
	 * @param price
	 * @return
	 */
	public String modify(int type,long course_id,long card_id,int price) {
		if (cardDAO.isExist(card_id) == false | courseDAO.isExist(course_id) == false) {
			return MethodTool.qr(Reference.NSR);
		}
		if (shopDAO.queryShopIdByCourseId(course_id) != shopDAO.queryShopIdByCardId(card_id)) {
			return MethodTool.qr(Reference.INST_NOT_MATCH);
		}
		switch (type) {
		case Reference.TYPE_OPERATE_ADD :
			boolean isAdded = false;
			if (courseSupportedCardDAO.isExist(course_id, card_id) == true) {
				return MethodTool.qr(Reference.DUPLICATE);
			}
			if(cardDAO.queryTypeById(card_id) == Reference.TYPE_TIME_CARD) {
				isAdded = courseSupportedCardDAO.addSupportedTimeCard(course_id, card_id);
			}else {
				isAdded = courseSupportedCardDAO.addSupportedCard(course_id, card_id, price);
			}
			if (isAdded == true) {
				return MethodTool.qr(Reference.EXE_SUC);
			}
			return MethodTool.qr(Reference.EXE_FAIL);
		case Reference.TYPE_OPERATE_MODIFY :
			if (courseSupportedCardDAO.isExist(course_id, card_id) == false) {
				return MethodTool.qr(Reference.NSR);
			}
			if (cardDAO.queryTypeById(card_id) == Reference.TYPE_TIME_CARD) {
				return MethodTool.tfs(Reference.EXE_FAIL);
			}
			boolean isModified = false;
			isModified = courseSupportedCardDAO.modifySupportedCard(course_id, card_id, price);
			if (isModified == true) {
				return MethodTool.qr(Reference.EXE_SUC);
			}
			return MethodTool.qr(Reference.EXE_FAIL);
		case Reference.TYPE_OPERATE_REMOVE :
			boolean isDel = false;
			if(courseSupportedCardDAO.isExist(course_id, card_id) == false) {
				return MethodTool.qr(Reference.NSR);
			}
			isDel = courseSupportedCardDAO.removeSupportedCard(course_id, card_id);
			if (isDel == true) {
				return MethodTool.qr(Reference.EXE_SUC);
			}
			return MethodTool.qr(Reference.EXE_FAIL);
		default :
			return null;
		}		
	}

}
