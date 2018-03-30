package com.alfred.Sailfish.app.ShopmemberService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;

import com.alfred.Sailfish.app.DAO.*;
import com.alfred.Sailfish.app.MemberService.MCoursePlanBookAndAttendService;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class CoursePlanService {
	
	private CoursePlanDAO coursePlanDAO = new CoursePlanDAO();
	private ShopDAO shopDAO = new ShopDAO();
	private CourseDAO courseDAO = new CourseDAO();
	private ShopConfigDAO shopConfigDAO = new ShopConfigDAO();
	private CourseplanBookAndAttendDAO courseplanBookAndAttendDAO = new CourseplanBookAndAttendDAO();
	private MCoursePlanBookAndAttendService mCoursePlanBookAndAttendService = new MCoursePlanBookAndAttendService();

	
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
	public String add(boolean batch,long shop_id,String sm_type,long c_id,long cr_id,long lmu_id,String s_time,String e_time,String remark) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_COURSEPLAN,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		if (!courseDAO.isExist(c_id)) {
			return MethodTool.tfs(Reference.NSR);
		}
		int course_type = courseDAO.queryType(c_id);
		boolean isAdded = false;
		long shopId_1 = shopDAO.queryShopIdByCourseId(c_id);
		long shopId_2 = shopDAO.queryShopByShopmemberId(lmu_id);
		long shopId_3 = shopDAO.queryShopIdByClassroomId(cr_id);
		if((shopId_1 != shopId_2) |( shopId_1 != shopId_3 )|( shopId_2 != shopId_3)) {
			return MethodTool.tfs(Reference.INST_NOT_MATCH);
		}
		if (coursePlanDAO.isRepeated(c_id,cr_id,s_time,e_time)) {
			return MethodTool.tfs(Reference.DUPLICATE);
		}
		if (course_type == 4) {
			if (courseDAO.queryPrivateCourseRestTimes(c_id) < 1) {
				return Reference.EXE_FAIL;
			}
		}
		isAdded = coursePlanDAO.addCoursePlan(c_id, cr_id, lmu_id, s_time, e_time, remark);
		if(!isAdded) {
			return MethodTool.tfs(Reference.EXE_FAIL);
		}
		long id = coursePlanDAO.queryCoursePlanIdByInfo(c_id,cr_id,s_time);
		if (course_type == 4) {
			long m_id = courseDAO.queryPrivateCourseStudentId(c_id);
			courseplanBookAndAttendDAO.book(id,m_id);
		}
		if (batch) {
			return String.valueOf(id);
		}
		return MethodTool.tfs(Reference.dataprefix + id + Reference.datasuffix);
	}
	
	/**
	 * 删除排课
	 * @param cp_id
	 * @param lmu_id
	 * @return
	 */
	public String remove(long shop_id,String sm_type,long cp_id,long lmu_id) {
		boolean isRemoved = false;
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_COURSEPLAN,shop_id).contains(sm_type)) {//权限检查
			return Reference.AUTHORIZE_FAIL;
		}
		if (!coursePlanDAO.isExist(cp_id)) {//检查排课是否存在
			return MethodTool.tfs(Reference.NSR);
		}
		isRemoved = coursePlanDAO.removeCoursePlan(cp_id, lmu_id);
		if (isRemoved) {
			long c_id = courseDAO.querycourseIdByCoursePlanId(cp_id);
			int c_type = courseDAO.queryType(c_id);
			if (c_type == 4) {//私教课处理
				long m_id = courseDAO.queryPrivateCourseStudentId(c_id);
				if (courseplanBookAndAttendDAO.isAttended(cp_id,m_id)) {//如果已经签到，需要将将次数加回去
					int rest_times = courseDAO.queryPrivateCourseRestTimes(c_id);
					courseDAO.updatePrivateCourseResetTimes(c_id,rest_times+1);
				}
				courseplanBookAndAttendDAO.updateStatus(cp_id,m_id,0);
			}
			return MethodTool.tfs(Reference.EXE_SUC);
		}
		return MethodTool.tfs(Reference.EXE_FAIL);
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
	public String modify(long shop_id,String sm_type,long id,long cr_id,long lmu_id,String s_time,String e_time,String remark) {
		if (!shopConfigDAO.queryShopConfig(Reference.SC_ALLOW_MANAGE_COURSEPLAN,shop_id).contains(sm_type)) {
			return Reference.AUTHORIZE_FAIL;
		}
		long ce_id = courseDAO.querycourseIdByCoursePlanId(id);
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

	/**
	 * 通过排课ID查询排课详情
	 * @param cp_id
	 * @return
	 */
	public String queryByCoursePlanId(long cp_id,long m_id) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<>();
		list = coursePlanDAO.queryById(cp_id);
		if (m_id != 0) {
			if (list.size() != 0) {
				map = list.get(0);
				int status = courseplanBookAndAttendDAO.queryBookStatus(cp_id,m_id);
				map.put("status",status);
				ArrayList<HashMap<String, Object>> list_2 = new ArrayList<HashMap<String, Object>>();
				list_2.add(map);
				return MethodTool.tfc(list_2);
			}
		}else {
			return MethodTool.tfc(list);
		}
		return MethodTool.tfs(Reference.NSR);
	}

	/**
	 * 通过排课ID查询私教排课
	 * @param cp_id
	 * @return
	 */
	public String queryPrivateByCoursePlanId(long cp_id) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		list = coursePlanDAO.queryPrivateById(cp_id);
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
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
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
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
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
