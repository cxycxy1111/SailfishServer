package com.alfred.Sailfish.app.Controller.Shopmember;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alfred.Sailfish.app.Service.ShopmemberService;
import com.alfred.Sailfish.app.Util.MethodTool;

/**
 * Servlet implementation class ShopMemberRegister
 */
@WebServlet("/ShopMemberRegister")
public class ShopmemberRegister extends HttpServlet {
	
	private ShopmemberService shopMemberService = new ShopmemberService();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopmemberRegister() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		String login_name = request.getParameter("login_name");
		String password = request.getParameter("pwd");
		String name = request.getParameter("name");
		long shop_id = MethodTool.reqParseToLong(request, "s_id");
		String str = shopMemberService.register(shop_id, login_name, name, password);
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
