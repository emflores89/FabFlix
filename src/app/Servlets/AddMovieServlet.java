package app.Servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;

import app.Management.MetaData;
import app.Search.Search;

/**
 * Servlet implementation class AddMovieServlet
 */
public class AddMovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMovieServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//Required Fields NOT NULL
			String title = request.getParameter("title");
			String year = request.getParameter("year");
			String director = request.getParameter("director");
			String name = request.getParameter("name");
			String genre = request.getParameter("genre_name");
			
			
			//Possible Not Needed according to Project Requirements
			//But can add later
			String banner_url = request.getParameter("banner_url");
			String trailer_url = request.getParameter("trailer_url");
			
			String dob = request.getParameter("dob");
			String photo_url = request.getParameter("photo_url");
			
			String first_name = "";
			String last_name = "";
			
			String[] name_split = name.split("\\s+");
			
			if(name_split.length < 2)
			{
				last_name = name_split[0];
			}
			else
			{
				first_name = name_split[0];
				last_name = name_split[1];
			}
			
			if(!dob.isEmpty() && !dob.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))
			{
				String message = "Invalid Date Format";
				request.setAttribute("message",message);
				RequestDispatcher view = request.getRequestDispatcher("/_dashboard/addMovie.jsp");
		        view.forward(request, response); 
		        return;
			}
			
			try {
				Integer.parseInt(year);
			}
			
			catch(NumberFormatException e)
			{
				String message = "Invalid Year Entered";
				request.setAttribute("message",message);
				RequestDispatcher view = request.getRequestDispatcher("/_dashboard/addMovie.jsp");
		        view.forward(request, response); 
		        return;
			
			}
			

			
			Search search = new Search();
			Connection connection = search.getConnection();	
					
			Map<String,List<MetaData>> metadata = 
					search.addMovie(title, year, director, banner_url, trailer_url, genre, first_name, last_name, dob, photo_url, connection);
			
			request.setAttribute("results",metadata);
			RequestDispatcher view = request.getRequestDispatcher("/_dashboard/metaDataPage.jsp");
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

		doGet(request, response);
	}

}