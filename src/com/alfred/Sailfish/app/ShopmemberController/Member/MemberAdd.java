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
 * Servlet implementation class AddNewMember
 * 请求示例
 * AddNewMember?shop_id=5&shop_member_id=1&name=邓伟雄&login_name=dengweixiong44&password=111&phone=13751729017&im=111&birthday=2017-01-01 00:00:00
 */
@WebServlet(name = "MemberAdd",urlPatterns = "/AddNewMember")
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
		HttpSession session = request.getSession(false);
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		} else {
			long shop_id = MethodTool.getSessionValueToLong(session,"s_id");
			long shopmember_id = MethodTool.getSessionValueToLong(session,"sm_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String name = request.getParameter("name");
			String login_name = request.getParameter("login_name");
			String birthday = request.getParameter("birthday");
			String phone = request.getParameter("phone");
			String im = request.getParameter("im");
			String password = request.getParameter("password");
			String str = memberService.addMember(sm_type,login_name, shop_id, shopmember_id, name, password, birthday, phone, im);
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
