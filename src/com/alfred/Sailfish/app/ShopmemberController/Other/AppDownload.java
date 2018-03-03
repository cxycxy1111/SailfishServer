package com.alfred.Sailfish.app.ShopmemberController.Other;

import com.alfred.Sailfish.app.DAO.AppDownloadDAO;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "AppDownload",urlPatterns = "/appDownload")
public class AppDownload extends HttpServlet {

    AppDownloadDAO appDownloadDAO = new AppDownloadDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File f = new File("/usr/local/appRelease/GuanGuanYi.apk");
        if(f.exists()){
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String req_time = simpleDateFormat.format(date);
            String ip = request.getRemoteAddr();
            appDownloadDAO.add(ip,req_time);
            FileInputStream fis = new FileInputStream(f);
            String filename= URLEncoder.encode(f.getName(),"utf-8"); //解决中文文件名下载后乱码的问题
            byte[] b = new byte[fis.available()];
            fis.read(b);
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition","attachment; filename="+filename+"");
            response.addHeader("Content-Length", (new Long(f.length())).toString());
            //获取响应报文输出流对象
            ServletOutputStream out =response.getOutputStream();
            //输出
            out.write(b);
            out.flush();
            out.close();
        }
    }
}
