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
		int plan_key = Integer.parseInt(request.getParameter("plan_key"));
		int item_key = Integer.parseInt(request.getParameter("item_key"));
		int process_key = Integer.parseInt(request.getParameter("process_key"));
		Date work_date = Date.valueOf(request.getParameter("work_date"));
		int order_qty = Integer.parseInt(request.getParameter("order_qty"));
		String order_status = request.getParameter("order_status");
		int user_key = Integer.parseInt(request.getParameter("user_key"));
		int machine_key = Integer.parseInt(request.getParameter("machine_key"));

		WorkOrderDTO dto = new WorkOrderDTO();
		dto.setWork_order_code(work_order_code);
		dto.setPlan_key(plan_key);
		dto.setItem_key(item_key);
		dto.setProcess_key(process_key);
		dto.setWork_date(work_date);
		dto.setOrder_qty(order_qty);
		dto.setOrder_status(order_status);
		dto.setUser_key(user_key);
		dto.setMachine_key(machine_key);

		WorkOrderService service = new WorkOrderService();
		int result = service.addWorkOrder(dto);
		System.out.println("result : " + result);

		response.sendRedirect("workorder");
	}

	protected void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/workorder/update doPost 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		int work_order_key = Integer.parseInt(request.getParameter("work_order_key"));
		String work_order_code = request.getParameter("work_order_code");
		int plan_key = Integer.parseInt(request.getParameter("plan_key"));
		int item_key = Integer.parseInt(request.getParameter("item_key"));
		int process_key = Integer.parseInt(request.getParameter("process_key"));
		Date work_date = Date.valueOf(request.getParameter("work_date"));
		int order_qty = Integer.parseInt(request.getParameter("order_qty"));
		String order_status = request.getParameter("order_status");
		int user_key = Integer.parseInt(request.getParameter("user_key"));
		Date created_at = Date.valueOf(request.getParameter("created_at"));
		int machine_key = Integer.parseInt(request.getParameter("machine_key"));

		try {
			WorkOrderDTO dto = new WorkOrderDTO();
			
			dto.setWork_order_key(work_order_key);
			dto.setWork_order_code(work_order_code);
			dto.setPlan_key(plan_key);
			dto.setItem_key(item_key);
			dto.setProcess_key(process_key);
			dto.setWork_date(work_date);
			dto.setOrder_qty(order_qty);
			dto.setOrder_status(order_status);
			dto.setUser_key(user_key);
			dto.setCreated_at(created_at);
			dto.setMachine_key(machine_key);

			WorkOrderService service = new WorkOrderService();
			int result = service.updateWorkOrder(dto);
			System.out.println("result : " + result);

			response.sendRedirect("workorder");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/workorder delete 실행");
		
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8;");
			
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