package com.alfred.Sailfish.app.Controller.Shopmember;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alfred.Sailfish.app.Service.ShopmemberService;
import com.alfred.Sailfish.app.Util.MethodTool;

/**
 * Servlet implementation class ShopmemberQueryDetail
 */
@WebServlet(name = "ShopmemberDetailQuery",urlPatterns = "/ShopmemberQueryDetail")
public class ShopmemberQueryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ShopmemberService shopmemberService = new ShopmemberService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopmemberQueryDetail() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		long sm_id = MethodTool.reqParseToLong(request, "sm_id");
		long s_id = MethodTool.reqParseToLong(request, "s_id");
		String str = shopmemberService.queryShopmemberDetail(sm_id, s_id);
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
