package homework3;

import homework2.CS320User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AirportDisplay
 */
@WebServlet("/AirportDisplay")
public class AirportDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static int hit_counter = 0;
	
	public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

		
		double R = 6372.8;
		
		
		
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
 
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
		

	}
	
	
	public void init() throws ServletException {
		ServletContext context = this.getServletContext();
		if (context.getAttribute("PhoneticContext") == null) {
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
				
				
				String phonetic_query = "SELECT city FROM zipcodes";
				ResultSet rs = statement.executeQuery(phonetic_query);
				List <String> phonetic = new ArrayList<String>();
				while ( rs.next() ){
					phonetic.add (rs.getString("city"));
				}
				Set<String> hs = new HashSet<>();
				hs.addAll(phonetic);
				phonetic.clear();
				phonetic.addAll(hs);
				System.out.println("phonetic" + phonetic.size());
				context.setAttribute("PhoneticContext", phonetic);
				connection.close();
			} catch(Exception e) {System.out.println("exception");}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 ServletContext context = this.getServletContext();
		List<String> phonetic = (List<String>) context.getAttribute("PhoneticContext");
		request.setAttribute("phonetic", phonetic);
		request
		.getRequestDispatcher("/WEB-INF/jsp/homework3/Airports.jsp")
		.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			
			// Create the Query
			
			ArrayList<AirportBean> zip_op = 
					new ArrayList<AirportBean>();
			// Query the Database and get a reference to a Result Set
			if (request.getParameter("search_zip") != null ||
					request.getParameter("searchQuery") != null) {
				
				boolean isCity = true;
				String city_name = null;
				if (request.getParameter("searchQuery") != null) {
					try {
						int zip = Integer.parseInt(request.getParameter("searchQuery"));
						isCity = false;
					} catch(NumberFormatException e) {
						city_name = request.getParameter("searchQuery");
						isCity=true;
					}
				} else
					city_name=request.getParameter("city_name");
				
				if (isCity) {
				
					String query = "SELECT * FROM zipcodes WHERE city LIKE '%" + city_name + "%'";
					ResultSet resultSet = statement.executeQuery(query);
				
					while ( resultSet.next() ){
						String zip = resultSet.getString("zip");
						String city = resultSet.getString("city");
						double lat = resultSet.getDouble("latitude");
						double lon = resultSet.getDouble("longitude");
						zip_op.add(new AirportBean(zip, city, lat, lon));
					}
					if(request.getParameter("searchQuery") == null)
						request.setAttribute("DisplayZip", zip_op);
				}
			}
			
			
			if (request.getParameter("search_airport") != null) {
				String city;
				int zipcode = 0;
				boolean isZip;
				double distance = Double.parseDouble(request.getParameter("distance"));
				String airportQuery= "SELECT * FROM airports;";
				try {
					zipcode = Integer.parseInt(request.getParameter("searchQuery"));
					isZip = true;
				}
				catch(NumberFormatException e) {
					isZip = false;
				}
				
				
				
				
				if (isZip) {
					String queryname = "SELECT * FROM zipcodes WHERE zip=" + zipcode;
					System.out.println("Query" +queryname);
					ResultSet resultSet = statement.executeQuery(queryname);
					System.out.println("isZip " +isZip);
					double latitude = 0;
					double longitude = 0;
					
					while(resultSet.next()) {
						latitude = resultSet.getDouble("latitude");
						longitude = resultSet.getDouble("longitude");
					}
					
					ResultSet resultSet1 = statement.executeQuery(airportQuery);
					double airLat;
					double airLon;
					ArrayList<AirportBean> display_airport = 
							new ArrayList<AirportBean>();
					while(resultSet1.next()) {
						airLat = resultSet1.getDouble("Latitude");
						airLon = resultSet1.getDouble("Longitude");
						String airport = resultSet1.getString("Airport");
						double km = calculateDistance(latitude, longitude, airLat, airLon);
						if (km <= distance){
							System.out.println("Airport " + airport + " Latitude " + airLat + " Longitude " +airLon);
							display_airport.add(new AirportBean(airport, airLat, airLon ));
						}
					}
					request.setAttribute("airports", display_airport);
					
				} else {
					double airLat;
					double airLon;
					ArrayList<AirportBean> display_airport = 
							new ArrayList<AirportBean>();
					String [] airport_name = new String[2048];
					int i = 0;
					System.out.println("before "  + airportQuery);
					ResultSet resultSet1 = statement.executeQuery(airportQuery);
					System.out.println("Executed");
					while(resultSet1.next()) {
						airLat = resultSet1.getDouble("Latitude");
						airLon = resultSet1.getDouble("Longitude");
						String airport = resultSet1.getString("Airport");
						System.out.println("airport is " +airport +airLat+airLon);
						display_airport.add(new AirportBean(airport, airLat, airLon ));
					}
					
					for(AirportBean zip : zip_op) {
						for(AirportBean air : display_airport) {
							double km = calculateDistance(zip.latitude, zip.longitude, air.latitude, air.longitude);
							if (km <= distance) {
								
										airport_name[i] = air.airport;
										i++; 
								
							}
						}
					}
					// Remove the duplicate entries of airport
				    Set<String> stringSet = new HashSet<>(Arrays.asList(airport_name));
				    String[] filteredArray = stringSet.toArray(new String[0]);
					for(i=0; i <filteredArray.length; i++)
						System.out.println( i +"Airport" +filteredArray[i]);
					request.setAttribute("cityAirport", filteredArray);
					
				}
			}
			connection.close();
			
		}catch(Exception e){};
		doGet(request,response);
	}

}
