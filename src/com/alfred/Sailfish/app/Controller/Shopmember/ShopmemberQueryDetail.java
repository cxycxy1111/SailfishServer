package com.alfred.Sailfish.app.Controller.Shopmember;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.Service.ShopmemberService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class ShopmemberQueryDetail
 */
@WebServlet(name = "ShopmemberDetailQuery",urlPatterns = "/ShopmemberQueryDetail")
public class ShopmemberQueryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShopmemberService shopmemberService = new ShopmemberService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopmemberQueryDetail() {
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
			long sm_id = MethodTool.reqParseToLong(request, "sm_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String str = shopmemberService.queryShopmemberDetail(sm_id, s_id,sm_type);
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
