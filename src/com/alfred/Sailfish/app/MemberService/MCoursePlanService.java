package com.alfred.Sailfish.app.MemberService;

import com.alfred.Sailfish.app.DAO.CoursePlanDAO;
import com.alfred.Sailfish.app.DAO.CourseplanAttendanceDAO;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import java.util.ArrayList;
import java.util.HashMap;

public class MCoursePlanService {

    private CoursePlanDAO coursePlanDAO;
    private CourseplanAttendanceDAO courseplanAttendanceDAO = new CourseplanAttendanceDAO();

    public MCoursePlanService() {

        coursePlanDAO = new CoursePlanDAO();
    }

    /**
     * 通过会员ID与机构ID查询排课列表
     * @param s_id
     * @param m_id
     * @return
     */
    public String queryCourseplanListByMemberId(long s_id,long m_id) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        ArrayList<HashMap<String, Object>> list_private = new ArrayList<>();
        list = coursePlanDAO.queryByShopIdExceptPrivate(s_id);
        list_private = coursePlanDAO.queryPrivateCoursePlanByMemberId(m_id);
        if (list_private.size() != 0) {
            list.addAll(list_private);
        }
        if (list.size()!=0) {
            return MethodTool.tfc(list);
        }
        return Reference.EMPTY_RESULT;
    }

    /**
     * 通过排课ID查询私教排课
     * @param cp_id
     * @return
     */
    public String queryPrivateByCoursePlanId(long m_id,long cp_id) {
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list = coursePlanDAO.queryPrivateById(cp_id);
        boolean isAttend = courseplanAttendanceDAO.isAttended(m_id,cp_id);
        if (list.size() != 0) {
            HashMap<String, Object> map = list.get(0);
            if (isAttend) {
                map.put("isAttend","1");
            }else {
                map.put("isAttend","0");
            }
            list.clear();
            list.add(map);
            return MethodTool.tfc(list);
        }
        return MethodTool.tfs(Reference.NSR);
    }

}
