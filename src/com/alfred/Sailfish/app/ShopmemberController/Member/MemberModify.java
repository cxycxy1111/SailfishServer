package com.alfred.Sailfish.app.ShopmemberController.Member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.ShopmemberService.MemberService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class ModifyMember
 */
@WebServlet(name = "MemberModify",urlPatterns = "/ModifyMember")
public class MemberModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberModify() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter w = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
			w.append(Reference.SESSION_EXPIRED);
		}else {
			long s_id = MethodTool.getSessionValueToLong(session,"s_id");
			long member_id = Long.parseLong(request.getParameter("member_id"));
			long shopmember_id = MethodTool.getSessionValueToLong(session,"sm_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String name = request.getParameter("name");
			String birthday = request.getParameter("birthday");
			String phone = request.getParameter("phone");
			String im = request.getParameter("im");
			String str = memberService.modifyMember(s_id,sm_type,member_id, shopmember_id, name, birthday, phone, im);
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
			w.append(str);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
