package com.alfred.Sailfish.app.Controller.CoursePlanTeacher;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.Service.CoursePlanTeacherService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * http://localhost:8080/Sailfish/CoursePlanTeacherAdd?m=3_1_2-2_1_3
 * Servlet implementation class CoursePlanTeacherAdd
 */
@WebServlet(name = "CoursePlanTeacherModify",urlPatterns = "/CoursePlanTeacherModify")
public class CoursePlanTeacherModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoursePlanTeacherService coursePlanTeacherService = new CoursePlanTeacherService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoursePlanTeacherModify() {
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
			long s_id = MethodTool.getSessionValueToLong(session,"s_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String s = req.getParameter("m");
			String [] strs = s.split("-");
			StringBuilder builder = new StringBuilder();
			for (int i = 0;i < strs.length;i++) {
				String [] s_strs = strs[i].split("_");
				String result = coursePlanTeacherService.modify(s_id,sm_type,Integer.parseInt(s_strs[0]), Long.parseLong(s_strs[1]), Long.parseLong(s_strs[2]));
				builder.append(result);
				builder.append(",");
			}
			String result = builder.toString();
			String js_result;
			if (result.contains(Reference.EXE_FAIL)) {
				if (!result.contains(Reference.EXE_SUC)) {
					js_result = MethodTool.tfs(Reference.EXE_FAIL);
				}else {
					js_result = MethodTool.tfs(Reference.EXE_PARTLY_FAIL);
				}
			}else {
				js_result = MethodTool.tfs(Reference.EXE_SUC);
			}
			out.append(js_result);
			System.out.println(MethodTool.getTime() +  ",Response:" + js_result);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
