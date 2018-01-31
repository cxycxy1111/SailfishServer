package com.alfred.Sailfish.app.Service;

import com.alfred.Sailfish.app.DAO.ShopConfigDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;
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
                         String allow_view_teacher,String allow_manage_teacher,
                         String allow_view_classroom, String allow_manage_classroom,
                         String allow_view_card_type,String allow_manage_card_type,
                         String allow_manage_course,String allow_manage_courseplan,
                         String allow_view_member,String allow_manage_member,
                         String allow_view_member_card,String allow_manage_member_card,
                         String allow_deduct_after_arrearage,String allow_deduct_after_overdue,
                         String allow_book_after_arrearange,String allow_book_after_overdue,
                         String allow_attendance_after_arrearage,String allow_attendance_after_overdue) {
        if (!shopDAO.isExist(s_id)) {
            return Reference.NSR;
        }else if (shopDAO.isDeleted(s_id)) {
            return Reference.NSR;
        }else {
            boolean isModified = false;
            isModified = shopConfigDAO.update2(s_id,
                    allow_view_teacher,allow_manage_teacher,allow_view_classroom,
                    allow_manage_classroom, allow_view_card_type,
                    allow_manage_card_type, allow_manage_course,
                    allow_manage_courseplan, allow_view_member,
                    allow_manage_member, allow_view_member_card,
                    allow_manage_member_card, allow_deduct_after_arrearage,
                    allow_deduct_after_overdue, allow_book_after_arrearange,
                    allow_book_after_overdue, allow_attendance_after_arrearage,
                    allow_attendance_after_overdue);
            if (isModified) {
                return Reference.EXE_SUC;
            }
        }
        return Reference.EXE_FAIL;
    }

}
