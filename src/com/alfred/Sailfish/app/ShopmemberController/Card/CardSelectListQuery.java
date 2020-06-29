package com.alfred.Sailfish.app.ShopmemberController.Card;

import com.alfred.Sailfish.app.ShopmemberService.CardService;
import com.alfred.Sailfish.app.Util.ShopMemberBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CardSelectListQuery",urlPatterns = "/CardSelectListQuery")
public class CardSelectListQuery extends ShopMemberBaseServlet {

    private CardService cardService = new CardService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long s_id, long sm_id, String sm_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, s_id, sm_id, sm_type);
        String s = cardService.querySelectCardList(sm_type,s_id);
        System.out.println(s);
        out.append(s);
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, out);
    }
}
