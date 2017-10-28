package com.alfred.Sailfish.app.Service;

import java.util.ArrayList;
import java.util.IdentityHashMap;

import com.alfred.Sailfish.app.DAO.CourseDAO;
import com.alfred.Sailfish.app.DAO.CoursePlanDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class CoursePlanService {
	
	private CoursePlanDAO coursePlanDAO = new CoursePlanDAO();
	private ShopDAO shopDAO = new ShopDAO();
	private CourseDAO courseDAO = new CourseDAO();
	
	public CoursePlanService (){
	}
	
	/**
	 * 添加排课
	 * @param c_id
	 * @param cr_id
	 * @param lmu_id
	 * @param s_time
	 * @param e_time
	 * @param remark
	 * @return
	 */
	public String add(long c_id,long cr_id,long lmu_id,String s_time,String e_time,String remark) {
		if (courseDAO.isExist(c_id) == false) {
			return MethodTool.tfs(Reference.NSR);
		}
		boolean isAdded = false;
		long shopId_1 = shopDAO.queryShopIdByCourseId(c_id);
		long shopId_2 = shopDAO.queryShopByShopmemberId(lmu_id);
		long shopId_3 = shopDAO.queryShopIdByClassroomId(cr_id);
		if((shopId_1 != shopId_2) |( shopId_1 != shopId_3 )|( shopId_2 != shopId_3)) {
			return MethodTool.tfs(Reference.INST_NOT_MATCH);
		}
		
		isAdded = coursePlanDAO.addCoursePlan(c_id, cr_id, lmu_id, s_time, e_time, remark);
		if(isAdded == false) {
			return MethodTool.tfs(Reference.EXE_FAIL);
		}
		return MethodTool.tfs(Reference.EXE_SUC);
	}
	
	/**
	 * 删除排课
	 * @param id
	 * @param lmu_id
	 * @return
	 */
	public String remove(long id,long lmu_id) {
		boolean isRemoved = false;
		if (coursePlanDAO.isExist(id) == false) {
			return MethodTool.tfs(Reference.NSR);
		}
		if (shopDAO.queryShopIdByCoursePlanId(id) != shopDAO.queryShopByShopmemberId(lmu_id)) {
			return MethodTool.tfs(Reference.INST_NOT_MATCH);
		}
		isRemoved = coursePlanDAO.removeCoursePlan(id, lmu_id);
		if (isRemoved == false) {
			return MethodTool.tfs(Reference.EXE_FAIL);
		}
		return MethodTool.tfs(Reference.EXE_SUC);
	}
	
	/**
	 * 修改排课
	 * @param id
	 * @param cr_id
	 * @param lmu_id
	 * @param s_time
	 * @param e_time
	 * @param remark
	 * @return
	 */
	public String modify(long id,long cr_id,long lmu_id,String s_time,String e_time,String remark) {
		if(shopDAO.queryShopIdByCoursePlanId(id) != shopDAO.queryShopByShopmemberId(lmu_id)) {
			return MethodTool.tfs(Reference.INST_NOT_MATCH);
		}
		if(coursePlanDAO.isExist(id) == false) {
			return MethodTool.tfs(Reference.NSR);
		}
		boolean isModified = coursePlanDAO.modifyCoursePlan(id, cr_id, lmu_id, s_time, e_time, remark);
		if (isModified = true) {
			return MethodTool.tfs(Reference.EXE_SUC);
		}else {
			return MethodTool.tfs(Reference.EXE_FAIL);
		}
	}
	
	/**
	 * 通过机构ID查询所有排课
	 * @param s_id
	 * @return
	 */
	public String queryByShopId(long s_id) {
		ArrayList<IdentityHashMap<String, Object>> list = new ArrayList<IdentityHashMap<String, Object>>();
		list = coursePlanDAO.queryByShopId(s_id);
		if (list.size() == 0) {
			return MethodTool.qr(Reference.EMPTY_RESULT);
		}
		return MethodTool.tfc(list);
	}
	
	/**
	 * 通过课程ID查询该课程下的所有排课
	 * @param ce_id
	 * @return
	 */
	public String queryByCourseId(long ce_id) {
		ArrayList<IdentityHashMap<String, Object>> list = new ArrayList<IdentityHashMap<String, Object>>();
		list = coursePlanDAO.queryByCourseId(ce_id);
		if (list.size() == 0) {
			return MethodTool.tfs(Reference.EMPTY_RESULT);
		}
		return MethodTool.tfc(list);
	}
	
	/**
	 * 查询某位教师所带的所有排课
	 * @param sm_id
	 * @return
	 */
	public String queryByShopmemberId(long sm_id){
		ArrayList<IdentityHashMap<String, Object>> list = new ArrayList<IdentityHashMap<String, Object>>();
		list = coursePlanDAO.queryByShopMemberId(sm_id);
		if (list.size() == 0) {
			return MethodTool.qr(Reference.EMPTY_RESULT);
		}
		return MethodTool.tfc(list);
	}
	
}
