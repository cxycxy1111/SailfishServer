package com.alfred.Sailfish.app.ShopmemberController.MemberCard;

import com.alfred.Sailfish.app.ShopmemberService.MemberCardService;
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

@WebServlet(name = "MemberCardListQuery",urlPatterns = "/QueryMemberCardList")
public class MemberCardListQuery extends HttpServlet{

    private static final long serialVersionUID = 1L;
    private MemberCardService memberCardService = new MemberCardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession(false);
        if (session == null) {
            out.append(Reference.SESSION_EXPIRED);
        }else {
            long m_id = Long.parseLong(req.getParameter("m_id"));
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
            String str = memberCardService.queryListByMemberId(s_id,sm_type,m_id);
            System.out.println(MethodTool.getTime() +  ",Response:" + m_id);
            out.append(str);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
