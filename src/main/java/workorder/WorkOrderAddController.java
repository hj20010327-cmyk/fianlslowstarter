package workorder;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/workorder/add")
public class WorkOrderAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("/workorder/add doPost 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		try {
			String work_order_code = request.getParameter("work_order_code");
			int order_user_key = Integer.parseInt(request.getParameter("order_user_key"));
			int work_user_key = Integer.parseInt(request.getParameter("work_user_key"));
			int order_qty = Integer.parseInt(request.getParameter("order_qty"));
			Date work_date = Date.valueOf(request.getParameter("work_date"));
			int plan_key = Integer.parseInt(request.getParameter("plan_key"));

			WorkOrderDTO dto = new WorkOrderDTO();
			dto.setWork_order_code(work_order_code);
			dto.setOrder_user_key(order_user_key);
			dto.setWork_user_key(work_user_key);
			dto.setOrder_qty(order_qty);
			dto.setWork_date(work_date);
			dto.setPlan_key(plan_key);

			WorkOrderService service = new WorkOrderService();
			int result = service.addWorkOrder(dto);

			System.out.println("workorder insert result : " + result);

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect("/slowstarter/workorder");
	}
}