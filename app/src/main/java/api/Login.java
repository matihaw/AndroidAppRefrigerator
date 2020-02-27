
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet{
 
  @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      resp.setContentType("text/html");
      PrintWriter pw = resp.getWriter();
      try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("DATABASE","USER","PASSWORD");
              PreparedStatement preparedStatement = connection.prepareStatement("SELECT `id` FROM `user_data` WHERE `username` LIKE ? AND `password` LIKE ?"); 
            preparedStatement.setString(1, req.getParameter("username"));
            preparedStatement.setString(2, req.getParameter("password"));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {  
                pw.print(rs.getString(1)
                );
              }
      }catch (Exception e) {
        pw.println(e.toString());
      }
    }
}