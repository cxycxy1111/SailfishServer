package com.alfred.Sailfish.app.Controller.CoursePlan;

import com.alfred.Sailfish.app.Service.CoursePlanService;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CoursePlanPrivateDetailQuery",urlPatterns = "/CoursePlanPrivateDetailQuery")
public class CoursePlanPrivateDetailQuery extends HttpServlet {

    private CoursePlanService coursePlanService = new CoursePlanService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long cp_id = MethodTool.reqParseToLong(request,"cp_id");
        response.setCharacterEncoding("utf-8");
        String str = coursePlanService.queryPrivateByCoursePlanId(cp_id);
        System.out.println(MethodTool.getTime() +  ",Response:" + str);
        response.getWriter().append(str);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}