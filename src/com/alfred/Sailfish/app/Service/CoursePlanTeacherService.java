package com.alfred.Sailfish.app.Service;

import com.alfred.Sailfish.app.DAO.CourseDAO;
import com.alfred.Sailfish.app.DAO.CoursePlanDAO;
import com.alfred.Sailfish.app.DAO.CoursePlanTeacherDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class CoursePlanTeacherService {
	
	private CoursePlanTeacherDAO coursePlanTeacherService = new CoursePlanTeacherDAO();
	private ShopDAO shopDAO = new ShopDAO();
	private CoursePlanDAO coursePlanDAO = new CoursePlanDAO();
	private CourseDAO courseDAO = new CourseDAO();
	
	public CoursePlanTeacherService(){
		
	}

	/**
	 * 修改教师排课安排
	 * @param type 操作类型，详见QR.JAVA
	 * @param cp_id 排课ID
	 * @param sm_id 教师ID
	 * @return
	 */
	public String modify(int type,long cp_id,long sm_id) {
		if(shopDAO.queryShopIdByCoursePlanId(cp_id) != shopDAO.queryShopByShopmemberId(sm_id)) {
			return MethodTool.qr(Reference.INST_NOT_MATCH);
		}
		//检查课程类型是否私教课程
		if(courseDAO.queryType(courseDAO.querycourseIdByCoursePlanId(cp_id)) == Reference.TYPE_PRIVATE_COURSE) {
			return MethodTool.qr(Reference.NOT_MATCH);
		}
		switch (type) {
		case Reference.TYPE_OPERATE_ADD:
			if(coursePlanTeacherService.isLead(cp_id, sm_id)) {
				return MethodTool.qr(Reference.DUPLICATE);
			}
			boolean isAdded = coursePlanTeacherService.add(cp_id, sm_id);
			if (!isAdded) {
				return MethodTool.qr(Reference.EXE_FAIL);
			}
			return MethodTool.qr(Reference.EXE_SUC);
		case Reference.TYPE_OPERATE_REMOVE:
			if(!coursePlanTeacherService.isLead(cp_id, sm_id)) {
				return MethodTool.qr(Reference.NSR);
			}
			boolean isRemoved = coursePlanTeacherService.remove(cp_id, sm_id);
			if (isRemoved) {
				return MethodTool.qr(Reference.EXE_SUC);
			}
			return MethodTool.qr(Reference.EXE_FAIL);
		default:
			return null;
		}
	}
	
	/**
	 * 通过排课ID获取教师列表
	 * @param cp_id
	 * @return
	 */
	public String queryList(long cp_id) {
		if(!coursePlanDAO.isExist(cp_id)) {
			return MethodTool.tfs(Reference.NSR);
		}
		long ce_id = courseDAO.querycourseIdByCoursePlanId(cp_id);
		long s_id = shopDAO.queryShopIdByCoursePlanId(cp_id);
		if (courseDAO.queryType(ce_id) == Reference.TYPE_PRIVATE_COURSE) {
			return MethodTool.tfc(coursePlanTeacherService.queryPrivateCoursePlanTeacher(ce_id));
		}
		return MethodTool.tfc(coursePlanTeacherService.queryById(cp_id,s_id));
	}
	
}
