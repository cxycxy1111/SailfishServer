package com.alfred.Sailfish.app.Controller.CourseSupportedCard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alfred.Sailfish.app.Service.CourseSupportedCardService;
import com.alfred.Sailfish.app.Util.MethodTool;

/**
 * Servlet implementation class QuerySupportedCard
 */
@WebServlet(name = "SupportedCardQuery",urlPatterns = "/SupportedCardQuery")
public class SupportedCardQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CourseSupportedCardService courseSupportedCardService = new CourseSupportedCardService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupportedCardQuery() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		long c_id = MethodTool.reqParseToLong(req, "c_id");
		long s_id = MethodTool.reqParseToLong(req,"s_id");
		String str = courseSupportedCardService.query(s_id,c_id);
		System.out.println(MethodTool.getTime() +  ",Response:" + str);
		out.append(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
