package app.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.Management.Movie;
import app.Search.Search;

/**
 * Servlet implementation class MovieServlet
 */
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieServlet() {
        super();
        // TODO Auto-generated constructor stub
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	
		try 
		{
			String title = request.getParameter("title");
			String movieid = request.getParameter("movieid");
			
			//Intialize Search Class
			Search search = new Search();
			Connection connection = search.getConnection();
			
			//Execute Query
			LinkedHashMap<Integer, Movie> results = search.searchMovie(movieid,title,connection);
			 
			//Direct to results page and show results from query
			ArrayList<Movie> resultsList = new ArrayList<Movie>(results.values());
			request.setAttribute("results", resultsList);
	    	RequestDispatcher view = request.getRequestDispatcher("MovieResult.jsp");
	        view.forward(request, response);  
			 		 
		}
		
		catch (Exception e) {
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

