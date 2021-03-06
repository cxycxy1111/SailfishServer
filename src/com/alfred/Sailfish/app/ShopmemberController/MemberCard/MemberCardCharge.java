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
 * Servlet implementation class Charge
 */
@WebServlet(name = "MemberCardCharge",urlPatterns = "/Charge")
public class MemberCardCharge extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberCardService memberCardService = new MemberCardService();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberCardCharge() {
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
			long member_card_id = Long.parseLong(request.getParameter("mc_id"));
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			long last_modify_user = MethodTool.getSessionValueToLong(session,"sm_id");
			int num = Integer.parseInt(request.getParameter("num"));
			String new_invalid_date = request.getParameter("invalid_date");
			long charge_fee = 0;
			if (request.getParameter("charge_fee") != null) {
				charge_fee = Long.parseLong(request.getParameter("charge_fee"));
			}
			String str = memberCardService.increaseBalance(s_id,sm_type,member_card_id, last_modify_user, new_invalid_date, num,charge_fee);
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
