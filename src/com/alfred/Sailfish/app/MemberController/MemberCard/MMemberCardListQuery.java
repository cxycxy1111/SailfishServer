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
import java.io.PrintWriter;

@WebServlet(name = "MMemberCardListQuery",urlPatterns = "/mMemberCardListQuery")
public class MMemberCardListQuery extends MemberBaseServlet {

    private MMemberCardService mMemberCardService = new MMemberCardService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long s_id, long m_id) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, s_id, m_id);
        String s = mMemberCardService.queryListByMemberId(m_id);
        System.out.println(s);
        out.append(s);
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, out);
    }
}
