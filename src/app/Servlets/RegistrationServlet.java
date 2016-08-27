package app.Servlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String email = request.getParameter("email");
		String reemail = request.getParameter("reemail");
		String password =  request.getParameter("password");
		String fname = request.getParameter("fname");
		String lname= request.getParameter("lname");
		
		
		Pattern pattern = Pattern.compile("^.+@.+(\\.[^\\.]+)+$");
		Matcher matcher = pattern.matcher(email);
		
		boolean checks = true;
		
		if(!matcher.matches())
		{
			request.setAttribute("message_email", "Please enter a valid email address.");
			checks = false;
		}
		
		
		int cmp = email.compareTo(reemail);
		if(cmp != 0)
		{
			request.setAttribute("message_remail", "Looks like these email addresses don’t match.");
			checks = false;
		}
		
		if(password.length() < 6)
		{
			request.setAttribute("message_pass", "Password is too short");
			checks = false;
		}
		
		if(checks)
		{
			//RequestDispatcher view = request.getRequestDispatcher("Registration.jsp");
			//view.forward(arg0, arg1);
			response.sendRedirect("/IndexServlet");
		}
		
		else
		{
			
	    	RequestDispatcher view = request.getRequestDispatcher("Registration.jsp");
	        view.forward(request, response);  		 
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
