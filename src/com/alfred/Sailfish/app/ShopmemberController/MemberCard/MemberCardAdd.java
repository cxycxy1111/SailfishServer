package com.alfred.Sailfish.app.ShopmemberController.MemberCard;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.ShopmemberService.MemberCardService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class AddNewMemberCard
 * localhost:8080/Sailfish/AddNewMemberCard?smid=1&mid=2&cid=1&balance=50&stime=2017-09-28 17:00:00&etime=2018-09-28+17:00:00
 */
@WebServlet(name = "MemberCardAdd",urlPatterns = "/AddNewMemberCard")
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
		HttpSession session = request.getSession(false);
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		}else {
			long s_id = MethodTool.getSessionValueToLong(session,"s_id");
			long shopmember_id = MethodTool.getSessionValueToLong(session,"sm_id");
			long member_id = Long.parseLong(request.getParameter("mid"));
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			long card_id = Long.parseLong(request.getParameter("cid"));
			int balance = Integer.parseInt(request.getParameter("balance"));
			String start_time = request.getParameter("stime");
			String expired_time = request.getParameter("etime");
			String str = memberCardService.add(s_id,sm_type,member_id, card_id,
					shopmember_id, balance, start_time, expired_time);
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
			out.append(str);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
