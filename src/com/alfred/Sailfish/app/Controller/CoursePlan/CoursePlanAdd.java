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
 * http://localhost:8080/Sailfish/CoursePlanAdd?c_id=2&cr_id=3&lmu_id=5&s_time=2017-12-28%2000:00:00&e_time=2017-12-28%2000:40:00&remark=123
 * Servlet implementation class CoursePlanAdd
 */
@WebServlet("/CoursePlanAdd")
public class CoursePlanAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoursePlanService coursePlanService = new CoursePlanService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoursePlanAdd() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		long c_id = MethodTool.reqParseToLong(req, "c_id");
		long cr_id = MethodTool.reqParseToLong(req, "cr_id");
		long lmu_id = MethodTool.reqParseToLong(req, "lmu_id");
		String s_time = req.getParameter("s_time");
		String e_time = req.getParameter("e_time");
		String remark = req.getParameter("remark");
		String str = coursePlanService.add(c_id, cr_id, lmu_id, s_time, e_time, remark);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
