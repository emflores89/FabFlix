<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>FabFlix Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="${pageContext.request.contextPath}/css/loginStyle.css" rel="stylesheet" type="text/css" />
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <script src='https://www.google.com/recaptcha/api.js'></script>
    </head>


    <body>
        <form id='info' method="post" action="LoginServlet">
            <fieldset>
                <legend>FabFlix Login</legend>
				
				<p>${message}</p>
				
				<div id="mail">
					<label for="email">Email</label>
                	<input type="text" placeholder="example@email.com..." id="email" name="email" required><br>
				</div>
				
				<div id="pass">
                	<label for="password">Password </label>
                	<input type="text" placeholder="password..." id="password" name="password" required><br>
				</div>
				
				
				<div id="buttons">
                	<input type='submit'  value='Login' />
                	<input type="button" onclick="resetData()" value="Reset"/>
                </div>
         
         		<div id="alt">
                	<input type='button' onclick="resetData()" value='Forget your password?' />
                	<input type="button" onclick="location.href='Registration.jsp'" value="Register an account"/>
                </div>
            
            </fieldset>
            <div  class="g-recaptcha" data-sitekey="6LeMyh4TAAAAACXi8tVVNyTHLjruLaT0CAb-hGir"></div>
        </form>
    </body>
</html>