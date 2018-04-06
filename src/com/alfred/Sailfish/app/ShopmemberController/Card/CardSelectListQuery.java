package com.alfred.Sailfish.app.ShopmemberController.Card;

import com.alfred.Sailfish.app.ShopmemberService.CardService;
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

@WebServlet(name = "CardSelectListQuery",urlPatterns = "/CardSelectListQuery")
public class CardSelectListQuery extends BaseServlet {

    private CardService cardService = new CardService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }else {
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            String sm_id = MethodTool.getSessionValueToInt(session,"sm_id");
            String s = cardService.querySelectCardList(sm_id,s_id);
            System.out.println(s);
            response.getWriter().append(s);
        }
    }
}
