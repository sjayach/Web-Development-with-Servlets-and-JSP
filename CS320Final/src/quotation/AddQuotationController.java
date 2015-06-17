package quotation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddQuotationController
 */
@WebServlet("/AddQuotationController")
public class AddQuotationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/AddQuotationView.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String author = request.getParameter("author");
		String quotes = request.getParameter("quotes");
		if (author.trim().length() <= 0 || quotes.trim().length() <= 0) {
			request.setAttribute("error", "Author or Quotes should not be empty");
			doGet(request,response);
		} else {
			
			System.out.println("author" + author + quotes);
			
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
				String query= "INSERT INTO Quotation (author,quotes) VALUES ('"+author+"','"+quotes+"')";
				statement.executeUpdate(query);
	
				connection.close();
			} catch(Exception e) {
				System.out.println("Exception" +e);
			} 
			doGet(request,response);
		}
	}

}
