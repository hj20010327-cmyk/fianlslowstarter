package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
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

		// pass the request along the filter chain
		request.setCharacterEncoding("utf-8");
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		String path = req.getServletPath();
		
		if(isExclude(path)) {
			
			chain.doFilter(request, response);
		} else {
			HttpSession session = req.getSession();
			String login = (String) session.getAttribute("login");
			if("Y".equals(login)) {
				chain.doFilter(request, response);
			} else {
				resp.sendRedirect(req.getContextPath() + "/login.jsp");
			}
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	private boolean isExclude (String path) {
		boolean result = false;
		
		if( path.equals("/login.jsp")
			|| path.indexOf("/asset") != -1
			|| path.startsWith("/asset")
			|| path.endsWith(".css")
			|| path.endsWith(".js")
			|| path.equals("/login")
			|| path.equals("/findpw")
			|| path.equals("/signup")
			) {
			result = true;
		}
		return result;
	}

}
