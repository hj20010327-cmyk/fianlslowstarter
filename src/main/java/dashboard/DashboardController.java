package dashboard;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.LoginDTO;

@WebServlet("/index")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    DashboardService service = new DashboardService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("dto") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");

        DashboardDTO dashboard = service.getDashboard(loginUser);
        request.setAttribute("dashboard", dashboard);

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }


}
