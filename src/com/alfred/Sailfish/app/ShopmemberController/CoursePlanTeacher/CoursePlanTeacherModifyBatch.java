package com.alfred.Sailfish.app.ShopmemberController.CoursePlanTeacher;

import com.alfred.Sailfish.app.ShopmemberService.CoursePlanTeacherService;
import com.alfred.Sailfish.app.Util.ShopMemberBaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CoursePlanTeacherModifyBatch",urlPatterns = "/coursePlanTeacherModifyBatch")
public class CoursePlanTeacherModifyBatch extends ShopMemberBaseServlet {

    private CoursePlanTeacherService coursePlanTeacherService = new CoursePlanTeacherService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }else {
            String sm_type = MethodTool.getSessionValueToInt(session,"sm_type");
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            String str_cp_id = request.getParameter("cp_id");
            String str_teacher_id = request.getParameter("tea_id");
            long sm_id = MethodTool.getSessionValueToLong(session,"sm_id");
            String[] strs_cp_id = new String[]{};
            strs_cp_id = str_cp_id.split(",");
            String[] strs_tea_id = new String[]{};
            strs_tea_id = str_teacher_id.split(",");
            StringBuilder builder = new StringBuilder();
            for (int i = 0;i < strs_cp_id.length;i++) {
                for (int j = 0;j < strs_tea_id.length;j++) {
                    String s = coursePlanTeacherService.modify(s_id,sm_type,Reference.TYPE_OPERATE_ADD,Long.parseLong(strs_cp_id[i]),Long.parseLong(strs_tea_id[j]));
                    builder.append(s);
                }
            }
            String s = builder.toString();
            System.out.println(s);
            response.getWriter().append(s);
        }
    }
}
