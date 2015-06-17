<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<title>Homework 3</title>

</head>
<body>


  
  

	<form  class="form-horizontal" action="AirportDisplay" method=post>
	<c:if test="${empty phonetic }">
		<c:out value="empty"></c:out>
	</c:if>
		<datalist id="cities">
			<c:forEach items="${ phonetic}" var="cityPhonetic">
    			<option value="${cityPhonetic}">
    			     
    		</c:forEach>
  		</datalist>
	<h3>Specify the city name:</h3>
	<input  class="form-control" type="text" name="city_name" list="cities" placeholder="Enter city name" x-webkit-speech>
	<br />
	<div class="form-group">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="btn btn-success" type="submit" value="Search ZIP" name="search_zip">
	</div>
	</form>
	<br />
	
	<form action="AirportDisplay" method=post>
		<h3>Specify the Zipcode or City:</h3>
		<input class="form-control" type="text" name="searchQuery" placeholder="Enter ZIP or City">
		<h3>Specify the radius(in km):</h3>
		<input class="form-control" type="text" name="distance" placeholder="Enter the radius(in Km)">
		<div class="form-group">
		<input class="btn btn-success" type="submit" value="Serch airport" name="search_airport">
		</div>
	</form>

	<c:if test="${not empty DisplayZip}">
		<div class="container">
			<table class="table table-bordered table-striped">
				<tr>
					<th>City</th>
					<th>ZipCode </th>
					<th>Map Thumbnail</th>
				</tr>
				<c:forEach items="${DisplayZip}" var="place" varStatus="stat">
					<tr>
						<td>${place.city}</td>
						<td>${place.zip}</td>
						<td><img alt="Map" src="https://maps.googleapis.com/maps/api/staticmap?&zoom=9&size=300x300&maptype=roadmap&markers=${place.latitude},${ place.longitude}"/></td>
					</tr>
					
				</c:forEach>
			</table>
		</div>
	</c:if>
	
	
	
	<c:if test="${not empty airports}">
		<div class="container">
			<table class="table table-bordered table-striped">
				<tr>
					<th>Airport</th>
					<th>Latitude</th>
					<th>Longitude</th>
					<th>Map Thumbnail</th>
				</tr>
				<c:forEach items="${ airports}" var="plot" varStatus="stat">
					<tr>
						<td>${plot.airport}</td>
						<td>${plot.latitude}</td>
						<td>${plot.longitude}</td>
						<td><img alt="Map" src="https://maps.googleapis.com/maps/api/staticmap?&zoom=9&size=300x300&maptype=roadmap&markers=${plot.latitude},${ plot.longitude}"/></td>
						  
						
					</tr>
				</c:forEach>
			</table>
			
		</div>
	</c:if>
	
	<c:if test="${not empty cityAirport}">
		<div class="container">
			<table class="table table-bordered table-striped">
				<tr>
					<th>Airport</th>
				</tr>
				<c:forEach items="${cityAirport}" var="airport" varStatus="stat">
					<tr>
						<c:if test="${not empty airport}">
						<td>${airport}</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
			
		</div>
	</c:if>
	<c:if test="${not empty myVar}">
		<img alt="Map" src="https://maps.googleapis.com/maps/api/staticmap?&zoom=9&size=600x300&maptype=roadmap${myVar}">
	</c:if>
</body>
</html>