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
 * Servlet implementation class MetaDataServlet
 */
public class MetaDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MetaDataServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		try {
			
			Search search = new Search();
			Connection connection = search.getConnection();
			
			Map<String,List<MetaData>> metadata = search.getMetaData(connection);
			
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
