package com.alfred.Sailfish.app.Controller.CoursePlanTeacher;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alfred.Sailfish.app.Service.CoursePlanTeacherService;
import com.alfred.Sailfish.app.Util.MethodTool;

/**
 * Servlet implementation class CoursePlanTeacherQuery
 */
@WebServlet(name = "CoursePlanTeacherQuery",urlPatterns = "/CoursePlanTeacherQuery")
public class CoursePlanTeacherQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoursePlanTeacherService coursePlanTeacherService = new CoursePlanTeacherService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoursePlanTeacherQuery() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		long cp_id = MethodTool.reqParseToLong(req, "cp_id");
		String str = coursePlanTeacherService.queryList(cp_id);
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
