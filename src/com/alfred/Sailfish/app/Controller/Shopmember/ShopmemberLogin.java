package com.alfred.Sailfish.app.Controller.Shopmember;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.alfred.Sailfish.app.DAO.ShopmemberDAO;
import com.alfred.Sailfish.app.Service.ShopmemberService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class Login
 */
@WebServlet(name = "ShopMemberLogin",urlPatterns = "/ShopMemberLogin")
public class ShopmemberLogin extends HttpServlet {
	private ShopmemberService shopMemberService = new ShopmemberService();
	private ShopmemberDAO shopmemberDAO = new ShopmemberDAO();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopmemberLogin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		String user_name = request.getParameter("user_name");
		String password = request.getParameter("password");
		String str = shopMemberService.loginCheck(user_name, password);
		if (str.startsWith("[")) {
			long sm_id = shopmemberDAO.queryIdByUserName(user_name);
			long s_id = shopmemberDAO.queryShopIdByUserName(user_name);
			session.setAttribute("sm_id",sm_id);
			session.setAttribute("s_id",s_id);
			session.setAttribute("sm_type",shopmemberDAO.queryShopmemberTypeById(sm_id));
		}
		System.out.println(MethodTool.getTime() +  ",Response:" + str);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
