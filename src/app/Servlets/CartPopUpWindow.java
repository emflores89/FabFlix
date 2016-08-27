package app.Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Management.Cart;
import app.Management.Movie;
import app.Management.User;
import app.Search.Search;

/**
 * Servlet implementation class CartPopUpWindow
 */
public class CartPopUpWindow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartPopUpWindow() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String movieid = request.getParameter("movieid");
			
			HttpSession session = request.getSession(true);
			User user = (User) session.getAttribute("User");
			Cart cart = user.getCart();
			
			Search search = new Search();
			Connection connection = search.getConnection();
			
			LinkedHashMap<Integer, Movie> results = search.searchMovie(movieid, "", connection);	
			Movie movie = results.get(Integer.parseInt(movieid));
			
			//Add movie to users cart
			cart.addItem(movie);
			user.setCart(cart);
			session.setAttribute("User", user);
			
			HashMap<Movie,Integer> items = cart.getItems();
			
			double movie_total = movie.getPrice() * items.get(movie);
			double round_total = Math.round(movie_total * 100.0) / 100.0;
			
			String out = "";
			out += "<h3>Movie Added To Cart</h3>";
			out += "<table border='1'>";
			out += "<tr><th>Title</th> <th>Qty</th> <th>Price</th> <th>Total</th>  </tr>";
			out += "<tr><td>" + movie.getTitle()  + "</td> <td>" + items.get(movie) + "</td> <td>" + movie.getPrice() + "</td> <td>" + round_total + "</td> </tr>";
			out += "</table>";
			out += "<button class='.checkouttrigger'type='submit'>Add To Cart</button>";
			
			
			//for (Map.Entry<Movie, Integer> entry : items.entrySet()) {
			  //  Movie currrent_movie = entry.getKey();
			  //  Integer qty = entry.getValue();
			//	out += "<tr>"
			  //  out += "<h2>Movie Added To Cart: </h2>";
			 //   out +="<th>Firstname</th> <th>Lastname</th>";
			//	out += "<tr><td>Title: " + movie.getTitle()  + "</td></tr>";
			//	out += "<tr><td>Qty: " + 1  + "</td></tr>";
			    
			//}
			//out += "</table>";
			
	        response.setStatus(200);
	        response.getWriter().write(out);
			
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
