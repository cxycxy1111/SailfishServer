package com.alfred.Sailfish.app.Service;

import java.util.ArrayList;
import java.util.HashMap;

import com.alfred.Sailfish.app.DAO.*;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class CourseSupportedCardService {
	
	private CourseSupportedCardDAO courseSupportedCardDAO = new CourseSupportedCardDAO();
	public ShopDAO shopDAO = new ShopDAO();
	public ShopConfigDAO shopConfigDAO = new ShopConfigDAO();
	private CardDAO cardDAO = new CardDAO();
	private CourseDAO courseDAO = new CourseDAO();
	
	/**
	 * 查询受支持的会员卡列表
	 * @param s_id
	 * @param c_id
	 * @return
	 */
	public String query(long s_id,long c_id) {
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list = courseSupportedCardDAO.querySupportedCards(s_id,c_id);
		if (list.size() == 0) {
			return MethodTool.qr(Reference.EMPTY_RESULT);
		}
		return MethodTool.tfc(list);
	}
	
	/**
	 * 修改受支持的会员卡列表
	 * @param type 操作类型：1新增，2删除，3修改
	 * @param course_id 课程ID
	 * @param card_id 卡ID
	 * @param price 价格
	 * @return
	 */
	public String modify(long s_id,String sm_type,int type,long course_id,long card_id,int price) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_COURSE,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		if (!cardDAO.isExist(card_id) | !courseDAO.isExist(course_id)) {
			return MethodTool.qr(Reference.NSR);
		}
		if (shopDAO.queryShopIdByCourseId(course_id) != shopDAO.queryShopIdByCardId(card_id)) {
			return MethodTool.qr(Reference.INST_NOT_MATCH);
		}
		switch (type) {
		case Reference.TYPE_OPERATE_ADD :
			boolean isAdded = false;
			if (courseSupportedCardDAO.isExist(course_id, card_id)) {
				return MethodTool.qr(Reference.DUPLICATE);
			}
			if(cardDAO.queryTypeById(card_id) == Reference.TYPE_TIME_CARD) {
				isAdded = courseSupportedCardDAO.addSupportedTimeCard(course_id, card_id);
			}else {
				isAdded = courseSupportedCardDAO.addSupportedCard(course_id, card_id, price);
			}
			if (isAdded) {
				return MethodTool.qr(Reference.EXE_SUC);
			}
			return MethodTool.qr(Reference.EXE_FAIL);
		case Reference.TYPE_OPERATE_MODIFY :
			if (!courseSupportedCardDAO.isExist(course_id, card_id)) {
				return MethodTool.qr(Reference.NSR);
			}
			if (cardDAO.queryTypeById(card_id) == Reference.TYPE_TIME_CARD) {
				return MethodTool.tfs(Reference.EXE_FAIL);
			}
			boolean isModified = false;
			isModified = courseSupportedCardDAO.modifySupportedCard(course_id, card_id, price);
			if (isModified) {
				return MethodTool.qr(Reference.EXE_SUC);
			}
			return MethodTool.qr(Reference.EXE_FAIL);
		case Reference.TYPE_OPERATE_REMOVE :
			boolean isDel = false;
			if(!courseSupportedCardDAO.isExist(course_id, card_id)) {
				return MethodTool.qr(Reference.NSR);
			}
			isDel = courseSupportedCardDAO.removeSupportedCard(course_id, card_id);
			if (isDel) {
				return MethodTool.qr(Reference.EXE_SUC);
			}
			return MethodTool.qr(Reference.EXE_FAIL);
		default :
			return null;
		}		
	}

}
