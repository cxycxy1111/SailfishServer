package com.alfred.Sailfish.app.Service;

import com.alfred.Sailfish.app.DAO.ShopConfigDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;

public class ShopConfigService {

    private ShopDAO shopDAO = new ShopDAO();
    private ShopConfigDAO shopConfigDAO = new ShopConfigDAO();

    public ShopConfigService() {
    }

    public String query(long s_id) {
        if (!shopDAO.isExist(s_id)) {
            return Reference.NSR;
        }else if (shopDAO.isDeleted(s_id)) {
            return Reference.NSR;
        }else {
            ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
            mapArrayList = shopConfigDAO.queryShopConfig(s_id);
            if (mapArrayList.size()==0) {
                return Reference.NSR;
            }
            return MethodTool.tfc(mapArrayList);
        }
    }

    public String moidfy(long s_id,
                         int allow_inner_view_teacher, int allow_inner_manage_teacher,
                         int allow_outer_view_teacher, int allow_outer_manage_teacher,
                         int allow_inner_view_classroom, int allow_inner_manage_classroom,
                         int allow_outer_view_classroom, int allow_outer_manage_classroom,
                         int allow_inner_view_card_type, int allow_inner_manage_card_type,
                         int allow_outer_view_card_type, int allow_outer_manage_card_type,
                         int allow_inner_manage_course, int allow_outer_manage_course,
                         int allow_inner_manage_courseplan, int allow_outer_manage_courseplan,
                         int allow_inner_view_member, int allow_inner_manage_member,
                         int allow_outer_view_member, int allow_outer_manage_member,
                         int allow_inner_view_member_card, int allow_inner_manage_member_card,
                         int allow_outer_view_member_card, int allow_outer_manage_member_card,
                         int allow_deduct_after_arrearage, int allow_deduct_after_overdue,
                         int allow_book_after_arrearange, int allow_book_after_overdue,
                         int allow_attendance_after_arrearage, int allow_attendance_after_overdue) {
        if (!shopDAO.isExist(s_id)) {
            return Reference.NSR;
        }else if (shopDAO.isDeleted(s_id)) {
            return Reference.NSR;
        }else {
            boolean isModified = false;
            isModified = shopConfigDAO.update(s_id, allow_inner_view_teacher,allow_inner_manage_teacher,
            allow_outer_view_teacher,allow_outer_manage_teacher, allow_inner_view_classroom,allow_inner_manage_classroom,
            allow_outer_view_classroom,allow_outer_manage_classroom, allow_inner_view_card_type,allow_inner_manage_card_type,
            allow_outer_view_card_type,allow_outer_manage_card_type, allow_inner_manage_course,allow_outer_manage_course,
            allow_inner_manage_courseplan,allow_outer_manage_courseplan, allow_inner_view_member,allow_inner_manage_member,
            allow_outer_view_member,allow_outer_manage_member, allow_inner_view_member_card,allow_inner_manage_member_card,
            allow_outer_view_member_card,allow_outer_manage_member_card, allow_deduct_after_arrearage,allow_deduct_after_overdue,
            allow_book_after_arrearange,allow_book_after_overdue, allow_attendance_after_arrearage,allow_attendance_after_overdue);
            if (isModified) {
                return Reference.EXE_SUC;
            }
        }
        return Reference.EXE_FAIL;
    }

}
