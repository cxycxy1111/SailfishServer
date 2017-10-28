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
 * Servlet implementation class AddNewCard
 * 请求示例
 * http://localhost:8080/Sailfish/AddNewCard?shop_id=5&shopmember_id=1&name=余额卡&type=1&price=200&balance=210&start_time=2017-09-25%2000:00:00&expired_time=2018-09-25%2000:00:00
 */
@WebServlet("/AddNewCard")
public class CardAdd extends HttpServlet {
	private CardService cardService = new CardService();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardAdd() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		long shop_id = Long.parseLong(req.getParameter("shop_id"));
		long shopmember_id = Long.parseLong(req.getParameter("shopmember_id"));
		String name = req.getParameter("name");
		int type = Integer.parseInt(req.getParameter("type"));
		int price = Integer.parseInt(req.getParameter("price"));
		int balance = Integer.parseInt(req.getParameter("balance"));
		String start_time = req.getParameter("start_time");
		String expired_time = req.getParameter("expired_time");
		String str = cardService.addCard(shop_id, name, shopmember_id, type, price, balance, start_time, expired_time);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
