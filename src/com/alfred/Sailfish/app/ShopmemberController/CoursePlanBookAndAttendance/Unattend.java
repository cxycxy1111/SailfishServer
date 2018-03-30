package com.alfred.Sailfish.app.ShopmemberController.CoursePlanBookAndAttendance;

import com.alfred.Sailfish.app.ShopmemberService.CourseplanBookAndAttendanceService;
import com.alfred.Sailfish.app.Util.BaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Unattend",urlPatterns = "/unattend")
public class Unattend extends BaseServlet {

    private CourseplanBookAndAttendanceService courseplanBookAndAttendanceService = new CourseplanBookAndAttendanceService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }else {
            long sm_id = MethodTool.getSessionValueToLong(session,"sm_id");
            long m_id = MethodTool.reqParseToLong(request,"m_id");
            long cp_id = MethodTool.reqParseToLong(request,"cp_id");
            String s = courseplanBookAndAttendanceService.unattend(sm_id,m_id,cp_id);
            response.getWriter().append(s);
        }
    }
}
