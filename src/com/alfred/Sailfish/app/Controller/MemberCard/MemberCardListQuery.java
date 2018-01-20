package com.alfred.Sailfish.app.Controller.MemberCard;

import com.alfred.Sailfish.app.Service.MemberCardService;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        long m_id = Long.parseLong(req.getParameter("m_id"));
        String str = memberCardService.queryListByMemberId(m_id);
        System.out.println(MethodTool.getTime() +  ",Response:" + m_id);
        out.append(str);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}