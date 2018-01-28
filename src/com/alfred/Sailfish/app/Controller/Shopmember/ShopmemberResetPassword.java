package com.alfred.Sailfish.app.Controller.Shopmember;

import com.alfred.Sailfish.app.Service.ShopmemberService;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ShopmemberResetPassword",urlPatterns = "/shopmemberResetPassword")
public class ShopmemberResetPassword extends HttpServlet {

    private ShopmemberService shopmemberService = new ShopmemberService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        long sm_id = MethodTool.reqParseToLong(request,"sm_id");
        String password = request.getParameter("pwd");
        String result = shopmemberService.modifyPassword(sm_id,password);
        response.getWriter().append(result);
    }
}
