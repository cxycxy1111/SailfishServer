package com.alfred.Sailfish.app.Controller.Shop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.Service.ShopService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class ShopmemberIdQuery
 */
@WebServlet(name = "ShopIdQueryByShopMemberId",urlPatterns = "/ShopIdQueryByShopMemberId")
public class ShopIdQueryByShopMemberId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShopService shopService = new ShopService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopIdQueryByShopMemberId() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession(false);
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		}else {
			long sm_id = MethodTool.reqParseToLong(request, "sm_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String str = shopService.queryShopIdByShopmemberId(sm_id);
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
			out.append(str);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
