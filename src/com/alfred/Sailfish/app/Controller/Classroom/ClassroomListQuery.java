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
 * Servlet implementation class ClassroomListQuery
 */
@WebServlet("/ClassroomListQuery")
public class ClassroomListQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassroomService classroomService = new ClassroomService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassroomListQuery() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		long s_id = MethodTool.reqParseToLong(req, "s_id");
		String str = classroomService.getList(s_id);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
