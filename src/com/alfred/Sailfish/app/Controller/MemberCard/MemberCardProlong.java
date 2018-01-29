package com.alfred.Sailfish.app.Controller.MemberCard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.MTOM;

import com.alfred.Sailfish.app.Service.MemberCardService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class ProlongExpiredTime
 */
@WebServlet(name = "MemberCardProlongExpiredTime",urlPatterns = "/ProlongExpiredTime")
public class MemberCardProlong extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberCardService memberCardService = new MemberCardService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberCardProlong() {
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
		if (session == null){
			out.append(Reference.SESSION_EXPIRED);
		}else {
			long mc_id = Long.parseLong(request.getParameter("mc_id"));
			long lmu_id = MethodTool.getSessionValueToLong(session,"sm_id");
			String expiredTime = request.getParameter("et");
			String str = memberCardService.changeExpiredTime(lmu_id, mc_id, expiredTime);
			System.out.println(MethodTool.getTime() + ",Response:" + str);
			out.append(str);
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
