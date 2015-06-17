package mediaStore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayMedia
 */
@WebServlet("/DisplayMedia")
public class DisplayMedia extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = this.getServletContext();
		String host = "cs3.calstatela.edu";
		String port = "3306";
		String dbName = "cs320stu47";
		String username = "cs320stu47";
		String password = "..7qX4#g";
		
		String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
		
		try{
			// Dynamically include the MySQL Driver
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			
			// Instantiate the Driver
			Driver driver = new org.gjt.mm.mysql.Driver();
			
			// Connect to the Database
			Connection connection = 
					DriverManager.getConnection(url, username, password);
			
			// Create a Statement
			Statement statement = connection.createStatement();
			
			if (request.getParameter("return") != null) {
				int id=Integer.parseInt(request.getParameter("return"));
				String query ="UPDATE Media SET isLoan=0,Borrowed=NULL,lentDate=NULL WHERE id="+id;
				statement.executeUpdate(query);
			}
			String query;
			if (request.getParameter("search") != null) {
				String search = request.getParameter("search");
				query= "SELECT * FROM Media WHERE nameMedia LIKE '%"+search+"%' OR Borrowed LIKE '%" +search+"%'";
				System.out.println("Select Query" +query);
			} else 
				query ="SELECT * FROM Media";
			List<MediaBean> mediaList = new ArrayList<MediaBean>();
			ResultSet rs = statement.executeQuery(query);
			System.out.println("Select Query" +query);
			while(rs.next()) {
				int id = rs.getInt("id");
				String type = rs.getString("type");
				String namemedia = rs.getString("nameMedia");
				Timestamp addDate = rs.getTimestamp("addDate");
				boolean isloan = rs.getBoolean("isLoan");
				String lentdate= rs.getString("lentDate");
				String borrowed = rs.getString("Borrowed");
				mediaList.add(new MediaBean(id, type,namemedia,addDate,isloan,lentdate,borrowed));
				System.out.println("type" +type + "Name" +namemedia+ "date" + addDate);
			}
			connection.close();
			request.setAttribute("medialist", mediaList);
			context.setAttribute("MediContext", mediaList);
			request.getRequestDispatcher("/WEB-INF/MediaDisplay.jsp").forward(request,response);
		} catch(Exception e) {}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request,response);
	}

}
