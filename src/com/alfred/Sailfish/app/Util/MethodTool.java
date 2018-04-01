package com.alfred.Sailfish.app.Util;

import java.io.Serializable;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Quick Tool快速工具，包含一些静态方法，全局变量
 * @author dengweixiong
 * 
 */
public class MethodTool implements Serializable{
	
	public final static String MD5(String pwd) {  
        //用于加密的字符  
        char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
                'A', 'B', 'C', 'D', 'E', 'F' };  
        try {  
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中  
            byte[] btInput = pwd.getBytes();  
               
            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
               
            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要  
            mdInst.update(btInput);  
               
            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文  
            byte[] md = mdInst.digest();  
               
            // 把密文转换成十六进制的字符串形式  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {   //  i = 0  
                byte byte0 = md[i];  //95  
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5   
                str[k++] = md5String[byte0 & 0xf];   //   F  
            }  
               
            //返回经过加密后的字符串  
            return new String(str);  
               
        } catch (Exception e) {  
            return null;  
        }  
    }  
	
	/**
	 * 从集合中取出值转为int
	 * @param list
	 * @param key
	 * @return
	 */
	public static int toInt(ArrayList<HashMap<String,Object>> list,String key) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map = list.get(0);
		Object o = map.get(key);
		String s = String.valueOf(o);
		int i = Integer.parseInt(s);
		return i;
	}
	
	/**
	 * 从集合中取出值转为long
	 * @param list
	 * @param key
	 * @return
	 */
	public static long toLong(ArrayList<HashMap<String,Object>> list,String key) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map = list.get(0);
		Object o = map.get(key);
		String s = String.valueOf(o);
		System.out.println(s);

		return Long.parseLong(s);
	}
	
	/**
	 * 从集合中取出值转化为boolean
	 * @param list
	 * @param key
	 * @return
	 */
	public static boolean toBool(ArrayList<HashMap<String,Object>> list,String key) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map = list.get(0);
		Object o = map.get(key);
		String s = String.valueOf(o);
		boolean l = Boolean.valueOf(s);
		System.out.println("toBool:" + l);
		return l;
	}
	
	/**
	 * 从集合中取出值转化为String
	 * @param list
	 * @param key
	 * @return
	 */
	public static String toString(ArrayList<HashMap<String,Object>> list,String key) {
		HashMap<String,Object> map = list.get(0);
		Object o = map.get(key);
		return String.valueOf(o);
	}
	
	/**
	 * qr Quick Return 快速返回
	 * @param s
	 * @return
	 */
	public static String qr(String s) {
		return tfs(s);
	}
	
	/**
	 * tfc transform form Collection 将集合转为json字符串
	 * @param c
	 * @return
	 */
	public static String tfc(Collection<?> c) {
		if (c != null) {
			JSONArray ja = new JSONArray(c);
			return ja.toString();
		}else {
			return e();
		}
	}
	
	/**
	 * tfs transform form String，将字符串转为json字符串
	 * 从字符串中转化
	 * @return
	 */
	public static String tfs(String s) {
		if (s != null && !Objects.equals(s, "")) {
			JSONObject js = null;
			try {
				js = new JSONObject(s);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return js.toString();
		}else {
			return e();
		}
	}
	
	/**
	 * tfo transform form Object 将对象转为json字符串
	 * 从对象中转化
	 * @param o
	 * @return
	 */
	public static String tfo(Object o) {
		if (o != null) {
			JSONObject jo = new JSONObject(o);
			return jo.toString();
		}else {
			return e();
		}
	}
	
	public static String e() {
		JSONObject j = null;
		try {
			j = new JSONObject("{\"status\":\"Interal Error\"}");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return j.toString();
	}
	
	/**
	 * 将请求参数解释为long
	 * @param req
	 * @param param
	 * @return
	 */
	public static long reqParseToLong (HttpServletRequest req,String param) {
		String s = req.getParameter(param);
		if (s == null) {
			return 0;
		}
		return Long.valueOf(req.getParameter(param));
	}

	/**
	 * 将请求参数解释为int
	 * @param req
	 * @param param
	 * @return
	 */
	public static int reqParseToInt (HttpServletRequest req,String param) {
		return Integer.parseInt(req.getParameter(param));
	}

	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSSSS");
		return sdf.format(new Date());
	}

	public static long getSessionValueToLong(HttpSession session,String key) {
		Object o = session.getAttribute(key);
		return Long.parseLong(String.valueOf(o));
	}

	public static String getSessionValueToInt(HttpSession session,String key) {
		Object o = session.getAttribute(key);
		return String.valueOf(o);
	}
	
}
