package dashboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DaschboardController
 */
@WebServlet("/index")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DashboardService service = new DashboardService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 request.setCharacterEncoding("utf-8");

	        HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("dto") == null) {
	            response.sendRedirect(request.getContextPath() + "/login.jsp");
	            return;
	        }

	        DashboardDTO dashboard = service.getDashboardData();
	        request.setAttribute("dashboard", dashboard);

	        request.getRequestDispatcher("/index.jsp").forward(request, response);
		
	}


}
