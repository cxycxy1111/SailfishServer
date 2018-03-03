package com.alfred.Sailfish.app.ShopmemberController.Card;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.ShopmemberService.CardService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class AddNewCard
 * 请求示例
 * http://localhost:8080/Sailfish/AddNewCard?shop_id=5&shopmember_id=1&name=余额卡&type=1&price=200&balance=210&start_time=2017-09-25%2000:00:00&expired_time=2018-09-25%2000:00:00
 */
@WebServlet(name = "CardAdd",urlPatterns = "/AddNewCard")
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
		HttpSession session = req.getSession(false);
		PrintWriter out = resp.getWriter();
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		}else {
			long s_id = MethodTool.getSessionValueToLong(session,"s_id");
			long sm_id = MethodTool.getSessionValueToLong(session,"sm_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String name = req.getParameter("name");
			int type = Integer.parseInt(req.getParameter("type"));
			int price = Integer.parseInt(req.getParameter("price"));
			int balance = Integer.parseInt(req.getParameter("balance"));
			String start_time = req.getParameter("start_time");
			String expired_time = req.getParameter("expired_time");
			String str = cardService.addCard(sm_type,s_id, name, sm_id, type, price, balance, start_time, expired_time);
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
			out.append(str);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
