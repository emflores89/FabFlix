package app.Filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.Management.User;

/**
 * Servlet Filter implementation class SessionFilter
 */
public class SessionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SessionFilter() {
 
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        
        //System.out.println(uri);
        
        String loginURI = req.getContextPath() + "/login.jsp";
        String employeeLoginURI =  req.getContextPath() + "/_dashboard/employeeLogin.jsp";
        String loginServlet = req.getContextPath() + "/LoginServlet";
        String loginEmployeeServlet = req.getContextPath() + "/EmployeeLoginServlet";
        String dashboard = "_dashboard";
              
        HttpSession session = req.getSession(true);
   	 	session.setMaxInactiveInterval(60*30);
	 
   	 	User session_user = (User)session.getAttribute("User");
   	 	
   	 	if(session_user == null)
   	 	{
   	 		session.setAttribute("User", new User());
   	 		session.setAttribute("auth", false);
   	 	}
 
   	 
        //request.setAttribute(arg0, arg1);
        chain.doFilter(request, response);    

        /*
        
        if (session.getAttribute("auth") != null || uri.equals(loginURI) || uri.contains("loginStyle.css")
        	|| uri.equals(loginServlet) || uri.equals(loginEmployeeServlet)
        	|| uri.equals(employeeLoginURI) || uri.contains("reports") || uri.contains("Mobile") 
        	|| uri.contains("SearchServlet") || uri.contains("img")) {
        	// pass the request along the filter chain
        	chain.doFilter(request, response);    
        }
        else
        {
        	if(uri.contains(dashboard))
        	{
        		res.sendRedirect(employeeLoginURI);
        	}
        	else
        	{
        		// pass the request along the filter chain
            	res.sendRedirect(req.getContextPath()+ "/login.jsp");
        	}
    	   
        }
       
        
        chain.doFilter(request, response);   
        
          */
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
