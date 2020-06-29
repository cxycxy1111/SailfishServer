package com.alfred.Sailfish.app.MemberController.MemberCard;

import com.alfred.Sailfish.app.MemberService.MMemberCardService;
import com.alfred.Sailfish.app.Util.MemberBaseServlet;
import com.alfred.Sailfish.app.Util.ShopMemberBaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MMemberCardAttendList",urlPatterns = "/mMemberCardAttendList")
public class MMemberCardAttendList extends MemberBaseServlet {

    private MMemberCardService mMemberCardService = new MMemberCardService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            long m_id = 0;
            m_id = MethodTool.getSessionValueToLong(session,"m_id");
            long cp_id = MethodTool.reqParseToLong(request,"cp_id");
            String s = mMemberCardService.querySupportedMemberCard(m_id,cp_id);
            response.getWriter().append(s);
        }else {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }

    }
}
