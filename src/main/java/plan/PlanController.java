package plan;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Commoncode.CommoncodeDTO;
import Commoncode.CommoncodeService;

@WebServlet("/plan")
public class PlanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	public PlanController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
	}
	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/plan controller doPost 실행");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		String cmd = request.getParameter("cmd");

		if (cmd.equals("insert")) {
			insert(request, response);
		} else if (cmd.equals("update")) {
			update(request, response);
		} else if (cmd.equals("delete")) {
			delete(request, response);
		}
	}
	protected void insert(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/plan/insert doPost 실행");

		// 한글 처리
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		// 파라메터 확보
		int plan_key = Integer.parseInt(request.getParameter("plan_key"));
		String plan_code = request.getParameter("plan_code");
		int item_key = Integer.parseInt(request.getParameter("item_key")); 
		Date plan_date = Date.valueOf(request.getParameter("plan_date")); // 계획 일 
		Date due_date = Date.valueOf(request.getParameter("due_date")); // 마감 일
		int plan_qty = Integer.parseInt(request.getParameter("plan_qty")); //  계획 수량 
		String status = request.getParameter("status");	// 상태 
		int user_key = Integer.parseInt(request.getParameter("user_key"));
		Date create_at = Date.valueOf(request.getParameter("create_at")); // 생성 일
		int priority = Integer.parseInt(request.getParameter("priority")); // 우선순위
		

		// DTO에 담기
		PlanDTO dto = new PlanDTO();
		dto.setCreate_at(create_at);
		dto.setDue_date(due_date);
		dto.setItem_key(item_key);
		dto.setPlan_code(plan_code);
		dto.setPlan_date(plan_date);
		dto.setPlan_key(plan_key);
		dto.setPriority(priority);
		dto.setStatus(status);
		dto.setUser_key(user_key);
		dto.setPlan_qty(plan_qty);
		
		
		// service로 DTO를 보냄
		PlanService planservice = new PlanService();
//		int result = planservice.addplan(dto);
//		System.out.println("result2 : " + result);

		
	}

	protected void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/plan/update doPost 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		// 파라메터 확보
		int plan_key = Integer.parseInt(request.getParameter("plan_key"));
		String plan_code = request.getParameter("plan_code");
		int item_key = Integer.parseInt(request.getParameter("item_key")); 
		Date plan_date = Date.valueOf(request.getParameter("plan_date")); // 계획 일 
		Date due_date = Date.valueOf(request.getParameter("due_date")); // 마감 일
		int plan_qty = Integer.parseInt(request.getParameter("plan_qty")); //  계획 수량 
		String status = request.getParameter("status");	// 상태 
		int user_key = Integer.parseInt(request.getParameter("user_key"));
		Date create_at = Date.valueOf(request.getParameter("create_at")); // 생성 일
		int priority = Integer.parseInt(request.getParameter("priority")); // 우선순위;

		try {
			PlanDTO dto = new PlanDTO();
			
			dto.setCreate_at(create_at);
			dto.setDue_date(due_date);
			dto.setItem_key(item_key);
			dto.setPlan_code(plan_code);
			dto.setPlan_date(plan_date);
			dto.setPlan_key(plan_key);
			dto.setPriority(priority);
			dto.setStatus(status);
			dto.setUser_key(user_key);
			dto.setPlan_qty(plan_qty);

			// service로 DTO를 보냄
			PlanService planservice = new PlanService();
//			int result = planservice.updateplan(dto);
//			System.out.println("result2 : "+ result);

		
			response.sendRedirect("");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/plan delete 실행");
		
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8;");
			
			PlanDTO dto = new PlanDTO();
			int plan_key = Integer.parseInt(request.getParameter("plan_key"));
			dto.setPlan_key(plan_key);
			
			PlanService planservice = new PlanService(); 
//			int result = planservice.deletePlan(dto);
			
			response.sendRedirect("");
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

}
