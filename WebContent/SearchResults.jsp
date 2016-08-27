<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Search Result</title>
        <link href="${pageContext.request.contextPath}/css/SearchResultStyle.css" rel="stylesheet" type="text/css" />
        <link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
  		<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
  		<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
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
        <h1>${message}</h1>
		
		<div id="sort">
			<a href="${servlet}?order=title&sort=desc">Title(DESC)</a> |
        	<a href="${servlet}?order=title&sort=asc">Title(ASC)</a> |
        	<a href="${servlet}?order=year&sort=desc">Year(DESC)</a> |
        	<a href="${servlet}?order=year&sort=asc">Year(ASC)</a>	
		</div>
		
   
		<c:forEach items="${results}" var="current">
        <table id="movietable">
            <tr>
                <td>
                    <img src=<c:out value="${current.banner_url}"/> onerror="this.src='img/No-Image-Available.jpg';">
                </td>

                <td>
                    <table id="information">              
                        <tr><td>Title: <a class="trigger"  id="${current.movieid}" data-movie_id="${current.movieid}" href="MovieServlet?title=${current.title}&movieid=${current.movieid}">${current.title}</a></td></tr>             
                        <tr><td>Year: <c:out value="${current.year}" /></td></tr>
                        <tr><td>Director: <c:out value="${current.director}"/></td></tr>
                        <tr><td>Genres: 
                        <c:forEach items="${current.genres}" var="genre" varStatus="loop">
                        	${genre.name}
   							<c:if test="${!loop.last}">,</c:if>
						</c:forEach>
						</td></tr>
                        <tr><td>Cast: 
                        <c:forEach items="${current.stars}" var="star" varStatus="loop">
                        	<a href="StarServlet?firstname=${star.first_name}&lastname=${star.last_name}
                        			&starid=${star.id}">${star.name}</a>
   							<c:if test="${!loop.last}">,</c:if>
						</c:forEach>
                        </td></tr>
                    </table>

                </td>
            </tr>
            <tr>
				<td>
					<h3>Price: $${current.price}</h3>
				</td>
			</tr>
            <tr>
                <td>  	
                	<button class="carttrigger" id="${current.movieid}" type="submit">Add To Cart</button>
                </td>
            </tr>

        </table>
        </c:forEach>
        
        <div class="sorting"> 
	       
	        <form method="get" action="${servlet}">
  				<select name="num">
  				<option value="5" selected>5</option>
    			<option value="10">10</option>
    			<option value="25">25</option>
    			<option value="50" >50</option>
    			<option value="100">100</option>
  				</select>
  				<br><br>
  				<input type="submit" value="Results">
  			<br><br>
		</form>
		
		<form id='info' method="get" action="${servlet}">
			<input type="submit" name="prev" value="Prev"/>
            <input type="submit" name="next" value="Next"/>
        </form>        
         
        </div>

        
    </body>
</html>