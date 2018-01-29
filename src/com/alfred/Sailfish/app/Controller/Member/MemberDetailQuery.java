package com.alfred.Sailfish.app.Controller.Member;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.Service.MemberService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class QueryMemberDetail
 */
@WebServlet(name = "MemberDetailQuery",urlPatterns = "/QueryMemberDetail")
public class MemberDetailQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDetailQuery() {
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
			long member_id = Long.parseUnsignedLong(request.getParameter("member_id"));
			System.out.println(member_id);
			long shop_id = MethodTool.getSessionValueToLong(session,"s_id");
			String str = memberService.queryMemberDetail(shop_id, member_id);
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
