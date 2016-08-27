package app.Servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Management.User;
import app.Search.Search;

/**
 * Servlet implementation class EmployeeLoginServlet
 */
public class EmployeeLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeLoginServlet() {
        super();
  
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
		
			// read form fields
	        String email = request.getParameter("email");
	        String password = request.getParameter("password");
	        
	        //String md5password = org.apache.commons.codec.digest.DigestUtils.md5Hex(password);	      	     	 
	        
			Search search = new Search();
			Connection connection = search.getConnection();
			User user = search.searchEmployee(email, password, connection);
			 
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
		    	 request.setAttribute("user_name", user.getlast_name());
		    	 RequestDispatcher view = request.getRequestDispatcher("/_dashboard/main.jsp");
		         view.forward(request, response);  
		     }
		     
		     else
		     {
		    	 //Message user with error
		    	 String message = "Invalid email/password";
		    	 request.setAttribute("message", message);
		    	 
		    	 RequestDispatcher view = request.getRequestDispatcher("/_dashboard/employeeLogin.jsp");
		         view.forward(request, response);  		    	 
		     }
		     
		}
		
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
