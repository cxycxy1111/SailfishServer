package com.alfred.Sailfish.app.MemberService;

import com.alfred.Sailfish.app.DAO.MEnrollPrivDAO;
import com.alfred.Sailfish.app.Util.Reference;

import java.sql.SQLException;

public class MEnrollPrivService {

    private MEnrollPrivDAO mEnrollPrivDAO;

    public MEnrollPrivService () {
        mEnrollPrivDAO = new MEnrollPrivDAO();

    }

    public String add(String name,String number,int grade,long time,long course) {
        if (name.length()>20 || number.length()>11) {
            return Reference.EXE_FAIL;
        }
        if (name.equals("")|| number.equals("")) {
            return Reference.EMPTY_RESULT;
        }
        if (name.contains("'") || number.equals("'")) {
            return Reference.EXE_FAIL;
        }
        String s = "";
        try {
            mEnrollPrivDAO.add(name,number,grade,time,course);
            return Reference.EXE_SUC;
        } catch (Exception e) {
            e.printStackTrace();
            return Reference.EXE_FAIL;
        }
    }

}
