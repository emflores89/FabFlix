package app.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import java.sql.*;
import java.util.List;

import app.Search.Search;

/**
 * Servlet implementation class MovieAutoComplete
 */
public class MovieAutoComplete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieAutoComplete() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			
			String query = request.getParameter("term");
			
			String[] tokens = query.split("\\s+");
			
			Search search = new Search();
			Connection connection = search.getConnection();
			List<String> titles = search.autoCompleteMovies(tokens, connection);
			
			String gsonTitles = new Gson().toJson(titles);
            response.getWriter().write(gsonTitles);
			
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
