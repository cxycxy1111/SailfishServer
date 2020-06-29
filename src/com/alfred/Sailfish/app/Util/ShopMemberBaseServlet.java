package com.alfred.Sailfish.app.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ShopMemberBaseServlet")
public class ShopMemberBaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/text;charset=utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("s_id") != null) {
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            long sm_id = MethodTool.getSessionValueToLong(session,"sm_id");
            String sm_type = String.valueOf(session.getAttribute("sm_type"));
            dealWithSessionAlive(request,response,session,out,s_id,sm_id,sm_type);
        }else {
            dealWithSessionDead(request,response,out);
        }
    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session
            , PrintWriter out, long s_id,long sm_id,String sm_type) throws IOException {
    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        out.append(Reference.SESSION_EXPIRED);
    }

}
