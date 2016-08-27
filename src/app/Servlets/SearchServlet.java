package app.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Management.Movie;
import app.Search.Search;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.servlet.RequestDispatcher;




/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
				
		try
		{
			HttpSession session = request.getSession(true);
			String type = request.getParameter("type");
			String term = request.getParameter("term");	
			
			//System.out.println("Type: " + type + " Term: " + term);
			String PARAM = (String) session.getAttribute("param");
			Search search = new Search();
			
			//User came from the index/main page
			if(type != null) {
				Connection connection = search.getConnection();
				LinkedHashMap<Integer, Movie> results;
				

				ArrayList<String> values = new ArrayList<String>();
					
				//User clicked on the Alphabet Link
				if(type.equals("letter"))
				{
					PARAM = " movies.title LIKE ?";
					values.add(term + "%");
					
				}
				//User clicked on the genre link
				else if(type.equals("genre"))
				{
					PARAM = " genres.name = ?";
					values.add(term);
				}
					
				//User used the search box;
				else
				{
					PARAM = " movies.title LIKE ?";		
					values.add("%" + term + "%");
				}
					
					
				results = search.searchMovies(PARAM, values, connection);	
				
				//Direct to results page and show results from query
				ArrayList<Movie> resultsList = new ArrayList<Movie>(results.values());
				
				//CHANGE THIS
				int LIMIT = 50;
				//CHANGE THIS
				
				int PAGE = 1;			
				int OFFSET = 0;
				
				//Store in session for future sorting
				//Default Values for Page Result limit, offset, and page No.
				session.setAttribute("limit", LIMIT);
				session.setAttribute("offset", OFFSET);
				session.setAttribute("page", PAGE);
				session.setAttribute("data", resultsList);
				
				//Set results for JSP  
				String message = "Search Results (" + resultsList.size() + " Movies Found)";
				request.setAttribute("message",message);
				request.setAttribute("results",search.movieSubList(resultsList, OFFSET, LIMIT));
				request.setAttribute("servlet", "SearchServlet");
		    	
				RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
		        view.forward(request, response);  
			}
			
			//User clicked on sorting/show/prev/next
			else
			{
				ArrayList<Movie> movies = (ArrayList<Movie>) session.getAttribute("data");			
 				
				String orderby = request.getParameter("order");
				String sortby = request.getParameter("sort");
				
				String next = request.getParameter("next");
				String prev = request.getParameter("prev");
				
				String limit = (String) request.getParameter("num");
				
				//If Movie List is empty do nothing and return
				if(movies.size() == 0)
				{
					request.setAttribute("message","Search Results (0 Movies Found)");
					RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
			        view.forward(request, response); 
				}
				
				//Get 5,10,15,20 results
				//Keep results as they were stored
				else if(limit != null)
				{
					int LIMIT = Integer.parseInt(limit);
					int OFFSET = (int) session.getAttribute("offset");
					
					ArrayList<Movie> results = search.movieSubList(movies, OFFSET, OFFSET + LIMIT);
			    	
					session.setAttribute("limit", LIMIT);
					
					request.setAttribute("results", results);
					request.setAttribute("servlet", "SearchServlet");
					
					request.setAttribute("message","Search Results");
					RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
			        view.forward(request, response); 
				
				}
				
			
				//User Click on the PREV or NEXT buttons
				else if (next != null || prev != null)
				{
					ArrayList<Movie> sublist = new ArrayList<Movie>();;
				
					int LIMIT = (int) session.getAttribute("limit");
					int OFFSET = (int) session.getAttribute("offset");
					int PAGE = (int) session.getAttribute("page");
					
					//User Clicks Next Page
					if(next != null && next.compareTo("Next") == 0)
					{
						int adjOFFSET = OFFSET + LIMIT;
						
						//Make sure that offset doesn't go over
						//otherwise use the previous offset
						if(adjOFFSET <= movies.size() )
						{
							PAGE++;	
							OFFSET = OFFSET + LIMIT;
						}
						
						sublist = search.movieSubList(movies, OFFSET, OFFSET + LIMIT);
					}
					
					//User Clicks Prev Page
					else
					{
						//Page is not 1st
						if(PAGE > 1 )
						{
							PAGE--;
							OFFSET = OFFSET - LIMIT;
							sublist = search.movieSubList(movies,OFFSET, OFFSET + LIMIT);
						
						}
						
						//Page is the 1st
						else
						{	
							PAGE = 1;
							OFFSET = 0;
							sublist = search.movieSubList(movies, OFFSET, LIMIT); 
							
						}					
					
					}
					
					session.setAttribute("offset", OFFSET);
					session.setAttribute("page", PAGE);
					
					//Set results for JSP 
					String message = "Search Results";;
					request.setAttribute("message",message);
					request.setAttribute("results", sublist);
					request.setAttribute("servlet", "SearchServlet");
			    	
					RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
			        view.forward(request, response);  
	
				}
				
				else
				{
					//User Clicked on the Sorting Links
					//Pass that information to be sorted
					
					int LIMIT = (int) session.getAttribute("limit");
					int OFFSET = (int) session.getAttribute("offset");
					
					ArrayList<Movie> sorted = search.sortResults(movies, orderby, sortby);
					ArrayList<Movie> sublist = search.movieSubList(sorted, OFFSET, OFFSET + LIMIT);
					
					//Add it back to the session sorted
					session.setAttribute("data", sorted);
			
					//Send it back to JSP to show to user							
					request.setAttribute("message","Search Results (Sorted)");
					request.setAttribute("results", sublist);
					request.setAttribute("servlet", "SearchServlet");
					RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
			        view.forward(request, response); 
				}
				
			}
			 			 
		}
		
		catch (Exception e) {
			response.getWriter().write(e.toString());
			e.printStackTrace();
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
