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
 * Servlet implementation class CoursePlanDelete
 */
@WebServlet(name = "CoursePlanRemove",urlPatterns = "/CoursePlanRemove")
public class CoursePlanRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoursePlanService coursePlanService = new CoursePlanService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoursePlanRemove() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		long id = MethodTool.reqParseToLong(req, "id");
		long lmu_id = MethodTool.reqParseToLong(req, "lmu_id");
		String str = coursePlanService.remove(id, lmu_id);
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
