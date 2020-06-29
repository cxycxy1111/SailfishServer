package com.alfred.Sailfish.app.ShopmemberController.Other;

import com.alfred.Sailfish.app.Util.ShopMemberBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "ImageLoad",urlPatterns = "/imageLoad")
public class ImageLoad extends ShopMemberBaseServlet {

    private static final String content_type_jpeg = "image/jpeg";
    private static final String content_type_bmp = "image/bmp";
    private static final String content_type_png = "image/png";
    private static final String content_type_tiff = "image/tiff";
    private static final String content_type_gif = "image/gif";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        String name = request.getParameter("imgName");
        String suffix = name.substring(name.lastIndexOf(".") + 1);
        FileInputStream inputStream = new FileInputStream("/usr/local/img/"+name);
        int i = inputStream.available();
        //byte数组用于存放图片字节数据
        byte[] buff = new byte[i];
        inputStream.read(buff);
        //记得关闭输入流
        inputStream.close();
        //设置发送到客户端的响应内容类型
        if (suffix.equals("jpg")) {
            response.setContentType(content_type_jpeg);
        }else if (suffix.equals("jpeg")) {
            response.setContentType(content_type_jpeg);
        }else if (suffix.equals("png")) {
            response.setContentType(content_type_png);
        }else if (suffix.equals("bmp")) {
            response.setContentType(content_type_bmp);
        }else if (suffix.equals("gif")) {
            response.setContentType(content_type_gif);
        }else if (suffix.equals("tiff")) {
            response.setContentType(content_type_tiff);
        }
        response.setHeader("Content-Length" ,i +"");
        OutputStream out = response.getOutputStream();
        out.write(buff);
        //关闭响应输出流
        out.close();
    }
}
