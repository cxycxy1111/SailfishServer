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
 * Servlet implementation class QueryCardList
 * 请求示例
 * http://localhost:8080/Sailfish/QueryCardList?shop_id=5
 */
@WebServlet(name = "CardListQuery",urlPatterns = "/QueryCardList")
public class CardListQuery extends HttpServlet {
	private CardService cardService = new CardService();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CardListQuery() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session != null) {
			long shop_id = MethodTool.getSessionValueToLong(session,"s_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			int type = MethodTool.reqParseToInt(request,"type");
			String str = cardService.queryCardList(sm_type,shop_id,type);
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
			out.append(str);
		}else {
			out.append(Reference.SESSION_EXPIRED);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
