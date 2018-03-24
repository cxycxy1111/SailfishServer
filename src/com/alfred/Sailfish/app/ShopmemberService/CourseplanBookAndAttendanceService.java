package com.alfred.Sailfish.app.ShopmemberService;

import com.alfred.Sailfish.app.DAO.CourseplanBookAndAttendDAO;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseplanBookAndAttendanceService {

    private CourseplanBookAndAttendDAO courseplanBookAndAttendDAO;

    public CourseplanBookAndAttendanceService() {
        courseplanBookAndAttendDAO = new CourseplanBookAndAttendDAO();
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

}
