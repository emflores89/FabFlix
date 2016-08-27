<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Add Movie</title>
        <link href="${pageContext.request.contextPath}/css/addStarStyle.css" rel="stylesheet" type="text/css" />
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <link href="${pageContext.request.contextPath}/css/dashboardStyle.css" rel="stylesheet" type="text/css" />
    </head>

    <div class = 'title'>
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
        <form id='info' method="get" action = "${pageContext.request.contextPath}/AddMovieServlet">
            <fieldset>
                <legend>Add/Update Movie</legend>

                <p>Movie Information (All information is required)</p>
                <label for="title">Title: </label>
                <input type="text" placeholder="The GodFather..." id="title" name="title" required><br>

                <label for="year">Year: </label>
                <input type="text" placeholder="2001..." id="year" name="year" required><br>

                <label for="director">Director: </label>
                <input type="text" placeholder="Steven Speilberg..." id="director" name="director" required><br>
				
                <p> Star Information </p>
                <label for="name">Name: </label>
                <input type="text" placeholder="Leave Blank if None..." id="name" name="name" required><br>

            	<label for="dob">Date of Birth: </label>
                <input type="text" placeholder="YYYY-MM-DD" id="dob" name="dob"><br>
				
				<label for="photo_url">Photo URL: </label>
                <input type="text" placeholder="Leave Blank if None..." id="photo_url" name="photo_url"><br>

				<p> Genre Information </p>
				
				<label for="genre_name">Genre: </label>
                <input type="text" placeholder="Action..." id="genre_name" name="genre_name" required><br>
				

                <input type='submit' value='Submit' />
                <input type="button" onclick="resetData()" value="Reset"/>

				<br>
				<br>
				<p>${message}</p>
            </fieldset>
        </form>

    </body>
</html>