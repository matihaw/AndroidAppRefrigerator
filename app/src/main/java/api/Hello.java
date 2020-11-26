
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
          Connection connection = DriverManager.getConnection("jdbc:mysql://mysql.ct8.pl/m12094_fridgeApp","m12094_root","PASSWORD");

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
         Connection connection = DriverManager.getConnection("jdbc:mysql://mysql.ct8.pl/m12094_fridgeApp","m12094_root","Starwars17");

        PreparedStatement getDataFromDb = connection.prepareStatement("SELECT * FROM Product WHERE user_id LIKE ? ");
        getDataFromDb.setString(1, req.getParameter("id"));
        ResultSet rs = getDataFromDb.executeQuery();
      boolean notIncremented = true;
        while(rs.next()) {
          if(rs.getString(1).equals(req.getParameter("barcode")) && rs.getInt(4) == Integer.valueOf(req.getParameter("id"))  && rs.getString(2).equals(req.getParameter("name"))) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `Product` SET `Amount`=`Amount` + ? WHERE `user_id` = ? AND `ID_barcode` LIKE ? AND `name` LIKE ?");
            preparedStatement.setString(1, req.getParameter("amount"));
            preparedStatement.setString(2, req.getParameter("id"));
            preparedStatement.setString(3, req.getParameter("barcode"));
            preparedStatement.setString(4, req.getParameter("name"));
            preparedStatement.executeUpdate();
            pw.println(preparedStatement.toString());
            notIncremented = false;
            break;        
            }
             pw.println("jestem w petli");

        }
          pw.println("wychodze z petli");
          if(notIncremented){
           PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `Product`(`ID_barcode`, `Name`, `Amount`, `user_id`) VALUES (?,?,?,?)"); 
                preparedStatement.setString(1, req.getParameter("barcode"));
                preparedStatement.setString(2, req.getParameter("name"));
                preparedStatement.setString(3, req.getParameter("amount"));
                preparedStatement.setString(4, req.getParameter("id"));
                pw.println(preparedStatement.toString());
                preparedStatement.executeUpdate();
                pw.println(preparedStatement.toString());
          }
        
    }catch (Exception e) {
      pw.println(e.toString());
    }
  }
}
