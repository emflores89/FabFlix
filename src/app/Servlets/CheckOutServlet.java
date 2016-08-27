package app.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.util.Map;

import app.Management.Cart;
import app.Management.Movie;
import app.Management.User;
import app.Search.Search;

/**
 * Servlet implementation class CheckOutServlet
 */
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOutServlet() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			
			//Set database connection
			Search search = new Search();
			Connection connection = search.getConnection();
			
			//Read Fields
	        String cc_id = request.getParameter("creditcard");
	        String expires = request.getParameter("expire");
	        String first_name = request.getParameter("fname");
	        String last_name = request.getParameter("lname");
	        
    		HttpSession session = request.getSession(true);
    		User user = (User) session.getAttribute("User");
    		Cart cart = user.getCart();

	        int status = search.checkOutAuthentication(cc_id, first_name, last_name, expires, connection);
			
	      
	        //If result is less than 1(false) 
	        //Information entered was not valid
	        if(status < 1) {
	        	
	        	String message = "Error: Record was not found in database";
		    	request.setAttribute("message", message);
		    	 
		    	RequestDispatcher view = request.getRequestDispatcher("Checkout.jsp");
		        view.forward(request, response);  
	        }
	        
	        else {
	    			    		
	    		int sold_status = 1;
	    		outerloop:
	    		for(Map.Entry<Movie,Integer> item: cart.getItems().entrySet())
	    		{
	    			Movie movie = item.getKey();
	    			int quantity = item.getValue();
	    			
	    			while(quantity != 0)
	    			{	
	    				//System.out.println(quantity);
	    				connection = search.getConnection();
	    				sold_status = search.recordSale(user.getid(), movie.getMovieid(), connection);
	    			
	    				//Database was unable to insert sale. Breakout of loop and inform user.
	    				if(sold_status == 0)
	    				{
	    					break outerloop;
	    				}
	     				
	    				quantity = quantity - 1;
	    				cart.setQuantity(movie, quantity); 				
	    			}
	   
	    		}
	    		
	    		user.setCart(cart);
	    		request.setAttribute("User", user);
	    		
	    		if(sold_status == 0)
	    		{
	    			String stat = "Order Status: Problem With Order";
	    			request.setAttribute("order_status", stat);
	    			String message = "Error: Was unable to process some of the items. Your cart has been updated according to item(s)"
	    					+ " that have to been processed. Please Try Again.";
			    	request.setAttribute("message", message);
			    	RequestDispatcher view = request.getRequestDispatcher("Confirmation.jsp");
			    	view.forward(request, response);  
	    			
	    		}
	    		
	    		else
	    		{
	    			String stat = "Order Status: Processed";
	    			request.setAttribute("order_status", stat);
	    			String message = "An email will be sent to " + user.getEmail() + " for your personal records.";
	    			message += "Thank you for shopping at FabFlix.";
			    	request.setAttribute("message", message);
			    	 
			    	RequestDispatcher view = request.getRequestDispatcher("Confirmation.jsp");
			        view.forward(request, response);  
		        }
	        }
		}
		
		catch(Exception e) {
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
