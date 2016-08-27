package app.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import app.Management.User;
import app.Search.Search;

import java.sql.*;

/**
 * Servlet implementation class MobileLoginServlet
 */
public class MobileLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private JsonObject json = new JsonObject();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MobileLoginServlet() {
        super();
      
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			System.out.println("HERE");
			System.out.println(email);
			System.out.println(password);
			
			Search search = new Search();
			Connection connection = search.getConnection();
			User user = search.searchUser(email, password, connection);
			
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
		    	 json.addProperty("status", "success");
		    	 
		    }
			
			else
			{
				json.addProperty("status","failure");
			}
		}

		catch(Exception e)
		{
			System.out.println(e);
			json.addProperty("status","error");
			
		}
		
		finally
		{
			response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(json.toString());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
