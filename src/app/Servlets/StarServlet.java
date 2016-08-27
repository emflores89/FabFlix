package app.Servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Management.Star;
import app.Search.Search;

/**
 * Servlet implementation class StarServlet
 */
public class StarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		//Get values from href
		String first_name = request.getParameter("firstname");
		String last_name = request.getParameter("lastname");
		String starid = request.getParameter("starid");
		
		try {
			
			//Intialize Search Class
			Search search = new Search();
			Connection connection = search.getConnection();
					
			//Execute Query
			Star star = search.searchStar(first_name,last_name, starid, connection);
					
			//Direct to results page and show results from query
			request.setAttribute("star", star);
	    	RequestDispatcher view = request.getRequestDispatcher("StarResult.jsp");
	        view.forward(request, response);  
						 
		}
		
		catch(Exception e)
		{
			System.out.println(e);
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
