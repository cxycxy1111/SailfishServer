package com.alfred.Sailfish.app.Controller.CoursePlan;

import com.alfred.Sailfish.app.Service.CoursePlanService;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        long cp_id = MethodTool.reqParseToLong(request,"cp_id");
        String resp = coursePlanService.queryByCoursePlanId(cp_id);
        PrintWriter printWriter = response.getWriter();
        System.out.println(MethodTool.getTime() +  ",Response:" + resp);
        printWriter.append(resp);
    }
}
