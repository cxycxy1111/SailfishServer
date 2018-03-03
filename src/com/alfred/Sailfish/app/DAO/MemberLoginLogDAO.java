package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;

public class MemberLoginLogDAO extends DAO{

    public MemberLoginLogDAO() {
        super();
    }

    public void insert(String request_time,
                          int is_success,
                          String ip_address,
                          String user_name,
                          String password,
                          String system_version,
                          String system_model,
                          String device_brand,
                          String imei,
                          String app_version) {
        String sql = "INSERT INTO member_login_log (" +
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
            isInserted = sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
