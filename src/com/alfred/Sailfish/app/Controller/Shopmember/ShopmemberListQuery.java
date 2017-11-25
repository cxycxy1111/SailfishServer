package com.alfred.Sailfish.app.Controller.Shopmember;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.alfred.Sailfish.app.Service.ShopmemberService;
import com.alfred.Sailfish.app.Util.MethodTool;

/**
 * Servlet implementation class QueryMemberList
 */
@WebServlet("/QueryShopmemberList")
public class ShopmemberListQuery extends HttpServlet {
	private ShopmemberService shopmemberService = new ShopmemberService();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopmemberListQuery() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		long shop_id = 0;
		HttpSession session = request.getSession();
		shop_id = Long.parseUnsignedLong(request.getParameter("shop_id"));
		int type = MethodTool.reqParseToInt(request, "type");
		session.setAttribute("shop_id", shop_id);
		String str = shopmemberService.queryShopmemberDetail(shop_id,type);
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
