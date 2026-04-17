package workorder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.LoginDTO;
import user.UserDTO;

@WebServlet("/workorder/delete")
public class WorkOrderDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("/workorder/delete doPost 실행");

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");
		
		HttpSession session = request.getSession();
		LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");
		
//		System.out.println("loginUser = " + loginUser);

		if (loginUser == null
				|| (!"관리자".equals(loginUser.getUser_role()) && !"슈퍼바이저".equals(loginUser.getUser_role()))) {
			// 이거는 주소로 들어오는사람 막는용도
			response.sendRedirect(request.getContextPath() + "/workorder");
			return;
		}

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