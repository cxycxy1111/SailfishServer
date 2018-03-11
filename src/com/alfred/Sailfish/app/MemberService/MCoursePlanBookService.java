package com.alfred.Sailfish.app.MemberService;

import com.alfred.Sailfish.app.DAO.*;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MCoursePlanBookService{

    private CourseplanBookDAO courseplanBookDAO = new CourseplanBookDAO();
    private CoursePlanDAO coursePlanDAO = new CoursePlanDAO();
    private CourseSupportedCardDAO courseSupportedCardDAO = new CourseSupportedCardDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private ShopDAO shopDAO = new ShopDAO();
    private MemberCardDAO memberCardDAO = new MemberCardDAO();

    public MCoursePlanBookService() {

    }

    /**
     * 预订排课
     * @param cp_id
     * @param m_id
     * @return
     */
    public String book(long cp_id,long m_id) {
        boolean isSuccessed = false;

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
        if (courseplanBookDAO.isBooked(cp_id,m_id)) {
            return Reference.DUPLICATE;
        }
        //判断是否有预订过
        if (courseplanBookDAO.wasBooked(cp_id,m_id)) {
            isSuccessed = courseplanBookDAO.updateBookState(cp_id,m_id,0);
        }else {
            isSuccessed = courseplanBookDAO.book(cp_id,m_id);
        }

        if (isSuccessed == false) {
            return Reference.EXE_FAIL;
        }
        return Reference.EXE_SUC;
    }

    /**
     * 查询已预订的排课列表
     * @param m_id
     * @return
     */
    public String queryBookList(long m_id) {
        ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
        mapArrayList = courseplanBookDAO.queryListByMemberId(m_id);
        if (mapArrayList.size() != 0) {
            return MethodTool.tfc(mapArrayList);
        }else {
            return Reference.NSR;
        }
    }

    /**
     * 查询排课基本信息
     * @param cp_id
     * @return
     */
    public String queryBookDetail(long cp_id) {
        ArrayList<HashMap<String,Object>> mapArrayList = new ArrayList<>();
        mapArrayList = courseplanBookDAO.queryDetailByCoursePlanId(cp_id);
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
        if (!courseplanBookDAO.isBooked(cp_id,m_id)) {
            return Reference.NSR;
        }
        if (courseplanBookDAO.wasBooked(cp_id,m_id)) {
            isUnbook = courseplanBookDAO.updateBookState(cp_id,m_id,1);
            if (isUnbook) {
                return Reference.EXE_SUC;
            }else {
                return Reference.EXE_FAIL;
            }
        }else {
            isUnbook = courseplanBookDAO.unBook(cp_id,m_id);
            if (isUnbook) {
                return Reference.EXE_SUC;
            }else {
                return Reference.EXE_FAIL;
            }
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

}
