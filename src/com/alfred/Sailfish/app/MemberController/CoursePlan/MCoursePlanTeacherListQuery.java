package com.alfred.Sailfish.app.MemberController.CoursePlan;

import com.alfred.Sailfish.app.ShopmemberService.CoursePlanTeacherService;
import com.alfred.Sailfish.app.Util.MemberBaseServlet;
import com.alfred.Sailfish.app.Util.ShopMemberBaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MCoursePlanTeacherListQuery",urlPatterns = "/mCoursePlanTeacherListQuery")
public class MCoursePlanTeacherListQuery extends MemberBaseServlet {

    private CoursePlanTeacherService coursePlanTeacherService = new CoursePlanTeacherService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        long cp_id = MethodTool.reqParseToLong(request,"cp_id");
        if (session != null) {
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            long m_id = MethodTool.getSessionValueToLong(session,"m_id");
            String s = coursePlanTeacherService.queryList(cp_id);
            System.out.println(MethodTool.getTime() +  ",Response:" + s);
            response.getWriter().append(s);
        }else {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }
    }
}
