package com.alfred.Sailfish.app.Controller.Course;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.Service.CourseService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class QueryCourseList
 */
@WebServlet(name = "CourseListQuery",urlPatterns = "/CourseListQuery")
public class CourseListQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CourseService courseService = new CourseService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseListQuery() {
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
			String str = courseService.queryList(s_id);
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
