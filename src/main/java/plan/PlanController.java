
// 파일 하나에서 처리할려다가 구조 맞출려고 controller 마다 따로 관리하기 위해서 파일삭제는 하지않고 전부 주석처리함
//package plan;
//
//import java.io.IOException;
//import java.sql.Date;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import Commoncode.CommoncodeDTO;
//import Commoncode.CommoncodeService;
//import machine.MachineDTO;
//import machine.MachineService;
//
//@WebServlet("/plan")
//public class PlanController extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		System.out.println("/plan do Get ����");
//		request.setCharacterEncoding("utf-8");
//		response.setContentType("text/html; charset=utf-8;");
//		
//		PlanService service = new PlanService();
//		List<PlanDTO> list = service.getList();
//		
//		request.setAttribute("list", list);
//		
//		request.getRequestDispatcher("/plan.jsp").forward(request, response);
//		
//		
//	}
//	
//	
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		System.out.println("/plan controller doPost ����");
//		request.setCharacterEncoding("utf-8");
//		response.setContentType("text/html; charset=utf-8;");
//
//		String cmd = request.getParameter("cmd");
//
//		if ("insert".equals(cmd)) {
//		    insert(request, response);
//		} else if ("update".equals(cmd)) {
//		    update(request, response);
//		} else if ("delete".equals(cmd)) {
//		    delete(request, response);
//		}
//	}
//	protected void insert(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		System.out.println("/plan/insert doPost ����");
//
//		// �ѱ� ó��
//		request.setCharacterEncoding("utf-8");
//		response.setContentType("text/html; charset=utf-8;");
//
//		// �Ķ���� Ȯ��
////		int plan_key = Integer.parseInt(request.getParameter("plan_key"));
//		String plan_code = request.getParameter("plan_code");
//		int item_key = Integer.parseInt(request.getParameter("item_key")); 
//		Date plan_date = Date.valueOf(request.getParameter("plan_date")); // ��ȹ �� 
//		Date due_date = Date.valueOf(request.getParameter("due_date")); // ���� ��
//		int plan_qty = Integer.parseInt(request.getParameter("plan_qty")); //  ��ȹ ���� 
//		String status = request.getParameter("status");	// ���� 
//		int user_key = Integer.parseInt(request.getParameter("user_key"));
////		Date create_at = Date.valueOf(request.getParameter("create_at")); // ���� ��
//		int priority = Integer.parseInt(request.getParameter("priority")); // �켱����
//		
//
//		// DTO�� ���
//		PlanDTO dto = new PlanDTO();
////		dto.setCreate_at(create_at);
//		dto.setDue_date(due_date);
//		dto.setItem_key(item_key);
//		dto.setPlan_code(plan_code);
//		dto.setPlan_date(plan_date);
////		dto.setPlan_key(plan_key);
//		dto.setPriority(priority);
//		dto.setStatus(status);
//		dto.setUser_key(user_key);
//		dto.setPlan_qty(plan_qty);
//		
//		
//		// service�� DTO�� ����
//		PlanService planservice = new PlanService();
//		int result = planservice.addplan(dto);
//		System.out.println("result2 : " + result);
//		response.sendRedirect("plan");
//
//		
//	}
//
//	protected void update(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		System.out.println("/plan/update doPost ����");
//
//		request.setCharacterEncoding("utf-8");
//		response.setContentType("text/html; charset=utf-8;");
//
//		// �Ķ���� Ȯ��
//		int plan_key = Integer.parseInt(request.getParameter("plan_key"));
//		String plan_code = request.getParameter("plan_code");
//		int item_key = Integer.parseInt(request.getParameter("item_key")); 
//		Date plan_date = Date.valueOf(request.getParameter("plan_date")); // ��ȹ �� 
//		Date due_date = Date.valueOf(request.getParameter("due_date")); // ���� ��
//		int plan_qty = Integer.parseInt(request.getParameter("plan_qty")); //  ��ȹ ���� 
//		String status = request.getParameter("status");	// ���� 
//		int user_key = Integer.parseInt(request.getParameter("user_key"));
//		Date create_at = Date.valueOf(request.getParameter("create_at")); // ���� ��
//		int priority = Integer.parseInt(request.getParameter("priority")); // �켱����;
//
//		try {
//			PlanDTO dto = new PlanDTO();
//			
//			dto.setCreate_at(create_at);
//			dto.setDue_date(due_date);
//			dto.setItem_key(item_key);
//			dto.setPlan_code(plan_code);
//			dto.setPlan_date(plan_date);
//			dto.setPlan_key(plan_key);
//			dto.setPriority(priority);
//			dto.setStatus(status);
//			dto.setUser_key(user_key);
//			dto.setPlan_qty(plan_qty);
//
//			// service�� DTO�� ����
//			PlanService planservice = new PlanService();
//			int result = planservice.updateplan(dto);
//			System.out.println("result2 : "+ result);
//
//		
//			response.sendRedirect("plan");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		System.out.println("/plan delete ����");
//		
//		try {
//			request.setCharacterEncoding("utf-8");
//			response.setContentType("text/html; charset=utf-8;");
//			
//			PlanDTO dto = new PlanDTO();
//			int plan_key = Integer.parseInt(request.getParameter("plan_key"));
//			dto.setPlan_key(plan_key);
//			
//			PlanService planservice = new PlanService(); 
//			int result = planservice.deleteplan(plan_key);
//			
//			response.sendRedirect("plan");
//			
//		} catch (Exception e) {
//			e.printStackTrace(); 
//		}
//	}
//
//}
