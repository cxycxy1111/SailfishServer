package com.alfred.Sailfish.app.DAO;

import java.sql.SQLException;
import java.util.Date;

public class MemberCardConsumeLogDAO extends DAO{

    public MemberCardConsumeLogDAO(){
        super();
    }

    public boolean charge(long s_id,long mc_id,long sm_id,long num,String remark) {
        boolean isInserted = false;
        String sql = "INSERT INTO member_card_consume_log " +
                "(shop_id,membercard_id,courseplan_id,consume,consume_time,remark,operation,operator,operator_type) " +
                "VALUES (" + s_id + "," + mc_id + ",0," +num + ",'" + simpleDateFormat.format(new Date()) + "','"
                + remark + "',1,"+ sm_id + ",1)";
        try {
            isInserted = super.sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInserted;
    }

    public boolean deduct(long s_id,long mc_id,long sm_id,long num,String remark) {
        boolean done = false;
        String sql = "INSERT INTO member_card_consume_log " +
                "(shop_id,membercard_id,courseplan_id,consume,consume_time,remark,operation,operator,operator_type) " +
                "VALUES (" + s_id + "," + mc_id + ",0," +num + ",'" + simpleDateFormat.format(new Date()) + "','"
                + remark + "',2,"+ sm_id + ",1)";
        try {
            done = super.sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return done;
    }

    public void attend(long s_id, long mc_id, long cp_id, long u_id, int u_type, long num, String remark) {
        String sql = "INSERT INTO member_card_consume_log " +
                "(shop_id,membercard_id,courseplan_id,consume,consume_time,remark,operation,operator,operator_type) " +
                "VALUES (" + s_id + "," + mc_id + "," + cp_id + "," + num + ",'" + simpleDateFormat.format(new Date()) + "','"
                + remark + "',3,"+ u_id + "," + u_type + ")";
        try {
            super.sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean unAttend(long s_id,long mc_id,long cp_id,long u_id,int u_type,long num,String remark) {
        boolean done = false;
        String sql = "INSERT INTO member_card_consume_log " +
                "(shop_id,membercard_id,courseplan_id,consume,consume_time,remark,operation,operator,operator_type) " +
                "VALUES (" + s_id + "," + mc_id + "," + cp_id + "," +num + ",'" + simpleDateFormat.format(new Date()) + "','"
                + remark + "',4,"+ u_id + "," + u_type + ")";
        try {
            done = super.sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return done;
    }

}
