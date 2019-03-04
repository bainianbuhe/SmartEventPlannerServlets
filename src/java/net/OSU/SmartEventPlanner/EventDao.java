package net.OSU.SmartEventPlanner;

/**
 *
 * @author Bo Liu
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
public class EventDAO {
    public static Event queryEvent(String userName) {
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
            Event event = new Event();
            if (resultSet.next()) {
                event.setContacts(resultSet.getString("contacts"));
                event.setDescription(resultSet.getString("description"));
                event.setLocation(resultSet.getString("location"));
                event.setPriority(resultSet.getString("priority"));
                event.setTime(resultSet.getString("time"));
                event.setTitle(resultSet.getString("title"));
                return event;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            DBManager.closeAll(connection, preparedStatement, resultSet);
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
