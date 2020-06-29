package com.alfred.Sailfish.app.ShopmemberController.CoursePlan;

import com.alfred.Sailfish.app.ShopmemberService.CoursePlanService;
import com.alfred.Sailfish.app.Util.ShopMemberBaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@WebServlet(name = "CoursePlanAddBatch",urlPatterns = "/coursePlanAddBatch")
public class CoursePlanAddBatch extends ShopMemberBaseServlet {

    private CoursePlanService coursePlanService = new CoursePlanService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }else {
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            long sm_id = MethodTool.getSessionValueToLong(session,"sm_id");
            String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
            String date = request.getParameter("date");
            String time = request.getParameter("time");
            long c_id = MethodTool.reqParseToLong(request,"c_id");
            long cr_id = MethodTool.reqParseToLong(request,"cr_id");
            int last_time = MethodTool.reqParseToInt(request,"last_time");
            String[] dates = new String[]{};
            dates = date.split(",");
            int i = dates.length;
            StringBuilder builder = new StringBuilder();
            for (int j = 0;j < i;j++) {
                String s_time = dates[j] + " " + time;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date_s_time = new Date();
                String str_s_time = simpleDateFormat.format(date_s_time);
                try {
                    date_s_time = simpleDateFormat.parse(s_time);
                    str_s_time = simpleDateFormat.format(date_s_time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date_s_time);
                calendar.add(Calendar.MINUTE,last_time);
                Date date_e_time = calendar.getTime();
                String str_e_time = simpleDateFormat.format(date_e_time);
                String id = coursePlanService.add(true,s_id,sm_type,c_id,cr_id,sm_id,str_s_time,str_e_time,"");
                builder.append(id).append(",");
            }
            String last_id = builder.toString();
            last_id = last_id.substring(0,last_id.length()-1);
            ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("id",last_id);
            arrayList.add(hashMap);

            System.out.println(MethodTool.tfc(arrayList));
            response.getWriter().append(last_id);
        }
    }
}
