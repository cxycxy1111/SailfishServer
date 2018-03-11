package com.alfred.Sailfish.app.MemberService;

import com.alfred.Sailfish.app.DAO.*;
import com.alfred.Sailfish.app.Util.Reference;

import java.util.ArrayList;
import java.util.HashMap;

public class MCoursePlanAttendanceService {

    private CourseplanAttendanceDAO courseplanAttendanceDAO = new CourseplanAttendanceDAO();
    private CourseplanBookDAO courseplanBookDAO = new CourseplanBookDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private ShopConfigDAO shopConfigDAO = new ShopConfigDAO();
    private MemberCardDAO memberCardDAO = new MemberCardDAO();
    private CardDAO cardDAO = new CardDAO();
    private CourseSupportedCardDAO courseSupportedCardDAO = new CourseSupportedCardDAO();
    private MemberCardConsumeLogDAO memberCardConsumeLogDAO = new MemberCardConsumeLogDAO();
    private ShopDAO shopDAO = new ShopDAO();

    public MCoursePlanAttendanceService() {

    }

    public String attend(long m_id,long cp_id,long member_card_id) {

        long s_id = shopDAO.queryShopIdByCoursePlanId(cp_id);
        long course_id = courseDAO.querycourseIdByCoursePlanId(cp_id);
        int type = courseDAO.queryType(course_id);
        //对于非私教课需要检查是否有预订排课
        if (type != 4) {
            if (!courseplanBookDAO.isBooked(cp_id,m_id)) {
                return Reference.AUTHORIZE_FAIL;
            }
        }
        if (courseplanAttendanceDAO.isAttended(m_id,cp_id)) {//检查是否已经预订
            return Reference.DUPLICATE;
        }
        if (courseplanAttendanceDAO.wasAttended(m_id,cp_id)) {//如果之前有预订过
            if (courseplanAttendanceDAO.updateAttendanceState(m_id,cp_id,0)){//签到
                if (type == Reference.TYPE_PRIVATE_COURSE) { //私教课
                    int rest_times = courseDAO.queryPrivateCourseRestTimes(course_id);
                    if (rest_times > 0) {
                        boolean isUpdated = courseDAO.updatePrivateCourseResetTimes(course_id,rest_times-1);
                        if (isUpdated) {//更新剩余次数成功
                            return Reference.EXE_SUC;
                        }else {
                            courseplanAttendanceDAO.updateAttendanceState(m_id,cp_id,1);
                            return Reference.EXE_FAIL;
                        }
                    }else {
                        return Reference.REST_TIMES_NOT_ENOUGH;
                    }
                }else { //非私教课
                    long card_id = cardDAO.queryCardIdByMemberCardId(member_card_id);
                    int price = 0;//查询课程价格
                    if (cardDAO.queryTypeById(card_id) != Reference.TYPE_TIME_CARD) {//如果
                        price = courseSupportedCardDAO.queryPrice(course_id,card_id);
                    }
                    int balance = memberCardDAO.queryCurrentBalanceByMemberCardId(member_card_id);
                    String allow_deduct_after_overdue = shopConfigDAO.queryShopConfig("allow_attendance_after_overdue",s_id);
                    if (allow_deduct_after_overdue.equals("1") && !memberCardDAO.isExpired(member_card_id)) {//允许会员卡到期后签到
                        if (balance < price) {//余额不足
                            String isAllow = shopConfigDAO.queryShopConfig("allow_attendance_after_arrearage",s_id);
                            if (isAllow.equals("1")) {//机构允许签到
                                boolean done = memberCardDAO.updateBalanceReduce(member_card_id,0,price,"");//扣费
                                if (done) {//扣费成功
                                    memberCardConsumeLogDAO.attend(s_id,member_card_id,cp_id,m_id,2,price,"");//记录签到日志
                                    return Reference.EXE_SUC;
                                }else{//扣费失败
                                    courseplanAttendanceDAO.updateAttendanceState(m_id,cp_id,1);//撤销签到
                                    return Reference.EXE_FAIL;
                                }
                            }else {//机构不允许签到
                                return Reference.BALANCE_NOT_ENOUGH;
                            }
                        }else {//余额充足
                            boolean done = memberCardDAO.updateBalanceReduce(member_card_id,0,price,"");//扣费
                            if (done) {//扣费成功
                                memberCardConsumeLogDAO.attend(s_id,member_card_id,cp_id,m_id,2,price,"");//记录签到日志
                                return Reference.EXE_SUC;
                            }else{//扣费失败
                                courseplanAttendanceDAO.updateAttendanceState(m_id,cp_id,1);//撤销签到
                                return Reference.EXE_FAIL;
                            }
                        }
                    }else {
                        return Reference.MEMBER_CARD_EXPIRED;
                    }
                }
            }else {
                return Reference.EXE_FAIL;
            }
        }else {//如果之前没有签到过
            if (courseplanAttendanceDAO.attend(m_id,cp_id)){//签到
                if (type == Reference.TYPE_PRIVATE_COURSE) { //私教课
                    int rest_times = courseDAO.queryPrivateCourseRestTimes(course_id);
                    if (rest_times > 0) {//剩余次数大于0
                        boolean isUpdated = courseDAO.updatePrivateCourseResetTimes(course_id,rest_times-1);//更新剩余次数
                        if (isUpdated) {//更新成功
                            return Reference.EXE_SUC;
                        }else {//更新失败
                            courseplanAttendanceDAO.updateAttendanceState(m_id,cp_id,1);
                            return Reference.EXE_FAIL;
                        }
                    }else {
                        return Reference.REST_TIMES_NOT_ENOUGH;
                    }
                }else { //非私教课
                    long card_id = cardDAO.queryCardIdByMemberCardId(member_card_id);
                    int price = 0;
                    if (cardDAO.queryTypeById(card_id) != Reference.TYPE_TIME_CARD) {
                        price = courseSupportedCardDAO.queryPrice(course_id,card_id);
                    }
                    int balance = memberCardDAO.queryCurrentBalanceByMemberCardId(member_card_id);
                    String allow_deduct_after_overdue = shopConfigDAO.queryShopConfig("allow_attendance_after_overdue",s_id);
                    if (allow_deduct_after_overdue.equals("1") && !memberCardDAO.isExpired(member_card_id)) {//允许会员卡过期后签到
                        if (balance < price) {//余额不足
                            String isAllow = shopConfigDAO.queryShopConfig("allow_attendance_after_arrearage",s_id);
                            if (isAllow.equals("1")) {//允许余额不足是扣费
                                boolean done = memberCardDAO.updateBalanceReduce(member_card_id,0,price,"");
                                if (done) {//扣费成功
                                    memberCardConsumeLogDAO.attend(s_id,member_card_id,cp_id,m_id,2,price,"");//签到
                                    return Reference.EXE_SUC;
                                }else{
                                    courseplanAttendanceDAO.updateAttendanceState(m_id,cp_id,1);//撤销签到
                                    return Reference.EXE_FAIL;
                                }
                            }else {
                                return Reference.BALANCE_NOT_ENOUGH;
                            }
                        }else {//余额充足
                            boolean done = memberCardDAO.updateBalanceReduce(member_card_id,0,price,"");//扣费
                            if (done) {//扣费成功
                                memberCardConsumeLogDAO.attend(s_id,member_card_id,cp_id,m_id,2,price,"");//记录签到日志
                                return Reference.EXE_SUC;
                            }else{
                                courseplanAttendanceDAO.updateAttendanceState(m_id,cp_id,1);//撤销签到
                                return Reference.EXE_FAIL;
                            }
                        }
                    }else {
                        return Reference.MEMBER_CARD_EXPIRED;
                    }
                }
            }else {
                return Reference.EXE_FAIL;
            }
        }
    }

    public String unAttend() {


        return "";
    }

}
