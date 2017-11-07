package com.alfred.Sailfish.app.Controller.Course;

import com.alfred.Sailfish.app.Service.CourseService;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.MTOM;
import java.io.IOException;

@WebServlet("/CourseAddPrivate")
public class CourseAddPrivate extends HttpServlet {

    CourseService courseService = new CourseService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        long s_id = MethodTool.reqParseToLong(request,"s_id");
        long lmu_id = MethodTool.reqParseToLong(request,"lmu_id");
        long sm_id = MethodTool.reqParseToLong(request,"sm_id");
        long m_id = MethodTool.reqParseToLong(request,"m_id");
        String name = request.getParameter("name");
        int type = MethodTool.reqParseToInt(request,"type");
        int total_times = MethodTool.reqParseToInt(request,"total_times");
        String e_time = request.getParameter("e_time");
        int acutal_cost = MethodTool.reqParseToInt(request,"actual_cost");
        String str = courseService.addPrivate(s_id,lmu_id,sm_id,m_id,name,type,total_times,e_time,acutal_cost);
        response.getWriter().append(str);
    }
}
