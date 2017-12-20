package com.alfred.Sailfish.app.Controller.Classroom;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alfred.Sailfish.app.Service.ClassroomService;
import com.alfred.Sailfish.app.Util.MethodTool;

/**
 * testeds
 * 请求示例
 * http://localhost:8080/Sailfish/ClassroomModify?cr_id=3&name=广州大剧院
 * Servlet implementation class ClassroomModify
 */
@WebServlet(name = "ClassroomModify",urlPatterns = "/ClassroomModify")
public class ClassroomModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassroomService classroomService = new ClassroomService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassroomModify() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		long cr_id = MethodTool.reqParseToLong(req, "cr_id");
		String name = req.getParameter("name");
		String str = classroomService.modify(cr_id, name);
		System.out.println(str);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
