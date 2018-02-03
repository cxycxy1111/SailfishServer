package com.alfred.Sailfish.app.DAO;

import com.alfred.Sailfish.app.Util.SQLHelper;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ShopmemberLoginLogDAO {

    SQLHelper helper = new SQLHelper();
    private ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ShopmemberLoginLogDAO() {

    }

    public boolean insert(String request_time,
                          int is_success,
                          String ip_address,
                          String user_name,
                          String password,
                          String system_version,
                          String system_model,
                          String device_brand,
                          String imei,
                          String app_version) {
        String sql = "INSERT INTO shopmember_login_log (" +
                "request_time," +
                "is_success," +
                "ip_address," +
                "input_user_name," +
                "input_password," +
                "system_version," +
                "system_model," +
                "device_brand," +
                "imei," +
                "app_version" +
                ") VALUES ( '" +
                request_time + "'," +
                is_success + ",'" +
                ip_address + "','" +
                user_name + "','" +
                password + "','" +
                system_version + "','" +
                system_model + "','" +
                device_brand + "','" +
                imei + "','" +
                app_version + "')";
        boolean isInserted = false;
        try {
            isInserted = helper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInserted;
    }

}
