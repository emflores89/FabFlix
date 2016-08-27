package app.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import app.Management.Movie;
import app.Search.Search;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		char[] chars= "0123456789ABCDEFGHIKLMNOPQRSTUVWXYZ".toCharArray();			
		
		try {
			Search search = new Search();
			
			Connection connection = search.getConnection();
			ArrayList<String> genres = search.getGenres(connection);
			HashSet<Movie> movies = search.getBestSellingMovies(connection);
			connection.close();
			
			request.setAttribute("sellers", movies);
			request.setAttribute("titles",chars);
			request.setAttribute("genres",genres);
			request.getRequestDispatcher("index.jsp").forward(request, response);
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
