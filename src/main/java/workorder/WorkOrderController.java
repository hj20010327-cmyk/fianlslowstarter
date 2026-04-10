package workorder;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/workorder")
public class WorkOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/workorder do Get 실행");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		WorkOrderService service = new WorkOrderService();
		List<WorkOrderDTO> list = service.getList();
		
		request.setAttribute("list", list);
		
		request.getRequestDispatcher("/workorder.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/workorder controller doPost 실행");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		String cmd = request.getParameter("cmd");

		if ("insert".equals(cmd)) {
		    insert(request, response);
		} else if ("update".equals(cmd)) {
		    update(request, response);
		} else if ("delete".equals(cmd)) {
		    delete(request, response);
		}
	}

	protected void insert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/workorder/insert doPost 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

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
		System.out.println("insert result : " + result);

		response.sendRedirect("workorder");
	}

	

	protected void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/workorder/update doPost 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		try {
			int work_order_key = Integer.parseInt(request.getParameter("work_order_key"));
			String work_order_code = request.getParameter("work_order_code");
			int order_user_key = Integer.parseInt(request.getParameter("order_user_key"));
			int work_user_key = Integer.parseInt(request.getParameter("work_user_key"));
			int order_qty = Integer.parseInt(request.getParameter("order_qty"));
			Date work_date = Date.valueOf(request.getParameter("work_date"));
			Date created_at = Date.valueOf(request.getParameter("created_at"));
			int plan_key = Integer.parseInt(request.getParameter("plan_key"));

			WorkOrderDTO dto = new WorkOrderDTO();
			dto.setWork_order_key(work_order_key);
			dto.setWork_order_code(work_order_code);
			dto.setOrder_user_key(order_user_key);
			dto.setWork_user_key(work_user_key);
			dto.setOrder_qty(order_qty);
			dto.setWork_date(work_date);
			dto.setCreated_at(created_at);
			dto.setPlan_key(plan_key);

			WorkOrderService service = new WorkOrderService();
			int result = service.updateWorkOrder(dto);
			System.out.println("update result : " + result);

			response.sendRedirect("workorder");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/workorder delete 실행");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		try {
			
			int work_order_key = Integer.parseInt(request.getParameter("work_order_key"));
			
			WorkOrderService service = new WorkOrderService(); 
			int result = service.deleteWorkOrder(work_order_key);
			System.out.println("result : " + result);
			
			response.sendRedirect("workorder");
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
}