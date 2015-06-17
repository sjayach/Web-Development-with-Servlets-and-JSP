package quotation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/QuotationController")
public class QuotationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		String host = "cs3.calstatela.edu";
		String port = "3306";
		String dbName = "cs320stu47";
		String username = "cs320stu47";
		String password = "";
		
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
			if (request.getParameter("delete") != null) {
				int id = Integer.parseInt(request.getParameter("delete"));
				String query = "DELETE FROM Quotation WHERE id="+id;
				statement.executeUpdate(query);
			}
			List<QuotationBean> quotation = new ArrayList<QuotationBean>();
			String query;
			if (request.getParameter("search") != null) {
				String search = request.getParameter("search"); 
				query = "SELECT * FROM Quotation WHERE author LIKE '%" +search+"%' OR quotes LIKE'%" +search+"%'";
			}else 
				query = "SELECT * FROM Quotation";
			ResultSet rs = statement.executeQuery(query);
			System.out.println("Select" + query);
			while(rs.next()) {
				int id = rs.getInt("id");
				String author = rs.getString("author");
				System.out.println("id " + id + author);
				String quotes = rs.getString("quotes");
				quotation.add(new QuotationBean(id,author,quotes));  
			}
			request.setAttribute("quotation", quotation);
			context.setAttribute("quotationContext", quotation);
			connection.close();
		} catch(Exception e) {
			System.out.println("Exception" +e);
		} 
		request.getRequestDispatcher("/WEB-INF/QuotationView.jsp").forward(request, response);
 
			
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
