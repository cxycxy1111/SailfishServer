package com.alfred.Sailfish.app.Service;

import java.util.*;

import com.alfred.Sailfish.app.DAO.CourseDAO;
import com.alfred.Sailfish.app.DAO.CourseSupportedCardDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.sun.org.apache.regexp.internal.RE;

public class CourseService {
	
	private CourseDAO courseDAO = new CourseDAO();
	private ShopDAO shopDAO = new ShopDAO();
	private CourseSupportedCardDAO courseSupportedCardDAO= new CourseSupportedCardDAO();
	
	public CourseService() {
	}
	
	/**
	 * 增加会员课、教练班、集训课
	 * @param s_id
	 * @param lmu_id
	 * @param name
	 * @param type
	 * @param last_time
	 * @param max_book_num
	 * @param summary
	 * @return
	 */
	public String add(long s_id, long lmu_id, String name, int type, int last_time, int max_book_num, String summary) {
		if (courseDAO.isCourseNameExist(name, s_id)) {
			return MethodTool.qr(Reference.DUPLICATE);
		}
		if(type == Reference.TYPE_PRIVATE_COURSE){
			return MethodTool.qr(Reference.NOT_MATCH);
		}
		if (shopDAO.queryShopByShopmemberId(lmu_id) != s_id) {
			return qr(Reference.INST_NOT_MATCH);
		}
		boolean isAdded = false;
		isAdded = courseDAO.add(s_id, lmu_id, name, type, last_time, max_book_num, summary);
		if (!isAdded) {
			return qr(Reference.EXE_FAIL);
		}
		long ce_id = courseDAO.queryCourseIdByCourseName(name,s_id);
		return MethodTool.tfs(Reference.dataprefix + ce_id + Reference.datasuffix);
	}
	
	/**
	 * 新增私教课程
	 * @param s_id
	 * @param lmu_id
	 * @param sm_id
	 * @param m_id
	 * @param name
	 * @param type
	 * @param total_times
	 * @param e_time
	 * @param actual_cost
	 * @return
	 */
	public String addPrivate(long s_id,long lmu_id,long sm_id,long m_id,String name,int type,int total_times,String e_time,int actual_cost) {
		if (type != Reference.TYPE_PRIVATE_COURSE) {
			return qr(Reference.NOT_MATCH);
		}
		if (s_id != shopDAO.queryShopByShopmemberId(lmu_id)) {
			return qr(Reference.INST_NOT_MATCH);
		}
		boolean isAdded = courseDAO.addPrivate(s_id, lmu_id, sm_id, m_id, name, total_times, e_time, actual_cost);
		if (!isAdded) {
			return qr(Reference.EXE_FAIL);
		}
		long ce_id = courseDAO.queryCourseIdByCourseName(name,s_id);
		return MethodTool.tfs(Reference.dataprefix + ce_id + Reference.datasuffix);
	}
	
	/**
	 * 删除课程
	 * @param id
	 * @param lmu
	 * @return
	 */
	public String remove(long id,long lmu) {
		if (shopDAO.queryShopIdByCourseId(id) != shopDAO.queryShopByShopmemberId(lmu)) {
			return qr(Reference.INST_NOT_MATCH);
		}
		boolean isRemoved = false;
		isRemoved = courseDAO.remove(id, lmu);
		if (isRemoved) {
			return qr(Reference.EXE_SUC);
		}
		return qr(Reference.EXE_FAIL);
	}
	
	/**
	 * 查询课程列表
	 * @param s_id
	 * @return
	 */
	public String queryList(long s_id) {
		if(shopDAO.isDeleted(s_id)){
			return qr(Reference.NSR);
		}
		ArrayList<HashMap<String,Object>> course_list = new ArrayList<>();
		ArrayList<HashMap<String,Object>> full_list = new ArrayList<>();
		ArrayList<HashMap<String,Object>> csc_list = new ArrayList<>();
		course_list = courseDAO.queryList(s_id);
		csc_list = courseSupportedCardDAO.querySupportedCards(s_id,0);
		for (int i = 0;i < course_list.size();i++) {
			HashMap<String,Object> temp_map = course_list.get(i);
			StringBuilder builder = new StringBuilder();
			String temp_id = String.valueOf(temp_map.get("id"));
			for (int j = 0;j < csc_list.size();j++) {
				String temp_csc_course_id = String.valueOf(csc_list.get(j).get("course_id"));
				if ((temp_csc_course_id.equals(temp_id))) {
					builder.append(csc_list.get(j).get("card_name"));
					builder.append("|");
				}
			}
			String sc = builder.toString();
			String [] scs = sc.split("、");
			StringBuilder b = new StringBuilder();
			temp_map.put("supportedcard",builder.toString());
			full_list.add(temp_map);
		}
		return MethodTool.tfc(full_list);
	}
	
	/**
	 * 修改课程
	 * @param c_id
	 * @param lmu_id
	 * @param name
	 * @param last_time
	 * @param max_book_num
	 * @param summary
	 * @return
	 */
	public String modify(long c_id,long lmu_id,String name,int last_time,int max_book_num,String summary) {
		boolean isModified = false;
		long s_id_1 = shopDAO.queryShopIdByCourseId(c_id);
		long s_id_2 = shopDAO.queryShopByShopmemberId(lmu_id);
		if (s_id_1 != s_id_2) {
			return qr(Reference.INST_NOT_MATCH);
		}
		isModified = courseDAO.modify(c_id, lmu_id, name, last_time, max_book_num, summary);
		if (isModified) {
			return qr(Reference.EXE_SUC);
		}
		return qr(Reference.EXE_FAIL);
	}
	
	/**
	 * 查询课程详情
	 * @param c_id
	 * @return
	 */
	public String queryDetail(long c_id) {
		ArrayList<IdentityHashMap<String, Object>> list = new ArrayList<IdentityHashMap<String,Object>>();
		ArrayList<HashMap<String, Object>> list_2 = new ArrayList<HashMap<String,Object>>();
		int i = courseDAO.queryType(c_id);
		switch (i){
		case Reference.TYPE_PRIVATE_COURSE:
			list = courseDAO.queryPrivateDetail(c_id);
			if (list.size() == 0) {
				return qr(Reference.EMPTY_RESULT);
			}
			return MethodTool.tfc(list);
		case Reference.TYPE_COLLECTION_COURSE:
			list_2 = courseDAO.queryDetail(c_id);
			if (list_2.size() == 0) {
				return qr(Reference.EMPTY_RESULT);
			}
			return MethodTool.tfc(list_2);
		case Reference.TYPE_MEMBER_COURSE:
			list_2 = courseDAO.queryDetail(c_id);
			if (list_2.size() == 0) {
				return qr(Reference.EMPTY_RESULT);
			}
			return MethodTool.tfc(list_2);
		case Reference.TYPE_TRAINNER_COURSE:
			list_2 = courseDAO.queryDetail(c_id);
			if (list_2.size() == 0) {
				return qr(Reference.EMPTY_RESULT);
			}
			return MethodTool.tfc(list_2);
		default :
			return qr(Reference.NSR);
		}
	}
	
	/**
	 * 快速转换String
	 * @param s
	 * @return
	 */
	private String qr(String s) {
		return MethodTool.tfs(s);
	}
}
