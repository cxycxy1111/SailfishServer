package com.alfred.Sailfish.app.ShopmemberController.MemberCard;

import com.alfred.Sailfish.app.ShopmemberService.MemberCardService;
import com.alfred.Sailfish.app.Util.ShopMemberBaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MemberCardAddBatch",urlPatterns = "/MemberCardAddBatch")
public class MemberCardAddBatch extends ShopMemberBaseServlet {

    private MemberCardService memberCardService = new MemberCardService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            long sm_id = MethodTool.getSessionValueToLong(session,"sm_id");

            String m_id = request.getParameter("m_id");
            long c_id = MethodTool.reqParseToLong(request,"c_id");
            int balance = MethodTool.reqParseToInt(request,"balance");
            String start_time = request.getParameter("start_time");
            String expired_time = request.getParameter("expired_time");
            String s = memberCardService.addBatch(s_id,sm_type,c_id,m_id,sm_id,balance,start_time,expired_time);
            response.getWriter().append(s);
        }else {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }
    }
}
