<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>FabFlix Main WebPage</title>
        <link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/indexStyle.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
  		<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    </head>

    <nav>
    	<div class="menu">
			<ul>
 	 			<li class="dropdown">
    				<a href="#" class="dropbtn">Account</a>
    				<div class="dropdown-content">
      					<a href="#">Orders</a>
     	 				<a href="#">Settings</a>
     					<a href="#">Sign Out</a>
   		 			</div>
  				</li>
  			
  				<li><a href="CartServlet">Cart</a></li>
      			<li> <a href="AdvancedSearch.jsp">Search</a></li>
        		<li><a class="active" href="#home">Home</a></li>
        
			</ul>
		</div>
		<div class="search_box">
 			<form method="get" action="SearchServlet">
            	<input type="text" placeholder="Search For Movies..." id="term" name="term" required>
            	<input type="hidden" name="type" value="Search">
            	<input type="submit" value="Search">
            	<script>
        			$("form#term").autocomplete("/fabflix/MovieAutoComplete");
    			</script>
        	</form>		
		</div>

    </nav>

    <body>
    	<div class="browsing">		
    		<h2>Popular Movies</h2>
    		<table>
    			
    			<tr>
    				<c:forEach items="${sellers}" var="current">
    					<td> <img src=<c:out value="${current.banner_url}" /> onerror="this.src='img/No-Image-Available.jpg';"></td>
    	 	    	</c:forEach> 	
    			</tr>
    			<tr>
    				<c:forEach items="${sellers}" var="current">
    	 				<td><a href="MovieServlet?movieid=${current.movieid}&title=${current.title}">${current.title} (${current.year})</a></td>
    	 	    	</c:forEach> 	 			    			
    			</tr>	
    		</table>
    		
    		<h2> Browse By Title</h2>
    		 <c:forEach items="${titles}" var="current">
    	 		<a href="SearchServlet?type=letter&term=${current}">${current}</a>
    	 		<c:if test="${!loop.last}">|</c:if>
    	 	</c:forEach> 
    	 
    	 	<h2> Browse By Genres</h2>
    	 	<c:forEach items="${genres}" var="current">
    	 		<a href="SearchServlet?type=genre&term=${current}">${current}</a>
    	 		<c:if test="${!loop.last}">|</c:if>
    	 	</c:forEach> 
    	 </div>
    </body>
</html>