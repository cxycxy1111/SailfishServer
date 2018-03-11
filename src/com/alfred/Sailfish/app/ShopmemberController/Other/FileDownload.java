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

@WebServlet(name = "DocDownload",urlPatterns = "/docDownload")
public class FileDownload extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("docName");
        File f = new File("/usr/local/docs/"+ fileName);
        if(f.exists()){
            FileInputStream fis = new FileInputStream(f);
            String filename= URLEncoder.encode(f.getName(),"gb2312"); //解决中文文件名下载后乱码的问题
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println(suffix);
            byte[] b = new byte[fis.available()];
            fis.read(b);
            response.setCharacterEncoding("ISO8859-1");
            if (suffix.equals("docx")) {
                response.setContentType("application/octet-stream");
            }
            response.setHeader("Content-Disposition","attachment;filename="+filename+"");
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
