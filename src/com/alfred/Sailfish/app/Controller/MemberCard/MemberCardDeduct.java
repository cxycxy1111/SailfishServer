package com.alfred.Sailfish.app.Controller.MemberCard;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alfred.Sailfish.app.Service.MemberCardService;

/**
 * Servlet implementation class Deduct
 */
@WebServlet("/Deduct")
public class MemberCardDeduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberCardService memberCardService = new MemberCardService();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberCardDeduct() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		int num = Integer.valueOf(request.getParameter("num"));
		long mc_id = Long.valueOf(request.getParameter("mc_id"));
		long shop_member_id = Long.parseLong(request.getParameter("sm_id"));
		String str = memberCardService.reduceBalance(mc_id, shop_member_id, num);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
