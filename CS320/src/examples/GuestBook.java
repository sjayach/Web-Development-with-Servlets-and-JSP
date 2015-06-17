package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.TreeMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.ranges.Range;

/**
 * Servlet implementation class GuestBook
 */
@WebServlet("/GuestBook")
public class GuestBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuestBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		NavigableMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		map.put(0, 0);    // 0..4     => 0
		map.put(5, 1);    // 5..10    => 1
		map.put(11, 2);   // 11..200  => 2

		int key = 11;
		// To do a lookup for some value in 'key'
		if (key < 0 || key > 200) {
		    // out of range
		} else {
		   System.out.println("Tree value" +map.floorEntry(key).getValue());
		}

		ServletContext context = this.getServletContext();
		if (context.getAttribute("books") == null){
			ArrayList<Books> array = new ArrayList<Books>();
			array.add(new Books("cs312", "data structures and algorithms"));
			array.add(new Books("cs320", "web programming"));
			context.setAttribute("books", array);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("	<meta charset=\"UTF-8\">");
		out.println("	<title>Register Page</title>");
		out.println("	<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css\">");
		out.println("</head>");
		out.println("<body>");
		out.println("	<form class=\"form-horizontal\" action=\"GuestBook\" method=\"post\">");
		out.println(" search <input type=\"text\" name=\"search\" placeholder=\"Enter book code\">");
		out.println("<input type=\"submit\" value=\"submit\">");
		out.println("</form>");
		out.println("	<form class=\"form-horizontal\" action=\"GuestBook\" method=\"post\">");
		out.println(" Code <input type=\"text\" name=\"code\" placeholder=\"Enter book code\">");
		out.println(" Book name <input type=\"text\" name=\"bookname\" placeholder=\"Enter book name\">");
		out.println("<input type=\"submit\" value=\"submit\">");
		ServletContext context = this.getServletContext();
		ArrayList<Books> array = (ArrayList<Books>) context.getAttribute("books");
		
		for (Books book : array) {
			if (request.getParameter("search") == null || book.getCode().contains(request.getParameter("search"))
					|| book.getName().contains(request.getParameter("search")))
			
			out.println(book.getCode() + ","+book.getName());
		}
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String code = request.getParameter("code");
		String name = request.getParameter("bookname");
		
		if (code != null && name != null) {
			ServletContext context = this.getServletContext();
			ArrayList<Books> array = (ArrayList<Books>) context.getAttribute("books");
			array.add(new Books(code, name));
		}
		doGet(request, response);
	}

}
