package com.alfred.Sailfish.app.Service;

import java.util.ArrayList;
import java.util.HashMap;
import com.alfred.Sailfish.app.DAO.ClassroomDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.Util.Reference;
import com.alfred.Sailfish.app.Util.MethodTool;

public class ClassroomService {
	
	private ClassroomDAO classroomDAO = new ClassroomDAO();
	private ShopDAO shopDAO = new ShopDAO();
	public ClassroomService () {
	}
	
	/**
	 * 新增教室
	 * @param shop_id
	 * @param name
	 * @return
	 */
	public String add(long shop_id,String name) {
		System.out.println(shopDAO.isExist(shop_id));
		if (!shopDAO.isExist(shop_id)) {
			return MethodTool.tfs(Reference.NSR);
		}
		boolean isAdded = false;
		if (classroomDAO.isNameRepeated(name, shop_id)) {
			return MethodTool.tfs(Reference.DUPLICATE);
		}
		isAdded = classroomDAO.add(shop_id, name);
		
		if (!isAdded) {
			return MethodTool.tfs(Reference.EXE_FAIL);
		}
		return MethodTool.tfs(Reference.id_prefix + classroomDAO.queryCRIdByCRName(shop_id,name) + Reference.datasuffix);
	}
	
	/**
	 * 获取教室列表
	 * @param s_id 机构ID
	 * @return
	 */
	public String getList (long s_id) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		list = classroomDAO.queryByShopId(s_id);
		if (list.size() == 0) {
			return MethodTool.tfs(Reference.NSR);
		}else {
			return MethodTool.tfc(list);
		}
	}
	
	/**
	 * 获取教室详情
	 * @param cr_id
	 * @return
	 */
	public String queryDetail(long cr_id) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		list = classroomDAO.queryDetailById(cr_id);
		if (classroomDAO.isDel(cr_id) || list.size() == 0) {
			return MethodTool.tfs(Reference.NSR);
		}
		return MethodTool.tfc(list);
	}
	
	public String queryCRIdByCRName(long shop_id,String name) {
		return MethodTool.tfs(Reference.id_prefix + classroomDAO.queryCRIdByCRName(shop_id,name) + Reference.datasuffix);
	}
	
	/**
	 * 更新教室
	 * @param cr_id
	 * @param name
	 * @return
	 */
	public String modify(long cr_id,String name) {
		if (!classroomDAO.isExist(cr_id)) {
			return MethodTool.tfs(Reference.NSR);
		}
		if (classroomDAO.isDel(cr_id)) {
			return MethodTool.tfs(Reference.NSR);
		}
		boolean updated = classroomDAO.modify(cr_id, name);
		if(updated) {
			return MethodTool.tfs(Reference.EXE_SUC);
		}
		return MethodTool.tfs(Reference.EXE_FAIL);
	}
	
	/**
	 * 删除教室
	 * @param cr_id
	 * @return
	 */
	public String remove(long cr_id) {
		if (classroomDAO.isDel(cr_id)) {
			return MethodTool.tfs(Reference.NSR);
		}
		boolean isDel = classroomDAO.remove(cr_id);
		if (isDel) {
			return MethodTool.tfs(Reference.EXE_SUC);
		}else {
			return MethodTool.tfs(Reference.EXE_FAIL);
		}
	}

}
