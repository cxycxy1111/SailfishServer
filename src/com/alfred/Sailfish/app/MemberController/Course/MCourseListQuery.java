package com.alfred.Sailfish.app.MemberController.Course;

import com.alfred.Sailfish.app.ShopmemberService.CourseService;
import com.alfred.Sailfish.app.Util.BaseServlet;
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

@WebServlet(name = "MCourseListQuery",urlPatterns = "/mCourseListQuery")
public class MCourseListQuery extends BaseServlet {

    private CourseService courseService = new CourseService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
        if (session == null) {
            out.append(Reference.SESSION_EXPIRED);
        }else {
            long m_id = MethodTool.getSessionValueToLong(session,"m_id");
            String s = courseService.queryCourseByMemberId(m_id);
            System.out.println(MethodTool.getTime() +  ",Response:" + s);
            out.append(s);
        }
    }
}
