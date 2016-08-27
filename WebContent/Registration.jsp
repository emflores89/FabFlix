<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>FabFlix Registration</title>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link href="${pageContext.request.contextPath}/css/loginStyle.css" rel="stylesheet" type="text/css" />
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <script src='https://www.google.com/recaptcha/api.js'></script>
    </head>


    <body>
        <form id='info' method="post" action="RegistrationServlet">
            <fieldset>
                <legend>FabFlix Registration</legend>
				
				<p>${message}</p>
				
				<div id="mail">
					<label for="email">Email</label>
                	<input type="text" id="email" name="email" required>
                	<p>${message_email}</p>
				
				</div>
				
				<div id="mail">
					<label for="reemail">Re-Enter Email</label>
                	<input type="text" id="reemail" name="reemail" required>
                	<p>${message_remail}</p>
				</div>
				
				<div id="pass">
                	<label for="password">Password (Must be at least 6 characters long)</label>
                	<input type="password" id="password" name="password" required>
                	<p>${message_pass}</p>
				</div>
				
				<div id="name">
                	<label for="fname">First Name</label>
                	<input type="text"  id="fname" name="fname" required>
                	<p>${message_fname}</p>
                	
                	<label for="lname">Last Name</label>
                	<input type="text" id="lname" name="lname" required>
                	<p>${message_lname}</p>
				</div>
				<div  class="g-recaptcha" data-sitekey="6LeMyh4TAAAAACXi8tVVNyTHLjruLaT0CAb-hGir"></div>
	
				
				
				<div id="reg_button">
                	<input type='submit'  value='Create your FabFlix account' />
                </div>
         
            
            </fieldset>
            <div  class="g-recaptcha" data-sitekey="6LeMyh4TAAAAACXi8tVVNyTHLjruLaT0CAb-hGir"></div>
        </form>
    </body>
</html>