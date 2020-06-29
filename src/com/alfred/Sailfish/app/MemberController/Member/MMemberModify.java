package com.alfred.Sailfish.app.MemberController.Member;

import com.alfred.Sailfish.app.MemberService.MMemberService;
import com.alfred.Sailfish.app.ShopmemberService.MemberService;
import com.alfred.Sailfish.app.Util.MemberBaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MMemberModify",urlPatterns = "/MModifyMember")
public class MMemberModify extends MemberBaseServlet {
	private static final long serialVersionUID = 1L;
	private MMemberService mMemberService = new MMemberService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	super.doGet(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request,response);
	}

	@Override
	protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long s_id, long m_id) throws IOException {
		super.dealWithSessionAlive(request, response, session, out, s_id, m_id);
		long member_id = MethodTool.getSessionValueToLong(session,"m_id");
		String name = request.getParameter("name");
		String birthday = request.getParameter("birthday");
		String phone = request.getParameter("phone");
		String im = request.getParameter("im");
		String str = mMemberService.modifyMember(s_id,member_id, name, birthday, phone, im);
		System.out.println(MethodTool.getTime() +  ",Response:" + str);
		out.append(str);
	}

	@Override
	protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
		super.dealWithSessionDead(request, response, out);
	}
}
