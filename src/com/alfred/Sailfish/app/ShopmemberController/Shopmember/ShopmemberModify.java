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
import java.sql.SQLException;

@WebServlet(name = "ShopmemberModify",urlPatterns = "/ShopmemberModify")
public class ShopmemberModify extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ShopmemberService shopmemberService = new ShopmemberService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session == null) {
            out.append(Reference.SESSION_EXPIRED);
        }else {
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            long sm_id = MethodTool.reqParseToLong(request,"sm_id");
            long lmu_id = MethodTool.getSessionValueToLong(session,"sm_id");
            String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
            int new_type = MethodTool.reqParseToInt(request,"new_type");
            String name = request.getParameter("name");
            try {
                String str = shopmemberService.modifyInfo(s_id,sm_type,sm_id,name,lmu_id,new_type);
                System.out.println(MethodTool.getTime() +  ",Response:" + str);
                out.append(str);
                response.flushBuffer();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
