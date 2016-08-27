<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="${pageContext.request.contextPath}/css/StarResultStyle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
  		<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
  		<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
		<title>Star Result</title>
	</head>
	
	<nav>
        <a href="LogoutServlet">Sign Out</a>
        <a href="CartServlet">Cart</a>     
        <a href="AdvancedSearch.jsp">Search</a> 
        <a class="active" href="IndexServlet">Home</a> 
        
        <form method="get" action="SearchServlet">
            <input type="text" placeholder="Search For Movies..." id="term" name="term" required>
            <input type="hidden" name="type" value="Search">
            <input type="submit" value="Search">
            <script>
        		$("form#term").autocomplete("/fabflix/MovieAutoComplete");
    		</script>
        </form>

    </nav>
    
	<body>
		<h1>Star Result</h1>
		<table id="startable">
            <tr>
                <td>
                    <img src="${star.photo_url}" onerror="this.src='img/No-Image-Available.jpg';"/>
                </td>
                <td>
                    <table id="starinformation">
                        <tr><td>Name: ${star.name}</td></tr>
                        <tr><td>Date of Birth: ${star.dob}</td></tr>
                        <tr><td>Star ID: ${star.id}</td></tr>
                        <tr><td>Starred In: 
                        <c:forEach items="${star.starredin}" var="movie" varStatus="loop">
                        	<a href="MovieServlet?title=${movie.title}&movieid=${movie.movieid}">${movie.title}</a>
   							<c:if test="${!loop.last}">,</c:if>
						</c:forEach>
						</td></tr>                
                    </table>

                </td>
            </tr>
        </table>	
	</body>
</html>