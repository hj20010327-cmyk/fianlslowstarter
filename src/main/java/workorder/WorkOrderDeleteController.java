package workorder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/workorder/delete")
public class WorkOrderDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("/workorder/delete doPost 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		try {
			String[] workOrderKeys = request.getParameterValues("work_order_key");

			if (workOrderKeys != null) {
				WorkOrderService service = new WorkOrderService();

				for (String key : workOrderKeys) {
					service.deleteWorkOrder(Integer.parseInt(key));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect("/slowstarter/workorder");
	}
}