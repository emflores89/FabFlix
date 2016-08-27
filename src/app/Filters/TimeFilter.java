package app.Filters;

import java.io.IOException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.*;

import app.Management.MyConstants;

/**
 * Servlet Filter implementation class TimeFilter
 */
public class TimeFilter implements Filter {

    /**
     * Default constructor. 
     */
    public TimeFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        
        if(uri.contains("SearchServlet"))
        {
            long startTime = System.nanoTime();
            chain.doFilter(request, response);
            long endTime = System.nanoTime();
            
            long elapsedTime = endTime - startTime; // elapsed time in nano seconds. Note: print the values in nano seconds  	
        	
           // System.out.println("####" + MyConstants.JDBC + " "+ elapsedTime);
            Logger.getLogger (TimeFilter.class.getName()).log(Level.INFO, "#### " + MyConstants.JDBC + " "+ elapsedTime);
            
        }
        else
        {
    		// pass the request along the filter chain
    		chain.doFilter(request, response);
        }
	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
