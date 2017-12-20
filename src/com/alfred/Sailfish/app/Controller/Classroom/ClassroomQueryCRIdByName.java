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
 * Servlet implementation class ClassroomQueryCRIdByName
 */
@WebServlet(name = "ClassroomIdQueryByName",urlPatterns = "/ClassroomQueryCRIdByName")
public class ClassroomQueryCRIdByName extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClassroomService classroomService = new ClassroomService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClassroomQueryCRIdByName() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		long s_id = MethodTool.reqParseToLong(request, "s_id");
		String cr_name = request.getParameter("cr_name");
		String str = classroomService.queryCRIdByCRName(s_id,cr_name);
		System.out.println(str);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
