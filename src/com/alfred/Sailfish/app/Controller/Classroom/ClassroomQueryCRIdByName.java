package com.alfred.Sailfish.app.Controller.Classroom;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.Service.ClassroomService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

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
		HttpSession session = request.getSession(false);
		if (session != null) {
			long s_id = MethodTool.getSessionValueToLong(session, "s_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String cr_name = request.getParameter("cr_name");
			String str = classroomService.queryCRIdByCRName(s_id,cr_name);
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
			out.append(str);
		}else {
			out.append(Reference.SESSION_EXPIRED);
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
