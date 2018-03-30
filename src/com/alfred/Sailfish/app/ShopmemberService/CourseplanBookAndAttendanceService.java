package com.alfred.Sailfish.app.ShopmemberService;

import com.alfred.Sailfish.app.DAO.*;
import com.alfred.Sailfish.app.Util.EnumPackage.EnumMemberCardConsumeLogOperatorType;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseplanBookAndAttendanceService {

    private CourseplanBookAndAttendDAO courseplanBookAndAttendDAO;
    private CourseDAO courseDAO;
    private MemberCardConsumeLogDAO memberCardConsumeLogDAO;
    private MemberCardDAO memberCardDAO;
    private ShopDAO shopDAO;

    public CourseplanBookAndAttendanceService() {
        courseplanBookAndAttendDAO = new CourseplanBookAndAttendDAO();
        courseDAO = new CourseDAO();
        memberCardConsumeLogDAO = new MemberCardConsumeLogDAO();
        memberCardDAO = new MemberCardDAO();
        shopDAO = new ShopDAO();
    }

    public String queryBookListByCoursePlanId(long cp_id) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = courseplanBookAndAttendDAO.queryBookListByCoursePlanId(cp_id);
        if (arrayList.size() == 0) {
            return Reference.EMPTY_RESULT;
        }else {
            return MethodTool.tfc(arrayList);
        }
    }

    public String queryAttendanceListByCoursePlanId(long cp_id) {
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = courseplanBookAndAttendDAO.queryAttendanceListByCoursePlanId(cp_id);
        if (arrayList.size() == 0) {
            return Reference.EMPTY_RESULT;
        }else {
            return MethodTool.tfc(arrayList);
        }
    }

    public String unattend(long sm_id,long m_id,long cp_id) {
        long s_id = shopDAO.queryShopIdByMemberId(m_id);
        if (!courseplanBookAndAttendDAO.isAttended(cp_id,m_id)) {
            return Reference.NSR;
        }
        if (courseplanBookAndAttendDAO.unAttend(cp_id,m_id)) {
            long mc_id = courseplanBookAndAttendDAO.queryMemberCardIdByCoursePlanIdAndMemberId(m_id,cp_id);
            int consume = memberCardConsumeLogDAO.queryConsumeByCourseplanIdAndMemberCardId(cp_id,mc_id);
            if (memberCardDAO.updateBalancePlus(mc_id,consume,"",sm_id)) {
                memberCardConsumeLogDAO.unAttend(s_id,mc_id,cp_id,sm_id,consume,"");
                return Reference.EXE_SUC;
            }else {
                courseplanBookAndAttendDAO.updateStatus(cp_id,m_id,Reference.TYPE_COURSE_PLAN_BOOK_STATE_ATTENDED);
                return Reference.EXE_FAIL;
            }
        }
        return Reference.EXE_FAIL;
    }

}
