package com.alfred.Sailfish.app.ShopmemberController.CoursePlanBookAndAttendance;

import com.alfred.Sailfish.app.ShopmemberService.CourseplanBookAndAttendanceService;
import com.alfred.Sailfish.app.Util.ShopMemberBaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CoursePlanBookListQueryByCoursePlanId",urlPatterns = "/coursePlanBookListQueryByCoursePlanId")
public class CoursePlanBookListQuryByCoursePlanId extends ShopMemberBaseServlet {

    private CourseplanBookAndAttendanceService courseplanBookAndAttendanceService = new CourseplanBookAndAttendanceService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session == null){
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }else {
            long cp_id = MethodTool.reqParseToLong(request,"cp_id");
            String s = courseplanBookAndAttendanceService.queryBookListByCoursePlanId(cp_id);
            System.out.println(s);
            response.getWriter().append(s);
        }
    }
}
