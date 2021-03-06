package com.alfred.Sailfish.app.ShopmemberController.Shopmember;

import com.alfred.Sailfish.app.ShopmemberService.ShopmemberService;
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

@WebServlet(name = "ShopmemberResetPassword",urlPatterns = "/shopmemberResetPassword")
public class ShopmemberResetPassword extends HttpServlet {

    private ShopmemberService shopmemberService = new ShopmemberService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session == null) {
            out.append(Reference.SESSION_EXPIRED);
        }else {
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            long sm_id = MethodTool.reqParseToLong(request,"sm_id");
            String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
            String password = request.getParameter("pwd");
            String result = shopmemberService.modifyPassword(s_id,sm_type,sm_id,password);
            response.getWriter().append(result);
        }

    }
}
