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
 * Servlet implementation class ProlongExpiredTime
 */
@WebServlet(name = "MemberCardProlongExpiredTime",urlPatterns = "/ProlongExpiredTime")
public class MemberCardProlong extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberCardService memberCardService = new MemberCardService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberCardProlong() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		long mc_id = Long.parseLong(request.getParameter("mc_id"));
		long lmu_id = Long.parseLong(request.getParameter("lmu_id"));
		String expiredTime = request.getParameter("et");
		String str = memberCardService.changeExpiredTime(lmu_id, mc_id, expiredTime);
		System.out.println(str);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
