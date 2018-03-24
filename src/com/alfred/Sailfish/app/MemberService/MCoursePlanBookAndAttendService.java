package com.alfred.Sailfish.app.MemberService;

import com.alfred.Sailfish.app.DAO.*;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MCoursePlanBookAndAttendService {

    private CardDAO cardDAO;
    private CourseplanBookAndAttendDAO courseplanBookAndAttendDAO;
    private CoursePlanDAO coursePlanDAO = new CoursePlanDAO();
    private CourseSupportedCardDAO courseSupportedCardDAO = new CourseSupportedCardDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private ShopDAO shopDAO = new ShopDAO();
    private ShopConfigDAO shopConfigDAO = new ShopConfigDAO();
    private MemberCardDAO memberCardDAO = new MemberCardDAO();
    private MemberCardConsumeLogDAO memberCardConsumeLogDAO;

    public MCoursePlanBookAndAttendService() {
        cardDAO = new CardDAO();
        memberCardConsumeLogDAO = new MemberCardConsumeLogDAO();
        courseplanBookAndAttendDAO = new CourseplanBookAndAttendDAO();
    }

    /**
     * 预订排课
     * @param cp_id
     * @param m_id
     * @return
     */
    public String book(long cp_id,long m_id) {
        boolean isSuccessed = false;
        //判断是否满人
        long course_id = courseDAO.querycourseIdByCoursePlanId(cp_id);
        int coursetype = courseDAO.queryType(course_id);
        if (coursetype != Reference.TYPE_PRIVATE_COURSE) {
            int max_book_num = courseDAO.queryBookNum(cp_id);
            int booked_num = courseplanBookAndAttendDAO.queryBookAndAttendanceNumByCoursePlanId(cp_id);
            if (max_book_num <= booked_num) {
                return Reference.BALANCE_NOT_ENOUGH;
            }
        }
        //判断是否已办理课程所支持的会员卡
        if (!canMemberBookCoursePlan(m_id,cp_id)) {
            return Reference.AUTHORIZE_FAIL;
        }
        //判断是否过期
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start_time = coursePlanDAO.queryStartDateByCourseplanId(cp_id);
        Date date_start_time = null;
        try {
            date_start_time = simpleDateFormat.parse(start_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date_start_time.before(date)) {
            return Reference.COURSEPLAN_EXPIRED;
        }
        //判断是否已经预订
        if (courseplanBookAndAttendDAO.isBooked(cp_id,m_id)) {
            return Reference.DUPLICATE;
        }
        //判断是否有预订过
        if (courseplanBookAndAttendDAO.wasBookOrAttend(cp_id,m_id,Reference.TYPE_COURSE_PLAN_BOOK_STATE_UNBOOK)) {
            isSuccessed = courseplanBookAndAttendDAO.updateStatus(cp_id,m_id,Reference.TYPE_COURSE_PLAN_BOOK_STATE_BOOKED);
        }else {
            isSuccessed = courseplanBookAndAttendDAO.book(cp_id,m_id);
        }

        if (!isSuccessed) {
            return Reference.EXE_FAIL;
        }
        return Reference.EXE_SUC;
    }

    /**
     * 根据会员ID查询已预订的排课列表
     * @param m_id
     * @return
     */
    public String queryBookList(long m_id) {
        ArrayList<HashMap<String,Object>> book_list = new ArrayList<>();
        ArrayList<HashMap<String,Object>> teacher_list = new ArrayList<>();
        ArrayList<HashMap<String,Object>> private_teacher_list = new ArrayList<>();
        ArrayList<HashMap<String,Object>> join_teacher_list = new ArrayList<>();
        ArrayList<HashMap<String,Object>> result_list = new ArrayList<>();
        book_list = courseplanBookAndAttendDAO.queryBookListByMemberId(m_id);
        if (book_list.size() >0) {
            teacher_list = courseplanBookAndAttendDAO.queryTeacherListByMemberId(m_id);
            private_teacher_list = courseplanBookAndAttendDAO.queryCoursePrivateTeacherList(m_id);
            join_teacher_list.addAll(teacher_list);
            join_teacher_list.addAll(private_teacher_list);
            for (int i = 0;i < book_list.size();i++) {
                HashMap<String,Object> book_map = new HashMap<String,Object>();
                book_map = book_list.get(i);
                StringBuilder builder = new StringBuilder();
                for (int j =0;j < join_teacher_list.size();j++) {
                    HashMap<String,Object> teacher_map = new HashMap<String,Object>();
                    teacher_map = join_teacher_list.get(j);
                    if (String.valueOf(book_map.get("courseplan_id")).equals(String.valueOf(teacher_map.get("courseplan_id")))) {
                        builder.append(teacher_map.get("sm_name")).append("、");
                    }
                }
                String str_teacher = builder.toString();
                if (str_teacher.length()>1) {
                    str_teacher = str_teacher.substring(0,str_teacher.length()-1);
                }else {
                    str_teacher = "暂未设置教师";
                }
                book_map.put("teacher",str_teacher);
                result_list.add(book_map);
            }
            return MethodTool.tfc(result_list);
        }else {
            return Reference.EMPTY_RESULT;
        }
    }

    /**
     * 查询排课基本信息
     * @param cp_id
     * @return
     */
    public String queryBookDetail(long cp_id) {
        ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
        mapArrayList = courseplanBookAndAttendDAO.queryDetailByCoursePlanId(cp_id);
        if (mapArrayList.size() != 0) {
            return MethodTool.tfc(mapArrayList);
        }else {
            return Reference.NSR;
        }
    }

    /**
     * 会员取消预订排课
     * @param m_id
     * @param cp_id
     * @return
     */
    public String unBook(long m_id,long cp_id) {
        boolean isUnbook = false;
        //如果有预订或签到过
        if (courseplanBookAndAttendDAO.isBooked(cp_id,m_id)) {
            isUnbook = courseplanBookAndAttendDAO.updateStatus(cp_id,m_id,Reference.TYPE_COURSE_PLAN_BOOK_STATE_UNBOOK);
            if (isUnbook) {
                return Reference.EXE_SUC;
            }else {
                return Reference.EXE_FAIL;
            }
        }else {
            return Reference.NSR;
        }

    }

    /**
     * 判断会员是否可以预订该排课
     * @param m_id
     * @param cp_id
     * @return
     */
    private boolean canMemberBookCoursePlan(long m_id,long cp_id) {
        //查询课程所支持的会员卡列表
        long c_id = courseDAO.querycourseIdByCoursePlanId(cp_id);
        long s_id = shopDAO.queryShopIdByCourseId(c_id);
        ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        list = courseSupportedCardDAO.querySupportedCards(s_id,c_id);
        ArrayList<Long> list_temp = new ArrayList<>();
        for (HashMap<String, Object> aList : list) {
            list_temp.add(Long.parseLong(String.valueOf(aList.get("c_id"))));
        }

        //查询会员所拥有的所有会员卡类型的列表
        ArrayList<HashMap<String,Object>> list_mc = new ArrayList<>();
        list_mc = memberCardDAO.queryListByMemberId(m_id);
        ArrayList<Long> list_mc_temp = new ArrayList<>();
        for (int i = 0;i < list_mc_temp.size();i++) {
            list_mc_temp.add(Long.parseLong(String.valueOf(list_mc.get(i).get("card_id"))));
        }

        for (int i = 0;i < list.size();i++) {
            for (int j = 0;j < list_mc.size();j++) {
                if (String.valueOf(list.get(i).get("c_id")).equals(String.valueOf(list_mc.get(j).get("card_id")))) {
                    System.out.println(true);
                    return true;
                }
            }
        }
        System.out.println(false);
        return false;
    }

    public String attend(long m_id,long cp_id,long member_card_id) {
        long s_id = shopDAO.queryShopIdByCoursePlanId(cp_id);
        long course_id = courseDAO.querycourseIdByCoursePlanId(cp_id);
        int type = courseDAO.queryType(course_id);
        //对于非私教课需要检查是否有预订排课
        if (type != 4) {
            if (!courseplanBookAndAttendDAO.isBooked(cp_id,m_id)) {
                if ((courseplanBookAndAttendDAO.isAttended(cp_id,m_id))) {
                    return Reference.DUPLICATE;
                }
                return Reference.AUTHORIZE_FAIL;
            }
        }
        if (courseplanBookAndAttendDAO.isAttended(cp_id,m_id)) {//检查是否已经预订
            return Reference.DUPLICATE;
        }
        if (!courseplanBookAndAttendDAO.updateStatus(cp_id,m_id,Reference.TYPE_COURSE_PLAN_BOOK_STATE_ATTENDED)) {//签到
            return Reference.EXE_FAIL;
        }
        if (type == Reference.TYPE_PRIVATE_COURSE) { //私教课处理
            int rest_times = courseDAO.queryPrivateCourseRestTimes(course_id);
            if (rest_times < 0) {//余额不足处理
                return Reference.REST_TIMES_NOT_ENOUGH;
            }
            boolean isUpdated = courseDAO.updatePrivateCourseResetTimes(course_id, rest_times - 1);//更新剩余次数处理
            if (isUpdated) {
                return Reference.EXE_SUC;
            }
            courseplanBookAndAttendDAO.updateStatus(cp_id, m_id, Reference.TYPE_COURSE_PLAN_BOOK_STATE_BOOKED);
            return Reference.EXE_FAIL;
        }
        //非私教课
        long card_id = cardDAO.queryCardIdByMemberCardId(member_card_id);
        int price = 0;//查询课程价格
        if (cardDAO.queryTypeById(card_id) != Reference.TYPE_TIME_CARD) {//如果用户签到所用卡不为有效期卡时查询课程价格
            price = courseSupportedCardDAO.queryPrice(course_id, card_id);
        }
        int balance = memberCardDAO.queryCurrentBalanceByMemberCardId(member_card_id);//查询会员卡余额
        String allow_deduct_after_overdue = shopConfigDAO.queryShopConfig("allow_attendance_after_overdue", s_id);
        //会员卡过期且不允许会员卡过期后签到时处理
        if (!allow_deduct_after_overdue.equals("1") && memberCardDAO.isExpired(member_card_id)) {//允许会员卡到期后签到
            return Reference.MEMBER_CARD_EXPIRED;
        }
        //余额不足且不允许余额不足时签到时处理
        if (balance < price && !shopConfigDAO.queryShopConfig("allow_attendance_after_arrearage", s_id).equals("1")) {
            return Reference.BALANCE_NOT_ENOUGH;
        }
        //扣费过程
        boolean done = memberCardDAO.updateBalanceReduce(member_card_id, 0, price, "");//扣费
        if (done) {//扣费成功，记录会员卡消费日志
            memberCardConsumeLogDAO.attend(s_id, member_card_id, cp_id, m_id, 2, price, "");//记录签到日志
            return Reference.EXE_SUC;
        } else {//扣费失败，更新为预订状态
            courseplanBookAndAttendDAO.updateStatus(cp_id, m_id, Reference.TYPE_COURSE_PLAN_BOOK_STATE_BOOKED);//撤销签到
            return Reference.EXE_FAIL;
        }
    }

}
