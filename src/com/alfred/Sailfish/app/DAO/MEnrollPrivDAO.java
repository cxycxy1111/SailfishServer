package com.alfred.Sailfish.app.DAO;

import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.SQLHelper;

import java.sql.SQLException;

public class MEnrollPrivDAO {

    SQLHelper helper;

    public MEnrollPrivDAO() {
        helper = new SQLHelper();
    }

    public boolean add(String name,String number,long time,long course) throws SQLException {
        return helper.update("INSERT INTO enroll_priv (name,phone,course,time,create_time) VALUES ('" +
                name + "','" +
                number + "'," +
                course + "," + time + ",now())");

    }

}
