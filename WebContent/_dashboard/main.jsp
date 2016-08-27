<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Management Home</title>
		<link href="${pageContext.request.contextPath}/css/dashboardStyle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/mainStyle.css" rel="stylesheet" type="text/css" />
	</head>
	
	<div class = "title">
        <h1>FabFlix Management</h1>
    </div>
	
	<nav>
        <a class="active" href="#home">Home</a> |
        <a href="${pageContext.request.contextPath}/_dashboard/insertStar.jsp">Add Star</a> |
        <a href="${pageContext.request.contextPath}/_dashboard/addMovie.jsp">Add Movie</a> |
        <a href="${pageContext.request.contextPath}/MetaDataServlet">Metadata</a> |
        <a href="${pageContext.request.contextPath}/EmployeeLogoutServlet">Logout</a>
    </nav>
	
	<body>	
		<h1>Welcome ${user_name}!</h1>
		<h2>Dashboard Actions</h2>
		<h3>Add/Update Movie: Add or a update a movie in the database </h3>
		<h3>Add Star: Add a star to the database</h3>
		<h3>Add Star: Get metadata (i.e table names and information) from the database</h3>
		<h3>Logout: Leave the Dashboard </h3>
	</body>
</html>