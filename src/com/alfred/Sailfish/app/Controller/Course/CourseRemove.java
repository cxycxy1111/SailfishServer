package com.alfred.Sailfish.app.Controller.Course;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.MTOM;

import com.alfred.Sailfish.app.Service.CourseService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class CourseDelete
 */
@WebServlet(name = "CourseDelete",urlPatterns = "/CourseDelete")
public class CourseRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CourseService courseService = new CourseService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseRemove() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		resp.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession(false);
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		} else {
			long id = MethodTool.reqParseToLong(req, "id");
			long s_id = MethodTool.getSessionValueToLong(session,"s_id");
			long lmu_id = MethodTool.getSessionValueToLong(session, "sm_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String str = courseService.remove(s_id,sm_type,id, lmu_id);
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
