<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Advanced Search</title>
        <link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/AdvancedSearchStyle.css" rel="stylesheet" type="text/css" />
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>

    </head>

    <nav>
        <a href="LogoutServlet">Sign Out</a>
        <a href="CartServlet">Cart</a>     
        <a href="AdvancedSearch.jsp">Search</a> 
        <a class="active" href="IndexServlet">Home</a> 
        
    </nav>

    <body>
        <form id='info' method="get" action = "AdvancedSearchServlet">
            <fieldset>
                <legend>Advanced Search</legend>

                <p>General Information</p>
                <label for="title">Title: </label>
                <input type="text" placeholder="Leave Blank if None..." id="title" name="title"><br>

                <label for="year">Year: </label>
                <input type="text" placeholder="Leave Blank if None..." id="year" name="year"><br>

                <label for="director">Director: </label>
                <input type="text" placeholder="Leave Blank if None..." id="director" name="director"><br>

                <p> Cast Information </p>
                <label for="fname">First Name: </label>
                <input type="text" placeholder="Leave Blank if None..." id="fname" name="fname"><br>

                <label for="lname">Last Name: </label>
                <input type="text" placeholder="Leave Blank if None..." id="lname" name="lname"><br>

                <input type='submit' value='Submit' />
                <input type="button" onclick="resetData()" value="Reset"/>

				<br>
				<br>
				<p>${message}</p>
            </fieldset>
        </form>

    </body>
</html>