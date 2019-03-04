/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.OSU.SmartEventPlanner;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.BufferedWriter;

/**
 * 
 * 测试登录Servlet
 *
 * @author Implementist
 */
public  class AddEventServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 设置响应内容类型  
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Event event=new Event();

        try (PrintWriter out = response.getWriter()) {
           
            event.setTitle(request.getParameter("title").trim());
            event.setDescription(request.getParameter("description").trim());
            event.setTime(request.getParameter("time").trim());
            event.setLocation(request.getParameter("location").trim());
            event.setPriority(request.getParameter("priority").trim());
            event.setContacts(request.getParameter("contacts").trim());
            event.setUserName(request.getParameter("userName").trim());
            UserDAO.addEvent(event);
            Map<String, String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();
            params.put("Result", "success");
            jsonObject.put("params", params);
            out.write(jsonObject.toString());
            WriteStringToFile("D:/smarteventplannerlog.txt","username is "+event.getUserName());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

   public static void WriteStringToFile(String filePath,String imformation) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("\n");
            bw.write(imformation);// 往已有的文件上添加字符串
            bw.close();
            fw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}