package com.alfred.Sailfish.app.DAO;

import com.alfred.Sailfish.app.Util.SQLHelper;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ShopConfigDAO {

    SQLHelper helper = new SQLHelper();
    private ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ShopConfigDAO() {
    }

    public boolean initParamaterAfterShopCreated(long s_id) {
        boolean isSuccessed = false;
        String sql = "INSERT INTO shop_config (shop_id,allow_inner_view_teacher," +
                "allow_inner_manage_teacher," +
                "allow_outer_view_teacher," +
                "allow_outer_manage_teacher," +
                "allow_inner_view_classroom" +
                "allow_inner_manage_classroom," +
                "allow_outer_view_classroom," +
                "allow_outer_manage_classroom," +
                "allow_inner_view_card_type," +
                "allow_inner_manage_card_type," +
                "allow_outer_view_card_type," +
                "allow_outer_manage_card_type," +
                "allow_inner_manage_course," +
                "allow_outer_manage_course," +
                "allow_inner_manage_courseplan," +
                "allow_outer_manage_courseplan," +
                "allow_inner_view_member," +
                "allow_inner_manage_member," +
                "allow_outer_view_member," +
                "allow_outer_manage_member," +
                "allow_inner_view_member_card," +
                "allow_inner_manage_member_card," +
                "allow_outer_view_member_card," +
                "allow_outer_manage_member_card," +
                "allow_deduct_after_arrearage," +
                "allow_deduct_after_overdue," +
                "allow_book_after_arrearange," +
                "allow_book_after_overdue," +
                "allow_attendance_after_arrearage," +
                "allow_attendance_after_overdue) VALUES(" + s_id + ",1,0,1,0,1,1,1,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0,1,1,1,1,1,1)";
        try {
            isSuccessed = helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccessed;
    }

    public ArrayList<HashMap<String,Object>> queryShopConfig(long s_id) {
        String sql = "SELECT * FROM shop_config WHERE shop_id=" + s_id;
        return helper.query(sql);
    }

    public boolean update(long s_id,
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
        boolean isSuccessed = false;
        String sql = "UPDATE shop_config SET " +
                "allow_inner_view_teacher=" + allow_inner_view_teacher +
                ",allow_inner_manage_teacher=" + allow_inner_manage_teacher +
                ",allow_outer_view_teacher=" + allow_outer_view_teacher +
                ",allow_outer_manage_teacher=" + allow_outer_manage_teacher +
                ",allow_inner_view_classroom=" + allow_inner_view_classroom +
                ",allow_inner_manage_classroom=" + allow_inner_manage_classroom +
                ",allow_outer_view_classroom=" + allow_outer_view_classroom +
                ",allow_outer_manage_classroom=" + allow_outer_manage_classroom +
                ",allow_inner_view_card_type=" + allow_inner_view_card_type +
                ",allow_inner_manage_card_type=" + allow_inner_manage_card_type +
                ",allow_outer_view_card_type=" + allow_outer_view_card_type +
                ",allow_outer_manage_card_type=" + allow_outer_manage_card_type +
                ",allow_inner_manage_course=" + allow_inner_manage_course +
                ",allow_outer_manage_course=" + allow_outer_manage_course +
                ",allow_inner_manage_courseplan=" + allow_inner_manage_courseplan +
                ",allow_outer_manage_courseplan=" + allow_outer_manage_courseplan +
                ",allow_inner_view_member=" + allow_inner_view_member +
                ",allow_inner_manage_member=" + allow_inner_manage_member +
                ",allow_outer_view_member=" + allow_outer_view_member +
                ",allow_outer_manage_member=" + allow_outer_manage_member +
                ",allow_inner_view_member_card=" + allow_inner_view_member_card +
                ",allow_inner_manage_member_card=" + allow_inner_manage_member_card +
                ",allow_outer_view_member_card=" + allow_outer_view_member_card +
                ",allow_outer_manage_member_card=" + allow_outer_manage_member_card +
                ",allow_deduct_after_arrearage=" + allow_deduct_after_arrearage +
                ",allow_deduct_after_overdue=" + allow_deduct_after_overdue +
                ",allow_book_after_arrearange=" + allow_book_after_arrearange +
                ",allow_book_after_overdue=" + allow_book_after_overdue +
                ",allow_attendance_after_arrearage=" + allow_attendance_after_arrearage +
                ",allow_attendance_after_overdue=" + allow_attendance_after_overdue +
                " WHERE shop_id=" + s_id;
        try {
            isSuccessed = helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccessed;
    }

}
