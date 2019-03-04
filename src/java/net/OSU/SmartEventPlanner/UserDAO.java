/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.OSU.SmartEventPlanner;

/**
 *
 * @author Administrator
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
/**
 *
 * @author Administrator
 */
public class UserDAO {
    public static User queryUser(String userName) {
        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("SELECT * FROM user WHERE UserName=?");

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, userName);
            
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            if (resultSet.next()) {
                user.setUserName(resultSet.getString("userName"));
                user.setPassword(resultSet.getString("passWord"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);
        }
    }
    public static void addUser(String userName,String passWord)
    {
        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        int result = 0;
        ResultSet resultSet=null;


        //生成SQL代码
         StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("INSERT INTO user(UserName,Password) VALUES(?,?)");
       

        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, passWord);
            result = preparedStatement.executeUpdate();
            //resultSet=preparedStatement.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        } finally {
            DBManager.closeAll(connection, preparedStatement,resultSet);
        }
    }
     public  static void addEvent(Event event)
    {
        //获得数据库的连接对象
        Connection connection = DBManager.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        int result=0;
        //生成SQL代码
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("UPDATE user SET Title=?,Description=?,Time=?,Location=?,Priority=?,Contacts=? WHERE UserName=?");    
        //设置数据库的字段值
        try {
            preparedStatement = connection.prepareStatement(sqlStatement.toString());
           //preparedStatement.setString(1,startAddr);
           // preparedStatement.setString(2,destAddr);
           preparedStatement.setString(1,event.getTitle());
           preparedStatement.setString(2,event.getDescription());
           preparedStatement.setString(3,event.getTime());
           preparedStatement.setString(4,event.getLocation());
           preparedStatement.setString(5,event.getPriority());
           preparedStatement.setString(6,event.getContacts());
           preparedStatement.setString(7,event.getUserName());
           AddEventServlet.WriteStringToFile("D:/smarteventplannerlog.txt", preparedStatement.toString());
           //preparedStatement.setString(6,userName);
           result=preparedStatement.executeUpdate();
            //resultSet=preparedStatement.executeQuery();
           AddEventServlet.WriteStringToFile("D:/smarteventplannerlog.txt", "row_num"+result+"username"+event.getUserName());

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        
        } finally {
           DBManager.closeAll(connection, preparedStatement,resultSet);
         
        }

    }
}
      