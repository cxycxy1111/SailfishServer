package com.alfred.Sailfish.app.ShopmemberController.Course;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.ShopmemberService.CourseService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * http://localhost:8080/Sailfish/courseAdd?s_id=5&lmu_id=1&name=余额卡1111&type=2&last_time=40&max_book_num=50&summary=测试会员5
 * Servlet implementation class CourseAdd
 */
@WebServlet(name = "CourseAdd",urlPatterns = "/courseAdd")
public class CourseAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CourseService courseService = new CourseService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseAdd() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession(false);
		String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		} else {
			long s_id = MethodTool.getSessionValueToLong(session, "s_id");
			long lmu_id = MethodTool.getSessionValueToLong(session, "sm_id");
			String name = request.getParameter("name");
			int type = MethodTool.reqParseToInt(request, "type");
			int last_time = MethodTool.reqParseToInt(request, "last_time");
			int max_book_num = MethodTool.reqParseToInt(request, "max_book_num");
			String summary = request.getParameter("summary");
			String str = courseService.add(s_id,sm_type, lmu_id, name, type, last_time, max_book_num, summary);
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
			out.append(str);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
