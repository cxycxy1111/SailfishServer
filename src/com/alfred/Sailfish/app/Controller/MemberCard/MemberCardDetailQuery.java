package com.alfred.Sailfish.app.Controller.MemberCard;

import com.alfred.Sailfish.app.Service.MemberCardService;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "MemberCardDetailQuery",urlPatterns = "/QueryMemberCardDetail")
public class MemberCardDetailQuery extends HttpServlet {

    private MemberCardService memberCardService = new MemberCardService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        long mc_id = Long.parseLong(req.getParameter("mc_id"));
        String str = memberCardService.queryDetail(mc_id);
        System.out.println(MethodTool.getTime() +  ",Response:" + mc_id);
        out.append(str);
    }
}
