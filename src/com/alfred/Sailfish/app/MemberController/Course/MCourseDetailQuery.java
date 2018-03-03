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

@WebServlet(name = "MCourseDetailQuery",urlPatterns = "/mCourseDetailQuery")
public class MCourseDetailQuery extends BaseServlet {

    private CourseService courseService = new CourseService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        long c_id = MethodTool.reqParseToLong(request,"c_id");
        HttpSession session = request.getSession(false);
        if (session != null) {
            String s = courseService.queryDetail(c_id);
            System.out.println(MethodTool.getTime() +  ",Response:" + s);
            response.getWriter().append(s);
        }else {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }
    }
}
