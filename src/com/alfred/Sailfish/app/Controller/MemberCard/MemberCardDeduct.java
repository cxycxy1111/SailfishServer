package com.alfred.Sailfish.app.Controller.MemberCard;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.Service.MemberCardService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class Deduct
 */
@WebServlet(name = "MemberCardDeduct",urlPatterns = "/Deduct")
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
		HttpSession session = request.getSession(false);
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		}else {
			long s_id = MethodTool.getSessionValueToLong(session,"s_id");
			int num = Integer.valueOf(request.getParameter("num"));
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			long mc_id = Long.valueOf(request.getParameter("mc_id"));
			long shop_member_id = MethodTool.getSessionValueToLong(session,"sm_id");
			String invalid_date = request.getParameter("invalid_date");
			String str = memberCardService.reduceBalance(s_id,sm_type,mc_id, shop_member_id, num,invalid_date);
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
