package app.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import app.Management.Movie;
import app.Search.Search;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
/**
 * Servlet implementation class MobileMovieSearch
 */
public class MobileMovieSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MobileMovieSearch() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String query = request.getParameter("query");
			
			Search search = new Search();
			Connection connection = search.getConnection();
			String PARAM = " movies.title LIKE '%" + query + "%'";
			LinkedHashMap<Integer, Movie> results = search.searchMovies(PARAM, null, connection);
			List<Movie> movies = new ArrayList<Movie>(results.values());
			
			if(movies.size() > 25)
			{
				movies = movies.subList(0, 25);
			}
			
			Gson gson = new Gson();
			response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().write(gson.toJson(movies));
			
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
		doGet(request, response);
	}

}
