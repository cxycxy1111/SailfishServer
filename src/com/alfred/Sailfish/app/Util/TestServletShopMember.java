package com.alfred.Sailfish.app.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TestServletShopMember",urlPatterns = "/testServlet")
public class TestServletShopMember extends ShopMemberBaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        String s = "hello";
        String s1 = "你好你好";
        response.getWriter().append(String.valueOf(s.length())).append(String.valueOf(s1.length()));
    }
}
