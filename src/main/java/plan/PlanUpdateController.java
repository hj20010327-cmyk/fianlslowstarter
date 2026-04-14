package plan;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.LoginDTO;
import user.UserDTO;

@WebServlet("/plan/update")
public class PlanUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("/plan/update doPost 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		HttpSession session = request.getSession();
		LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");
		
		System.out.println("loginUser = " + loginUser);

		if (loginUser == null
				|| (!"관리자".equals(loginUser.getUser_role()) && !"슈퍼바이저".equals(loginUser.getUser_role()))) {
			// 이거는 주소로 들어오는사람 막는용도
			response.sendRedirect(request.getContextPath() + "/plan");
			return;
		}

		try {
			int plan_key = Integer.parseInt(request.getParameter("plan_key"));
			String plan_code = request.getParameter("plan_code");
			int item_key = Integer.parseInt(request.getParameter("item_key"));
			Date plan_date = Date.valueOf(request.getParameter("plan_date"));
			Date due_date = Date.valueOf(request.getParameter("due_date"));
			int plan_qty = Integer.parseInt(request.getParameter("plan_qty"));
			String status = request.getParameter("status");
			int user_key = Integer.parseInt(request.getParameter("user_key"));
			int priority = Integer.parseInt(request.getParameter("priority"));

			PlanDTO dto = new PlanDTO();
			dto.setPlan_key(plan_key);
			dto.setPlan_code(plan_code);
			dto.setItem_key(item_key);
			dto.setPlan_date(plan_date);
			dto.setDue_date(due_date);
			dto.setPlan_qty(plan_qty);
			dto.setStatus(status);
			dto.setUser_key(user_key);
			dto.setPriority(priority);

			PlanService service = new PlanService();
			int result = service.updateplan(dto);

			System.out.println("plan update result : " + result);

		} catch (Exception e) {
			e.printStackTrace();
		}

		response.sendRedirect("/slowstarter/plan");
	}
}