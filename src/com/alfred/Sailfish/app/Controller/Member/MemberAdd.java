package com.alfred.Sailfish.app.Controller.Member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alfred.Sailfish.app.Service.MemberService;

/**
 * Servlet implementation class AddNewMember
 * 请求示例
 * AddNewMember?shop_id=5&shop_member_id=1&name=邓伟雄&login_name=dengweixiong44&password=111&phone=13751729017&im=111&birthday=2017-01-01 00:00:00
 */
@WebServlet("/AddNewMember")
public class MemberAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberAdd() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		long shop_id = Long.parseUnsignedLong(request.getParameter("shop_id"));
		long shopmember_id = Long.parseUnsignedLong(request.getParameter("shop_member_id"));
		String name = request.getParameter("name");
		String login_name = request.getParameter("login_name");
		String birthday = request.getParameter("birthday");
		String phone = request.getParameter("phone");
		String im = request.getParameter("im");
		String password = request.getParameter("password");
		String str = memberService.addMember(login_name, shop_id, shopmember_id, name, password, birthday, phone, im);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
