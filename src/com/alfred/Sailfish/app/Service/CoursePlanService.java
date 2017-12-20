package com.alfred.Sailfish.app.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;

import com.alfred.Sailfish.app.DAO.CourseDAO;
import com.alfred.Sailfish.app.DAO.CoursePlanDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

import javax.xml.ws.soap.MTOM;

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
		if (!courseDAO.isExist(c_id)) {
			return MethodTool.tfs(Reference.NSR);
		}
		boolean isAdded = false;
		long shopId_1 = shopDAO.queryShopIdByCourseId(c_id);
		long shopId_2 = shopDAO.queryShopByShopmemberId(lmu_id);
		long shopId_3 = shopDAO.queryShopIdByClassroomId(cr_id);
		if((shopId_1 != shopId_2) |( shopId_1 != shopId_3 )|( shopId_2 != shopId_3)) {
			return MethodTool.tfs(Reference.INST_NOT_MATCH);
		}
		if (coursePlanDAO.isRepeated(c_id,cr_id,s_time)) {
			return MethodTool.tfs(Reference.DUPLICATE);
		}
		isAdded = coursePlanDAO.addCoursePlan(c_id, cr_id, lmu_id, s_time, e_time, remark);
		if(!isAdded) {
			return MethodTool.tfs(Reference.EXE_FAIL);
		}
		long id = coursePlanDAO.queryCoursePlanIdByInfo(c_id,cr_id,s_time);
		return MethodTool.tfs(Reference.dataprefix + id + Reference.datasuffix);
	}
	
	/**
	 * 删除排课
	 * @param id
	 * @param lmu_id
	 * @return
	 */
	public String remove(long id,long lmu_id) {
		boolean isRemoved = false;
		if (!coursePlanDAO.isExist(id)) {
			return MethodTool.tfs(Reference.NSR);
		}
		if (shopDAO.queryShopIdByCoursePlanId(id) != shopDAO.queryShopByShopmemberId(lmu_id)) {
			return MethodTool.tfs(Reference.INST_NOT_MATCH);
		}
		isRemoved = coursePlanDAO.removeCoursePlan(id, lmu_id);
		if (!isRemoved) {
			return MethodTool.tfs(Reference.EXE_FAIL);
		}
		return MethodTool.tfs(Reference.EXE_SUC);
	}
	
	/**
	 * 修改排课
	 * @param id 排课ID
	 * @param cr_id 课室ID
	 * @param lmu_id 最后修改人ID
	 * @param s_time 开始时间
	 * @param e_time 结束时间
	 * @param remark 备注
	 * @return
	 */
	public String modify(long id,long cr_id,long lmu_id,String s_time,String e_time,String remark) {
		long ce_id = courseDAO.querycourseIdByCoursePlanId(id);
		if (coursePlanDAO.isRepeated(ce_id,cr_id,s_time)) {
			return MethodTool.tfs(Reference.DUPLICATE);
		}
		if(shopDAO.queryShopIdByCoursePlanId(id) != shopDAO.queryShopByShopmemberId(lmu_id)) {
			return MethodTool.tfs(Reference.INST_NOT_MATCH);
		}
		if(!coursePlanDAO.isExist(id)) {
			return MethodTool.tfs(Reference.NSR);
		}
		if (coursePlanDAO.modifyCoursePlan(id, cr_id, lmu_id, s_time, e_time, remark)) {
			return MethodTool.tfs(Reference.EXE_SUC);
		}else {
			return MethodTool.tfs(Reference.EXE_FAIL);
		}
	}

	public String queryByCoursePlanId(long cp_id) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		list = coursePlanDAO.queryById(cp_id);
		if (list.size() != 0) {
			return MethodTool.tfc(list);
		}
		return MethodTool.tfs(Reference.NSR);
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
