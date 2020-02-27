
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Delete extends HttpServlet{
 
  @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      resp.setContentType("text/html");
      PrintWriter pw = resp.getWriter();
      try{
        Class.forName("com.mysql.jdbc.Driver");
          Connection connection = DriverManager.getConnection("DATABASE","USER","PASSWORD");
              PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Product WHERE ID_barcode LIKE ?"); 
            preparedStatement.setString(1, req.getParameter("id"));
            pw.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
          
      }catch (Exception e) {
        pw.println(e.toString());
      }
    }
  

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html");
    PrintWriter pw = resp.getWriter();
    try {
      Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("DATABASE","USER","PASSWORD");
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Product WHERE `user_id` LIKE ?");
                preparedStatement.setString(1, req.getParameter("id"));
              pw.println(preparedStatement.toString());
              preparedStatement.executeUpdate();
    }catch (Exception e) {
      pw.print("Error: " + e);
    }
  }
}