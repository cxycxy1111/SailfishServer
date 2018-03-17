package com.alfred.Sailfish.app.MemberController.MemberCard;

import com.alfred.Sailfish.app.MemberService.MMemberCardService;
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

@WebServlet(name = "MMemberCardConsumeLogQuery",urlPatterns = "/mMemberCardConsumeLogQuery")
public class MMemberCardConsumeLogQuery extends BaseServlet {

    private MMemberCardService memberCardService = new MMemberCardService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }else {
            long mc_id = MethodTool.reqParseToLong(request,"mc_id");
            response.getWriter().append(memberCardService.queryMemberCardConsumeLog(mc_id));
        }
    }
}
