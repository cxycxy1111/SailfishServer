package com.alfred.Sailfish.app.MemberController;

import com.alfred.Sailfish.app.MemberService.MEnrollPrivService;
import com.alfred.Sailfish.app.Util.MemberBaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "EnrollPrivAdd",urlPatterns="/EnrollPriv/Add")
public class EnrollPrivAdd extends MemberBaseServlet {

    private MEnrollPrivService mEnrollPrivService = new MEnrollPrivService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();

    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long s_id, long m_id) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, s_id, m_id);
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        long course = MethodTool.reqParseToLong(request,"course");
        long time = MethodTool.reqParseToLong(request,"time");
        int grade = MethodTool.reqParseToInt(request,"grade");
        out.append(mEnrollPrivService.add(name,number,grade,time,course));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, out);
    }
}
