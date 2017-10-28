package com.alfred.Sailfish.app.Controller.CoursePlanTeacher;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alfred.Sailfish.app.Service.CoursePlanTeacherService;

/**
 * http://localhost:8080/Sailfish/CoursePlanTeacherAdd?m=3_1_2-2_1_3
 * Servlet implementation class CoursePlanTeacherAdd
 */
@WebServlet("/CoursePlanTeacherModify")
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
		String s = req.getParameter("m");
		String [] str = s.split("-");
		for (int i = 0;i < str.length;i++) {
			String [] strs = str[i].split("_");
			String result = coursePlanTeacherService.modify(Integer.parseInt(strs[0]), Long.parseLong(strs[1]), Long.parseLong(strs[2]));
			out.append(result);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
