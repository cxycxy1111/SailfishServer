package com.alfred.Sailfish.app.MemberController.CoursePlanAttendance;

import com.alfred.Sailfish.app.MemberService.MCoursePlanAttendanceService;
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

@WebServlet(name = "MAttend",urlPatterns = "/mAttend")
public class MAttend extends BaseServlet {

    private MCoursePlanAttendanceService mCoursePlanAttendanceService = new MCoursePlanAttendanceService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            long m_id = MethodTool.getSessionValueToLong(session,"m_id");
            long cp_id = MethodTool.reqParseToLong(request,"cp_id");
            long mc_id = MethodTool.reqParseToLong(request,"mc_id");
            String s = mCoursePlanAttendanceService.attend(m_id,cp_id,mc_id);
            System.out.println(s);
            response.getWriter().append(s);
        }else {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }
    }
}
