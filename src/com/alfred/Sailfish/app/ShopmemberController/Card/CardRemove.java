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
 * Servlet implementation class RemoveCard
 */
@WebServlet(name = "CardRemove",urlPatterns = "/RemoveCard")
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
		HttpSession session = req.getSession(false);
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		} else {
			resp.setCharacterEncoding("utf-8");
			long card_id = Long.parseLong(req.getParameter("card_id"));
			long shopmember_id = MethodTool.getSessionValueToLong(session,"sm_id");
			long s_id = MethodTool.getSessionValueToLong(session,"s_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String str = cardService.removeCard(s_id,sm_type,card_id, shopmember_id);
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
			out.append(str);
		}
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
