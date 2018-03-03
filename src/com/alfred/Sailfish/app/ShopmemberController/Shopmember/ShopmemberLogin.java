package com.alfred.Sailfish.app.ShopmemberController.Shopmember;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.alfred.Sailfish.app.DAO.ShopmemberDAO;
import com.alfred.Sailfish.app.ShopmemberService.ShopmemberService;
import com.alfred.Sailfish.app.Util.MethodTool;

/**
 * Servlet implementation class Login
 */
@WebServlet(name = "ShopMemberLogin",urlPatterns = "/ShopMemberLogin")
public class ShopmemberLogin extends HttpServlet implements Serializable{
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
		String ip_address = request.getRemoteAddr();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String request_time = simpleDateFormat.format(date);
		PrintWriter out = response.getWriter();
		String user_name = request.getParameter("user_name");
		String password = request.getParameter("password");
		String system_version = request.getParameter("system_version");
		String system_model = request.getParameter("system_model");
		String device_brand = request.getParameter("device_brand");
		String imei = request.getParameter("imei");
		String app_version = request.getParameter("app_version");
		if (request.getSession(false) == null) {
			String str = shopMemberService.loginCheck(request_time,ip_address,system_version,system_model,device_brand,imei,app_version,user_name,password);
			if (str.startsWith("[")) {
				HttpSession session = request.getSession(true);
				System.out.println("ShopmemberLogin Session:" + session.getId());
				long sm_id = shopmemberDAO.queryIdByUserName(user_name);
				long s_id = shopmemberDAO.queryShopIdByUserName(user_name);
				session.setAttribute("sm_id",sm_id);
				session.setAttribute("s_id",s_id);
				session.setAttribute("sm_type",shopmemberDAO.queryShopmemberTypeById(sm_id));
				System.out.println("ShopmemberLogin Session:" + session.getId());
			}
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
			out.append(str);
		} else {
			out.append(shopMemberService.loginCheck(request_time,ip_address,system_version,system_model,device_brand,imei,app_version,user_name,password));
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
