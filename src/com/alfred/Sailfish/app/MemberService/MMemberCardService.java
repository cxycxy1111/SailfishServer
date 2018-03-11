package com.alfred.Sailfish.app.MemberService;

import com.alfred.Sailfish.app.DAO.CourseDAO;
import com.alfred.Sailfish.app.DAO.CourseSupportedCardDAO;
import com.alfred.Sailfish.app.DAO.MemberCardDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.ShopmemberService.MemberCardService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import java.util.ArrayList;
import java.util.HashMap;

public class MMemberCardService {

    private CourseDAO courseDAO = new CourseDAO();
    private MemberCardDAO memberCardDAO = new MemberCardDAO();
    private CourseSupportedCardDAO courseSupportedCardDAO = new CourseSupportedCardDAO();
    private ShopDAO shopDAO = new ShopDAO();

    public MMemberCardService() {

    }

    /**
     * 通过会员ID查询会员卡列表
     * @param m_id
     * @return
     */
    public String queryListByMemberId(long m_id) {
        ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
        mapArrayList = memberCardDAO.queryListByMemberId(m_id);
        if (mapArrayList.size() != 0) {
            return MethodTool.tfc(mapArrayList);
        }else {
            return Reference.EMPTY_RESULT;
        }
    }

    /**
     * 通过会员卡ID查询会员卡详情
     * @param mc_id
     * @return
     */
    public String queryDetail(long mc_id) {
        if (memberCardDAO.isDel(mc_id)) {
            return MethodTool.tfs(Reference.NSR);
        }
        return MethodTool.tfc(memberCardDAO.queryDetailById(mc_id));
    }

    public String querySupportedMemberCard(long m_id,long cp_id) {
        ArrayList<HashMap<String,Object>> mapArrayList_1 = new ArrayList<>();
        ArrayList<HashMap<String,Object>> mapArrayList_2 = new ArrayList<>();
        ArrayList<HashMap<String,Object>> mapArrayList_3 = new ArrayList<>();
        long course_id = courseDAO.querycourseIdByCoursePlanId(cp_id);
        long s_id = shopDAO.queryShopIdByCoursePlanId(cp_id);
        mapArrayList_1 = memberCardDAO.queryListByMemberId(m_id);
        mapArrayList_2 = courseSupportedCardDAO.querySupportedCards(s_id,course_id);

        for (int i = 0;i < mapArrayList_1.size();i++) {
            HashMap<String,Object> map_1 = mapArrayList_1.get(i);
            for (int j = 0;j < mapArrayList_2.size();j++) {
                HashMap<String,Object> map_2 = mapArrayList_2.get(j);
                if (String.valueOf(map_1.get("card_id")).equals(String.valueOf(map_2.get("c_id")))) {
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("mc_id",String.valueOf(map_1.get("id")));
                    map.put("c_name",String.valueOf(map_2.get("card_name")));
                    map.put("balance",String.valueOf(map_1.get("balance")));
                    map.put("type",String.valueOf(map_2.get("type")));
                    mapArrayList_3.add(map);
                }
            }
        }
        if (mapArrayList_3.size() >0) {
            return MethodTool.tfc(mapArrayList_3);
        }else {
            return Reference.EMPTY_RESULT;
        }
    }

}
