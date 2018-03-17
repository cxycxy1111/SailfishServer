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
        String sql = "INSERT INTO shop_config (shop_id," +
                "allow_view_teacher," +
                "allow_manage_teacher," +
                "allow_view_classroom," +
                "allow_manage_classroom," +
                "allow_view_card_type," +
                "allow_manage_card_type," +
                "allow_manage_course," +
                "allow_manage_courseplan," +
                "allow_view_member," +
                "allow_manage_member," +
                "allow_view_member_card," +
                "allow_manage_member_card," +
                "allow_deduct_after_arrearage," +
                "allow_deduct_after_overdue," +
                "allow_book_after_arrearange," +
                "allow_book_after_overdue," +
                "allow_attendance_after_arrearage," +
                "allow_attendance_after_overdue) VALUES(" + s_id + ",'1,2','1','1,2','1','1,2'," +
                "'1','1','1','1,2','1,2','1,2','1'," +
                "1,1,1,1,1,1)";
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

    public boolean update2(long s_id,
                          String allow_view_teacher,String allow_manage_teacher,
                          String allow_view_classroom, String allow_manage_classroom,
                          String allow_view_card_type,String allow_manage_card_type,
                          String allow_manage_course,String allow_manage_courseplan,
                          String allow_view_member,String allow_manage_member,
                          String allow_view_member_card,String allow_manage_member_card,
                          String allow_deduct_after_arrearage,String allow_deduct_after_overdue,
                          String allow_book_after_arrearange,String allow_book_after_overdue,
                           String allow_attendance_after_arrearage,String allow_attendance_after_overdue) {
        String sql = "UPDATE shop_config SET allow_view_teacher='" + allow_view_teacher + "'," +
                "allow_manage_teacher='" + allow_manage_teacher +"'," +
                "allow_view_classroom='" + allow_view_classroom + "'," +
                "allow_manage_classroom='" + allow_manage_classroom + "'," +
                "allow_view_card_type='" + allow_view_card_type + "'," +
                "allow_manage_card_type='" + allow_manage_card_type + "'," +
                "allow_manage_course='" + allow_manage_course + "'," +
                "allow_manage_courseplan='" + allow_manage_courseplan + "'," +
                "allow_view_member='" + allow_view_member + "'," +
                "allow_manage_member='" + allow_manage_member + "'," +
                "allow_view_member_card='" + allow_view_member_card + "'," +
                "allow_manage_member_card='" + allow_manage_member_card + "'," +
                "allow_deduct_after_arrearage=" + allow_deduct_after_arrearage + "," +
                "allow_deduct_after_overdue=" + allow_deduct_after_overdue + "," +
                "allow_book_after_arrearange=" + allow_book_after_arrearange + "," +
                "allow_book_after_overdue=" + allow_book_after_overdue + "," +
                "allow_attendance_after_arrearage=" + allow_attendance_after_arrearage + "," +
                "allow_attendance_after_overdue=" + allow_attendance_after_overdue + " WHERE shop_id=" + s_id;
        boolean isSuccessed = false;
        try {
            isSuccessed = helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccessed;
    }


    public String queryShopConfig(String field,long s_id) {
        String sql = "SELECT " + field + " FROM shop_config WHERE shop_id=" + s_id;
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        arrayList = helper.query(sql);
        return String.valueOf(arrayList.get(0).get(field));
    }

}
