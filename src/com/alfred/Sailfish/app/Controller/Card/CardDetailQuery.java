package com.alfred.Sailfish.app.Controller.Card;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.Service.CardService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class CardDetailQuery
 */
@WebServlet(name = "CardDetailQuery",urlPatterns = "/CardDetailQuery")
public class CardDetailQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CardService cardService = new CardService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardDetailQuery() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		}else {
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			long s_id = MethodTool.getSessionValueToLong(session,"s_id");
			long c_id = MethodTool.reqParseToLong(request, "c_id");
			String str = cardService.queryCardDetail(sm_type,s_id,c_id);
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
			out.append(str);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
