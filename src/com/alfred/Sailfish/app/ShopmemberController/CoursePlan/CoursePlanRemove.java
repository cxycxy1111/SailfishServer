package com.alfred.Sailfish.app.ShopmemberController.CoursePlan;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alfred.Sailfish.app.ShopmemberService.CoursePlanService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class CoursePlanDelete
 */
@WebServlet(name = "CoursePlanRemove",urlPatterns = "/CoursePlanRemove")
public class CoursePlanRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CoursePlanService coursePlanService = new CoursePlanService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoursePlanRemove() {
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
		} else {
			long id = MethodTool.reqParseToLong(req, "id");
			long lmu_id = MethodTool.getSessionValueToLong(session, "sm_id");
			long s_id = MethodTool.getSessionValueToLong(session,"s_id");
			String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
			String str = coursePlanService.remove(s_id,sm_type,id, lmu_id);
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
