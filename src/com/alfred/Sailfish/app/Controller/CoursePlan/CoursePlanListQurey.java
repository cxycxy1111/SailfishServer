package com.alfred.Sailfish.app.Controller.CoursePlan;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.Service.CoursePlanService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * http://localhost:8080/Sailfish/CoursePlanListQurey?s_id=1
 * Servlet implementation class CoursePlanListQurey
 */
@WebServlet(name = "CoursePlanListQurey",urlPatterns = "/CoursePlanListQurey")
public class CoursePlanListQurey extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoursePlanService coursePlanService = new CoursePlanService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoursePlanListQurey() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		}else {
			long s_id = MethodTool.getSessionValueToLong(session, "s_id");
			String str = coursePlanService.queryByShopId(s_id);
			out.append(str);
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
