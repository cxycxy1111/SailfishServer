package com.alfred.Sailfish.app.ShopmemberService;

import java.util.*;

import com.alfred.Sailfish.app.DAO.*;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class CourseService {
	
	private CourseDAO courseDAO = new CourseDAO();
	private ShopDAO shopDAO = new ShopDAO();
	private ShopConfigDAO shopConfigDAO = new ShopConfigDAO();
	private CourseSupportedCardDAO courseSupportedCardDAO= new CourseSupportedCardDAO();
	private MemberDAO memberDAO = new MemberDAO();
	
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
	public String add(long s_id, String sm_type,long lmu_id, String name, int type, int last_time, int max_book_num, String summary) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_COURSE,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
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
	public String addPrivate(long s_id,String sm_type,long lmu_id,long sm_id,long m_id,String name,int type,int total_times,String e_time,int actual_cost) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_COURSE,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
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
	 * 根据课程ID删除课程
	 * @param id
	 * @param lmu
	 * @return
	 */
	public String remove(long s_id,String sm_type,long id,long lmu) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_COURSE,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
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
	 * 根据机构ID查询课程列表
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
		if (course_list.size() == 0) {
			return qr(Reference.EMPTY_RESULT);
		}
		csc_list = courseSupportedCardDAO.querySupportedCards(s_id,0);
		for (int i = 0;i < course_list.size();i++) {
			HashMap<String,Object> temp_map = course_list.get(i);
			StringBuilder builder = new StringBuilder();
			String temp_id = String.valueOf(temp_map.get("id"));
			for (int j = 0;j < csc_list.size();j++) {
				String temp_csc_course_id = String.valueOf(csc_list.get(j).get("course_id"));
				if ((temp_csc_course_id.equals(temp_id))) {
					builder.append(csc_list.get(j).get("card_name"));
					builder.append("、");
				}
			}
			String sc = builder.toString();
			if (sc.length()>1) {
				sc = sc.substring(0,sc.length()-1);
			}
			temp_map.put("supportedcard",sc);
			full_list.add(temp_map);
		}
		return MethodTool.tfc(full_list);
	}

	/**
	 * 根据会员ID查询课程
	 * @param m_id
	 * @return
	 */
	public String queryCourseByMemberId(long m_id) {
		if (memberDAO.isDel(m_id)) {
			return Reference.NSR;
		}else {
			ArrayList<HashMap<String,Object>> full_list = new ArrayList<>();
			ArrayList<HashMap<String,Object>> privateCourseList = new ArrayList<>();
			ArrayList<HashMap<String,Object>> publicCourseList = new ArrayList<>();
			ArrayList<HashMap<String,Object>> temp_full_list = new ArrayList<>();
			ArrayList<HashMap<String,Object>> csc_list = new ArrayList<>();
			long s_id = shopDAO.queryShopIdByMemberId(m_id);
			privateCourseList = courseDAO.queryPrivateCourseByMemberId(m_id);
			publicCourseList = courseDAO.queryNormalList(s_id);
			if (privateCourseList.size() == 0 && publicCourseList.size() == 0) {
				return Reference.EMPTY_RESULT;
			} else {
				csc_list = courseSupportedCardDAO.querySupportedCards(s_id,0);
				temp_full_list.addAll(privateCourseList);
				temp_full_list.addAll(publicCourseList);
				for (int i = 0;i < temp_full_list.size();i++) {
					HashMap<String,Object> temp_map = temp_full_list.get(i);
					StringBuilder builder = new StringBuilder();
					String temp_id = String.valueOf(temp_map.get("id"));
					for (int j = 0;j < csc_list.size();j++) {
						String temp_csc_course_id = String.valueOf(csc_list.get(j).get("course_id"));
						if ((temp_csc_course_id.equals(temp_id))) {
							builder.append(csc_list.get(j).get("card_name"));
							builder.append("、");
						}
					}
					String sc = builder.toString();
					if (sc.length()>1) {
						sc = sc.substring(0,sc.length()-1);
					}
					temp_map.put("supportedcard",sc);
					full_list.add(temp_map);
				}
				return MethodTool.tfc(full_list);
			}

		}
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
	public String modify(long s_id,String sm_type,long c_id,long lmu_id,String name,int last_time,int max_book_num,String summary) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_COURSE,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
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
	 * 修改私教课程
	 * @param s_id
	 * @param sm_type
	 * @param c_id
	 * @param lmu_id
	 * @param name
	 * @param total_times
	 * @param invalid_time
	 * @param total_cost
	 * @return
	 */
	public String modifyPrivate(long s_id,String sm_type,long c_id,long lmu_id,String name,int total_times,String invalid_time,int total_cost) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_COURSE,s_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		boolean isUpdated = false;
		if (shopDAO.queryShopIdByCourseId(c_id) != shopDAO.queryShopByShopmemberId(lmu_id)) {
			return qr(Reference.INST_NOT_MATCH);
		}
		isUpdated = courseDAO.modifyPrivate(c_id,lmu_id,name,invalid_time,total_times,total_cost);
		if (isUpdated) {
			return qr(Reference.EXE_SUC);
		}else {
			return qr(Reference.EXE_FAIL);
		}
	}

	/**
	 * 查询课程详情
	 * @param c_id
	 * @return
	 */
	public String queryDetail(long c_id) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		int i = courseDAO.queryType(c_id);
		if (i == 4) {
			list = courseDAO.queryPrivateDetail(c_id);
			if (list.size() == 0) {
				return MethodTool.tfs(Reference.EMPTY_RESULT);
			}
			return MethodTool.tfc(list);
		}else if (i == 1 || i == 2 || i == 3){
			list = courseDAO.queryDetail(c_id);
			if (list.size() == 0) {
				return MethodTool.tfs(Reference.EMPTY_RESULT);
			}
			return MethodTool.tfc(list);
		}else {
			return MethodTool.tfs(Reference.EMPTY_RESULT);
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
