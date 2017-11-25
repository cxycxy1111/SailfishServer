package com.alfred.Sailfish.app.Controller.Shopmember;

import com.alfred.Sailfish.app.Service.ShopmemberService;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ShopmemberDelete")
public class ShopmemberDelete extends HttpServlet {

    private ShopmemberService shopmemberService = new ShopmemberService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        long sm_id = MethodTool.reqParseToLong(request,"id");
        long lmu_id = MethodTool.reqParseToLong(request,"lmu_id");
        String str = shopmemberService.deleteShopmember(sm_id,lmu_id);
        System.out.println(str);
        response.getWriter().append(str);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
