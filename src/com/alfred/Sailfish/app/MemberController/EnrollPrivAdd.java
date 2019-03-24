package com.alfred.Sailfish.app.MemberController;

import com.alfred.Sailfish.app.MemberService.MEnrollPrivService;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "EnrollPrivAdd",urlPatterns="/EnrollPriv/Add")
public class EnrollPrivAdd extends HttpServlet {

    private MEnrollPrivService mEnrollPrivService = new MEnrollPrivService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        long course = MethodTool.reqParseToLong(request,"course");
        long time = MethodTool.reqParseToLong(request,"time");
        try {
            printWriter.append(mEnrollPrivService.add(name,number,time,course));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
