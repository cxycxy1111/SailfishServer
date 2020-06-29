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

@WebServlet(name = "MMemberCardDetailQuery",urlPatterns = "/mMemberCardDetailQuery")
public class MMemberCardDetailQuery extends MemberBaseServlet {

    private MMemberCardService mMemberCardService = new MMemberCardService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            long mc_id = MethodTool.reqParseToLong(request,"mc_id");
            if (mc_id != 0) {
                String s = mMemberCardService.queryDetail(mc_id);
                System.out.append(s);
                response.getWriter().append(s);
            }else {
                response.getWriter().append(Reference.SESSION_EXPIRED);
            }

        }else {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }
    }
}
