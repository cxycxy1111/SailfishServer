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

@WebServlet(name = "ShopmemberDelete",urlPatterns = "/ShopmemberDelete")
public class ShopmemberDelete extends HttpServlet {

    private ShopmemberService shopmemberService = new ShopmemberService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session == null) {
            out.append(Reference.SESSION_EXPIRED);
        }else {
            long sm_id = MethodTool.reqParseToLong(request,"id");
            long lmu_id = MethodTool.getSessionValueToLong(session,"sm_id");
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
            String str = shopmemberService.deleteShopmember(s_id,sm_type,sm_id,lmu_id);
            System.out.println(MethodTool.getTime() +  ",Response:" + str);
            response.getWriter().append(str);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
