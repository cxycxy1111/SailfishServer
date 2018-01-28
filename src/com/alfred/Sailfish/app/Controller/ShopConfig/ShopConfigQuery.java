package com.alfred.Sailfish.app.Controller.ShopConfig;

import com.alfred.Sailfish.app.Service.ShopConfigService;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ShopConfigQuery",urlPatterns = "/ShopConfigQuery")
public class ShopConfigQuery extends HttpServlet {

    private ShopConfigService shopConfigService = new ShopConfigService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        long s_id = MethodTool.reqParseToLong(request,"s_id");
        String str = shopConfigService.query(s_id);
        response.getWriter().append(str);
    }
}
