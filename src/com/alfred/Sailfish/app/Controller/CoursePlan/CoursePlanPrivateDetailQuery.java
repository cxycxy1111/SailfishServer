package com.alfred.Sailfish.app.Controller.CoursePlan;

import com.alfred.Sailfish.app.Service.CoursePlanService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CoursePlanPrivateDetailQuery",urlPatterns = "/CoursePlanPrivateDetailQuery")
public class CoursePlanPrivateDetailQuery extends HttpServlet {

    private CoursePlanService coursePlanService = new CoursePlanService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session == null) {
            out.append(Reference.SESSION_EXPIRED);
        }else {
            long cp_id = MethodTool.reqParseToLong(request,"cp_id");
            String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
            String str = coursePlanService.queryPrivateByCoursePlanId(cp_id);
            System.out.println(MethodTool.getTime() +  ",Response:" + str);
            response.getWriter().append(str);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
