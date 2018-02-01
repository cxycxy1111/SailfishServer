package com.alfred.Sailfish.app.Controller.Course;

import com.alfred.Sailfish.app.Service.CourseService;
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
import java.io.PrintWriter;

@WebServlet(name = "CourseAddPrivate",urlPatterns = "/CourseAddPrivate")
public class CourseAddPrivate extends HttpServlet {

    CourseService courseService = new CourseService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session ==null) {
            out.append(Reference.SESSION_EXPIRED);
        } else {
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
            long lmu_id = MethodTool.getSessionValueToLong(session,"sm_id");
            long sm_id = MethodTool.reqParseToLong(request,"sm_id");
            long m_id = MethodTool.reqParseToLong(request,"m_id");
            String name = request.getParameter("name");
            int type = MethodTool.reqParseToInt(request,"type");
            int total_times = MethodTool.reqParseToInt(request,"total_times");
            String e_time = request.getParameter("e_time");
            int acutal_cost = MethodTool.reqParseToInt(request,"actual_cost");
            String str = courseService.addPrivate(s_id,sm_type,lmu_id,sm_id,m_id,name,type,total_times,e_time,acutal_cost);
            System.out.println(MethodTool.getTime() +  ",Response:" + str);
            out.append(str);
        }
    }
}
