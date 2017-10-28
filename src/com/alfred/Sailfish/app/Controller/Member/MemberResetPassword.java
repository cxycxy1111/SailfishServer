package com.alfred.Sailfish.app.Controller.Member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alfred.Sailfish.app.Service.MemberService;
import com.alfred.Sailfish.app.Util.MethodTool;

/**
 * Servlet implementation class MemberResetPassword
 */
@WebServlet("/MemberResetPassword")
public class MemberResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberResetPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		long m_id = MethodTool.reqParseToLong(request, "m_id");
		long sm_id = MethodTool.reqParseToLong(request, "sm_id");
		String newpwd = request.getParameter("newpwd");
		String str = memberService.resetPassword(sm_id, m_id, newpwd);
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
