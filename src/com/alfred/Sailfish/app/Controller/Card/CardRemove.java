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
 * Servlet implementation class RemoveCard
 */
@WebServlet("/RemoveCard")
public class CardRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CardService cardService = new CardService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardRemove() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = initResponse(resp);
		resp.setCharacterEncoding("utf-8");
		long card_id = Long.parseLong(req.getParameter("card_id"));
		long shopmember_id = Long.parseLong(req.getParameter("shopmember_id"));
		String str = cardService.removeCard(card_id, shopmember_id);
		System.out.println(str);
		out.append(str);
	}
	
	private PrintWriter initResponse(HttpServletResponse resp) {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
