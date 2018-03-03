package com.alfred.Sailfish.app.ShopmemberController.Shopmember;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.ShopmemberService.ShopmemberService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class AddNewShopMember
 */
@WebServlet(name = "ShopMemberAdd",urlPatterns = "/AddNewShopMember")
public class ShopmemberAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShopmemberService shopmemberService = new ShopmemberService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopmemberAdd() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession(false);
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		}else {
			long shop_id = MethodTool.getSessionValueToLong(session,"s_id");
			long shopmember_id = MethodTool.getSessionValueToLong(session,"sm_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String name = req.getParameter("name");
			String user_name = req.getParameter("user_name");
			String password = req.getParameter("password");
			int type = Integer.parseInt(req.getParameter("type"));
			String str = shopmemberService.addShopmember(shop_id,sm_type, shopmember_id, name, user_name, type, password);
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
