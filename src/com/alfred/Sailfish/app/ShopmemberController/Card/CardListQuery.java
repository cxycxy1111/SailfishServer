package com.alfred.Sailfish.app.ShopmemberController.Card;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.ShopmemberService.CardService;
import com.alfred.Sailfish.app.Util.ShopMemberBaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;

@WebServlet(name = "CardListQuery",urlPatterns = "/QueryCardList")
public class CardListQuery extends ShopMemberBaseServlet {
	private CardService cardService = new CardService();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request,response);
	}

	@Override
	protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long s_id, long sm_id, String sm_type) throws IOException {
		super.dealWithSessionAlive(request, response, session, out, s_id, sm_id, sm_type);
		int type = MethodTool.reqParseToInt(request,"type");
		String str = cardService.queryCardList(sm_type,s_id,type);
		System.out.println(MethodTool.getTime() +  ",Response:" + str);
		out.append(str);
	}

	@Override
	protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
		super.dealWithSessionDead(request, response, out);
	}
}
