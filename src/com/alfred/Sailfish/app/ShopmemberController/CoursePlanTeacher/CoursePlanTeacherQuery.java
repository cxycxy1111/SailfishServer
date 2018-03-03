package com.alfred.Sailfish.app.ShopmemberController.CoursePlanTeacher;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.ShopmemberService.CoursePlanTeacherService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

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
		HttpSession session = req.getSession(false);
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		}else {
			long cp_id = MethodTool.reqParseToLong(req, "cp_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String str = coursePlanTeacherService.queryList(cp_id);
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
