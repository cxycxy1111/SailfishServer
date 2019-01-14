package com.alfred.Sailfish.app.ShopmemberService;

import com.alfred.Sailfish.app.DAO.*;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

import java.util.ArrayList;
import java.util.HashMap;

public class MemberCardService {
	
	private MemberDAO memberDAO = new MemberDAO();
	private MemberCardDAO memberCardDAO = new MemberCardDAO();
	private ShopDAO shopDAO = new ShopDAO();
	private ShopConfigDAO shopConfigDAO = new ShopConfigDAO();
	private MemberCardConsumeLogDAO memberCardConsumeLogDAO;
	
	public MemberCardService() {
		memberCardConsumeLogDAO = new MemberCardConsumeLogDAO();
	}

	/**
	 * 新增会员卡
	 * @param member_id
	 * @param card_id
	 * @param shopmember_id
	 * @param balance
	 * @param start_time
	 * @param expired_time
	 * @return
	 */
	public String add(long s_id,String sm_type,long member_id,long card_id,
		long shopmember_id,int balance,String start_time,String expired_time) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_MEMBER_CARD,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		boolean isMemberDeleted = memberDAO.isDel(member_id);
        long shop_id = shopDAO.queryShopIdByCardId(card_id);
		long shop_id_1 = shopDAO.queryShopByShopmemberId(shopmember_id);
        long shop_id_2 = shopDAO.queryShopIdByCardId(card_id);
        long shop_id_3 = shopDAO.queryShopIdByMemberId(member_id);
        if (!isMemberDeleted) {
            if (shop_id_3 == shop_id_1 && shop_id_1 == shop_id_2) {
                if (memberCardDAO.isMemberCardHasExist(member_id, card_id)) {
                	return qr(Reference.DUPLICATE);
                }else {
                    boolean isAdded = memberCardDAO.addMemberCard(member_id,shop_id,card_id,shopmember_id,balance,start_time,expired_time);
                    if (isAdded) {
                    	return qr(Reference.EXE_SUC);
                    }else {
                    	return qr(Reference.EXE_FAIL);
                    }
                }
            } else {
            	return qr(Reference.NOT_MATCH);
            }
        } else {
        	return qr(Reference.NSR);
        }
	}

	public String addBatch(long s_id,String sm_type,long c_id,String m_id,long sm_id,int balance,String start_time,String expired_time) {
		String[] strs_m_id = new String[]{};
		strs_m_id = m_id.split("_");
		StringBuilder builder = new StringBuilder();
		for (int i = 0;i < strs_m_id.length;i++) {
			String result = add(s_id,sm_type,Long.parseLong(strs_m_id[i]),c_id,sm_id,balance,start_time,expired_time);
			builder.append(result).append(",");
		}
		String resp = builder.toString();
		resp = resp.substring(0,resp.length()-1);
		if (resp.contains(Reference.AUTHORIZE_FAIL)) {
			return Reference.AUTHORIZE_FAIL;
		}else if (resp.contains(Reference.DUPLICATE) || resp.contains(Reference.AUTHORIZE_FAIL) || resp.contains(Reference.NOT_MATCH) || resp.contains(Reference.NSR)) {
		    if (!resp.contains(Reference.EXE_SUC)) {
		        return Reference.EXE_FAIL;
            }
			return Reference.EXE_PARTLY_FAIL;
		}else {
			return Reference.EXE_SUC;
		}
	}
	
	/**
	 * 删除会员卡
	 * @param mc_id
	 * @param lmu_id
	 * @return
	 */
	public String remove(long s_id,String sm_type,long mc_id,long lmu_id) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_MEMBER_CARD,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		if (shopDAO.queryShopByShopmemberId(lmu_id) == shopDAO.queryShopIdByMembercardId(mc_id)) {
			if (memberCardDAO.isDel(mc_id)) {
				return qr(Reference.NSR);
			} else {
				boolean isDeleted = false;
				isDeleted = memberCardDAO.deleteMemberCardById(mc_id,lmu_id);
				if (isDeleted) {
					return qr(Reference.EXE_SUC);
				} else {
					return qr(Reference.EXE_FAIL);
				}
			}
		} else {
			return qr(Reference.NOT_MATCH);
		}
	}

	/**
	 * 通过会员ID获取会员卡列表
	 * @param m_id 会员ID
	 * @return
	 */
	public String queryListByMemberId(long s_id,String sm_type,long m_id) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_VIEW_MEMBER_CARD,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
		arrayList = memberCardDAO.queryListByMemberId(m_id);
		if (arrayList.size() == 0) {
			return MethodTool.qr(Reference.EMPTY_RESULT);
		}
		return MethodTool.tfc(arrayList);
	}
	
	/**
	 * 增加账户余额
	 * @param member_card_id
	 * @param last_modify_user
	 * @param num
	 * @return
	 */
	public String increaseBalance(long s_id,String sm_type,long member_card_id,long last_modify_user,String invalid_date,int num,long charge_fee) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_MEMBER_CARD,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		boolean isCharged = memberCardDAO.updateBalancePlus(member_card_id, num,invalid_date, last_modify_user);
		if (shopDAO.queryShopByShopmemberId(last_modify_user) == shopDAO.queryShopIdByMembercardId(member_card_id)) {
			if (!memberCardDAO.isDel(member_card_id)) {
				if (isCharged) {
					memberCardConsumeLogDAO.charge(s_id,member_card_id,last_modify_user,num,"",charge_fee);
					return qr(Reference.EXE_SUC);
				}else {
					return qr(Reference.EXE_FAIL);
				}
			} else {
				return qr(Reference.NSR);
			}
		} else {
			return qr(Reference.NOT_MATCH);
		}
	}
	
	/**
	 * 缩减账户余额
	 * @param mc_id
	 * @param shop_member_id
	 * @param num
	 * @return
	 */
	public String reduceBalance(long s_id,String sm_type,long mc_id,long shop_member_id,int num,String invalid_date) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_MEMBER_CARD,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		if (!memberCardDAO.isBalanceEnough(mc_id,num) && String.valueOf(shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_DEDUCT_AFTER_ARREARANGE,s_id)).contains("0")) {
			return Reference.BALANCE_NOT_ENOUGH;
		}
		if (memberCardDAO.isExpired(mc_id) && String.valueOf(shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_DEDUCT_AFTER_OVERDUE,s_id)).contains("0")) {

		}
		boolean isEnough = false;
		isEnough = memberCardDAO.isBalanceEnough(mc_id, num);
		if (shopDAO.queryShopIdByMembercardId(mc_id) == shopDAO.queryShopByShopmemberId(shop_member_id)) {
			if (!memberCardDAO.isDel(mc_id)) {
				boolean isReduced = memberCardDAO.updateBalanceReduce(mc_id,shop_member_id, num,invalid_date);
				if (isReduced) {
					memberCardConsumeLogDAO.deduct(s_id,mc_id,shop_member_id,num,"");
					return qr(Reference.EXE_SUC);
				}else {
					return qr(Reference.EXE_FAIL);
				}
			} else {
				return qr(Reference.NSR);
			}
		} else {
			return qr(Reference.NOT_MATCH);
		}
	}

	/**
	 *
	 * @param s_id 商店ID
	 * @param sm_type 修改人类型
	 * @param lmu_id 修改人ID
	 * @param mc_id 会员卡ID
	 * @param expiredTime 到期时间
	 * @return
	 */
	public String changeExpiredTime(long s_id,String sm_type,long lmu_id,long mc_id,String expiredTime) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_MEMBER_CARD,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		if(shopDAO.queryShopByShopmemberId(lmu_id) == shopDAO.queryShopIdByMembercardId(mc_id)) {
			if (!memberCardDAO.isDel(mc_id)) {
				boolean isUpdated = memberCardDAO.updateExpiredTime(mc_id, lmu_id, expiredTime);
				if (isUpdated) {
					memberCardConsumeLogDAO.charge(s_id,mc_id,lmu_id,0L,expiredTime,0);
					return qr(Reference.EXE_SUC);
				}else {
					return qr(Reference.EXE_FAIL);
				}
			}else {
				return qr(Reference.NSR);
			}
		}else {
			return qr(Reference.NOT_MATCH);
		}
	}

	public String queryDetail(long s_id,String sm_type,long mc_id) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_VIEW_MEMBER_CARD,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		if (memberCardDAO.isDel(mc_id)) {
			return MethodTool.tfs(Reference.NSR);
		}
		return MethodTool.tfc(memberCardDAO.queryDetailById(mc_id));
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
