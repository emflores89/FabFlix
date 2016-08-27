package app.Servlets;

import java.io.IOException;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.sql.*;
import java.util.LinkedHashMap;


import app.Management.Movie;
import app.Management.Star;
import app.Search.Search;

/**
 * Servlet implementation class MoviePopUpWindow
 */
public class MoviePopUpWindow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoviePopUpWindow() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String movieid = request.getParameter("movieid");
			
			Search search = new Search();
			Connection connection = search.getConnection();
			LinkedHashMap<Integer, Movie> result = search.searchMovie(movieid, "", connection);
			
			Movie movie = result.get(Integer.parseInt(movieid));
		
			//Keep Might need this
			Gson gson = new Gson();
			gson.toJson(movie); 	
			
			String starsout = "";
			int count = 0;
			for(Star star: movie.getStars())
			{
				if(count == movie.getStars().size() - 1)
				{
					starsout += star.getName();
				}
				else
				{
					starsout += star.getName() +", ";
				}
				count++;
			}
			
			String out = "";
			out += "<table>";
			out += "<tr><td>Movie ID: " + movie.getMovieid() + "</td></tr>";
			out += "<tr><td>Title: " + movie.getTitle() + "</td></tr>";
			out += "<tr><td>Year: " + movie.getYear() + "</td></tr>";
			out += "<tr><td>Director: " + movie.getDirector() + "</td></tr>";
			out += "<tr><td>Banner URL: <a href="+ movie.getBanner_url() +">Banner Link</a></td></tr>";
			out += "<tr><td>Trailer URL: <a href="+ movie.getTrailer_url()+">Trailer Link</a></td></tr>";
			out += "<tr><td>Stars: " + starsout + "</td></tr>";
			out += "</table>";
			
			

            response.setStatus(200);
            response.getWriter().write(out);
            
            
		}
		
		catch(Exception e)
		{
			System.out.println("Error: " + e.toString());
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
