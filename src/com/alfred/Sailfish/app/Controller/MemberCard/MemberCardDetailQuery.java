package com.alfred.Sailfish.app.Controller.MemberCard;

import com.alfred.Sailfish.app.Service.MemberCardService;
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

@WebServlet(name = "MemberCardDetailQuery",urlPatterns = "/QueryMemberCardDetail")
public class MemberCardDetailQuery extends HttpServlet {

    private MemberCardService memberCardService = new MemberCardService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession(false);
        if (session == null) {
            out.append(Reference.SESSION_EXPIRED);
        }else {
            long mc_id = Long.parseLong(req.getParameter("mc_id"));
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
            String str = memberCardService.queryDetail(s_id,sm_type,mc_id);
            System.out.println(MethodTool.getTime() +  ",Response:" + mc_id);
            out.append(str);
        }
    }
}
