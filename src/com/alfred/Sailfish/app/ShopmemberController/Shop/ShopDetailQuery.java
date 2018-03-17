package com.alfred.Sailfish.app.ShopmemberController.Shop;

import com.alfred.Sailfish.app.DAO.ShopDAO;
import com.alfred.Sailfish.app.ShopmemberService.ShopService;
import com.alfred.Sailfish.app.Util.BaseServlet;
import com.alfred.Sailfish.app.Util.MethodTool;
import com.alfred.Sailfish.app.Util.Reference;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ShopDetailQuery",urlPatterns = "/shopDetailQuery")
public class ShopDetailQuery extends BaseServlet {

    private ShopService shopService = new ShopService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            long s_id = MethodTool.getSessionValueToLong(session,"s_id");
            String s = shopService.queryShopDetail(s_id);
            response.getWriter().append(s);
        }else {
            response.getWriter().append(Reference.SESSION_EXPIRED);
        }
    }
}
