package plan;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/plan/delete")
public class PlanDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("/plan/delete doPost 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		try {
			String[] planKeys = request.getParameterValues("plan_key");

			if (planKeys != null) {
				PlanService service = new PlanService();

				for (String key : planKeys) {
					service.deleteplan(Integer.parseInt(key));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect("/slowstarter/plan");
	}
}