package com.alfred.Sailfish.app.Controller.ShopConfig;

import com.alfred.Sailfish.app.Service.ShopConfigService;
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

@WebServlet(name = "ShopConfigModify",urlPatterns = "/ShopConfigModify")
public class ShopConfigModify extends HttpServlet {

    private ShopConfigService shopConfigService = new ShopConfigService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session == null) {
            out.append(Reference.SESSION_EXPIRED);
        }else {
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
            String s = request.getParameter("request");
            String [] array= s.split("_");
            String str = shopConfigService.moidfy(s_id,
                    array[0],array[1],array[2],
                    array[3],array[4],array[5],
                    array[6],array[7],array[8],
                    array[9],array[10],array[11],
                    array[12],array[13],array[14],
                    array[15],array[16],array[17]);
            out.append(str);
        }
    }
}
