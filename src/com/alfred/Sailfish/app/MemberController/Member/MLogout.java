package com.alfred.Sailfish.app.MemberController.Member;

import com.alfred.Sailfish.app.Util.MemberBaseServlet;
import com.alfred.Sailfish.app.Util.ShopMemberBaseServlet;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MLogout",urlPatterns = "/mLogout")
public class MLogout extends MemberBaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            response.getWriter().append(Reference.EXE_SUC);
        }else {
            response.getWriter().append(Reference.EXE_SUC);
        }
    }
}
