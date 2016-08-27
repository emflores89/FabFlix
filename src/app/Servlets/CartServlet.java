package app.Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.LinkedHashMap;

import app.Management.Cart;
import app.Management.Movie;
import app.Management.User;
import app.Search.Search;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String title = request.getParameter("title");
		String id = request.getParameter("movieid");
		
		String action = request.getParameter("action");
		String quantity = request.getParameter("quantity");
		
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("User");
		Cart cart = user.getCart();
	
		
		try {
		//User wants to add movie to cart.
		if(action != null)
		{
			if (action.equals("add"))
			{
				Search search = new Search();
				Connection connection = search.getConnection();
				LinkedHashMap<Integer,Movie> results = search.searchMovie(id, title, connection);
				
				//Add movie to users cart
				int movieid = Integer.parseInt(id);
				cart.addItem(results.get(movieid));
			}
			
			else if (action.equals("Checkout")) 
			{
				if(cart.getItems().size() == 0)
				{
					request.setAttribute("message", "Error: Cannot Checkout without any items in cart.");
				}
				
				else
				{
					RequestDispatcher view = request.getRequestDispatcher("Checkout.jsp");
					view.forward(request, response);  
					//Exit out of this servlet
					 return;
				}
			
			 } 
			 
			else if (action.equals("Empty Cart"))
			{
				 cart.emptyCart();
			} 
			
			
			else {
				//Update button was pressed
				int number_of_items = Integer.parseInt(quantity);
				Movie movie = new Movie();			
				
				movie.setMovieId(id);
				movie.setTitle(title);
				
				cart.setQuantity(movie, number_of_items);
			}
			
		}
				
		user.setCart(cart);
		session.setAttribute("User", user);
		
		request.setAttribute("results", cart.getItems());
		request.setAttribute("total", cart.calculate_total());
    	RequestDispatcher view = request.getRequestDispatcher("Cart.jsp");
        view.forward(request, response);  
        
		}
		
		catch (Exception e)
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
