<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="${pageContext.request.contextPath}/css/SearchResultStyle.css" rel="stylesheet" type="text/css" />
    	<link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
  		<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
  		<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
		<title>Movie Result</title>
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
        <h1>Movie Information</h1>
        
		<c:forEach items="${results}" var="current">
        <table id="movietable">
            <tr>
                <td>
                    <img src=<c:out value="${current.banner_url}" /> onerror="this.src='img/No-Image-Available.jpg';">
                </td>
				
                <td>
                    <table id="information">
                        <tr><td>Title: ${current.title}</td></tr>
                        <tr><td>Movie ID: ${current.movieid}</td></tr>
                        <tr><td>Year: <c:out value="${current.year}" /></td></tr>
                        <tr><td>Director: <c:out value="${current.director}"/></td></tr>
                        <tr><td>Trailer: <a href="${current.trailer_url}">Click Here To See Trailer</a></td></tr>
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
    </body>
</html>