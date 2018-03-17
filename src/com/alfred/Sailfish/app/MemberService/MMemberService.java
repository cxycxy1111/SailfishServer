package com.alfred.Sailfish.app.MemberService;

import com.alfred.Sailfish.app.DAO.MemberDAO;
import com.alfred.Sailfish.app.ShopmemberService.MemberService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import java.util.ArrayList;
import java.util.HashMap;

public class MMemberService {

    private MemberService memberService;

    public MMemberService() {
        memberService = new MemberService();
    }

    /**
     * 查询会员详情
     * @param shop_id
     * @param member_id
     * @return
     */
    public String queryMemberDetail(long shop_id,long member_id) {
        return memberService.queryMemberDetail(shop_id,"1",member_id);
    }

    public String resetPassword(long shop_id,long m_id,String password){
        return memberService.resetPassword(shop_id,"1",0,m_id,password);
    }

    public String modifyMember(long shop_id,long member_id,String name,String birthday,String phone,String im) {
        return memberService.modifyMember(shop_id,"1",member_id,0,name,birthday,phone,im);
    }

}
