package com.alfred.Sailfish.app.MemberController.CoursePlanBook;

import com.alfred.Sailfish.app.MemberService.MCoursePlanBookService;
import com.alfred.Sailfish.app.Util.BaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.MTOM;
import java.io.IOException;

@WebServlet(name = "MBookListQuery",urlPatterns = "/mBookListQuery")
public class MBookListQuery extends BaseServlet {

    private MCoursePlanBookService mCoursePlanBookService = new MCoursePlanBookService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            long m_id = MethodTool.getSessionValueToLong(session,"m_id");
            String str = mCoursePlanBookService.queryBookList(m_id);
            System.out.println(MethodTool.getTime() +  ",Response:" + str);
            response.getWriter().append(str);
        }else {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }
    }
}
