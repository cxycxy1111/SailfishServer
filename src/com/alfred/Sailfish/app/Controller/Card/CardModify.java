package com.alfred.Sailfish.app.Controller.Card;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alfred.Sailfish.app.Service.CardService;

/**
 * Servlet implementation class ModifyCard
 */
@WebServlet(name = "CardModify",urlPatterns = "/ModifyCard")
public class CardModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CardService cardService = new CardService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardModify() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		long id = Long.parseLong(req.getParameter("id"));
		long shopmember_id = Long.parseLong(req.getParameter("shopmember_id"));
		String name = req.getParameter("name");
		int price = Integer.parseInt(req.getParameter("price"));
		int balance = Integer.parseInt(req.getParameter("balance"));
		String start_time = req.getParameter("start_time");
		String expired_time = req.getParameter("expired_time");
		String str = cardService.modifyCard(id, name, shopmember_id, price, balance, start_time, expired_time);
		System.out.println(str);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
