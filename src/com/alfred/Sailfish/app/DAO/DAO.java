package com.alfred.Sailfish.app.DAO;

import com.alfred.Sailfish.app.Util.SQLHelper;

import java.text.SimpleDateFormat;

public class DAO {

    protected SimpleDateFormat simpleDateFormat;
    protected SQLHelper sqlHelper;

    public DAO() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sqlHelper = new SQLHelper();
    }

}
