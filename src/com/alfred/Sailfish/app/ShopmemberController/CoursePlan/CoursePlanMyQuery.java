package com.alfred.Sailfish.app.ShopmemberController.CoursePlan;

import com.alfred.Sailfish.app.ShopmemberService.CoursePlanService;
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

@WebServlet(name = "CoursePlanMyQuery",urlPatterns = "/coursePlanMyQuery")
public class CoursePlanMyQuery extends BaseServlet {

    private CoursePlanService coursePlanService = new CoursePlanService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            long sm_id = MethodTool.getSessionValueToLong(session,"sm_id");
            String s = coursePlanService.queryByShopmemberId(sm_id);
            response.getWriter().append(s);
        }else {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }
    }
}
