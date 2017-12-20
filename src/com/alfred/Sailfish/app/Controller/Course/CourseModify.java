package com.alfred.Sailfish.app.Controller.Course;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alfred.Sailfish.app.Service.CourseService;
import com.alfred.Sailfish.app.Util.MethodTool;

/**
 * Servlet implementation class CourseModify
 */
@WebServlet(name = "CourseModify",urlPatterns = "/CourseModify")
public class CourseModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
    CourseService courseDAO = new CourseService();
	
    /**
     * 修改课程
     * 请求示例：
     * @see HttpServlet#HttpServlet()
     */
    public CourseModify() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		long c_id = MethodTool.reqParseToLong(req, "c_id");
		long lmu_id = MethodTool.reqParseToLong(req, "lmu_id");
		String name = req.getParameter("name");
		int last_time = MethodTool.reqParseToInt(req, "last_time");
		int max_book_num = MethodTool.reqParseToInt(req, "max_book_num");
		String summary = req.getParameter("summary");
		String str = courseDAO.modify(c_id, lmu_id, name, last_time, max_book_num, summary);
		System.out.println(str);

		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
