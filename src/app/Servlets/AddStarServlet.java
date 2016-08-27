package app.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.List;
import java.util.Map;

import app.Management.MetaData;
import app.Search.Search;

/**
 * Servlet implementation class AddStarServlet
 */
public class AddStarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStarServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		try {
			
			String first_name = request.getParameter("fname");
			String last_name = request.getParameter("lname");	
			String dob = request.getParameter("dob");
			String photo_url = request.getParameter("photo_url");

			if(first_name != null && last_name == null)
			{
				last_name = first_name;
				first_name = "";
			}
			
			if(first_name != null && first_name.trim().isEmpty()
					&& last_name == null)
			{
				last_name = first_name;
				first_name = "";
			}
			
			if(dob != null && !dob.trim().isEmpty() && !dob.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))
			{
				String message = "Invalid Date Format";
				request.setAttribute("message",message);
				RequestDispatcher view = request.getRequestDispatcher("/_dashboard/insertStar.jsp");
		        view.forward(request, response); 
		        return;
			}
						
			
			////PERFORM QUERY////
			String[] star = {first_name,last_name,dob,photo_url};
			Search search = new Search();
			Connection connection = search.getConnection();	
			
			int count = search.starExists(star, connection);		
			
			if(count == 0)
			{
				Map<String,List<MetaData>> results = search.addStar(star, connection);
				request.setAttribute("results",results);
				RequestDispatcher view = request.getRequestDispatcher("/_dashboard/metaDataPage.jsp");
		        view.forward(request, response);  		
			}
			
			else
			{
				connection.close();
				String message = "Unable to add star to database (0 Rows Affected)";
				request.setAttribute("message",message);
				RequestDispatcher view = request.getRequestDispatcher("/_dashboard/insertStar.jsp");
		        view.forward(request, response);  						
			}
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
