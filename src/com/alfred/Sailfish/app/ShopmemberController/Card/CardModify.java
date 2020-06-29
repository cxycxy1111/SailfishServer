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

/**
 * Servlet implementation class ModifyCard
 */
@WebServlet(name = "CardModify",urlPatterns = "/ModifyCard")
public class CardModify extends ShopMemberBaseServlet {
	private static final long serialVersionUID = 1L;
	private CardService cardService = new CardService();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req,resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request,response);
	}

	@Override
	protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long s_id, long sm_id, String sm_type) throws IOException {
		super.dealWithSessionAlive(request, response, session, out, s_id, sm_id, sm_type);
		String name = request.getParameter("name");
		int price = Integer.parseInt(request.getParameter("price"));
		int balance = Integer.parseInt(request.getParameter("balance"));
		String start_time = request.getParameter("start_time");
		String expired_time = request.getParameter("expired_time");
		long id = Long.parseLong(request.getParameter("id"));
		String str = cardService.modifyCard(s_id,sm_type,id, name, sm_id, price, balance, start_time, expired_time);
		System.out.println(MethodTool.getTime() +  ",Response:" + str);
		out.append(str);
	}

	@Override
	protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
		super.dealWithSessionDead(request, response, out);
	}
}
