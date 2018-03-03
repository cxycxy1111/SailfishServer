package com.alfred.Sailfish.app.MemberService;

import com.alfred.Sailfish.app.DAO.MemberDAO;
import com.alfred.Sailfish.app.ShopmemberService.MemberService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import java.util.ArrayList;
import java.util.HashMap;

public class MMemberService {

    private MemberDAO memberDAO;
    private MemberService memberService;

    public MMemberService() {
        memberDAO = new MemberDAO();
        memberService = new MemberService();
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

    public String resetPassword(long shop_id,long m_id,String password){
        return memberService.resetPassword(shop_id,"1",0,m_id,password);
    }

}
