package homework2;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Homework2/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	public void init() throws ServletException {
		ServletContext context = this.getServletContext();
		if (context.getAttribute("HomeworkUsers") == null) {
			ArrayList<CS320User> user = new ArrayList<CS320User>();
			user.add(new CS320User("John", "Doe", "john@doe.com", "1!"));
			user.add(new CS320User("Joe", "Boxer", "joe@boxer.com", "2@"));
			context.setAttribute("HomeworkUsers", user);
		}

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("	<meta charset=\"UTF-8\">");
		out.println("	<title>Login Page</title>");
		out.println("	<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=\"container\">");
		out.println("<div class=\"jumbotron\">");
		out.println("<h3> Login </h2>");
		out.println("	<form class=\"form-horizontal\" action=\"Login\" method=\"post\">");
		
		if (request.getAttribute("error") != null) {
			out.println("<p class=\"text-danger\">"+request.getAttribute("error")+"</p>");
		}
		
		out.println(" Username <input type=\"text\" class=\"form-control\" name=\"username\" placeholder=\"Username\">");
		out.println("</br>");
		out.println(" Password <input type=\"password\" class=\"form-control\" name=\"password\" placeholder=\"Password\">");
		out.println("</br>");
		out.println("<div class=\"checkbox\">");
		out.println("<label><input type=\"checkbox\"  name=\"rememberMe\"> Remember me </label>");
		out.println("</div>");
		out.println("</br>");
		out.println("<div class=\"form-group\">");
		out.println("			<label><input type=\"submit\" class=\"btn btn-success\" value=\"Login\"></label>");
		out.println("		</div>");
		out.println("</div>");
		out.println("</div>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password");
		
		ServletContext context = this.getServletContext();
		ArrayList <CS320User> users = (ArrayList<CS320User>) context.getAttribute("HomeworkUsers");
		
		for(CS320User user : users) {
			if (user.getEmail().equals(username) && user.getPassword().equals(password)) {
				
				HttpSession session = request.getSession();
				
				if (request.getParameter("rememberMe") != null) {
					try {
						MessageDigest digest = MessageDigest.getInstance("SHA-256");
						byte[] hashedBytes = digest.digest(username.getBytes("UTF-8"));
						StringBuffer stringBuffer = new StringBuffer();
						for (int i = 0; i < hashedBytes.length; i++) {
							stringBuffer.append(Integer.toString((hashedBytes[i] & 0xff) + 0x100, 16)
									.substring(1));
						}
						Cookie cookie = new Cookie("Homework2", stringBuffer.toString());
						// Set the cookie age to one day
						cookie.setMaxAge(60*60*24);
						response.addCookie( cookie );

					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

				}
				session.setAttribute("CurrentUser", user);
				System.out.println(user.getName());
					
				response.sendRedirect("Welcome");
				
			}
		}
		request.setAttribute("error", "Invalid Username	and/or Password");
		doGet(request, response);
	}

}
