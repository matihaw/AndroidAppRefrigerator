
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Hello extends HttpServlet{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html");
    PrintWriter pw = resp.getWriter();
      try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("DATABASE","USER","PASSWORD");
        //String blobToString;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Product WHERE user_id LIKE ? ");
        preparedStatement.setString(1, req.getParameter("id"));
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {  
          ///blobToString = new String(rs.getBlob(4).getBytes(1, (int) rs.getBlob(4).length()));
          pw.print(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3) + ","///blobToString 
          );
        }
      }catch (Exception e) {
          pw.println(e.toString());
      }
      
  }
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html");
    PrintWriter pw = resp.getWriter();
    try{
      Class.forName("com.mysql.jdbc.Driver");
      Connection connection = DriverManager.getConnection("DATABASE","USER","PASSWORD");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `Product`(`ID_barcode`, `Name`, `Amount`, `user_id`) VALUES (?,?,?,?)"); 
        preparedStatement.setString(1, req.getParameter("barcode"));
        preparedStatement.setString(2, req.getParameter("name"));
        preparedStatement.setString(3, req.getParameter("amount"));
        preparedStatement.setString(4, req.getParameter("id"));
              pw.println(preparedStatement.toString());

        preparedStatement.executeUpdate();
    }catch (Exception e) {
      pw.println(e.toString());
    }
  }
}
