package com.alfred.Sailfish.app.MemberService;

import com.alfred.Sailfish.app.DAO.CoursePlanDAO;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import java.util.ArrayList;
import java.util.HashMap;

public class MCoursePlanService {

    private CoursePlanDAO coursePlanDAO;

    public MCoursePlanService() {

        coursePlanDAO = new CoursePlanDAO();
    }

    public String queryCourseplanListByMemberId(long s_id,long m_id) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        ArrayList<HashMap<String, Object>> list_private = new ArrayList<>();
        list = coursePlanDAO.queryByShopId(s_id);
        list_private = coursePlanDAO.queryPrivateCoursePlanByMemberId(m_id);
        if (list_private.size() != 0) {
            list.addAll(list_private);
        }
        if (list.size()!=0) {
            return MethodTool.tfc(list);
        }
        return Reference.EMPTY_RESULT;
    }

}
