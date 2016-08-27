package app.Servlets;

import java.io.IOException;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import app.Management.User;
import app.Management.VerifyUtils;
import app.Search.Search;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
 
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try {
			
			String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
			System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
			// Verify CAPTCHA.
			boolean valid = VerifyUtils.verify(gRecaptchaResponse);
			if (!valid) {
				
				 //Message user with error
		    	 String message = "reCAPTCHA is Invalid";
		    	 request.setAttribute("message", message);
		    	 
		    	 RequestDispatcher view = request.getRequestDispatcher("login.jsp");
		         view.forward(request, response); 
		         
		         return;
			}
										
			// read form fields
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	        
	        //String md5password = org.apache.commons.codec.digest.DigestUtils.md5Hex(password);	      	     	 
	        
			Search search = new Search();
			Connection connection = search.getConnection();
			User user = search.searchUser(email, password, connection);
			 
			 //Get Results
		     if(user != null)
		     {
		    	 //Create Session
		    	 HttpSession session = request.getSession(true);
		    	 session.setMaxInactiveInterval(60*30);
		    	 
		    	 User session_user = (User)session.getAttribute("User");
		    	 if(session_user == null)
		    	 {
		    		 session.setAttribute("User", user);
		    	 }
		    	 else
		    	 {
		    		 session.setAttribute("User", session_user);
		    	 }
		
		    	 session.setAttribute("auth", true);
		    	 
		    	 //Direct to Main Page
		    	// RequestDispatcher view = request.getRequestDispatcher("/IndexServlet");
		        // view.forward(request, response);  
				response.sendRedirect("IndexServlet");
		    	 
		     }
		     
		     else
		     {
		    	 //Message user with error
		    	 String message = "Invalid email/password";
		    	 request.setAttribute("message", message);
		    	 
		    	 RequestDispatcher view = request.getRequestDispatcher("login.jsp");
		         view.forward(request, response);  		    	 
		     }
		     
		}
		
		catch (Exception e) {
			response.getWriter().write(e.toString());
			System.out.println(e.getMessage());
		}
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
