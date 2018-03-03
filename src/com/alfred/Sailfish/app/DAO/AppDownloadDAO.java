package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;

public class AppDownloadDAO extends DAO{

    public AppDownloadDAO() {

    }

    public void add(String ip,String request_date) {
        String sql = "INSERT INTO app_download_log (ip,request_date) VALUES('"+ ip + "','" + request_date + "')";
        try {
            super.sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
