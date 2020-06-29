package com.alfred.Sailfish.app.MemberController.CoursePlanBook;

import com.alfred.Sailfish.app.ShopmemberService.CoursePlanTeacherService;
import com.alfred.Sailfish.app.Util.MemberBaseServlet;
import com.alfred.Sailfish.app.Util.ShopMemberBaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MBookTeachersListQuery",urlPatterns = "/mBookTeachersListQuery")
public class MBookTeachersListQuery extends MemberBaseServlet {

    private CoursePlanTeacherService coursePlanTeacherService = new CoursePlanTeacherService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long s_id, long m_id) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, s_id, m_id);
        long cp_id = MethodTool.reqParseToLong(request,"cp_id");
        String s = coursePlanTeacherService.queryList(cp_id);
        System.out.println(MethodTool.getTime() +  ",Response:" + s);
        out.append(s);
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, out);
    }
}

