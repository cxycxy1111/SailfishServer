package com.alfred.Sailfish.app.MemberController.CoursePlanBook;

import com.alfred.Sailfish.app.MemberService.MCoursePlanBookAndAttendService;
import com.alfred.Sailfish.app.Util.BaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "MBookDetailQuery",urlPatterns = "/mBookDetailQuery")
public class MBookDetailQuery extends BaseServlet {

    private MCoursePlanBookAndAttendService mCoursePlanBookAndAttendService = new MCoursePlanBookAndAttendService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            long cp_id = MethodTool.reqParseToLong(request,"cp_id");
            String str = mCoursePlanBookAndAttendService.queryBookDetail(cp_id);
            System.out.println(MethodTool.getTime() +  ",Response:" + str);
            response.getWriter().append(str);
        }else {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }
    }
}
