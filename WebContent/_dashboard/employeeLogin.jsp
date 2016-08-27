<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Management FabFlix Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="${pageContext.request.contextPath}/css/loginStyle.css" rel="stylesheet" type="text/css" />
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    </head>

    <div class = "title">
        <h1>Welcome to FabFlix Management</h1>
    </div>

    <body>
        <form id='info' method="post" action="${pageContext.request.contextPath}/EmployeeLoginServlet">
            <fieldset>
                <legend>FabFlix Employee Login</legend>
				
				<p>${message}</p>

                <label for="email">Email: </label>
                <input type="text" placeholder="example@email.com..." id="email" name="email" required><br>

                <label for="password">Password: </label>
                <input type="text" placeholder="password..." id="password" name="password" required><br>


                <input type='submit'  value='Login' />
                <input type="button" onclick="resetData()" value="Reset"/>
         

            </fieldset>
        </form>
    </body>