<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Insert Star</title>
        <link href="${pageContext.request.contextPath}/css/addStarStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/dashboardStyle.css" rel="stylesheet" type="text/css" />
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    </head>

    <div class = "title">
        <h1>FabFlix Management</h1>
    </div>
    
    <nav>
        <a class="active" href="${pageContext.request.contextPath}/_dashboard/main.jsp">Home</a> |
        <a href="${pageContext.request.contextPath}/_dashboard/insertStar.jsp">Add Star</a> |
        <a href="${pageContext.request.contextPath}/_dashboard/addMovie.jsp">Add Movie</a> |
        <a href="${pageContext.request.contextPath}/MetaDataServlet">Metadata</a> |
        <a href="${pageContext.request.contextPath}/EmployeeLogoutServlet">Logout</a>
    </nav>
	

    <body>
        <form id="info" method="get" action = "${pageContext.request.contextPath}/AddStarServlet">
            <fieldset>
                <legend>Insert Star</legend>

                <label for="fname">First Name: </label>
                <input type="text" placeholder="Leave Blank if None..." id="fname" name="fname"><br>

                <label for="lname">Last Name: </label>
                <input type="text" placeholder="Required..." id="lname" name="lname" required><br>
                
                <label for="dob">Date of Birth: </label>
                <input type="text" placeholder="YYYY-MM-DD" id="dob" name="dob"><br>
				
				<label for="photo_url">Photo URL: </label>
                <input type="text" placeholder="Leave Blank if None..." id="photo_url" name="photo_url"><br>
	
                <input type='submit' value='Submit' />
                <input type="button" onclick="resetData()" value="Reset"/>

				<br>
				<br>
				<p>${message}</p>
            </fieldset>
        </form>

    </body>
</html>