package com.alfred.Sailfish.app.Controller.Shop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alfred.Sailfish.app.Service.ShopService;
import com.alfred.Sailfish.app.Util.MethodTool;

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
		long sm_id = MethodTool.reqParseToLong(request, "sm_id");
		String str = shopService.queryShopIdByShopmemberId(sm_id);
		System.out.println(MethodTool.getTime() +  ",Response:" + str);
		out.append(str);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
