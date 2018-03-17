package com.alfred.Sailfish.app.MemberController.Member;

import com.alfred.Sailfish.app.MemberService.MMemberService;
import com.alfred.Sailfish.app.ShopmemberService.MemberService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class ModifyMember
 */
@WebServlet(name = "MMemberModify",urlPatterns = "/MModifyMember")
public class MMemberModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MMemberService mMemberService = new MMemberService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MMemberModify() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter w = response.getWriter();
		HttpSession session = request.getSession(false);
		if (session == null) {
			w.append(Reference.SESSION_EXPIRED);
		}else {
			long s_id = MethodTool.getSessionValueToLong(session,"s_id");
			long member_id = MethodTool.getSessionValueToLong(session,"m_id");
			String name = request.getParameter("name");
			String birthday = request.getParameter("birthday");
			String phone = request.getParameter("phone");
			String im = request.getParameter("im");
			String str = mMemberService.modifyMember(s_id,member_id, name, birthday, phone, im);
			System.out.println(MethodTool.getTime() +  ",Response:" + str);
			w.append(str);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
