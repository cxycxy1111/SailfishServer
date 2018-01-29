package com.alfred.Sailfish.app.Controller.Member;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.MTOM;

import com.alfred.Sailfish.app.Service.MemberService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * Servlet implementation class deleteMember
 */
@WebServlet(name = "MemberRemove",urlPatterns = "/removeMember")
public class MemberRemove extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberRemove() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
			out.append(Reference.SESSION_EXPIRED);
		}else {
			long sm_id = MethodTool.getSessionValueToLong(session,"sm_id");
			long m_id = Long.parseLong(request.getParameter("member_id"));
			System.out.println("SM_ID is :" + sm_id);
			String str = memberService.removeMember(m_id, sm_id);
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
