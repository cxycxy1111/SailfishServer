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
		HttpSession session = req.getSession(false);
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		}else {
			long id = Long.parseLong(req.getParameter("id"));
			long shopmember_id = MethodTool.getSessionValueToLong(session,"sm_id");
			long s_id = MethodTool.getSessionValueToLong(session,"s_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String name = req.getParameter("name");
			int price = Integer.parseInt(req.getParameter("price"));
			int balance = Integer.parseInt(req.getParameter("balance"));
			String start_time = req.getParameter("start_time");
			String expired_time = req.getParameter("expired_time");
			String str = cardService.modifyCard(s_id,sm_type,id, name, shopmember_id, price, balance, start_time, expired_time);
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
