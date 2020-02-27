
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet{
 
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html");
    PrintWriter pw = resp.getWriter();
    try{
      Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("DATABASE","USER","PASSWORD");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `user_data`(`username`, `password`) VALUES (?,?)"); 
        preparedStatement.setString(1, req.getParameter("username"));
        preparedStatement.setString(2, req.getParameter("password"));
        pw.println(preparedStatement.toString());
        preparedStatement.executeUpdate();
    }catch (Exception e) {
      pw.println(e.toString());
    }
  }
}
