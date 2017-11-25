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
 * Servlet implementation class AddNewMemberCard
 * localhost:8080/Sailfish/AddNewMemberCard?smid=1&mid=2&cid=1&balance=50&stime=2017-09-28 17:00:00&etime=2018-09-28+17:00:00
 */
@WebServlet(urlPatterns = "/AddNewMemberCard")
public class MemberCardAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberCardService memberCardService = new MemberCardService();
    
	/**
     * @see HttpServlet#HttpServlet()
     */
    public MemberCardAdd() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        long shopmember_id = Long.parseLong(request.getParameter("smid"));
        long member_id = Long.parseLong(request.getParameter("mid"));
        long card_id = Long.parseLong(request.getParameter("cid"));
        int balance = Integer.parseInt(request.getParameter("balance"));
        String start_time = request.getParameter("stime");
        String expired_time = request.getParameter("etime");
        String str = memberCardService.add(member_id, card_id,
        		shopmember_id, balance, start_time, expired_time);
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
