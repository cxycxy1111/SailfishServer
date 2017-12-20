package com.alfred.Sailfish.app.Controller.CoursePlan;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alfred.Sailfish.app.Service.CoursePlanService;
import com.alfred.Sailfish.app.Util.MethodTool;

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
		long s_id = MethodTool.reqParseToLong(req, "s_id");
		String str = coursePlanService.queryByShopId(s_id);
		out.append(str);
		System.out.println("排课列表：" + str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
