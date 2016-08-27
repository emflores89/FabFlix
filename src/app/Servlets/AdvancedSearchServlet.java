package app.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Management.Movie;
import app.Search.Search;

/**
 * Servlet implementation class AdvancedSearchServlet
 */
public class AdvancedSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdvancedSearchServlet() {
        super();
     
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
		
		HttpSession session = request.getSession(true);
		String referer = request.getHeader("Referer");
		Search search = new Search();
		
		Set<Entry<String, String[]>> map = request.getParameterMap().entrySet();
		
		//Process form or leave it as an empty string ""
		String query = "";
		ArrayList<String> paramValues = new ArrayList<String>();
		
		for (Entry<String, String[]> entry : map) {
		    String param = entry.getKey();
		    String[] values = entry.getValue();
		    String value = values[0];
		    		    
		    if(value.trim().length() != 0)
		    {
			    	if(param.equals("title")){
			    		query += " movies.title =? AND";
			    		paramValues.add(value);
			    	}		   
			    	if(param.equals("year")){
			    		query += " movies.year =? AND";
			    		paramValues.add(value);
			    	}
			    	if(param.equals("director")){
			    		query += " movies.director =? AND";
			    		paramValues.add(value);
			    	}			   
			    	if(param.equals("fname")){
			    		query += " stars.first_name =? AND";
			    		paramValues.add(value);
			    	}			   
			    	if(param.equals("lname")){
			    		query += " stars.last_name =? AND";
			    		paramValues.add(value);
			    	}	
		    }
		}
		
		////////////////MOVIE QUERY PROCESSING////////////////////
		
		//If request is empty and was sent by AdvanceSearch.jsp
		//User was searching for a movie not sorting by title etc.
		//Did not put anything in the form.
		if(query.isEmpty() && referer.contains("AdvancedSearch.jsp")){
	    	 String message = "Error: Nothing was Entered. Please try again";
	    	 request.setAttribute("message", message);
	    	 
	    	 RequestDispatcher view = request.getRequestDispatcher("AdvancedSearch.jsp");
	         view.forward(request, response);
	         
		}
		
		//User was on movie result page and clicked on sorting/next/#ofresults
		else {
			
			//User was on the movie result page and clicked Sort/Page Results
			if(query.isEmpty())
			{
				//Get Previously Stored Movie Results
				ArrayList<Movie> movies = (ArrayList<Movie>) session.getAttribute("data");
				ArrayList<Movie> results = new ArrayList<Movie>();
				
				String next = request.getParameter("next");
				String prev = request.getParameter("prev");
				
				String limit = (String) request.getParameter("num");
				
				//Get 5,10,15,20 results
				//Keep results as they were stored in session
				if(limit != null)
				{
					int LIMIT = Integer.parseInt(limit);
					int OFFSET = (int) session.getAttribute("offset");
					
					results = search.movieSubList(movies, OFFSET, OFFSET + LIMIT);
					
					request.setAttribute("results", results);
					request.setAttribute("servlet", "AdvancedSearchServlet");
			    	
					session.setAttribute("limit", LIMIT);
					session.setAttribute("offset", OFFSET);
					
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
					request.setAttribute("servlet", "AdvancedSearchServlet");;
			    	
					RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
			        view.forward(request, response);  
	
				}
				
				else
				{
					//User Clicked on the Sorting Links
					
					String orderby = request.getParameter("order");
					String sortby = request.getParameter("sort");
					
					int LIMIT = (int) session.getAttribute("limit");
					int OFFSET = (int) session.getAttribute("offset");
		
					//Pass that information to be sorted
					ArrayList<Movie> sorted = search.sortResults(movies, orderby, sortby);
					ArrayList<Movie> sublist = search.movieSubList(sorted, OFFSET, OFFSET + LIMIT);
					
					//Add it back to the session
					session.setAttribute("results", sorted);
					
					//Send it back to JSP to show to user
					request.setAttribute("message","Search Results (Sorted)");
					request.setAttribute("results", sublist);
					request.setAttribute("servlet", "AdvancedSearchServlet");
					RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
			        view.forward(request, response); 
				}
			
			}
			
			//Forward to original request (i.e User was on AdvancedSearch.jsp)
			//and submitted a search request
			else
			{
				//Remove last AND fron query string
				query = query.substring(0, query.lastIndexOf(" "));
				
				//Create and return a connection
				Connection connection = search.getConnection();
				
				//Execute Query
				LinkedHashMap<Integer, Movie> films;
				if(paramValues.isEmpty())
				{
					films = search.searchMovies(query, null, connection);	
				}
				else
				{				
					films = search.searchMovies(query, paramValues, connection);
				}
			
				//Direct to results page and show results from query
				ArrayList<Movie> resultsList = new ArrayList<Movie>(films.values());
				session.setAttribute("data", resultsList);
				
				//Set results for JSP  
				int LIMIT = 5;
				int OFFSET = 0;	
				int PAGE = 1;			

				//Default Values for Page Result limit, offset, and page No.
				session.setAttribute("limit", LIMIT);
				session.setAttribute("offset", OFFSET);
				session.setAttribute("page", PAGE);
				session.setAttribute("data", resultsList);
				
				
				String message = "Search Results (" + resultsList.size() + " Movies Found)";
				request.setAttribute("message",message);
				request.setAttribute("results", search.movieSubList(resultsList,OFFSET, LIMIT));
				request.setAttribute("servlet", "AdvancedSearchServlet");
		    	
				RequestDispatcher view = request.getRequestDispatcher("SearchResults.jsp");
		        view.forward(request, response);  
							
			}
								
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
