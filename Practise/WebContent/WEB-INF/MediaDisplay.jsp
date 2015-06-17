<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<a href ="AddMedia">Add Media</a><br /><br />


<br />
<form action="DisplayMedia" method="get">
<input type="text" name="search">
<input type="submit" value="Search">
</form>
<h3>CDs:</h3><br />
<table border="1">
	<tr>
		<th>Name of the Media</th>
		<th>Date Added</th>
		<th>Borrower</th>
		<th>Date Borrowed</th>
	</tr>
	<c:forEach items="${ medialist}" var="media">
		<c:if test="${media.types == 'CD' }">
		<tr>
			
			<td>${media.namemedia}</td>
			<td>${media.addDate}</td>
			<c:choose>
				<c:when test="${not empty media.borrowed}">
					<td>${media.borrowed}<br/><a href ="DisplayMedia?return=${media.id}">Return</a></td>
					
					<td>${media.lentdate }
				</c:when>
				<c:otherwise>
					<td><a href ="LendPage?id=${media.id}">Lend</a></td>
					<td></td>
				</c:otherwise>
			</c:choose>
		</tr>
	</c:if>
	</c:forEach>
	
</table>





<h3>DVDs:</h3><br />
<table border="1">
	<tr>
		<th>Name of the Media</th>
		<th>Date Added</th>
		<th>Borrower</th>
		<th>Date Borrowed</th>
	</tr>
	<c:forEach items="${ medialist}" var="media">
		<c:if test="${media.types == 'DVD' }">
		<tr>
			
			<td>${media.namemedia}</td>
			<td>${media.addDate}</td>
			<c:choose>
				<c:when test="${not empty media.borrowed}">
					<td>${media.borrowed}<br/><a href ="DisplayMedia?return=${media.id}">Return</a></td>
					<td>${media.lentdate }
				</c:when>
				<c:otherwise>
					<td><a href ="LendPage?id=${media.id}">Lend</a></td>
					<td></td>
				</c:otherwise>
			</c:choose>
		</tr>
	</c:if>
	</c:forEach>
	
</table>



<h3>BlueRay:</h3><br />
<table border="1">
	<tr>
		<th>Name of the Media</th>
		<th>Date Added</th>
		<th>Borrower</th>
		<th>Date Borrowed</th>
	</tr>
	<c:forEach items="${ medialist}" var="media">
		<c:if test="${media.types == 'BlueRay' }">
		<tr>
			
			<td>${media.namemedia}</td>
			<td>${media.addDate}</td>
			<c:choose>
				<c:when test="${not empty media.borrowed}">
					<td>${media.borrowed}<br/><a href ="DisplayMedia?return=${media.id}">Return</a></td>
					<td>${media.lentdate }
				</c:when>
				<c:otherwise>
					<td><a href ="LendPage?id=${media.id}">Lend</a></td>
					<td></td>
				</c:otherwise>
			</c:choose>
		</tr>
	</c:if>
	</c:forEach>
	
</table>


</body>
</html>