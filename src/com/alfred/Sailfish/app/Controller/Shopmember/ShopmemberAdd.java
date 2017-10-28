package com.alfred.Sailfish.app.Controller.Shopmember;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alfred.Sailfish.app.Service.ShopmemberService;

/**
 * Servlet implementation class AddNewShopMember
 */
@WebServlet("/AddNewShopMember")
public class ShopmemberAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShopmemberService shopmemberService = new ShopmemberService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopmemberAdd() {
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
		String user_name = req.getParameter("user_name");
		String password = req.getParameter("password");
		int type = Integer.parseInt(req.getParameter("type"));
		String str = shopmemberService.addShopmember(shop_id, shopmember_id, name, user_name, type, password);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
