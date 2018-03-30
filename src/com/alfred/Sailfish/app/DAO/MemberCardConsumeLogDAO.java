package com.alfred.Sailfish.app.DAO;

import com.alfred.Sailfish.app.Util.EnumPackage.EnumMemberCardConsumeLogOperation;
import com.alfred.Sailfish.app.Util.EnumPackage.EnumMemberCardConsumeLogOperatorType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MemberCardConsumeLogDAO extends DAO{

    public MemberCardConsumeLogDAO(){
        super();
    }

    public boolean charge(long s_id,long mc_id,long sm_id,long num,String remark) {
        return this.publicInsert(s_id,mc_id,0,sm_id, 1,num,remark, 1);
    }

    /**
     * 扣费
     * @param s_id
     * @param mc_id
     * @param sm_id
     * @param num
     * @param remark
     * @return
     */
    public boolean deduct(long s_id,long mc_id,long sm_id,long num,String remark) {
        return this.publicInsert(s_id,mc_id,0,sm_id, 1,num,remark, 2);
    }

    /**
     * 签到
     * @param s_id
     * @param mc_id
     * @param cp_id
     * @param u_id
     * @param u_type
     * @param num
     * @param remark
     */
    public void attend(long s_id, long mc_id, long cp_id, long u_id, int u_type, long num, String remark) {
        this.publicInsert(s_id,mc_id,cp_id,u_id, u_type,num,remark, 3);
    }

    /**
     * 取消签到
     * @param s_id
     * @param mc_id
     * @param cp_id
     * @param u_id
     * @param num
     * @param remark
     * @return
     */
    public boolean unAttend(long s_id,long mc_id,long cp_id,long u_id,long num,String remark) {
        return this.publicInsert(s_id,mc_id,cp_id,u_id, 2,num,remark, 4);
    }

    /**
     * 公共插入
     * @param s_id
     * @param mc_id
     * @param cp_id
     * @param u_id
     * @param u_type
     * @param num
     * @param remark
     * @param operation
     * @return
     */
    public boolean publicInsert(long s_id,long mc_id,long cp_id,long u_id,int u_type,long num,String remark,int operation) {
        boolean done = false;
        String sql = "INSERT INTO member_card_consume_log " +
                "(shop_id,membercard_id,courseplan_id,consume,consume_time,remark,operation,operator,operator_type) " +
                "VALUES (" + s_id + "," + mc_id + "," + cp_id + "," +num + ",'" + simpleDateFormat.format(new Date()) + "','"
                + remark + "'," + operation +  ","+ u_id + "," + u_type + ")";
        try {
            done = super.sqlHelper.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return done;
    }

    /**
     * 通过会员卡ID查询消费日志
     * @param mc_id
     * @return
     */
    public ArrayList<HashMap<String,Object>> queryMemberCardConsumeLog(long mc_id) {
        ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
        String sql = "SELECT mccl.consume,mccl.consume_time,mccl.operation,c.type FROM member_card_consume_log mccl " +
                "LEFT JOIN member_card mc ON mccl.membercard_id=mc.id " +
                "LEFT JOIN card c ON mc.card_id=c.id " +
                "WHERE mccl.membercard_id=" + mc_id + " ORDER BY mccl.consume_time DESC";
        mapList = super.sqlHelper.query(sql);
        return mapList;
    }

    public int queryConsumeByCourseplanIdAndMemberCardId(long cp_id,long mc_id) {
        ArrayList<HashMap<String,Object>> mapList = new ArrayList<HashMap<String,Object>>();
        String sql = "SELECT consume FROM member_card_consume_log " +
                "WHERE membercard_id=" + mc_id + " AND courseplan_id=" + cp_id +
                " AND operation=" + 3 + " ORDER BY id DESC";
        mapList = super.sqlHelper.query(sql);
        return Integer.parseInt(String.valueOf(mapList.get(0).get("consume")));
    }

}
