<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Checkout</title>
		<link href="${pageContext.request.contextPath}/css/navigationStyle.css" rel="stylesheet" type="text/css" />
    	<link href="${pageContext.request.contextPath}/css/CheckOutStyle.css" rel="stylesheet" type="text/css" />
    	<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
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
        <form id="info" method="post" action = "CheckOutServlet">
            <fieldset>
                <legend>Checkout Information</legend>
                
                <p>${message}</p>
    
    		                   
                <label for="title">Credit Card No.: </label>
                <input type="text" id="creditcard" name="creditcard" required><br>

                <label for="expire">Expiration Date: </label>
                <input type="text" id="expire" placeholder="YYYY-MM-DD" name="expire" required><br>
                
                <label for="fname">First Name: </label>
                <input type="text" id="fname" name="fname" required><br>

                <label for="lname">Last Name: </label>
                <input type="text" id="lname" name="lname" required><br>

                <input type="submit" value="Submit" />
                <input type="button" onclick="resetData()" value="Reset"/>

            </fieldset>
        </form>

    </body>
</html>