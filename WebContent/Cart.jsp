<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
	<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/CartStyle.css" rel="stylesheet" type="text/css" />
	    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
		<title>Cart</title>
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
		<h2>Cart Information</h2>
		<p id="msg">${message}</p>
        <table id="CartInformation" border="1">
            <tr>
                <td> Movie ID</td>
                <td> Title</td>
                <td> Quantity</td>
                <td> Price</td>
            </tr>
			
			<c:forEach items="${results}" var="current">
            <tr>
                <td>${current.key.movieid}</td>
                <td>${current.key.title}</td>
                <td>
                    <form id="qty" method="get" action="CartServlet">
                    	<input type="hidden" name="action" value="update">
                    	<input type="hidden" name="title" value="${current.key.title}">
                    	<input type="hidden" name="movieid" value="${current.key.movieid}">
                        <input type="number" name="quantity" min="0" value="${current.value}">
                        <button type="submit" value="Submit">Update</button>
                    </form>
                <td> ${current.key.price}</td>
                <td>
            </tr>
            </c:forEach>
            
            <tr>
            	<td> Total </td>
            	<td> ${total} </td>
            <tr>
        </table>
        
        <form id= "buttonActions" method="post" action="CartServlet">
        	<input type="submit"  name="action" value="Checkout" />       
            <input type="submit"  name="action" value="Empty Cart"/>
  		</form>
	</body>
</html>