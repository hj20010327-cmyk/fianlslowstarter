package workorder;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/workorder")
public class WorkOrderListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("/workorder doGet 실행");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8;");

		String workOrderCode = request.getParameter("workOrderCode");
		String planKey = request.getParameter("planKey");

		String pageStr = request.getParameter("page");
		int page = 1;
		if (pageStr != null) {
			page = Integer.parseInt(pageStr);
		}

		int pageSize = 5;
		int startRow = (page - 1) * pageSize + 1;
		int endRow = page * pageSize;

		WorkOrderService service = new WorkOrderService();
		List<WorkOrderDTO> list;

		if ((workOrderCode == null || workOrderCode.isEmpty()) &&
			(planKey == null || planKey.isEmpty())) {
			list = service.selectPage(startRow, endRow);
		} else {
			list = service.searchList(workOrderCode, planKey);
		}

		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("workOrderCode", workOrderCode);
		request.setAttribute("planKey", planKey);

		request.getRequestDispatcher("/workorder.jsp").forward(request, response);
	}
}