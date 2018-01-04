package com.alfred.Sailfish.app.Controller.Shopmember;

import com.alfred.Sailfish.app.Service.ShopmemberService;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ShopmemberModify",urlPatterns = "/ShopmemberModify")
public class ShopmemberModify extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ShopmemberService shopmemberService = new ShopmemberService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        long sm_id = MethodTool.reqParseToLong(request,"sm_id");
        long lmu_id = MethodTool.reqParseToLong(request,"lmu_id");
        String name = request.getParameter("name");
        try {
            String str = shopmemberService.modifyInfo(sm_id,name,lmu_id);
            System.out.println(MethodTool.getTime() +  ",Response:" + str);
            response.getWriter().append(str);
            response.flushBuffer();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
