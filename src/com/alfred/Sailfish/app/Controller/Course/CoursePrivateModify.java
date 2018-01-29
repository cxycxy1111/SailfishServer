package com.alfred.Sailfish.app.Controller.Course;

import com.alfred.Sailfish.app.Service.CourseService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;
import sun.jvm.hotspot.debugger.win32.coff.MachineTypes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.MTOM;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CourseModifyPrivate",urlPatterns = "/CoursePrivateModify")
public class CoursePrivateModify extends HttpServlet {

    private CourseService courseService = new CourseService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session == null) {
            out.append(Reference.SESSION_EXPIRED);
        } else {
            long lmu_id = MethodTool.getSessionValueToLong(session,"sm_id");
            long id = MethodTool.reqParseToLong(request,"c_id");
            int total_times = MethodTool.reqParseToInt(request,"total_times");
            String name = request.getParameter("name");
            String invalid_time = request.getParameter("invalid_time");
            int total_cost = MethodTool.reqParseToInt(request,"total_cost");
            String str = courseService.modifyPrivate(id,lmu_id,name,total_times,invalid_time,total_cost);
            System.out.println(MethodTool.getTime() +  ",Response:" + str);
            out.append(str);
        }
    }
}
