package com.alfred.Sailfish.app.MemberController.Member;

import com.alfred.Sailfish.app.ShopmemberService.MemberService;
import com.alfred.Sailfish.app.Util.BaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@WebServlet(name = "MemberLogin",urlPatterns = "/memberLogin")
public class MemberLogin extends BaseServlet {

    private MemberService memberService = new MemberService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        String ip_address = request.getRemoteAddr();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String request_time = simpleDateFormat.format(date);
        PrintWriter out = response.getWriter();
        String login_name = request.getParameter("login_name");
        String password = request.getParameter("password");
        String system_version = request.getParameter("system_version");
        String system_model = request.getParameter("system_model");
        String device_brand = request.getParameter("device_brand");
        String imei = request.getParameter("imei");
        String app_version = request.getParameter("app_version");
        if (request.getSession(false) == null) {
            String str = memberService.loginCheck(request_time,ip_address,system_version,system_model,device_brand,imei,app_version,login_name,password);
            if (str.startsWith("[")) {
                HttpSession session = request.getSession(true);
                System.out.println("ShopmemberLogin Session:" + session.getId());
                ArrayList<HashMap<String,Object>> map = memberService.queryMemberInfoByLoginName(login_name);
                long m_id = Long.parseLong(String.valueOf(map.get(0).get("id")));
                long s_id = Long.parseLong(String.valueOf(map.get(0).get("shop_id")));
                session.setAttribute("m_id",m_id);
                session.setAttribute("s_id",s_id);
            }
            System.out.println(MethodTool.getTime() +  ",Response:" + str);
            out.append(str);
        } else {
            out.append(memberService.loginCheck(request_time,ip_address,system_version,system_model,device_brand,imei,app_version,login_name,password));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
