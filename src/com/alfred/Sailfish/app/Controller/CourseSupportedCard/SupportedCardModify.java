package com.alfred.Sailfish.app.Controller.CourseSupportedCard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alfred.Sailfish.app.Service.CourseSupportedCardService;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

/**
 * 请求示例
 * http://localhost:8080/Sailfish/SupportedCardModify?m=3_1_2_100-2_1_3_100
 * 1_1_2_100-2_1_3_100
 * type_courseid_cardid_price
 * Servlet implementation class SupportedCardModify
 */
@WebServlet("/SupportedCardModify")
public class SupportedCardModify extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CourseSupportedCardService courseSupportedCardService = new CourseSupportedCardService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupportedCardModify() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		PrintWriter out = resp.getWriter();
		String s = req.getParameter("m");
		String [] str = s.split("-");
		String result;
		StringBuilder builder = new StringBuilder();
		for (String aStr : str) {
			String[] strs = aStr.split("_");
			builder.append(courseSupportedCardService.modify(Integer.valueOf(strs[0]), Integer.valueOf(strs[1]), Integer.valueOf(strs[2]), Integer.valueOf(strs[3])));
			builder.append(",");
		}
		result = builder.toString();
		String js_result;
		if (result.contains(Reference.EXE_FAIL)) {
			if (!result.contains(Reference.EXE_SUC)) {
				js_result = MethodTool.tfs(Reference.EXE_FAIL);
			}else {
				js_result = MethodTool.tfs(Reference.EXE_PARTLY_FAIL);
			}
		}else {
			js_result = MethodTool.tfs(Reference.EXE_SUC);
		}
		out.append(js_result);
		System.out.append(js_result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
