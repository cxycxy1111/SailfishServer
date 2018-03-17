package com.alfred.Sailfish.app.MemberController.Member;

import com.alfred.Sailfish.app.MemberService.MMemberService;
import com.alfred.Sailfish.app.Util.BaseServlet;
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

@WebServlet(name = "MResetPassword",urlPatterns = "/mResetPassword")
public class MResetPassword extends BaseServlet {

    private MMemberService mMemberService = new MMemberService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session == null) {
            out.append(Reference.SESSION_EXPIRED);
        }else {
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            long m_id = MethodTool.getSessionValueToLong(session,"m_id");
            String newpwd = request.getParameter("newpwd");
            String str = mMemberService.resetPassword(s_id, m_id, newpwd);
            System.out.println(MethodTool.getTime() +  ",Response:" + str);
            out.append(str);
        }
    }
}
