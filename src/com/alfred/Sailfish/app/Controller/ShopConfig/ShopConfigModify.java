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
            String s = request.getParameter("request");
            String [] array= s.split(",");
            int[] ints = new int[array.length];
            for (int i=0;i<array.length;i++) {
                ints[i] = Integer.parseInt(array[i]);
            }

            String str = shopConfigService.moidfy(s_id,ints[0],ints[1],ints[2],
                    ints[3],ints[4],ints[5],
                    ints[6],ints[7],ints[8],
                    ints[9],ints[10],ints[11],
                    ints[12],ints[13],ints[14],
                    ints[15],ints[16],ints[17],
                    ints[18],ints[19],ints[20],
                    ints[21],ints[22],ints[23],
                    ints[24],ints[25],ints[26],
                    ints[27],ints[28],ints[29]);
            out.append(str);
        }
    }
}
