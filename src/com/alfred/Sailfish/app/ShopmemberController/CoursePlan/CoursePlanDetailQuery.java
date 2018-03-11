package com.alfred.Sailfish.app.ShopmemberController.CoursePlan;

import com.alfred.Sailfish.app.ShopmemberService.CoursePlanService;
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

@WebServlet(name = "CoursePlanDetailQuery",urlPatterns = "/CoursePlanDetailQuery")
public class CoursePlanDetailQuery extends HttpServlet {

    private CoursePlanService coursePlanService = new CoursePlanService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session == null) {
            printWriter.append(Reference.SESSION_EXPIRED);
        }else {
            long cp_id = MethodTool.reqParseToLong(request,"cp_id");
            String resp = coursePlanService.queryByCoursePlanId(cp_id,0);
            System.out.println(MethodTool.getTime() +  ",Response:" + resp);
            printWriter.append(resp);
        }
    }
}
