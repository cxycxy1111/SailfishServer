package com.alfred.Sailfish.app.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alfred.Sailfish.app.DAO.CardDAO;
import com.alfred.Sailfish.app.DAO.ClassroomDAO;
import com.alfred.Sailfish.app.DAO.CourseDAO;
import com.alfred.Sailfish.app.DAO.CoursePlanDAO;
import com.alfred.Sailfish.app.DAO.CoursePlanTeacherDAO;
import com.alfred.Sailfish.app.DAO.CourseSupportedCardDAO;
import com.alfred.Sailfish.app.DAO.MemberCardDAO;
import com.alfred.Sailfish.app.DAO.MemberDAO;
import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.DAO.ShopmemberDAO;
import com.alfred.Sailfish.app.Service.CardService;
import com.alfred.Sailfish.app.Service.ClassroomService;
import com.alfred.Sailfish.app.Service.CourseService;
import com.alfred.Sailfish.app.Service.CourseSupportedCardService;
import com.alfred.Sailfish.app.Service.ShopmemberService;

public class Test {
	
	public static void main(String args[]) {
		
		CardDAO cardDAO = new CardDAO();
		ClassroomDAO classroomDAO = new ClassroomDAO();
		CourseDAO courseDAO = new CourseDAO();
		CoursePlanDAO coursePlanDAO = new CoursePlanDAO();
		CoursePlanTeacherDAO coursePlanTeacherDAO = new CoursePlanTeacherDAO();
		CourseSupportedCardDAO courseSupportedCardDAO = new CourseSupportedCardDAO();
		MemberCardDAO memberCardDAO = new MemberCardDAO();
		MemberDAO memberDAO = new MemberDAO();
		ShopDAO shopDAO = new ShopDAO();
		ShopmemberDAO shopmemberDAO = new ShopmemberDAO();
		
		ClassroomService classroomService = new ClassroomService();
		CardService cardService = new CardService();
		CourseService courseService = new CourseService();
		CourseSupportedCardService courseSupportedCardService = new CourseSupportedCardService();
		ShopmemberService shopmemberService = new ShopmemberService();
		
		ArrayList<HashMap<String,Object>> l = new ArrayList<HashMap<String,Object>>();
		LinkedList<HashMap<String,Object>> ll = new LinkedList<HashMap<String,Object>>();
		ArrayList<HashMap<String,Object>> hash = new ArrayList<HashMap<String,Object>>();
		String s = null;

		JSONObject jo = null;
		JSONArray ja = null;
		
		int i = courseDAO.queryType(3);
		l = memberDAO.queryList(5);
		hash = coursePlanDAO.queryByCourseId(2);
		ja = new JSONArray(l);
		jo = new JSONObject(s);
		Map<String, Object> h = jo.toMap();
		Set<String> set = h.keySet();
		Iterator<String> iterator = set.iterator();
		if(iterator.hasNext()) {
			String key = iterator.next();
		}
		p(courseService.queryList(5));
		ja = null;
	}
	
	private static void p(Object obj) {
		System.out.println(obj);
	}

}
